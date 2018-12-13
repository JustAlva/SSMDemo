package com.zkd.common.bean.request;

import com.zkd.common.bean.other.UserDataBean;

import java.util.List;

public class RequestPreliminaryCauseSubmitDataBean {
    private String currentStepId  ;//QRQC_CURRENT_DEAL_STEP 表的id
    private String stepTableId  ;//信息存入表的id
    private String flowID  ;//QRQC流程 流水号
    private String userCode  ;//用户id
    private String causeAnalysis  ;//初步原因分析
    private List<String> exceptionTypeList  ;//异常类型
    private List<String> responsibilityTypeList ;//责任类型
    private String supplier  ;//供应商
    private List<UserDataBean>  selectParts  ;//零部件围堵人员
    private List<UserDataBean>  selectFinished  ;//在制品 成品 围堵人员
    private List<UserDataBean> selectRootCauseList ;//根本原因分析和改善措施处理人员
    private String type  ;//类型，退回类型

    public RequestPreliminaryCauseSubmitDataBean(String currentStepId, String stepTableId, String flowID, String userCode, String causeAnalysis, List<String> exceptionTypeList, List<String> responsibilityTypeList, String supplier, List<UserDataBean> selectParts, List<UserDataBean> selectFinished, List<UserDataBean> selectRootCauseList, String type) {
        this.currentStepId = currentStepId;
        this.stepTableId = stepTableId;
        this.flowID = flowID;
        this.userCode = userCode;
        this.causeAnalysis = causeAnalysis;
        this.exceptionTypeList = exceptionTypeList;
        this.responsibilityTypeList = responsibilityTypeList;
        this.supplier = supplier;
        this.selectParts = selectParts;
        this.selectFinished = selectFinished;
        this.selectRootCauseList = selectRootCauseList;
        this.type = type;
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

    public String getCauseAnalysis() {
        return causeAnalysis;
    }

    public void setCauseAnalysis(String causeAnalysis) {
        this.causeAnalysis = causeAnalysis;
    }

    public List<String> getExceptionTypeList() {
        return exceptionTypeList;
    }

    public void setExceptionTypeList(List<String> exceptionTypeList) {
        this.exceptionTypeList = exceptionTypeList;
    }

    public List<String> getResponsibilityTypeList() {
        return responsibilityTypeList;
    }

    public void setResponsibilityTypeList(List<String> responsibilityTypeList) {
        this.responsibilityTypeList = responsibilityTypeList;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public List<UserDataBean> getSelectParts() {
        return selectParts;
    }

    public void setSelectParts(List<UserDataBean> selectParts) {
        this.selectParts = selectParts;
    }

    public List<UserDataBean> getSelectFinished() {
        return selectFinished;
    }

    public void setSelectFinished(List<UserDataBean> selectFinished) {
        this.selectFinished = selectFinished;
    }

    public List<UserDataBean> getSelectRootCauseList() {
        return selectRootCauseList;
    }

    public void setSelectRootCauseList(List<UserDataBean> selectRootCauseList) {
        this.selectRootCauseList = selectRootCauseList;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
