package com.zkd.common.bean.request;

import java.util.Date;

public class RequestIsCloseSureSubmitDataBean {
    private String currentStepId  ;
    private String stepTableId  ;
    private String flowID  ;
    private String userCode  ;
    private Date changeDate  ;
    private boolean isAdopt  ;
    private String selectBackStep  ;

    public RequestIsCloseSureSubmitDataBean(String currentStepId, String stepTableId, String flowID, String userCode, Date changeDate, boolean isAdopt, String selectBackStep) {
        this.currentStepId = currentStepId;
        this.stepTableId = stepTableId;
        this.flowID = flowID;
        this.userCode = userCode;
        this.changeDate = changeDate;
        this.isAdopt = isAdopt;
        this.selectBackStep = selectBackStep;
    }

    public String getCurrentStepId() {
        return currentStepId;
    }

    public void setCurrentStepId(String currentStepId) {
        this.currentStepId = currentStepId;
    }

    public String getStepTableId() {
        return stepTableId;
    }

    public void setStepTableId(String stepTableId) {
        this.stepTableId = stepTableId;
    }

    public String getFlowID() {
        return flowID;
    }

    public void setFlowID(String flowID) {
        this.flowID = flowID;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public Date getChangeDate() {
        return changeDate;
    }

    public void setChangeDate(Date changeDate) {
        this.changeDate = changeDate;
    }

    public boolean isAdopt() {
        return isAdopt;
    }

    public void setAdopt(boolean adopt) {
        isAdopt = adopt;
    }

    public String getSelectBackStep() {
        return selectBackStep;
    }

    public void setSelectBackStep(String selectBackStep) {
        this.selectBackStep = selectBackStep;
    }
}

