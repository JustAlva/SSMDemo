package com.zkd.common.bean.back;
import com.zkd.common.bean.other.UserDataBean;
import com.zkd.entity.CommissionerFill;

import java.util.Date;

/**
 * describe: 流程信息
 * creator: keding.zhou
 * date: 2018/8/22 14:38
 */
public class ReturnProcessDataBean {
   private  Integer status;
   private  String flowId;
   private  UserDataBean createUser;
   private  java.util.Date createDate;
   //CurrentStepDataBean currentStepData;
   private  Byte isClosedPre;
   private  UserDataBean lastDealUser;
   private  Date lastDealDate;
   private  CommissionerFill commissionerFill;

    public ReturnProcessDataBean(Integer status, String flowId, Date createDate, Byte isClosedPre, Date lastDealDate) {
        this.status = status;
        this.flowId = flowId;
        this.createDate = createDate;
        this.isClosedPre = isClosedPre;
        this.lastDealDate = lastDealDate;
    }

    public ReturnProcessDataBean(Integer status, String flowId, UserDataBean createUser, Date createDate, Byte isClosedPre, UserDataBean lastDealUser, Date lastDealDate, CommissionerFill commissionerFill) {
        this.status = status;
        this.flowId = flowId;
        this.createUser = createUser;
        this.createDate = createDate;
        this.isClosedPre = isClosedPre;
        this.lastDealUser = lastDealUser;
        this.lastDealDate = lastDealDate;
        this.commissionerFill = commissionerFill;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getflowId() {
        return flowId;
    }

    public void setflowId(String flowId) {
        this.flowId = flowId;
    }

    public UserDataBean getCreateUser() {
        return createUser;
    }

    public void setCreateUser(UserDataBean createUser) {
        this.createUser = createUser;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Byte getIsClosedPre() {
        return isClosedPre;
    }

    public void setIsClosedPre(Byte isClosedPre) {
        this.isClosedPre = isClosedPre;
    }

    public UserDataBean getLastDealUser() {
        return lastDealUser;
    }

    public void setLastDealUser(UserDataBean lastDealUser) {
        this.lastDealUser = lastDealUser;
    }

    public Date getLastDealDate() {
        return lastDealDate;
    }

    public void setLastDealDate(Date lastDealDate) {
        this.lastDealDate = lastDealDate;
    }

    public CommissionerFill getCommissionerFill() {
        return commissionerFill;
    }

    public void setCommissionerFill(CommissionerFill commissionerFill) {
        this.commissionerFill = commissionerFill;
    }
}
