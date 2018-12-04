package com.zkd.common.bean.back;

import com.zkd.entity.QEIsClosed;
import com.zkd.entity.UploadImagesWithBLOBs;

import java.util.List;

public class ReturnQEChiefAuditLoadDataBean {
    private  QEIsClosed qeIsClosed;
    private  List<UploadImagesWithBLOBs> fileList;
    private  ReturnIsCloseSureLoadDataBean loadData;

    public ReturnQEChiefAuditLoadDataBean(QEIsClosed qeIsClosed, List<UploadImagesWithBLOBs> fileList, ReturnIsCloseSureLoadDataBean loadData) {
        this.qeIsClosed = qeIsClosed;
        this.fileList = fileList;
        this.loadData = loadData;
    }

    public ReturnIsCloseSureLoadDataBean getLoadData() {
        return loadData;
    }

    public void setLoadData(ReturnIsCloseSureLoadDataBean loadData) {
        this.loadData = loadData;
    }

    public ReturnQEChiefAuditLoadDataBean() {
    }

    public QEIsClosed getQeIsClosed() {
        return qeIsClosed;
    }

    public void setQeIsClosed(QEIsClosed qeIsClosed) {
        this.qeIsClosed = qeIsClosed;
    }

    public List<UploadImagesWithBLOBs> getFileList() {
        return fileList;
    }

    public void setFileList(List<UploadImagesWithBLOBs> fileList) {
        this.fileList = fileList;
    }
}
