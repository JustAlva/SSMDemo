package com.zkd.controller;

import com.zkd.service.IRootCauseAnalysisService;
import com.zkd.service.impl.RootCauseAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/rootCause")
public class RootCauseAnalysisController {

    @Autowired
    IRootCauseAnalysisService rootCauseAnalysisService;

    @ResponseBody
    @RequestMapping(value = "/load",method = RequestMethod.POST)
    public String load(@RequestParam("data") String data) {
       return rootCauseAnalysisService.load(data);
    }


    @ResponseBody
    @RequestMapping(value = "/submit",method = RequestMethod.POST)
    public String submit(@RequestParam("data") String data) {
        return rootCauseAnalysisService.submit(data);
    }
}
