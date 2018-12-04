package com.zkd.common.bean.back.bean;

public class ReturnPreliminaryCauseNextStepDealDataBean {

    private int stepCode;
    private String stepName;
    private int departmentId;
    private String departmentName;

    public ReturnPreliminaryCauseNextStepDealDataBean(int stepCode, String stepName, int departmentId, String departmentName) {
        this.stepCode = stepCode;
        this.stepName = stepName;
        this.departmentId = departmentId;
        this.departmentName = departmentName;
    }

    public int getStepCode() {
        return stepCode;
    }

    public void setStepCode(int stepCode) {
        this.stepCode = stepCode;
    }

    public String getStepName() {
        return stepName;
    }

    public void setStepName(String stepName) {
        this.stepName = stepName;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }
}
