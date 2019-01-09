package com.zkd.common.bean.back;

import com.zkd.entity.CommissionerFill;

import java.util.List;

public class ReturnCommissionerFillShowDataBean {

   private  CommissionerFill commissionerFill;
   private  List<String> imgList;

    public ReturnCommissionerFillShowDataBean() {
    }

    public ReturnCommissionerFillShowDataBean(CommissionerFill commissionerFill, List<String> imgList) {
        this.commissionerFill = commissionerFill;
        this.imgList = imgList;
    }

    public CommissionerFill getCommissionerFill() {
        return commissionerFill;
    }

    public void setCommissionerFill(CommissionerFill commissionerFill) {
        this.commissionerFill = commissionerFill;
    }

    public List<String> getImgList() {
        return imgList;
    }

    public void setImgList(List<String> imgList) {
        this.imgList = imgList;
    }
}
