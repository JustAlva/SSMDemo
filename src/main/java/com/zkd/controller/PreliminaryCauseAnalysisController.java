package com.zkd.controller;

import com.zkd.common.bean.back.ReturnDataBean;
import com.zkd.common.constant.MsgConstant;
import com.zkd.service.IPreliminaryCauseAnalysisService;
import com.zkd.utils.EncryptUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/preliminary")
public class PreliminaryCauseAnalysisController {

    @Autowired
    IPreliminaryCauseAnalysisService preliminaryCauseAnalysisService;

    @ResponseBody
    @RequestMapping(value = "/load", method = RequestMethod.POST)
    public String loadData(@RequestParam("data") String data) {
        return preliminaryCauseAnalysisService.load(data);
    }

    @ResponseBody
    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    public String submitData(@RequestParam("data") String data) {
        try {
            return preliminaryCauseAnalysisService.submit(data);
        } catch (Exception e) {
            return new EncryptUtils<>().encryptObj(new ReturnDataBean<>(MsgConstant.CODE_ERROR, e.toString(), MsgConstant.MSG_FAIL));
        }
    }

}
