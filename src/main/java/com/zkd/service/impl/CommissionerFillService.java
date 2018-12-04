package com.zkd.service.impl;

import com.google.gson.Gson;
import com.zkd.common.bean.back.ReturnDataBean;
import com.zkd.common.bean.other.SaveFileDataBean;
import com.zkd.common.bean.other.StepJumpBean;
import com.zkd.common.bean.request.RequestCommissionerFillSubmitDataBean;
import com.zkd.common.constant.MsgConstant;
import com.zkd.common.constant.StepConstant;
import com.zkd.dao.map.*;
import com.zkd.entity.*;
import com.zkd.service.ICommissionerFillService;
import com.zkd.utils.EncryptUtils;
import com.zkd.utils.FileUtils;
import com.zkd.utils.MyDateUtils;
import com.zkd.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("commissionerFillService")
public class CommissionerFillService implements ICommissionerFillService {


    @Autowired
    ConfOccurrenceSourceMapper occurrenceSourceDao;
    @Autowired
    ConfStepJumpMapper stepJumpDao;
    @Autowired
    CommissionerFillMapper commissionerFillDao;
    @Autowired
    TotalFlowMapper totalFlowDao;
    @Autowired
    CurrentDealStepMapper currentDealStepDao;
    @Autowired
    PreliminaryCauseAnalysisMapper preliminaryCauseAnalysisDao;
    @Autowired
    StepDealUserMapper stepDealUserDao;
    @Autowired
    RecordSubmitMapper recordSubmitDao;
    @Autowired
    UploadImagesMapper uploadImagesDao;


