package com.zkd.common.bean.other;

public class UserDataBean {
    private Integer userId;
    private String userNo;
    private String userName;
    private Integer roleId;
    private String roleName;
    private Integer departmentId;
    private String departmentName;
    private Integer isLeader;

    public UserDataBean(Integer userId, String userNo, String userName, Integer roleId, String roleName,Integer departmentId,  String departmentName, Integer isLeader) {
        this.userId = userId;
        this.userNo = userNo;
        this.userName = userName;
        this.roleId = roleId;
        this.roleName = roleName;
        this.departmentId = departmentId;
        this.departmentName = departmentName;
        this.isLeader = isLeader;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public Integer getIsLeader() {
        return isLeader;
    }

    public void setIsLeader(Integer isLeader) {
        this.isLeader = isLeader;
    }
}
