package com.zkd.controller;

import com.zkd.common.bean.back.ReturnDataBean;
import com.zkd.common.constant.MsgConstant;
import com.zkd.service.IDepartmentAuditService;
import com.zkd.utils.EncryptUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/departmentAudit")
public class DepartmentAuditController {
    @Autowired
    IDepartmentAuditService departmentAuditService;

    @ResponseBody
    @RequestMapping(value = "/load",method = RequestMethod.POST)
    public String load(@RequestParam("data") String data) {
        try {
            return departmentAuditService.load(data);
        } catch (Exception e) {
            return new EncryptUtils<>().encryptObj(new ReturnDataBean<>(MsgConstant.CODE_ERROR, e.toString(), MsgConstant.MSG_FAIL));
        }
    }

    @ResponseBody
    @RequestMapping(value = "/submit",method = RequestMethod.POST)
    public String submit(@RequestParam("data") String data) {
        try {
            return departmentAuditService.submit(data);
        } catch (Exception e) {
            return new EncryptUtils<>().encryptObj(new ReturnDataBean<>(MsgConstant.CODE_ERROR, e.toString(), MsgConstant.MSG_FAIL));
        }
    }

    @ResponseBody
    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    public String getDetail(@RequestParam("data") String data) {
        try {
            return  departmentAuditService.getDetail(data);
        } catch (Exception e) {
            return new EncryptUtils<>().encryptObj(new ReturnDataBean<>(MsgConstant.CODE_FAIL, e.getMessage(), MsgConstant.MSG_ERROR));
        }
    }
}
