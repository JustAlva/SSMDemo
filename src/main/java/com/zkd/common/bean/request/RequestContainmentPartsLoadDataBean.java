package com.zkd.common.bean.request;

public class RequestContainmentPartsLoadDataBean {
    private String flowId;

    public RequestContainmentPartsLoadDataBean(String flowId) {
        this.flowId = flowId;
    }

    public String getFlowId() {
        return flowId;
    }

    public void setFlowId(String flowId) {
        this.flowId = flowId;
    }
}
