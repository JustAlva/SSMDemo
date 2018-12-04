package com.zkd.common.bean.request;

import java.util.Date;
import java.util.List;

public class RequestRootCauseAnalysisSubmitDataBean {

    private int currentStepId ;//QRQC_CURRENT_DEAL_STEP 表的id
    private int stepTableId ;//信息存入表的id
    private String flowID ;//QRQC流程 流水号
    private String userCode ;//用户id
    private String generativeCause ;//发生因
    private String generativeRes  ;//发生因负责人
    private String outFlowCause =  "";//流出因
    private String outFlowRes =  "";//流出因负责人
    private String improvement ;//改善措施
    private String improvementRes ;//改善措施责任人
    private Date planFinishDate  ;//计划完成时间
    private String preventiveMeasure ;//预防措施
    private String preventiveRes ;//预防措施责任人
    private Date preventiveFinishDate ;//预防措施计划完成时间
    private List<String> updateDatasList  ;//需要更新的资料
    private boolean isAdopt;//通过/退回

    public RequestRootCauseAnalysisSubmitDataBean(int currentStepId, int stepTableId, String flowID, String userCode, String generativeCause, String generativeRes, String outFlowCause, String outFlowRes, String improvement, String improvementRes, Date planFinishDate, String preventiveMeasure, String preventiveRes, Date preventiveFinishDate, List<String> updateDatasList, boolean isAdopt) {
        this.currentStepId = currentStepId;
        this.stepTableId = stepTableId;
        this.flowID = flowID;
        this.userCode = userCode;
        this.generativeCause = generativeCause;
        this.generativeRes = generativeRes;
        this.outFlowCause = outFlowCause;
        this.outFlowRes = outFlowRes;
        this.improvement = improvement;
        this.improvementRes = improvementRes;
        this.planFinishDate = planFinishDate;
        this.preventiveMeasure = preventiveMeasure;
        this.preventiveRes = preventiveRes;
        this.preventiveFinishDate = preventiveFinishDate;
        this.updateDatasList = updateDatasList;
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

    public String getGenerativeCause() {
        return generativeCause;
    }

    public void setGenerativeCause(String generativeCause) {
        this.generativeCause = generativeCause;
    }

    public String getGenerativeRes() {
        return generativeRes;
    }

    public void setGenerativeRes(String generativeRes) {
        this.generativeRes = generativeRes;
    }

    public String getOutFlowCause() {
        return outFlowCause;
    }

    public void setOutFlowCause(String outFlowCause) {
        this.outFlowCause = outFlowCause;
    }

    public String getOutFlowRes() {
        return outFlowRes;
    }

    public void setOutFlowRes(String outFlowRes) {
        this.outFlowRes = outFlowRes;
    }

    public String getImprovement() {
        return improvement;
    }

    public void setImprovement(String improvement) {
        this.improvement = improvement;
    }

    public String getImprovementRes() {
        return improvementRes;
    }

    public void setImprovementRes(String improvementRes) {
        this.improvementRes = improvementRes;
    }

    public Date getPlanFinishDate() {
        return planFinishDate;
    }

    public void setPlanFinishDate(Date planFinishDate) {
        this.planFinishDate = planFinishDate;
    }

    public String getPreventiveMeasure() {
        return preventiveMeasure;
    }

    public void setPreventiveMeasure(String preventiveMeasure) {
        this.preventiveMeasure = preventiveMeasure;
    }

    public String getPreventiveRes() {
        return preventiveRes;
    }

    public void setPreventiveRes(String preventiveRes) {
        this.preventiveRes = preventiveRes;
    }

    public Date getPreventiveFinishDate() {
        return preventiveFinishDate;
    }

    public void setPreventiveFinishDate(Date preventiveFinishDate) {
        this.preventiveFinishDate = preventiveFinishDate;
    }

    public List<String> getUpdateDatasList() {
        return updateDatasList;
    }

    public void setUpdateDatasList(List<String> updateDatasList) {
        this.updateDatasList = updateDatasList;
    }

    public boolean isAdopt() {
        return isAdopt;
    }

    public void setAdopt(boolean adopt) {
        isAdopt = adopt;
    }
}
