package com.zkd.utils;

import org.apache.commons.codec.binary.Base64;

import java.security.SecureRandom;
import java.util.List;
import java.util.Random;

public class StringUtils {


    /**
     * 随机生成n位随机字符串
     */
    public static String randomString(int n) {
        Random RANDOM = new SecureRandom();
        byte[] salt = new byte[n];
        RANDOM.nextBytes(salt);
        return new String(Base64.encodeBase64(salt)).substring(1, 1 + n);
    }

    public static Integer parseString2Int(String str){
        if (str == null || str.equals("")) {
            return 0;
        }else {
            return Integer.parseInt(str);
        }
    }
    public static Double parseString2Double(String str){
        if (str == null || str.equals("")) {
            return 0.0;
        }else {
            return Double.parseDouble(str);
        }
    }


    /**
     * list 转 string 以 ，隔开
     * @param list
     * @return
     */
    public static String list2String(List<String> list) {
        if (list!=null) {
            StringBuffer sb  = new StringBuffer();
            for (int i = 0; i < list.size(); i++) {
                sb.append(appendString(i, list.size(), list.get(i)));
            }
            return sb.toString();
        }else{
            return null;
        }
    }
    private static String appendString(int position, int size, String value) {
        if (position == 0 || ((position + 1) == size && size > 2)) {
            return value;
        } else {
            return "," + value;
        }
    }


}
