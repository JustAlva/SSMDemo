package com.zkd.common.bean.request;

public class RequestContainmentFinishSubmitDataBean {

    private int currentStepId;//QRQC_CURRENT_DEAL_STEP 表的id
    private int stepTableId;//信息存入表的id
    private String flowID;//QRQC流程 流水号
    private String userCode;//用户id
    private int inventoryProduct;//在制品库存
    private String disposeMeasureProduct;//在制品处理措施；
    private String dealResultProduct;//在制品处理结果；
    private int inventoryFinished;//成品库存
    private String disposeMeasureFinished;//成品处理措施
    private String dealResultFinished;//成品处理结果
    private boolean isAdopt  ;//确定，默认； false:退回到初步原因分析

    public RequestContainmentFinishSubmitDataBean(int currentStepId, int stepTableId, String flowID, String userCode, int inventoryProduct, String disposeMeasureProduct, String dealResultProduct, int inventoryFinished, String disposeMeasureFinished, String dealResultFinished, boolean isAdopt) {
        this.currentStepId = currentStepId;
        this.stepTableId = stepTableId;
        this.flowID = flowID;
        this.userCode = userCode;
        this.inventoryProduct = inventoryProduct;
        this.disposeMeasureProduct = disposeMeasureProduct;
        this.dealResultProduct = dealResultProduct;
        this.inventoryFinished = inventoryFinished;
        this.disposeMeasureFinished = disposeMeasureFinished;
        this.dealResultFinished = dealResultFinished;
        this.isAdopt = isAdopt;
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

    public int getInventoryProduct() {
        return inventoryProduct;
    }

    public void setInventoryProduct(int inventoryProduct) {
        this.inventoryProduct = inventoryProduct;
    }

    public String getDisposeMeasureProduct() {
        return disposeMeasureProduct;
    }

    public void setDisposeMeasureProduct(String disposeMeasureProduct) {
        this.disposeMeasureProduct = disposeMeasureProduct;
    }

    public String getDealResultProduct() {
        return dealResultProduct;
    }

    public void setDealResultProduct(String dealResultProduct) {
        this.dealResultProduct = dealResultProduct;
    }

    public int getInventoryFinished() {
        return inventoryFinished;
    }

    public void setInventoryFinished(int inventoryFinished) {
        this.inventoryFinished = inventoryFinished;
    }

    public String getDisposeMeasureFinished() {
        return disposeMeasureFinished;
    }

    public void setDisposeMeasureFinished(String disposeMeasureFinished) {
        this.disposeMeasureFinished = disposeMeasureFinished;
    }

    public String getDealResultFinished() {
        return dealResultFinished;
    }

    public void setDealResultFinished(String dealResultFinished) {
        this.dealResultFinished = dealResultFinished;
    }

    public boolean isAdopt() {
        return isAdopt;
    }

    public void setAdopt(boolean adopt) {
        isAdopt = adopt;
    }
}
