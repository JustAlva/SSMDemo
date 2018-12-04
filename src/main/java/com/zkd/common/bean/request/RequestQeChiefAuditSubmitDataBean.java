package com.zkd.common.bean.request;

public class RequestQeChiefAuditSubmitDataBean {
    private int currentStepId ;
    private int stepTableId;
    private String flowID ;
    private String userCode ;
    private boolean adoptOrBack;

    public RequestQeChiefAuditSubmitDataBean(int currentStepId, int stepTableId, String flowID, String userCode, boolean adoptOrBack) {
        this.currentStepId = currentStepId;
        this.stepTableId = stepTableId;
        this.flowID = flowID;
        this.userCode = userCode;
        this.adoptOrBack = adoptOrBack;
    }

    public int getCurrentStepId() {
        return currentStepId;
    }

    public void setCurrentStepId(int currentStepId) {
        this.currentStepId = currentStepId;
    }

    public int getStepTableId() {
        return stepTableId;
    }

    public void setStepTableId(int stepTableId) {
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
