package com.zkd.service.impl;

import com.zkd.common.bean.back.ReturnAllStaffDataBean;
import com.zkd.common.bean.back.ReturnDataBean;
import com.zkd.common.bean.other.UserDataBean;
import com.zkd.common.bean.request.RequestSelectStaffDepartmentBean;
import com.zkd.common.constant.MsgConstant;
import com.zkd.dao.map.ConfDepartmentMapper;
import com.zkd.entity.ConfDepartment;
import com.zkd.service.IDepartmentService;
import com.zkd.utils.EncryptUtils;
import com.zkd.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("departmentService")
public class DepartmentService implements IDepartmentService {

    @Autowired
    ConfDepartmentMapper departmentDao;

    @Override
    public String getAll(String data) {
        ReturnAllStaffDataBean returnData = new ReturnAllStaffDataBean();
        List<ConfDepartment> departmentList;
        List<UserDataBean> userList = new ArrayList<>();
        RequestSelectStaffDepartmentBean requestsBean = (RequestSelectStaffDepartmentBean) new EncryptUtils().decryptObj(data, RequestSelectStaffDepartmentBean.class);
        int departmentId = StringUtils.parseString2Int(requestsBean.getDepartmentId());
        if (departmentId <= 0) {
            departmentList = departmentDao.selectFirstLevel();
        } else {
            departmentList = departmentDao.selectByParentId(departmentId);
        }
        for (int i = 0; i < departmentList.size(); i++) {
            ConfDepartment department = departmentList.get(i);
            int count = departmentDao.selectIsParent(department.getId());
            if (count==0) {
                department.setStatus(9);
            }
            departmentList.set(i, department);
        }
        returnData.setDepartments(departmentList);
        returnData.setStaffs(userList);
        return new EncryptUtils<>().encryptObj(new ReturnDataBean<>(MsgConstant.CODE_SUCCESS, returnData, MsgConstant.MSG_SUCCESS));
    }
}
