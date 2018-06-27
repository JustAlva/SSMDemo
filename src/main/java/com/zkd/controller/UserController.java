package com.zkd.controller;

import com.zkd.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/user")
public class UserController {
    private static Logger log=LoggerFactory.getLogger(UserController.class);

    @Autowired
    private IUserService userService;

    // /user/test?id=1
    @RequestMapping(value="/check",method=RequestMethod.GET)
    public String checkUser(HttpServletRequest request, Model model){
       // userService = new UserServiceImpl();
        String userId = request.getParameter("id");
        System.out.println("userId:"+userId);
        String result = this.userService.checkUserById(userId);
        model.addAttribute("user",result );
        return "user";
    }
}
