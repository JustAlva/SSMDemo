package com.zkd.common.bean.request;

public class RequestSelectStaffSearchBean {
    private String search;

    public RequestSelectStaffSearchBean(String search) {
        this.search = search;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }
}
