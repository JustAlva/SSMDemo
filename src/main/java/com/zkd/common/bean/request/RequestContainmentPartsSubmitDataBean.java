package com.zkd.common.bean.request;

public class RequestContainmentPartsSubmitDataBean {
    private String currentStepId ;//QRQC_CURRENT_DEAL_STEP 表的id
    private String stepTableId;//信息存入表的id
    private String userCode ;//用户id
    private String flowID ;//QRQC流程 流水号
    private String inventory ;//库存
    private String disposeMeasure ;//处理措施
    private String dealResult ;//处理结果
    private boolean isSupplement = false;//是否补料
    private String supplementDate ;//补料时间
    private String unhealthyNumber ;//不良数量
    private String unhealthyRate ;//不良比率
    private boolean isAdopt = true;//确认还是退回，默认确认：true， 退回：false
    private String exceptionType  ;//异常类型 ，当异常类型为外观和不良率小于5％，直接转QE确认是否关闭

    public RequestContainmentPartsSubmitDataBean(String currentStepId, String stepTableId, String flowID, String userCode, String inventory, String disposeMeasure, String dealResult, boolean isSupplement, String supplementDate, String unhealthyNumber, String unhealthyRate, boolean isAdopt, String exceptionType) {
        this.currentStepId = currentStepId;
        this.stepTableId = stepTableId;
        this.flowID = flowID;
        this.userCode = userCode;
        this.inventory = inventory;
        this.disposeMeasure = disposeMeasure;
        this.dealResult = dealResult;
        this.isSupplement = isSupplement;
        this.supplementDate = supplementDate;
        this.unhealthyNumber = unhealthyNumber;
        this.unhealthyRate = unhealthyRate;
        this.isAdopt = isAdopt;
        this.exceptionType = exceptionType;
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

    public String getInventory() {
        return inventory;
    }

    public void setInventory(String inventory) {
        this.inventory = inventory;
    }

    public String getDisposeMeasure() {
        return disposeMeasure;
    }

    public void setDisposeMeasure(String disposeMeasure) {
        this.disposeMeasure = disposeMeasure;
    }

    public String getDealResult() {
        return dealResult;
    }

    public void setDealResult(String dealResult) {
        this.dealResult = dealResult;
    }

    public boolean isSupplement() {
        return isSupplement;
    }

    public void setSupplement(boolean supplement) {
        isSupplement = supplement;
    }

    public String getSupplementDate() {
        return supplementDate;
    }

    public void setSupplementDate(String supplementDate) {
        this.supplementDate = supplementDate;
    }

    public String getUnhealthyNumber() {
        return unhealthyNumber;
    }

    public void setUnhealthyNumber(String unhealthyNumber) {
        this.unhealthyNumber = unhealthyNumber;
    }

    public String getUnhealthyRate() {
        return unhealthyRate;
    }

    public void setUnhealthyRate(String unhealthyRate) {
        this.unhealthyRate = unhealthyRate;
    }

    public boolean isAdopt() {
        return isAdopt;
    }

    public void setAdopt(boolean adopt) {
        isAdopt = adopt;
    }

    public String getExceptionType() {
        return exceptionType;
    }

    public void setExceptionType(String exceptionType) {
        this.exceptionType = exceptionType;
    }
}
