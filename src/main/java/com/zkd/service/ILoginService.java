package com.zkd.service;


public interface ILoginService {
    String checkUserLogin(String data) throws Exception;

    String rootLogin(String data);
}
