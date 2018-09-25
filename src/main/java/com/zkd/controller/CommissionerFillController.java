package com.zkd.controller;

import com.zkd.service.ICommissionerFillService;
import com.zkd.service.impl.CommissionerFillService;
import com.zkd.utils.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/commissionerfill")
public class CommissionerFillController {
    @Autowired
    ICommissionerFillService commissionerFillService;

    @ResponseBody
    @RequestMapping(value = "/load", method = RequestMethod.GET)
    public String getData() throws Exception {
        return commissionerFillService.getData();
    }

    @ResponseBody
    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public String addNew(HttpServletRequest request, @RequestParam("data") String data, @RequestParam("file") List<MultipartFile> files) {
        String basePath = request.getSession().getServletContext().getRealPath("/upload");
        return commissionerFillService.addNew(data, files, basePath);
    }
}
