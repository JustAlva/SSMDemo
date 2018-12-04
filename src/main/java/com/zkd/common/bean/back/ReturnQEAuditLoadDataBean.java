package com.zkd.common.bean.back;

import com.zkd.common.bean.other.UserDataBean;
import com.zkd.entity.CurrentDealStep;
import com.zkd.entity.RootCauseAnalysisWithBLOBs;

public class ReturnQEAuditLoadDataBean {
    UserDataBean leader;
    UserDataBean dealUser;//根本原因分析和改善措施的处理人员
    CurrentDealStep departmentAudit;
    RootCauseAnalysisWithBLOBs rootCauseAnalysis;


    public ReturnQEAuditLoadDataBean(UserDataBean leader, UserDataBean dealUser, CurrentDealStep departmentAudit, RootCauseAnalysisWithBLOBs rootCauseAnalysis) {
        this.leader = leader;
        this.dealUser = dealUser;
        this.departmentAudit = departmentAudit;
        this.rootCauseAnalysis = rootCauseAnalysis;
    }

    public UserDataBean getLeader() {
        return leader;
    }

    public void setLeader(UserDataBean leader) {
        this.leader = leader;
    }

    public UserDataBean getDealUser() {
        return dealUser;
    }

    public void setDealUser(UserDataBean dealUser) {
        this.dealUser = dealUser;
    }

    public CurrentDealStep getDepartmentAudit() {
        return departmentAudit;
    }

    public void setDepartmentAudit(CurrentDealStep departmentAudit) {
        this.departmentAudit = departmentAudit;
    }

    public RootCauseAnalysisWithBLOBs getRootCauseAnalysis() {
        return rootCauseAnalysis;
    }

    public void setRootCauseAnalysis(RootCauseAnalysisWithBLOBs rootCauseAnalysis) {
        this.rootCauseAnalysis = rootCauseAnalysis;
    }
}
