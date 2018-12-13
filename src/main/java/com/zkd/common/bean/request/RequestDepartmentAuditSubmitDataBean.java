package com.zkd.common.bean.request;

public class RequestDepartmentAuditSubmitDataBean {
    private String currentStepId ;//QRQC_CURRENT_DEAL_STEP 表的id
    private String stepTableId ;//信息存入表的id
    private String flowID ;//QRQC流程 流水号
    private String userCode ;//用户id
    private boolean isAdopt  ;

    public RequestDepartmentAuditSubmitDataBean(String currentStepId, String stepTableId, String flowID, String userCode, boolean isAdopt) {
        this.currentStepId = currentStepId;
        this.stepTableId = stepTableId;
        this.flowID = flowID;
        this.userCode = userCode;
        this.isAdopt = isAdopt;
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

    public boolean isAdopt() {
        return isAdopt;
    }

    public void setAdopt(boolean adopt) {
        isAdopt = adopt;
    }
}
