package com.zkd.common.bean.back;

public class ReturnContainmentPartsLoadDataBean {

    private String exceptionType;

    public ReturnContainmentPartsLoadDataBean(String exceptionType) {
        this.exceptionType = exceptionType;
    }

    public ReturnContainmentPartsLoadDataBean() {
    }

    public String getExceptionType() {
        return exceptionType;
    }

    public void setExceptionType(String exceptionType) {
        this.exceptionType = exceptionType;
    }
}

