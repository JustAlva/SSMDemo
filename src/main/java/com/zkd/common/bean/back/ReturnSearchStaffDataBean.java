package com.zkd.common.bean.back;

import com.zkd.common.bean.other.DepartmentWithParentBean;
import com.zkd.common.bean.other.UserDataBean;

import java.util.List;

public class ReturnSearchStaffDataBean {
    List<DepartmentWithParentBean> departments;
    List<UserDataBean> staffs;

    public ReturnSearchStaffDataBean() {
    }

    public ReturnSearchStaffDataBean(List<DepartmentWithParentBean> departments, List<UserDataBean> staffs) {
        this.departments = departments;
        this.staffs = staffs;
    }

    public List<DepartmentWithParentBean> getDepartments() {
        return departments;
    }

    public void setDepartments(List<DepartmentWithParentBean> departments) {
        this.departments = departments;
    }

    public List<UserDataBean> getStaffs() {
        return staffs;
    }

    public void setStaffs(List<UserDataBean> staffs) {
        this.staffs = staffs;
    }
}
