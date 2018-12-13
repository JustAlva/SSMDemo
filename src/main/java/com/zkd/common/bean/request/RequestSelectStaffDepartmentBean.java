package com.zkd.common.bean.request;

public class RequestSelectStaffDepartmentBean {
    private String departmentId;
    private String role;

    public RequestSelectStaffDepartmentBean(String departmentId, String role) {
        this.departmentId = departmentId;
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }
}

