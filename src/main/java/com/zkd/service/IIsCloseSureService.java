package com.zkd.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IIsCloseSureService {
    String load(String data);

    String submit(String data, List<MultipartFile> files, String basePath, String urlPath);
}
