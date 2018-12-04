package com.zkd.common.bean.request;

public class RequestMeasureVerificationSubmitDataBean {
    private int currentStepId ;//QRQC_CURRENT_DEAL_STEP 表的id
    private int stepTableId ;//信息存入表的id
    private String flowID ;//QRQC流程 流水号
    private String userCode ;//用户id
    private String measureEffect ;//措施效果
    private String repeatNumber ;//重发编号
    private boolean isWrongCheck ;//错漏检项
    private boolean isAdopt ;//通过/退回，默认通过
    private String selectBackStep ;//回退步骤

    public RequestMeasureVerificationSubmitDataBean(int currentStepId, int stepTableId, String flowID, String userCode, String measureEffect, String repeatNumber, boolean isWrongCheck, boolean isAdopt, String selectBackStep) {
        this.currentStepId = currentStepId;
        this.stepTableId = stepTableId;
        this.flowID = flowID;
        this.userCode = userCode;
        this.measureEffect = measureEffect;
        this.repeatNumber = repeatNumber;
        this.isWrongCheck = isWrongCheck;
        this.isAdopt = isAdopt;
        this.selectBackStep = selectBackStep;
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

    public String getMeasureEffect() {
        return measureEffect;
    }

    public void setMeasureEffect(String measureEffect) {
        this.measureEffect = measureEffect;
    }

    public String getRepeatNumber() {
        return repeatNumber;
    }

    public void setRepeatNumber(String repeatNumber) {
        this.repeatNumber = repeatNumber;
    }

    public boolean isWrongCheck() {
        return isWrongCheck;
    }

    public void setWrongCheck(boolean wrongCheck) {
        isWrongCheck = wrongCheck;
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
