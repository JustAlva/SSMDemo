package com.zkd.common.bean.back;

import com.zkd.common.bean.other.UserDataBean;
import com.zkd.entity.ConfDepartment;

import java.util.List;

public class ReturnAllStaffDataBean {

    List<ConfDepartment> departments;
    List<UserDataBean> staffs;

    public ReturnAllStaffDataBean() {
    }

    public ReturnAllStaffDataBean(List<ConfDepartment> departments, List<UserDataBean> staffs) {
        this.departments = departments;
        this.staffs = staffs;
    }

    public List<ConfDepartment> getDepartments() {
        return departments;
    }

    public void setDepartments(List<ConfDepartment> departments) {
        this.departments = departments;
    }

    public List<UserDataBean> getStaffs() {
        return staffs;
    }

    public void setStaffs(List<UserDataBean> staffs) {
        this.staffs = staffs;
    }
}
