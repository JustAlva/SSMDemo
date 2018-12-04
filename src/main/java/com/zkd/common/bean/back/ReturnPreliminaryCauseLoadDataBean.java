package com.zkd.common.bean.back;

import com.zkd.common.bean.back.bean.ReturnPreliminaryCauseNextStepDealDataBean;

import java.util.List;

public class ReturnPreliminaryCauseLoadDataBean {

    private List<ReturnPreliminaryCauseNextStepDealDataBean> selectDepartment;
    private List<String> exceptionType;
    private List<String> resType;
    private String backFlag;

    public ReturnPreliminaryCauseLoadDataBean() {
    }

    public ReturnPreliminaryCauseLoadDataBean(List<ReturnPreliminaryCauseNextStepDealDataBean> selectDepartment, List<String> exceptionType, List<String> resType, String backFlag) {
        this.selectDepartment = selectDepartment;
        this.exceptionType = exceptionType;
        this.resType = resType;
        this.backFlag = backFlag;
    }

    public List<ReturnPreliminaryCauseNextStepDealDataBean> getSelectDepartment() {
        return selectDepartment;
    }

    public void setSelectDepartment(List<ReturnPreliminaryCauseNextStepDealDataBean> selectDepartment) {
        this.selectDepartment = selectDepartment;
    }

    public List<String> getExceptionType() {
        return exceptionType;
    }

    public void setExceptionType(List<String> exceptionType) {
        this.exceptionType = exceptionType;
    }

    public List<String> getResType() {
        return resType;
    }

    public void setResType(List<String> resType) {
        this.resType = resType;
    }

    public String getBackFlag() {
        return backFlag;
    }

    public void setBackFlag(String backFlag) {
        this.backFlag = backFlag;
    }
}
