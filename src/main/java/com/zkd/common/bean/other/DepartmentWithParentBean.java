package com.zkd.common.bean.other;

import com.zkd.entity.ConfDepartment;

import java.util.List;

public class DepartmentWithParentBean {

    private int id;
    private String departmentName;
    private int parentId;
    private List<ConfDepartment> parents;

    public DepartmentWithParentBean(int id, String departmentName, int parentId) {
        this.id = id;
        this.departmentName = departmentName;
        this.parentId = parentId;
    }

    public DepartmentWithParentBean(int id, String departmentName, int parentId, List<ConfDepartment> parents) {
        this.id = id;
        this.departmentName = departmentName;
        this.parentId = parentId;
        this.parents = parents;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public List<ConfDepartment> getParents() {
        return parents;
    }

    public void setParents(List<ConfDepartment> parents) {
        this.parents = parents;
    }
}
