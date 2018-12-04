package com.zkd.common.bean.other;

public class SaveFileDataBean {

    private String dirPath;
    private String filePath;
    private String fileName;
    private String fileUrl;

    public SaveFileDataBean(String dirPath, String filePath, String fileName, String fileUrl) {
        this.dirPath = dirPath;
        this.filePath = filePath;
        this.fileName = fileName;
        this.fileUrl = fileUrl;
    }

    public String getDirPath() {
        return dirPath;
    }

    public void setDirPath(String dirPath) {
        this.dirPath = dirPath;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }
}
