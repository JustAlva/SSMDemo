package com.zkd.common.bean.request;

public class RequestTotalProcessSearchDataBean {
    private String userCode;
    private String search;


    public RequestTotalProcessSearchDataBean(String search) {
        this.search = search;
    }

    public RequestTotalProcessSearchDataBean(String userCode, String search) {
        this.userCode = userCode;
        this.search = search;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }
}
