package com.zkd.utils;

import com.zkd.common.bean.other.SaveFileDataBean;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.springframework.util.MimeType;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * describe: 文件处理工具
 * creator: keding.zhou
 * date: 2018/10/29 13:56
 */
public class FileUtils {

    /**
     * 保存多文件
     * @param files 文件list MultipartFile
     * @param basePath 保存文件根路径
     * @param saveType 保存类型
     * @param urlPath url根地址
     * @return 保存后的信息
     */
    public static List<SaveFileDataBean> saveFileBackPath(List<MultipartFile> files, String basePath, String saveType, String urlPath) {
        List<SaveFileDataBean> filePathList = new ArrayList<>();
        for (MultipartFile file : files) {
            SaveFileDataBean path = FileUtils.saveFile(file, basePath, saveType, urlPath);
            if (path != null  ) {
                filePathList.add(path);
            }
        }
        return filePathList;
    }

    /**
     * 保存单文件
     *
     * @param file     文件 MultipartFile
     * @param basePath 保存文件根路径
     * @param saveType 保存类型
     * @param urlPath  url根地址
     * @return 保存后的信息
     */
    public static SaveFileDataBean saveFile(MultipartFile file, String basePath, String saveType, String urlPath) {
        SaveFileDataBean returnBean;
        try {
            if (file != null) {
                String dirPath = basePath;
                String type = getFileType(file.getContentType());
                if (type.equals("")) {
                    type = getFileType2(file.getOriginalFilename());
                }
                String currentTimeString = MyDateUtils.getYearMonthDayString();
                String fileDirStr = "/upload/" + saveType + "/" + currentTimeString + "/";
                dirPath += "\\" + saveType + "\\" + currentTimeString + "\\";
                File fileDir = new File(dirPath);
                if (!fileDir.exists()) {
                    fileDir.mkdirs();
                }
                String newFileName = MyDateUtils.getCurrentDateString(MyDateUtils.FORMAT_TYPE_7) + StringUtils.randomString(5);
                if (!type.equals("")) {
                    newFileName += "." + type;
                }
                String filePath = dirPath + newFileName;
                File targetFile = new File(filePath);
                file.transferTo(targetFile);
                returnBean = new SaveFileDataBean(fileDirStr, filePath, file.getOriginalFilename(), urlPath + fileDirStr + newFileName);
                return returnBean;
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    /***
     * 获取文件类型
     * @param type MultipartFile 的 getContentType ，image/jpeg
     * @return jpg png 等
     */
    private static String getFileType(String type) {
        if (type != null) {
            MimeType mimeType = MimeType.valueOf(type);
            return mimeType.getSubtype();
        } else {
            return "";
        }
    }

    /**
     * 根据后缀获取文件fileName的类型
     *
     * @return String 文件的类型
     **/
    @NonNull
    private static String getFileType2(String fileName) {
        if (!fileName.equals("") && fileName.length() > 3) {
            int dot = fileName.lastIndexOf(".");
            if (dot > 0) {
                return fileName.substring(dot + 1);
            } else {
                return "";
            }
        }
        return "";
    }
}
