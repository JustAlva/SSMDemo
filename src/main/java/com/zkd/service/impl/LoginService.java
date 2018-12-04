package com.zkd.service.impl;

import com.google.gson.Gson;
import com.zkd.common.bean.back.ReturnDataBean;
import com.zkd.common.bean.other.UserDataBean;
import com.zkd.common.bean.request.RequestLoginBean;
import com.zkd.common.constant.MsgConstant;
import com.zkd.dao.map.UserInfoMapper;
import com.zkd.dao.map.UserMapper;
import com.zkd.entity.User;
import com.zkd.entity.UserInfo;
import com.zkd.service.ILoginService;
import com.zkd.utils.EncryptUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("loginService")
public class LoginService implements ILoginService {
    @Autowired
    UserMapper userDao;
    @Autowired
    UserInfoMapper userInfoDao;

    @Override
    public String checkUserLogin(String getStr) throws Exception {
        int code = -1;
        String msg;
        try {
            String getRequest = EncryptUtils.decryptStr(getStr);
            RequestLoginBean loginBean = new Gson().fromJson(getRequest, RequestLoginBean.class);
            List<User> list = userDao.selectById(loginBean.getUserId());
            if (list.size()<=0) {
                code = MsgConstant.CODE_FAIL;
                msg = MsgConstant.LOGIN_FAIL_NO_SUCH_USER;
                return new EncryptUtils<>().encryptObj( new ReturnDataBean<>(code, null, msg));
            } else {
                User user = list.get(0);
                if (user.getUserPsd().equals(loginBean.getUserPsd())) {
                    List<UserDataBean> createUserList = userInfoDao.selectUserInfoById(user.getUserNo());

                    code = MsgConstant.CODE_SUCCESS;
                    msg = MsgConstant.LOGIN_SUCCESS;
                    return new EncryptUtils<>().encryptObj( new ReturnDataBean<>(code, createUserList.get(0), msg));
                } else {
                    code = MsgConstant.CODE_FAIL;
                    msg = MsgConstant.LOGIN_FAIL_PSD_ERROR;
                    return new EncryptUtils<>().encryptObj( new ReturnDataBean<>(code, null, msg));
                }
            }
        } catch (Exception e) {
            code = MsgConstant.CODE_ERROR;
            msg = MsgConstant.MSG_ERROR;
            return new EncryptUtils<>().encryptObj( new ReturnDataBean<>(code, e.toString(), msg));
        }


    }

    @Override
    public String rootLogin(String data) {
        RequestLoginBean requestData = new EncryptUtils<RequestLoginBean>().decryptObj(data, RequestLoginBean.class);

        List<User> list = userDao.selectById(requestData.getUserId());
        if (list.size()<=0) {
            return new EncryptUtils<>().encryptObj( new ReturnDataBean<>(MsgConstant.CODE_FAIL, null, MsgConstant.LOGIN_FAIL_NO_SUCH_USER));
        } else {
            List<UserDataBean> createUserList = userInfoDao.selectUserInfoById(requestData.getUserId());
            return new EncryptUtils<>().encryptObj( new ReturnDataBean<>(MsgConstant.CODE_SUCCESS, createUserList.get(0), MsgConstant.LOGIN_SUCCESS));

        }
    }
}
