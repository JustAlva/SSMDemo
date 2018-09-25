package com.zkd.utils;

import org.springframework.util.MimeType;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public class FileUtils {

    public static String saveFile(MultipartFile file, String basePath, String saveType) {
        try {
            if (file != null) {
                String dirPath = basePath;
                String type = getFileType(file.getContentType());
                dirPath += "\\" + saveType + "\\" + MyDateUtils.getYearMonthDayString() + "\\";
                File fileDir = new File(dirPath);
                if (!fileDir.exists()) {
                    fileDir.mkdirs();
                }
                String newFileName = MyDateUtils.getCurrentDateString(MyDateUtils.FORMAT_TYPE_7) + StringUtils.randomString(5) + "." + type;
                String filePath = dirPath + newFileName;
                File targetFile = new File(filePath);
                file.transferTo(targetFile);
                return filePath;
            } else {
                return "";
            }
        } catch (Exception e) {
            return "";
        }
    }

    /***
     * 获取文件类型
     * @param type MultipartFile 的 getContentType ，image/jpeg
     * @return jpg png 等
     */
    private static String getFileType(String type) {
        MimeType mimeType = MimeType.valueOf(type);
        return mimeType.getSubtype();
    }
}
