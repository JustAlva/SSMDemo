package com.zkd.common.bean.request;

public class RequestLoginBean {
    private String userId;
    private String userPsd;

    public RequestLoginBean(String userId, String userPsd) {
        this.userId = userId;
        this.userPsd = userPsd;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserPsd() {
        return userPsd;
    }

    public void setUserPsd(String userPsd) {
        this.userPsd = userPsd;
    }
}
