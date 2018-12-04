package com.zkd.common.bean.other;

public class StepJumpBean {
    private int startCode;//开始节点code
    private String startName;//开始节点name
    private String startUser; //下个节点的创建人员，即为当前结点的处理人员
    private int endCode;//结束节点code
    private String endName;//结束节点name
    private String endUser; //下个节点处理人员
    //------------------------;---------------------
    private String flowID;//流水号
    private int endTableId;
    private boolean isAdopt;//通过，退回   true：通过，false：退回
    private int backCode; //回退code

    public StepJumpBean() {
    }

    public StepJumpBean(int startCode, String startName, String startUser, String flowID, boolean isAdopt) {
        this.startCode = startCode;
        this.startName = startName;
        this.startUser = startUser;
        this.flowID = flowID;
        this.isAdopt = isAdopt;
    }

    public StepJumpBean(int startCode, String startName, int endCode, String endName, String startUser, String endUser, String flowID, boolean isAdopt, int backCode) {
        this.startCode = startCode;
        this.startName = startName;
        this.endCode = endCode;
        this.endName = endName;
        this.startUser = startUser;
        this.endUser = endUser;
        this.flowID = flowID;
        this.isAdopt = isAdopt;
        this.backCode = backCode;
    }

    public StepJumpBean(int startCode, String startName, String startUser, int endCode, String endName, String endUser, String flowID, int endTableId, boolean isAdopt, int backCode) {
        this.startCode = startCode;
        this.startName = startName;
        this.startUser = startUser;
        this.endCode = endCode;
        this.endName = endName;
        this.endUser = endUser;
        this.flowID = flowID;
        this.endTableId = endTableId;
        this.isAdopt = isAdopt;
        this.backCode = backCode;
    }

    public void setEndStep(int endCode, String endName, String endUser) {
        this.endCode = endCode;
        this.endName = endName;
        this.endUser = endUser;
    }


    public int getEndTableId() {
        return endTableId;
    }

    public void setEndTableId(int endTableId) {
        this.endTableId = endTableId;
    }

    public int getStartCode() {
        return startCode;
    }

    public void setStartCode(int startCode) {
        this.startCode = startCode;
    }

    public String getStartName() {
        return startName;
    }

    public void setStartName(String startName) {
        this.startName = startName;
    }

    public int getEndCode() {
        return endCode;
    }

    public void setEndCode(int endCode) {
        this.endCode = endCode;
    }

    public String getEndName() {
        return endName;
    }

    public void setEndName(String endName) {
        this.endName = endName;
    }

    public String getStartUser() {
        return startUser;
    }

    public void setStartUser(String startUser) {
        this.startUser = startUser;
    }

    public String getEndUser() {
        return endUser;
    }

    public void setEndUser(String endUser) {
        this.endUser = endUser;
    }

    public String getFlowID() {
        return flowID;
    }

    public void setFlowID(String flowID) {
        this.flowID = flowID;
    }

    public boolean isAdopt() {
        return isAdopt;
    }

    public void setAdopt(boolean adopt) {
        isAdopt = adopt;
    }

    public int getBackCode() {
        return backCode;
    }

    public void setBackCode(int backCode) {
        this.backCode = backCode;
    }
}
