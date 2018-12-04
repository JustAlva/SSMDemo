package com.zkd.common.bean.other;

public class CurrentStepDataBean {

    private String flowId;
    private int currentDealTableId;
    private String currentDealUser;
    private String currentStepName;
    private int currentStepCode;
    private int nextStepInsertId;
    private String nextStepName;
    private int nextStepCode;
    private String nextStepDealUser;

    public CurrentStepDataBean() {
    }

    public CurrentStepDataBean(String flowId, int currentDealTableId, String currentDealUser, String currentStepName, int currentStepCode, int nextStepInsertId, String nextStepName, int nextStepCode, String nextStepDealUser) {
        this.flowId = flowId;
        this.currentDealTableId = currentDealTableId;
        this.currentDealUser = currentDealUser;
        this.currentStepName = currentStepName;
        this.currentStepCode = currentStepCode;
        this.nextStepInsertId = nextStepInsertId;
        this.nextStepName = nextStepName;
        this.nextStepCode = nextStepCode;
        this.nextStepDealUser = nextStepDealUser;
    }

    public CurrentStepDataBean(String flowId, int insertId, String currentDealUserId, String currentStepName, int currentStepCode, String nextStepName, int nextStepCode, String nextStepDealUser) {
        this.flowId = flowId;
        this.nextStepInsertId = insertId;
        this.currentDealUser = currentDealUserId;
        this.currentStepName = currentStepName;
        this.currentStepCode = currentStepCode;
        this.nextStepName = nextStepName;
        this.nextStepCode = nextStepCode;
        this.nextStepDealUser = nextStepDealUser;
    }

    public int getCurrentDealTableId() {
        return currentDealTableId;
    }

    public void setCurrentDealTableId(int currentDealTableId) {
        this.currentDealTableId = currentDealTableId;
    }

    public String getNextStepDealUser() {
        return nextStepDealUser;
    }

    public void setNextStepDealUser(String nextStepDealUser) {
        this.nextStepDealUser = nextStepDealUser;
    }

    public String getFlowId() {
        return flowId;
    }

    public void setFlowId(String flowId) {
        this.flowId = flowId;
    }

    public int getNextStepInsertId() {
        return nextStepInsertId;
    }

    public void setNextStepInsertId(int nextStepInsertId) {
        this.nextStepInsertId = nextStepInsertId;
    }

    public String getCurrentDealUser() {
        return currentDealUser;
    }

    public void setCurrentDealUser(String currentDealUser) {
        this.currentDealUser = currentDealUser;
    }

    public String getCurrentStepName() {
        return currentStepName;
    }

    public void setCurrentStepName(String currentStepName) {
        this.currentStepName = currentStepName;
    }

    public int getCurrentStepCode() {
        return currentStepCode;
    }

    public void setCurrentStepCode(int currentStepCode) {
        this.currentStepCode = currentStepCode;
    }

    public String getNextStepName() {
        return nextStepName;
    }

    public void setNextStepName(String nextStepName) {
        this.nextStepName = nextStepName;
    }

    public int getNextStepCode() {
        return nextStepCode;
    }

    public void setNextStepCode(int nextStepCode) {
        this.nextStepCode = nextStepCode;
    }
}
