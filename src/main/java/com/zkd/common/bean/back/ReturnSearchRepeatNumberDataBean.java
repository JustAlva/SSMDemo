package com.zkd.common.bean.back;

import com.zkd.entity.CurrentDealStep;
import com.zkd.entity.RootCauseAnalysisWithBLOBs;

public class ReturnSearchRepeatNumberDataBean {
    private RootCauseAnalysisWithBLOBs rootCause;
    private CurrentDealStep currentStep;

    public ReturnSearchRepeatNumberDataBean(RootCauseAnalysisWithBLOBs rootCause, CurrentDealStep currentStep) {
        this.rootCause = rootCause;
        this.currentStep = currentStep;
    }

    public RootCauseAnalysisWithBLOBs getRootCause() {
        return rootCause;
    }

    public void setRootCause(RootCauseAnalysisWithBLOBs rootCause) {
        this.rootCause = rootCause;
    }

    public CurrentDealStep getCurrentStep() {
        return currentStep;
    }

    public void setCurrentStep(CurrentDealStep currentStep) {
        this.currentStep = currentStep;
    }
}
