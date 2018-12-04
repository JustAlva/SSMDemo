package com.zkd.controller;

import com.zkd.common.bean.back.ReturnDataBean;
import com.zkd.common.constant.MsgConstant;
import com.zkd.service.IMeasureVerificationService;
import com.zkd.utils.EncryptUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/measureVerification")
public class MeasureVerificationController {

    @Autowired
    IMeasureVerificationService measureVerificationService;

    @ResponseBody
    @RequestMapping(value = "/load", method = RequestMethod.POST)
    public String load(@RequestParam("data") String data) {
        try {
            return measureVerificationService.load(data);
        } catch (Exception e) {
            return new EncryptUtils<>().encryptObj(new ReturnDataBean<>(MsgConstant.CODE_ERROR, "", e.toString()));
        }
    }


    @ResponseBody
    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    public String submit(HttpServletRequest request, @RequestParam("data") String data, @RequestParam("file") List<MultipartFile> files) {
        try {
            String basePath = request.getSession().getServletContext().getRealPath("/upload");
            String urlPath = request.getRequestURL().toString().replaceAll(request.getServletPath(), "");
            return measureVerificationService.submit(data, files, basePath, urlPath);
        } catch (Exception e) {
            return new EncryptUtils<>().encryptObj(new ReturnDataBean<>(MsgConstant.CODE_ERROR, e.getMessage(), MsgConstant.COMMON_SAVE_FAIL));
        }

    }
}
