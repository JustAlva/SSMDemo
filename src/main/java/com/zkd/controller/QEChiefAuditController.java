package com.zkd.controller;

import com.zkd.common.bean.back.ReturnDataBean;
import com.zkd.common.constant.MsgConstant;
import com.zkd.service.IQEAuditService;
import com.zkd.service.IQEChiefAuditService;
import com.zkd.utils.EncryptUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/qeChiefAudit")
@Controller
public class QEChiefAuditController {

    @Autowired
    IQEChiefAuditService qeChiefAuditService;

    @ResponseBody
    @RequestMapping(value = "/load", method = RequestMethod.POST)
    public String load(@RequestParam("data") String data) {
        return qeChiefAuditService.load(data);
    }

    @ResponseBody
    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    public String submit(@RequestParam("data") String data) {
        try {
            return qeChiefAuditService.submit(data);
        } catch (Exception e) {
            return new EncryptUtils<>().encryptObj(new ReturnDataBean<>(MsgConstant.CODE_ERROR, e.toString(), MsgConstant.MSG_FAIL));
        }
    }
}
