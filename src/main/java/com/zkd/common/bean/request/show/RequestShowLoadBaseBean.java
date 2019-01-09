package com.zkd.common.bean.request.show;

public class RequestShowLoadBaseBean {
    private int tableId;
    private int currentStepId;

    public RequestShowLoadBaseBean(int tableId,int currentStepId) {
        this.tableId = tableId;
        this.currentStepId = currentStepId;
    }

    public int getTableId() {
        return tableId;
    }

    public void setTableId(int tableId) {
        this.tableId = tableId;
    }

    public int getCurrentStepId() {
        return currentStepId;
    }

    public void setCurrentStepId(int currentStepId) {
        this.currentStepId = currentStepId;
    }
}
