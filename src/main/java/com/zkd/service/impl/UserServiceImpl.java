package com.zkd.service.impl;

import com.zkd.dao.map.UserMapper;
import com.zkd.entity.User;
import com.zkd.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;


@Service("userService")
@Repository
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserMapper userDao;

    public String checkUserById(String userId) {
        Integer id = Integer.parseInt(userId);
        User user =this.userDao.selectByPrimaryKey(id);
        String result = "";
        if (user!=null) {
            result ="工号："+ user.getUserId()+",密码："+user.getUserPsd()+",姓名："+ user.getUserName();
        }else{
            result = "没有该名员工！";
        }
        return result;
    }
}
