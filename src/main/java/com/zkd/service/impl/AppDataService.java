package com.zkd.service.impl;

import com.zkd.common.bean.back.ReturnDataBean;
import com.zkd.common.constant.MsgConstant;
import com.zkd.dao.map.ConfAppVersionMapper;
import com.zkd.entity.ConfAppVersion;
import com.zkd.service.IAppDataService;
import com.zkd.utils.EncryptUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("appDataService")
public class AppDataService implements IAppDataService {

    @Autowired
    ConfAppVersionMapper confAppVersionDao;


    @Override
    public String getVersionData() {
        List<ConfAppVersion> list = confAppVersionDao.selectUseVersionData();
        return new EncryptUtils<>().encryptObj(new ReturnDataBean<>(MsgConstant.CODE_SUCCESS, list != null && list.size() > 0 ? list.get(0) : null, MsgConstant.MSG_SUCCESS));
    }
}
