package com.zkd.controller;

import com.zkd.service.IStaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller()
@RequestMapping("staff")
public class StaffController {

    @Autowired
    IStaffService staffService;

    @ResponseBody
    @RequestMapping(value = "all" , method = RequestMethod.POST)
    public String getAllStaff(@RequestParam("data") String data)  {
        return staffService.getAllStaff(data);
    }


    @ResponseBody
    @RequestMapping(value = "search" , method = RequestMethod.POST)
    public String search(@RequestParam("data") String data)  {
        return staffService.search(data);
    }


}
