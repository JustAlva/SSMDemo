package com.zkd.common.bean.request;

public class RequestLoadBaseDataBean {
    private String currentStepId;//QRQC_CURRENT_DEAL_STEP 表的id
    private String stepTableId;//信息存入表的id
    private String userCode;//创建用户
    private String flowID;//QRQC流程 流水号

    public RequestLoadBaseDataBean(String currentStepId, String stepTableId, String userCode, String flowID) {
        this.currentStepId = currentStepId;
        this.stepTableId = stepTableId;
        this.userCode = userCode;
        this.flowID = flowID;
    }

    public RequestLoadBaseDataBean(String flowID) {
        this.flowID = flowID;
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

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getFlowID() {
        return flowID;
    }

    public void setFlowID(String flowID) {
        this.flowID = flowID;
    }
}
