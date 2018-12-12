package com.zkd.service;

public interface IProcessService {
    String getAllProcess()  ;

    String getProcessAllDetail(String data);

    String getDealProcess(String data);

    String getFinishedProcess(String data);
}
