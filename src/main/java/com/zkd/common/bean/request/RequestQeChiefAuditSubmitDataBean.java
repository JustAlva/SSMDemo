package com.zkd.common.bean.request;

public class RequestQeChiefAuditSubmitDataBean {
    private String currentStepId ;
    private String stepTableId;
    private String flowID ;
    private String userCode ;
    private boolean adoptOrBack;

    public RequestQeChiefAuditSubmitDataBean(String currentStepId, String stepTableId, String flowID, String userCode, boolean adoptOrBack) {
        this.currentStepId = currentStepId;
        this.stepTableId = stepTableId;
        this.flowID = flowID;
        this.userCode = userCode;
        this.adoptOrBack = adoptOrBack;
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

    public boolean isAdoptOrBack() {
        return adoptOrBack;
    }

    public void setAdoptOrBack(boolean adoptOrBack) {
        this.adoptOrBack = adoptOrBack;
    }
}
