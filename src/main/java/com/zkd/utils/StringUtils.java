package com.zkd.utils;

import org.apache.commons.codec.binary.Base64;

import java.security.SecureRandom;
import java.util.Random;

public class StringUtils {


    /**
     * 随机生成n位随机字符串
     */
    public static String randomString(int n) {
        Random RANDOM = new SecureRandom();
        byte[] salt = new byte[n];
        RANDOM.nextBytes(salt);
        return new String(Base64.encodeBase64(salt)).substring(1, 1+n);
    }
}
