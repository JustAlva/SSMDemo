package com.zkd.common.bean.request.show;

public class RequestShowLoadBaseBean {
    int tableId;

    public RequestShowLoadBaseBean(int tableId) {
        this.tableId = tableId;
    }

    public int getTableId() {
        return tableId;
    }

    public void setTableId(int tableId) {
        this.tableId = tableId;
    }
}
