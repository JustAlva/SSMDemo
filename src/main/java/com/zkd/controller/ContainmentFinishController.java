package com.zkd.controller;

import com.zkd.common.bean.back.ReturnDataBean;
import com.zkd.common.constant.MsgConstant;
import com.zkd.service.IContainmentFinishService;
import com.zkd.service.impl.ContainmentFinishService;
import com.zkd.utils.EncryptUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/containmentFinish")
public class ContainmentFinishController {

    @Autowired
    IContainmentFinishService containmentFinishService;

    @ResponseBody
    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    public String submit(@RequestParam("data") String data) {
        try {
            return containmentFinishService.submit(data);
        } catch (Exception e) {
            return new EncryptUtils<>().encryptObj(new ReturnDataBean<>(MsgConstant.CODE_ERROR, "", e.toString()));
        }
    }

}
