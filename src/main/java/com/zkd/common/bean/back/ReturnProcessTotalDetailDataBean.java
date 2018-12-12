package com.zkd.common.bean.back;

import com.zkd.entity.CommissionerFill;
import com.zkd.entity.CurrentDealStep;

import java.util.List;

public class ReturnProcessTotalDetailDataBean {

    private CommissionerFill commissionerFill;
    private List<CurrentDealStep> stepList;

    public ReturnProcessTotalDetailDataBean() {
    }

    public ReturnProcessTotalDetailDataBean(CommissionerFill commissionerFill, List<CurrentDealStep> stepList) {
        this.commissionerFill = commissionerFill;
        this.stepList = stepList;
    }

    public CommissionerFill getCommissionerFill() {
        return commissionerFill;
    }

    public void setCommissionerFill(CommissionerFill commissionerFill) {
        this.commissionerFill = commissionerFill;
    }

    public List<CurrentDealStep> getStepList() {
        return stepList;
    }

    public void setStepList(List<CurrentDealStep> stepList) {
        this.stepList = stepList;
    }
}
