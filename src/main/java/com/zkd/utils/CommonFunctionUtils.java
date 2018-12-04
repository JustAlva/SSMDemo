package com.zkd.utils;

import com.zkd.entity.RootCauseAnalysisWithBLOBs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommonFunctionUtils {


    public static List<String> getUploadDataList(List<RootCauseAnalysisWithBLOBs> rootList){
        List<String> datas = new ArrayList<>();
        for (RootCauseAnalysisWithBLOBs rootBean : rootList) {
            String needDatas = rootBean.getNeedUpdateData();
            List<String> listStr = Arrays.asList(needDatas.split(","));
            for (String str : listStr) {
                if (!datas.contains(str)) {
                    datas.add(str);
                }
            }
        }
        return datas;
    }
}
