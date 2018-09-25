package com.zkd.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ICommissionerFillService {

    String getData() ;

    String addNew(String data,List<MultipartFile> files,String basePath);

}
