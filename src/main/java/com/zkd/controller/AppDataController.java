package com.zkd.controller;

import com.zkd.service.IAppDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("app")
public class AppDataController {
    @Autowired
    IAppDataService appDataService;

    @ResponseBody
    @RequestMapping(value = "load", method = RequestMethod.GET)
    public String getVersionData() {
        return appDataService.getVersionData();
    }


}
