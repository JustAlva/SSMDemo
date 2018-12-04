package com.zkd.common.bean.request;

public class RequestContainmentPartsSubmitDataBean {
    private int currentStepId ;//QRQC_CURRENT_DEAL_STEP 表的id
    private int stepTableId;//信息存入表的id
    private String flowID ;//QRQC流程 流水号
    private String userCode ;//用户id
    private int inventory ;//库存
    private String disposeMeasure ;//处理措施
    private String dealResult ;//处理结果
    private boolean isSupplement = false;//是否补料
    private String supplementDate ;//补料时间
    private int unhealthyNumber ;//不良数量
    private double unhealthyRate ;//不良比率
    private boolean isAdopt = true;//确认还是退回，默认确认：true， 退回：false
    private String exceptionType  ;//异常类型 ，当异常类型为外观和不良率小于5％，直接转QE确认是否关闭

    public RequestContainmentPartsSubmitDataBean(int currentStepId, int stepTableId, String flowID, String userCode, int inventory, String disposeMeasure, String dealResult, boolean isSupplement, String supplementDate, int unhealthyNumber, double unhealthyRate, boolean isAdopt, String exceptionType) {
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

    public int getInventory() {
        return inventory;
    }

    public void setInventory(int inventory) {
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

    public int getUnhealthyNumber() {
        return unhealthyNumber;
    }

    public void setUnhealthyNumber(int unhealthyNumber) {
        this.unhealthyNumber = unhealthyNumber;
    }

    public double getUnhealthyRate() {
        return unhealthyRate;
    }

    public void setUnhealthyRate(double unhealthyRate) {
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
