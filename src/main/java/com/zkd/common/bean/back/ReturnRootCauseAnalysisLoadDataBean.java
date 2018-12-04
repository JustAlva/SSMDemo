package com.zkd.common.bean.back;

import java.util.Date;
import java.util.List;

public class ReturnRootCauseAnalysisLoadDataBean {

    private List<String> needUpdateData ;//需要更新的数据
    private Date flowCreateDate ;//流程创建时间，即QRQC专员填写时间

    public ReturnRootCauseAnalysisLoadDataBean() {
    }

    public ReturnRootCauseAnalysisLoadDataBean(List<String> needUpdateData, Date flowCreateDate) {
        this.needUpdateData = needUpdateData;
        this.flowCreateDate = flowCreateDate;
    }

    public List<String> getNeedUpdateData() {
        return needUpdateData;
    }

    public void setNeedUpdateData(List<String> needUpdateData) {
        this.needUpdateData = needUpdateData;
    }

    public Date getFlowCreateDate() {
        return flowCreateDate;
    }

    public void setFlowCreateDate(Date flowCreateDate) {
        this.flowCreateDate = flowCreateDate;
    }
}
