package com.zkd.common.bean.back;

import java.util.List;

public class ReturnIsCloseSureLoadDataBean {
    boolean isClosePre;//是否提前关闭
    List<String> datas;

    public ReturnIsCloseSureLoadDataBean(boolean isClosePre, List<String> datas) {
        this.isClosePre = isClosePre;
        this.datas = datas;
    }

    public boolean isClosePre() {
        return isClosePre;
    }

    public void setClosePre(boolean closePre) {
        isClosePre = closePre;
    }

    public List<String> getDatas() {
        return datas;
    }

    public void setDatas(List<String> datas) {
        this.datas = datas;
    }
}
