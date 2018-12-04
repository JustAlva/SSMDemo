package com.zkd.common.bean.back;

import com.zkd.entity.ContainmentFinished;
import com.zkd.entity.ContainmentFinishedWithBLOBs;
import com.zkd.entity.ContainmentParts;
import com.zkd.entity.ContainmentPartsWithBLOBs;

public class ReturnMeasureVerificationLoadDataBean {

    ContainmentPartsWithBLOBs partsData;
    ContainmentFinishedWithBLOBs finishData;

    public ReturnMeasureVerificationLoadDataBean(ContainmentPartsWithBLOBs partsData, ContainmentFinishedWithBLOBs finishData) {
        this.partsData = partsData;
        this.finishData = finishData;
    }

    public ContainmentPartsWithBLOBs getPartsData() {
        return partsData;
    }

    public void setPartsData(ContainmentPartsWithBLOBs partsData) {
        this.partsData = partsData;
    }

    public ContainmentFinishedWithBLOBs getFinishData() {
        return finishData;
    }

    public void setFinishData(ContainmentFinishedWithBLOBs finishData) {
        this.finishData = finishData;
    }
}
