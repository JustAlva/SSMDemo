package com.zkd.common.bean.back;
/**
 * describe: 返回bean
 * creator: keding.zhou
 * date: 2018/7/10 13:40
 */
public class ReturnDataBean<T>{
    private int status;
    private T data;
    private String msg;

    /**
     * 返回data
     * @param status 返回码
     * @param data T data obj
     * @param msg 返回信息
     */
    public ReturnDataBean(int status, T data, String msg) {
        this.status = status;
        this.data = data;
        this.msg = msg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
