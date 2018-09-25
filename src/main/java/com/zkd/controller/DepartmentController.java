package com.zkd.controller;

import com.zkd.service.IDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("department")
public class DepartmentController {
    @Autowired
    IDepartmentService departmentService;

    @ResponseBody
    @RequestMapping(value = "all",method = RequestMethod.POST)
    public String getAllDepartment(@RequestParam("data") String data) throws Exception {
        return  departmentService.getAll(data);
    }



}
