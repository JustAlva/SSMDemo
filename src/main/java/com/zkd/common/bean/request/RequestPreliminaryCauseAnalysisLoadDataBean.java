package com.zkd.common.bean.request;

public class RequestPreliminaryCauseAnalysisLoadDataBean {
    private int stepTableId;

    public RequestPreliminaryCauseAnalysisLoadDataBean(int stepTableId) {
        this.stepTableId = stepTableId;
    }

    public int getStepTableId() {
        return stepTableId;
    }

    public void setStepTableId(int stepTableId) {
        this.stepTableId = stepTableId;
    }
}
