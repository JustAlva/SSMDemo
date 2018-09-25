package com.zkd.common.bean.request;

public class RequestSelectStaffDepartmentBean {
    private int departmentId;
    private String role;

    public RequestSelectStaffDepartmentBean(int departmentId, String role) {
        this.departmentId = departmentId;
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }
}

