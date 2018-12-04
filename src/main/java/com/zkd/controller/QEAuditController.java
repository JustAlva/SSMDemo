package com.zkd.controller;

import com.zkd.service.IQEAuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/qeAudit")
@Controller
public class QEAuditController {

    @Autowired
    IQEAuditService qeAuditService;

    @ResponseBody
    @RequestMapping(value = "/load", method = RequestMethod.POST)
    public String load(@RequestParam("data") String data) {
        return qeAuditService.load(data);
    }
    @ResponseBody
    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    public String submit(@RequestParam("data") String data) {
        return qeAuditService.submit(data);
    }
}