    @Override
    public String getData() {
        List<String> occurrenceSourceList = occurrenceSourceDao.selectAll();
        return new EncryptUtils<>().encryptObj(new ReturnDataBean<>(MsgConstant.CODE_SUCCESS, occurrenceSourceList, MsgConstant.MSG_SUCCESS));
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    @Override
    public String addNew(String data, List<MultipartFile> files, String basePath, String urlPath) {
        ReturnDataBean returnData;
        RequestCommissionerFillSubmitDataBean requestBean = (RequestCommissionerFillSubmitDataBean) new EncryptUtils().decryptObj(data, RequestCommissionerFillSubmitDataBean.class);

        Date now = MyDateUtils.getCurrentDate();
        // 年月日时分秒+5位随机
        String newFlowId = MyDateUtils.getCurrentDateString(MyDateUtils.FORMAT_TYPE_7) + StringUtils.randomString(5);
        CommissionerFill commissionerFill = new CommissionerFill(
                5,
                requestBean.getUserCode(),
                now,
                newFlowId,
                requestBean.getModel(),
                requestBean.getProductLine(),
                requestBean.getCustomer(),
                requestBean.getOccurrenceSource(),
                MyDateUtils.getDate(requestBean.getOccurrenceDate()),
                requestBean.getWorksheet(),
                requestBean.getSupplier(),
                MyDateUtils.getDate(requestBean.getReportDate()),
                MyDateUtils.getDate(requestBean.getReceiveDate()),
                requestBean.getReportDepartment() + "",
                requestBean.getPersonal(),
                requestBean.getSurePersonal(),
                MyDateUtils.getDate(requestBean.getProductDate()),
                requestBean.getProductedNum(),
                requestBean.getUnhealthyNum(),
                requestBean.getUnhealthyRate(),
                requestBean.getSelectPeOrQe(),
                requestBean.getSelectQE(),
                requestBean.getPhenomena()
        );
        //1.存入数据
        int insertTag = commissionerFillDao.insertBackId(commissionerFill);
        if (insertTag == 1) {
            //成功
            int newId = commissionerFill.getId();
            //1->11
            //nextStepDataBean nextStep = new nextStepDataBean(newFlowId, newId, requestBean.getUserCode(), StepConstant.QRQC_COMMISSIONER_FILL_NAME, StepConstant.QRQC_COMMISSIONER_FILL_CODE, StepConstant.QRQC_CAUSE_ANALYSIS_PRELIMINARY_NAME, StepConstant.QRQC_CAUSE_ANALYSIS_PRELIMINARY_CODE, requestBean.getSelectPeOrQe());

            StepJumpBean nextStep = new StepJumpBean(StepConstant.QRQC_COMMISSIONER_FILL_CODE, StepConstant.QRQC_COMMISSIONER_FILL_NAME, requestBean.getUserCode(), newFlowId ,true);
            nextStep.setEndStep( StepConstant.QRQC_CAUSE_ANALYSIS_PRELIMINARY_CODE, StepConstant.QRQC_CAUSE_ANALYSIS_PRELIMINARY_NAME, requestBean.getSelectPeOrQe());

            //2.在总表中插入一条新的纪录
            TotalFlow totalFlow = new TotalFlow(1, nextStep.getFlowID(), nextStep.getStartUser(), now, nextStep.getStartUser(),nextStep.getStartName(), nextStep.getStartCode()+ "", now, nextStep.getStartUser());
            totalFlowDao.insertSelective(totalFlow);

            //3.在当前处理表中插入qrqc专员填写完毕信息
            CurrentDealStep currentDealStep = new CurrentDealStep((byte) 1,0, nextStep.getStartUser(), now, nextStep.getStartCode(),nextStep.getStartName(),newId, nextStep.getFlowID(), nextStep.getStartCode(), nextStep.getStartName(), nextStep.getStartUser(), newId, nextStep.getStartUser(), now);
            currentDealStepDao.insertBackId(currentDealStep);

            //4.下个节点新插一条数据 11初步原因分析
            PreliminaryCauseAnalysis preliminaryCauseAnalysis = new PreliminaryCauseAnalysis(1, nextStep.getStartUser(), now, nextStep.getFlowID());
            preliminaryCauseAnalysisDao.insertBackId(preliminaryCauseAnalysis);
            nextStep.setEndTableId(preliminaryCauseAnalysis.getId());

            //5.在当前处理表中插入新节点待处理信息
            CurrentDealStep nextDealStep = new CurrentDealStep((byte) 0,currentDealStep.getId(), nextStep.getStartUser(), now, nextStep.getStartCode(),nextStep.getStartName(),newId, nextStep.getFlowID(), nextStep.getEndCode(), nextStep.getEndName(), nextStep.getEndUser(), nextStep.getEndTableId(), nextStep.getStartUser(), now);
            currentDealStepDao.insertSelective(nextDealStep);

            //6.在处理人表中插入qrqc专员填写的处理人员
            StepDealUser qrqcDealUser = new StepDealUser(nextStep.getFlowID(), nextStep.getStartCode()+"" , nextStep.getStartUser(), nextStep.getStartUser(), now);
            stepDealUserDao.insertSelective(qrqcDealUser);

            //7.在处理人表中插入下个节点的处理人员
            StepDealUser stepDealUser = new StepDealUser(nextStep.getFlowID(), nextStep.getEndCode() + "", nextStep.getEndUser(), nextStep.getStartUser(), now);
            stepDealUserDao.insertSelective(stepDealUser);

            //8.更新总表中下个节点处理人信息
            //TotalFlow nextTotalFlow = new TotalFlow(nextStep.getFlowId(), nextStep.getNextStepDealUser(), nextStep.getNextStepName(), nextStep.getNextStepCode() + "", now, nextStep.getCurrentDealUser());
            //totalFlowDao.updateByFlowId(nextTotalFlow);

            //9.保存记录
            RecordSubmit recordSubmit = new RecordSubmit(null, nextStep.getFlowID(), "QRQC专员填写", nextStep.getStartUser(), now, new Gson().toJson(requestBean));
            recordSubmitDao.insertSelective(recordSubmit);

            //10.保存文件
            List<SaveFileDataBean> filePathList = new ArrayList<>();
            if (files != null) {
                for (MultipartFile file : files) {
                    SaveFileDataBean path = FileUtils.saveFile(file, basePath, "commissioner", urlPath);
                    if (!"".equals(path)) {
                        filePathList.add(path);
                    }
                }
                if (filePathList.size() < files.size()) {
                    throw new RuntimeException(MsgConstant.COMMISSIONER_SUBMIT_SAVE_FILE_FAIL+"");
                } else {
                    //11.保存文件地址
                    for (SaveFileDataBean fileData : filePathList) {
                        UploadImagesWithBLOBs uploadImages = new UploadImagesWithBLOBs(null, 1, nextStep.getStartUser(), now, nextStep.getFlowID(), currentDealStep.getId(), fileData.getFileName(), fileData.getDirPath(), fileData.getFilePath(), fileData.getFileUrl());
                        uploadImagesDao.insertSelective(uploadImages);
                    }
                }
            }
            returnData = new ReturnDataBean<>(MsgConstant.CODE_SUCCESS, "", MsgConstant.COMMON_SAVE_SUCCESS);
        } else {
            returnData = new ReturnDataBean<>(MsgConstant.CODE_FAIL, "", MsgConstant.COMMON_SAVE_FAIL);
        }
        return new EncryptUtils<>().encryptObj(returnData);
    }
}
