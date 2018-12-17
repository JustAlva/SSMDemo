package com.zkd.common.bean.back;

import com.zkd.common.bean.back.tablebean.CommissionFillDataBean;
import com.zkd.common.bean.other.UserDataBean;

/**
 * describe: 流程信息
 * creator: keding.zhou
 * date: 2018/8/22 14:38
 */
public class ReturnProcessDataBean {
    private Integer status;
    private String flowId;
    private UserDataBean createUser;
    private String createDate;
    private Byte isClosedPre;
    private UserDataBean lastDealUser;
    private String lastDealDate;
    private CommissionFillDataBean commissionerFill;

    public ReturnProcessDataBean(Integer status, String flowId, String createDate, Byte isClosedPre, String lastDealDate) {
        this.status = status;
        this.flowId = flowId;
        this.createDate = createDate;
        this.isClosedPre = isClosedPre;
        this.lastDealDate = lastDealDate;
    }

    public ReturnProcessDataBean(Integer status, String flowId, UserDataBean createUser, String createDate, Byte isClosedPre, UserDataBean lastDealUser, String lastDealDate, CommissionFillDataBean commissionerFill) {
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

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
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

    public String getLastDealDate() {
        return lastDealDate;
    }

    public void setLastDealDate(String lastDealDate) {
        this.lastDealDate = lastDealDate;
    }

    public CommissionFillDataBean getCommissionerFill() {
        return commissionerFill;
    }

    public void setCommissionerFill(CommissionFillDataBean commissionerFill) {
        this.commissionerFill = commissionerFill;
    }
}
