package com.zkd.common.bean.request;

public class RequestPreliminaryCauseAnalysisLoadDataBean {
    private String stepTableId;

    public RequestPreliminaryCauseAnalysisLoadDataBean(String stepTableId) {
        this.stepTableId = stepTableId;
    }

    public String getStepTableId() {
        return stepTableId;
    }

    public void setStepTableId(String stepTableId) {
        this.stepTableId = stepTableId;
    }
}
