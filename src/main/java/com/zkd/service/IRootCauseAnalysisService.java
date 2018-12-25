package com.zkd.service;

public interface IRootCauseAnalysisService {
    String load(String data);

    String submit(String data);

    String getDetail(String data);

    String searchRepeatNumber(String data);
}
