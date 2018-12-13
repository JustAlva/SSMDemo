package com.zkd.common.bean.request;

public class RequestContainmentFinishSubmitDataBean {

    private String currentStepId;
    private String stepTableId;
    private String flowID;
    private String userCode;
    private String inventoryProduct;
    private String disposeMeasureProduct;
    private String dealResultProduct;
    private String inventoryFinished;
    private String disposeMeasureFinished;
    private String dealResultFinished;
    private boolean isAdopt  ;

    public RequestContainmentFinishSubmitDataBean(String currentStepId, String stepTableId, String flowID, String userCode, String inventoryProduct, String disposeMeasureProduct, String dealResultProduct, String inventoryFinished, String disposeMeasureFinished, String dealResultFinished, boolean isAdopt) {
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

    public String getInventoryProduct() {
        return inventoryProduct;
    }

    public void setInventoryProduct(String inventoryProduct) {
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

    public String getInventoryFinished() {
        return inventoryFinished;
    }

    public void setInventoryFinished(String inventoryFinished) {
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
