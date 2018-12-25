package com.zkd.controller;

import com.zkd.common.bean.back.ReturnDataBean;
import com.zkd.common.constant.MsgConstant;
import com.zkd.service.IProcessService;
import com.zkd.utils.EncryptUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/process")
public class ProcessController {

    @Autowired
    IProcessService processService ;


    @ResponseBody
    @RequestMapping(value = "/all",method = RequestMethod.GET)
    public String getAllProcess(HttpServletRequest request, Model model)  {
        return processService.getAllProcess();
    }

    @ResponseBody
    @RequestMapping(value = "/totalDetail",method = RequestMethod.POST)
    public String getProcessAllDetail( @RequestParam("data") String data)  {
        try {
            return processService.getProcessAllDetail(data);
        } catch (Exception e) {
            return new EncryptUtils<>().encryptObj(new ReturnDataBean<>(MsgConstant.CODE_ERROR, e.toString(), MsgConstant.MSG_ERROR));
        }
    }

    @ResponseBody
    @RequestMapping(value = "/deal",method = RequestMethod.POST)
    public String getDealProcess(@RequestParam("data") String data) {
        return processService.getDealProcess(data);
    }

    @ResponseBody
    @RequestMapping(value = "/finished",method = RequestMethod.POST)
    public String getFinishedProcess(@RequestParam("data") String data) {
        return processService.getFinishedProcess(data);
    }

    @ResponseBody
    @RequestMapping(value = "/search/all",method = RequestMethod.POST)
    public String search( @RequestParam("data") String data)  {
        try {
            return processService.search(data);
        } catch (Exception e) {
            return new EncryptUtils<>().encryptObj(new ReturnDataBean<>(MsgConstant.CODE_ERROR, e.toString(), MsgConstant.MSG_ERROR));
        }
    }

    @ResponseBody
    @RequestMapping(value = "/search/deal",method = RequestMethod.POST)
    public String searchDeal( @RequestParam("data") String data)  {
        try {
            return processService.searchDeal(data);
        } catch (Exception e) {
            return new EncryptUtils<>().encryptObj(new ReturnDataBean<>(MsgConstant.CODE_ERROR, e.toString(), MsgConstant.MSG_ERROR));
        }
    }

    @ResponseBody
    @RequestMapping(value = "/search/finished",method = RequestMethod.POST)
    public String searchFinished( @RequestParam("data") String data)  {
        try {
            return processService.searchFinished(data);
        } catch (Exception e) {
            return new EncryptUtils<>().encryptObj(new ReturnDataBean<>(MsgConstant.CODE_ERROR, e.toString(), MsgConstant.MSG_ERROR));
        }
    }

}
