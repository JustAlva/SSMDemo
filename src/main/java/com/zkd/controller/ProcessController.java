package com.zkd.controller;

import com.zkd.service.IProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("process")
public class ProcessController {

    @Autowired
    IProcessService processService ;


    @ResponseBody
    @RequestMapping(value = "all",method = RequestMethod.GET)
    public String getAllProcess(HttpServletRequest request, Model model) throws Exception {
        return processService.getAllProcess();
    }


}