package com.zkd.common.bean.request;

import com.zkd.common.bean.back.ReturnQEAuditLoadDataBean;

import java.util.List;

public class RequestQeAuditSubmitDataBean {
    private String currentStepId;
    private String stepTableId;
    private String flowID;
    private String userCode;
    private boolean isAdopt  ;
    private boolean isExport ;
    private List<ReturnQEAuditLoadDataBean> selectBackData;

    public RequestQeAuditSubmitDataBean(String currentStepId, String stepTableId, String flowID, String userCode, boolean isAdopt, boolean isExport, List<ReturnQEAuditLoadDataBean> selectBackData) {
        this.currentStepId = currentStepId;
        this.stepTableId = stepTableId;
        this.flowID = flowID;
        this.userCode = userCode;
        this.isAdopt = isAdopt;
        this.isExport = isExport;
        this.selectBackData = selectBackData;
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

    public boolean isAdopt() {
        return isAdopt;
    }

    public void setAdopt(boolean adopt) {
        isAdopt = adopt;
    }

    public boolean isExport() {
        return isExport;
    }

    public void setExport(boolean export) {
        isExport = export;
    }

    public List<ReturnQEAuditLoadDataBean> getSelectBackData() {
        return selectBackData;
    }

    public void setSelectBackData(List<ReturnQEAuditLoadDataBean> selectBackData) {
        this.selectBackData = selectBackData;
    }
}
