package com.zkd.controller;

import com.zkd.common.bean.back.ReturnDataBean;
import com.zkd.common.constant.MsgConstant;
import com.zkd.service.ICommissionerFillService;
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
@RequestMapping("/commissionerfill")
public class CommissionerFillController {
    @Autowired
    ICommissionerFillService commissionerFillService;

    @ResponseBody
    @RequestMapping(value = "/load", method = RequestMethod.GET)
    public String getData()  {
        return commissionerFillService.getData();
    }

    @ResponseBody
    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public String addNew(HttpServletRequest request, @RequestParam("data") String data, @RequestParam("file") List<MultipartFile> files) {
        try {
            String basePath = request.getSession().getServletContext().getRealPath("/upload");
            String urlPath = request.getRequestURL().toString().replaceAll(request.getServletPath(), "");
            return commissionerFillService.addNew(data, files, basePath, urlPath);
        } catch (Exception e) {
            return new EncryptUtils<>().encryptObj(new ReturnDataBean<>(MsgConstant.CODE_FAIL, e.getMessage(), MsgConstant.COMMON_SAVE_FAIL));
        }
    }
}
