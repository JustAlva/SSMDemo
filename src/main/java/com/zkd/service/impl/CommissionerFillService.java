package com.zkd.service.impl;

import com.zkd.common.bean.back.ReturnDataBean;
import com.zkd.common.bean.request.RequestCommissionerFillSubmitDataBean;
import com.zkd.common.constant.MsgConstant;
import com.zkd.dao.map.CommissionerFillMapper;
import com.zkd.dao.map.ConfOccurrenceSourceMapper;
import com.zkd.dao.map.ConfStepJumpMapper;
import com.zkd.service.ICommissionerFillService;
import com.zkd.utils.EncryptUtils;
import com.zkd.utils.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service("commissionerFillService")
public class CommissionerFillService implements ICommissionerFillService {

    @Autowired
    ConfOccurrenceSourceMapper occurrenceSourceDao;
    @Autowired
    ConfStepJumpMapper stepJumpDao;
    @Autowired
    CommissionerFillMapper commissionerFillDao;



    @Override
    public String getData() {
        List<String> occurrenceSourceList = occurrenceSourceDao.selectAll();
        return new EncryptUtils<>().encryptObj(new ReturnDataBean<>(MsgConstant.CODE_SUCCESS, occurrenceSourceList, MsgConstant.MSG_SUCCESS));
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    @Override
    public String addNew(String data, List<MultipartFile> files, String basePath) {
        RequestCommissionerFillSubmitDataBean requestBean = (RequestCommissionerFillSubmitDataBean) new EncryptUtils().decryptObj(data, RequestCommissionerFillSubmitDataBean.class);

        //1.存入数据




        List<String> filePathList = new ArrayList<>();
        if (files!=null) {
            for (MultipartFile file : files) {
                String path = FileUtils.saveFile(file, basePath, "commissioner");
                if (!path.equals("")) {
                    filePathList.add(path);
                }
            }
            if (filePathList.size() < files.size()) {
                throw new RuntimeException( MsgConstant.COMMISSIONER_SUBMIT_SAVE_FILE_FAIL);
               // return new EncryptUtils<>().encryptObj(new ReturnDataBean<>(MsgConstant.CODE_FAIL, "", MsgConstant.COMMISSIONER_SUBMIT_SAVE_FILE_FAIL));
            }
        }

        return null;
    }
}
