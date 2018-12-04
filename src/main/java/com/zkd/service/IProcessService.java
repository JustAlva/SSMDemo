package com.zkd.service;

public interface IProcessService {
    String getAllProcess() throws Exception;

    String getDealProcess(String data);

    String getFinishedProcess(String data);
}
