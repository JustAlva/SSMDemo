package com.zkd.utils;

import com.alibaba.druid.support.logging.Log;
import com.google.gson.Gson;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import com.zkd.common.constant.Constant;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import java.lang.reflect.Type;
import java.security.Key;
import java.util.logging.Logger;

/**
 * describe: 3des加密工具类
 * creator: keding.zhou
 * date: 2018/8/20 16:49
 */
public class EncryptUtils<T> {

    private Gson gson;

    public EncryptUtils() {
        if (this.gson == null) {
            gson = new Gson();
        }
    }

    public String encryptObj(T obj) {
        return encryptStr(gson.toJson(obj));
    }

    public T decryptObj(String data, Type type) {
        return gson.fromJson(decryptStr(data), type);
    }

    /**
     * 加密
     *
     * @param data 需要加密内容
     * @return 加密后字符串
     */
    private static String encryptStr(String data) {
        try {
            return encrypt(getKeyByte(Constant.ENCRYPT_APP_KEY), data.getBytes("UTF-8"));
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 解密
     *
     * @param data 需要解密字符串
     * @return 解密后字符串
     */
    public static String decryptStr(String data) {
        System.out.println("请求string："+data);
        String requestData = data.replace(" ", "+");
        return decrypt(getKeyByte(Constant.ENCRYPT_APP_KEY), Base64.decode(requestData));
    }

    private static String encrypt(byte[] key, byte[] data) {
        byte[] bOut = new byte[0];
        try {
            Key deskey;
            DESedeKeySpec spec = new DESedeKeySpec(key);
            SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
            deskey = keyfactory.generateSecret(spec);
            Cipher cipher = Cipher.getInstance("desede" + "/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, deskey);
            bOut = cipher.doFinal(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Base64.encode(bOut);
    }


    private static String decrypt(byte[] key, byte[] data) {
        String result = "";
        try {
            Key deskey;
            DESedeKeySpec spec;
            spec = new DESedeKeySpec(key);
            SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
            deskey = keyfactory.generateSecret(spec);
            Cipher cipher = Cipher.getInstance("desede" + "/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, deskey);
            byte[] bOut = cipher.doFinal(data);
            result = new String(bOut, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    private static byte[] getKeyByte(String key) {
        if (key == null) {
            return new byte[0];
        }
        int lenght = key.length();
        if (lenght >= 24) {
            return key.substring(0, 24).getBytes();
        } else {
            StringBuilder keyBuilder = new StringBuilder(key);
            for (int i = 0; i < (24 - lenght); i++) {
                keyBuilder.append("0");
            }
            key = keyBuilder.toString();
            return key.getBytes();
        }
    }

}
