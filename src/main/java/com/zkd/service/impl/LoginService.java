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
        //EncryptUtils encryptUtils = new EncryptUtils();
        try {
            String requestStr = getStr.replace(" ", "+");
            String getRequest = EncryptUtils.decryptStr(requestStr);
            RequestLoginBean loginBean = new Gson().fromJson(getRequest, RequestLoginBean.class);
            List<User> list = userDao.selectById(loginBean.getUserId());
            if (list.size()<=0) {
                code = MsgConstant.CODE_FAIL;
                msg = MsgConstant.LOGIN_FAIL_NO_SUCH_USER;
                return new EncryptUtils<>().encryptObj( new ReturnDataBean<>(code, "", msg));
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
                    return new EncryptUtils<>().encryptObj( new ReturnDataBean<>(code, "", msg));
                }
            }
        } catch (Exception e) {
            code = MsgConstant.CODE_ERROR;
            msg = MsgConstant.MSG_ERROR;
            return new EncryptUtils<>().encryptObj( new ReturnDataBean<>(code, e.toString(), msg));
        }


    }
}
