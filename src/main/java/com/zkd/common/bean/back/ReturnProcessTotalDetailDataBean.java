package com.zkd.common.bean.back;

import com.zkd.common.bean.back.tablebean.CommissionFillDataBean;
import com.zkd.entity.CurrentDealStep;

import java.util.List;

public class ReturnProcessTotalDetailDataBean {

    private CommissionFillDataBean commissionerFill;
    private List<CurrentDealStep> stepList;

    public ReturnProcessTotalDetailDataBean() {
    }

    public ReturnProcessTotalDetailDataBean(CommissionFillDataBean commissionerFill, List<CurrentDealStep> stepList) {
        this.commissionerFill = commissionerFill;
        this.stepList = stepList;
    }

    public CommissionFillDataBean getCommissionerFill() {
        return commissionerFill;
    }

    public void setCommissionerFill(CommissionFillDataBean commissionerFill) {
        this.commissionerFill = commissionerFill;
    }

    public List<CurrentDealStep> getStepList() {
        return stepList;
    }

    public void setStepList(List<CurrentDealStep> stepList) {
        this.stepList = stepList;
    }
}
