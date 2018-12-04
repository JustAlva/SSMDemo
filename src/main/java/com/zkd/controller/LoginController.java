package com.zkd.controller;

import com.zkd.service.ILoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Controller
@RequestMapping("")
public class LoginController {

    @Autowired
    ILoginService loginService;

    @ResponseBody
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public String login(HttpServletRequest request, Model model, @RequestParam("data") String data) throws Exception {
        return loginService.checkUserLogin(data);
    }


    @ResponseBody
    @RequestMapping(value = "test", method = RequestMethod.GET)
    public String mytest(HttpServletRequest request, Model model) throws Exception {
        //Thread.sleep(1000);
        String path = request.getRequestURI();
        return "test";
    }

    @ResponseBody
    @RequestMapping(value = "rootLogin", method = RequestMethod.POST)
    public String rootLogin(@RequestParam("data")String data  ) {
        return loginService.rootLogin(data);
    }



}
