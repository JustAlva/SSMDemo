package com.zkd.service.impl;

import com.zkd.common.bean.back.ReturnAllStaffDataBean;
import com.zkd.common.bean.back.ReturnDataBean;
import com.zkd.common.bean.back.ReturnSearchStaffDataBean;
import com.zkd.common.bean.other.DepartmentWithParentBean;
import com.zkd.common.bean.other.UserDataBean;
import com.zkd.common.bean.request.RequestSelectStaffDepartmentBean;
import com.zkd.common.bean.request.RequestSelectStaffSearchBean;
import com.zkd.common.constant.MsgConstant;
import com.zkd.dao.map.ConfDepartmentMapper;
import com.zkd.dao.map.UserInfoMapper;
import com.zkd.entity.ConfDepartment;
import com.zkd.service.IStaffService;
import com.zkd.utils.EncryptUtils;
import com.zkd.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("staffService")
public class StaffService implements IStaffService {

    @Autowired
    ConfDepartmentMapper departmentDao;

    @Autowired
    UserInfoMapper userDao;

    @Override
    public String getAllStaff(String data)  {
        ReturnAllStaffDataBean returnData = new ReturnAllStaffDataBean();
        List<ConfDepartment> departmentList= new ArrayList<>();
        List<UserDataBean> userList = new ArrayList<>();
        RequestSelectStaffDepartmentBean requestsBean = (RequestSelectStaffDepartmentBean) new EncryptUtils().decryptObj(data, RequestSelectStaffDepartmentBean.class);
        int departmentId = StringUtils.parseString2Int(requestsBean.getDepartmentId());
        if (departmentId == -3) {
            String role = requestsBean.getRole();
            if (role.equals("")) {
                departmentList.add(new ConfDepartment(-10, "QE", -10));
                returnData.setDepartments(departmentList);
                returnData.setStaffs(userList);
            } else {
                userList = userDao.selectUserByRoleName(role);
                returnData.setDepartments(departmentList);
                returnData.setStaffs(userList);
            }
        } else if (departmentId == -2) {
            String role = requestsBean.getRole();
            if (role.equals("")) {
                departmentList.add(new ConfDepartment(-10, "QE", -10));
                departmentList.add(new ConfDepartment(-10, "PE", -10));
                returnData.setDepartments(departmentList);
                returnData.setStaffs(userList);
            } else {
                userList = userDao.selectUserByRoleName(role);
                returnData.setDepartments(departmentList);
                returnData.setStaffs(userList);
            }
        } else if (departmentId == -1) {
            departmentList = departmentDao.selectFirstLevel();
            returnData.setDepartments(departmentList);
            returnData.setStaffs(userList);
        } else if (departmentId > 0) {
            departmentList = departmentDao.selectByParentId(departmentId);
            returnData.setDepartments(departmentList);
            userList = userDao.selectUserInfoByDepartmentId(departmentId);
            returnData.setStaffs(userList);
        }
        return new EncryptUtils<>().encryptObj(new ReturnDataBean<>(MsgConstant.CODE_SUCCESS, returnData, MsgConstant.MSG_SUCCESS));
    }


    @Override
    public String search(String data)  {
        RequestSelectStaffSearchBean requestsBean = (RequestSelectStaffSearchBean) new EncryptUtils().decryptObj(data, RequestSelectStaffSearchBean.class);
        ReturnSearchStaffDataBean returnBean = new ReturnSearchStaffDataBean();
        if (requestsBean != null) {
            String search = requestsBean.getSearch();
            List<DepartmentWithParentBean> departments = new ArrayList<>();
            List<ConfDepartment> departmentList = departmentDao.selectBySearch(search);
            for (ConfDepartment department : departmentList) {
                DepartmentWithParentBean bean = new DepartmentWithParentBean(department.getId(), department.getDepartmentName(), department.getParentId());
                String parentIds = department.getDepartmentPreIds();
                String[] parentIdList = parentIds.split(",");
                List<ConfDepartment> parentDepartments = new ArrayList<>();
                if (parentIdList.length > 0) {
                    for (String id : parentIdList) {
                        List<ConfDepartment> parentDepartment = departmentDao.selectById(id);
                        parentDepartments.addAll(parentDepartment);
                    }
                }
                bean.setParents(parentDepartments);
                departments.add(bean);
            }
            List<UserDataBean> userList = userDao.selectUserBySearch(search);
            returnBean.setDepartments(departments);
            returnBean.setStaffs(userList);
        }
        return new EncryptUtils<>().encryptObj(new ReturnDataBean<>(MsgConstant.CODE_SUCCESS, returnBean, MsgConstant.MSG_SUCCESS));
    }

}
