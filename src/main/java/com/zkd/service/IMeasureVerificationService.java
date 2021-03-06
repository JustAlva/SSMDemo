package com.zkd.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IMeasureVerificationService {

    String load(String data);

    String submit(String data, List<MultipartFile> files, String basePath, String urlPath);

    String getDetail(String data);
}
