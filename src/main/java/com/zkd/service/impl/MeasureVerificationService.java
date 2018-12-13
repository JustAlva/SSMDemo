package com.zkd.service.impl;

import com.google.gson.Gson;
import com.zkd.common.bean.back.ReturnDataBean;
import com.zkd.common.bean.back.ReturnMeasureVerificationLoadDataBean;
import com.zkd.common.bean.other.SaveFileDataBean;
import com.zkd.common.bean.other.StepJumpBean;
import com.zkd.common.bean.request.RequestLoadBaseDataBean;
import com.zkd.common.bean.request.RequestMeasureVerificationSubmitDataBean;
import com.zkd.common.bean.request.show.RequestShowLoadBaseBean;
import com.zkd.common.constant.MsgConstant;
import com.zkd.common.constant.StepConstant;
import com.zkd.dao.map.*;
import com.zkd.entity.*;
import com.zkd.service.IMeasureVerificationService;
import com.zkd.utils.*;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("measureVerificationService")
public class MeasureVerificationService implements IMeasureVerificationService {

    @Autowired
    ContainmentPartsMapper containmentPartsDao;
    @Autowired
    ContainmentFinishedMapper containmentFinishedDao;
    @Autowired
    CommissionerFillMapper commissionerFillDao;
    @Autowired
    CurrentDealStepMapper currentDealStepDao;
    @Autowired
    MeasureVerificationQEMapper measureVerificationQEDao;
    @Autowired
    StepDealUserMapper stepDealUserDao;
    @Autowired
    RecordSubmitMapper recordSubmitDao;
    @Autowired
    QEIsClosedMapper qeIsClosedDao;
    @Autowired
    UploadImagesMapper uploadImagesDao;

    @Override
    public String load(String data) {
        RequestLoadBaseDataBean requestData = new EncryptUtils<RequestLoadBaseDataBean>().decryptObj(data, RequestLoadBaseDataBean.class);

        ContainmentPartsWithBLOBs partsData = containmentPartsDao.selectByFlowId(requestData.getFlowID());
        ContainmentFinishedWithBLOBs finishData = containmentFinishedDao.selectByFlowId(requestData.getFlowID());

        ReturnMeasureVerificationLoadDataBean loadData = new ReturnMeasureVerificationLoadDataBean(partsData, finishData);
        return new EncryptUtils<>().encryptObj(new ReturnDataBean<>(MsgConstant.CODE_SUCCESS, loadData, MsgConstant.MSG_SUCCESS));
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    @Override
    public String submit(String data, List<MultipartFile> files, String basePath, String urlPath) {
        ReturnDataBean returnData;
        RequestMeasureVerificationSubmitDataBean requestData = new EncryptUtils<RequestMeasureVerificationSubmitDataBean>().decryptObj(data, RequestMeasureVerificationSubmitDataBean.class);
        if (requestData != null) {

            CurrentDealStep currentDealStep = currentDealStepDao.selectByPrimaryKey(StringUtils.parseString2Int(requestData.getCurrentStepId()));
            if (currentDealStep.getFlag() == 0) {
                Date now = MyDateUtils.getCurrentDate();
                ProcessDealUtils processDealUtils = new ProcessDealUtils();
                StepJumpBean nextStep = new StepJumpBean(StepConstant.QRQC_CONTAINMENT_ACTION_FINISHED_CODE, StepConstant.QRQC_CONTAINMENT_ACTION_FINISHED_NAME, requestData.getUserCode(), requestData.getFlowID(), requestData.isAdopt());
                if (requestData.isAdopt()) {
                    List<CommissionerFill> commissionerList = commissionerFillDao.selectByFlowId(requestData.getFlowID());
                    nextStep.setEndStep(StepConstant.QRQC_QE_SURE_CLOSED_CODE, StepConstant.QRQC_QE_SURE_CLOSED_NAME, commissionerList.get(0).getSelectQe());
                } else {
                    if (requestData.getSelectBackStep().equals("21")) {
                        StepDealUser backStepUser = new StepDealUser();
                        backStepUser.setFlowId(requestData.getFlowID());
                        backStepUser.setStepCode(StepConstant.QRQC_CONTAINMENT_ACTION_PARTS_CODE+"");
                        List<StepDealUser> userList =stepDealUserDao.selectDealUser(backStepUser);
                        nextStep.setEndStep(StepConstant.QRQC_CONTAINMENT_ACTION_PARTS_CODE, StepConstant.QRQC_CONTAINMENT_ACTION_PARTS_NAME, userList.get(0).getDealUser());
                        //
                        CurrentDealStep lastStep = new CurrentDealStep();
                        lastStep.setFlowId(requestData.getFlowID());
                        lastStep.setCurrentStepCode(StepConstant.QRQC_CONTAINMENT_ACTION_PARTS_CODE);
                        currentDealStepDao.updateIsMain(lastStep);
                        containmentPartsDao.updateIsMain(lastStep);
                    } else if (requestData.getSelectBackStep().equals("22")) {
                        StepDealUser backStepUser = new StepDealUser();
                        backStepUser.setFlowId(requestData.getFlowID());
                        backStepUser.setStepCode(StepConstant.QRQC_CONTAINMENT_ACTION_FINISHED_CODE+"");
                        List<StepDealUser> userList =stepDealUserDao.selectDealUser(backStepUser);
                        nextStep.setEndStep(StepConstant.QRQC_CONTAINMENT_ACTION_FINISHED_CODE, StepConstant.QRQC_CONTAINMENT_ACTION_FINISHED_NAME,  userList.get(0).getDealUser());
                        CurrentDealStep lastStep = new CurrentDealStep();
                        lastStep.setFlowId(requestData.getFlowID());
                        lastStep.setCurrentStepCode(StepConstant.QRQC_CONTAINMENT_ACTION_FINISHED_CODE);
                        currentDealStepDao.updateIsMain(lastStep);
                        containmentFinishedDao.updateIsMain(lastStep);
                    }
                }

                //1.保存当前步骤信息
                MeasureVerificationQE measureVerificationQE = new MeasureVerificationQE(StringUtils.parseString2Int(requestData.getStepTableId()), 5, requestData.getFlowID(), requestData.getRepeatNumber(), requestData.isWrongCheck() ? (byte) 1 : (byte) 0, requestData.isAdopt() ? (byte) 1 : (byte) 0, requestData.getSelectBackStep(), requestData.getUserCode(), now, requestData.getMeasureEffect());
                measureVerificationQEDao.updateByPrimaryKeySelective(measureVerificationQE);

                //2.将当前节点处理表中该步骤结束
                processDealUtils.endCurrentStep(currentDealStepDao, StringUtils.parseString2Int(requestData.getCurrentStepId()),requestData.isAdopt(),  requestData.getUserCode(), now);

                boolean tag = true;
                //3.在下个节点表中插入一条记录，并返回id
                switch (nextStep.getEndCode()) {
                    case StepConstant.QRQC_CONTAINMENT_ACTION_PARTS_CODE:
                        ContainmentParts containmentParts = new ContainmentParts(1, nextStep.getStartUser(), now,(byte)1,nextStep.getStartUser(), nextStep.getFlowID());
                        containmentPartsDao.insertBackId(containmentParts);
                        nextStep.setEndTableId(containmentParts.getId());
                        break;
                    case StepConstant.QRQC_CONTAINMENT_ACTION_FINISHED_CODE:
                        ContainmentFinished containmentFinished = new ContainmentFinished(1, nextStep.getStartUser(), now,(byte)1,nextStep.getStartUser(), nextStep.getFlowID());
                        containmentFinishedDao.insertBackId(containmentFinished);
                        nextStep.setEndTableId(containmentFinished.getId());
                        break;
                    case StepConstant.QRQC_QE_SURE_CLOSED_CODE:
                        //其他节点是否完成，完成则插入下一节点信息，否则不插入
                        List<CurrentDealStep> stepList = currentDealStepDao.selectByFlowId(currentDealStep);
                        for (CurrentDealStep step : stepList) {
                            if (step.getFlag().toString().equals("0")) {
                                tag = false;
                            }
                        }
                        if (tag) {
                            QEIsClosed qeIsClosed = new QEIsClosed(1, nextStep.getStartUser(), now, nextStep.getFlowID());
                            qeIsClosedDao.insertBackId(qeIsClosed);
                            nextStep.setEndTableId(qeIsClosed.getId());
                        }
                        break;
                }
                if (tag) {
                    //processDealUtils.newCurrentStep(currentDealStepDao, stepDealUserDao, nextStep, now);
                    processDealUtils.newCurrentStep(currentDealStepDao, stepDealUserDao, nextStep, now,StringUtils.parseString2Int(requestData.getCurrentStepId()),StringUtils.parseString2Int(requestData.getStepTableId()));
                }
                processDealUtils.saveRecord(recordSubmitDao, nextStep, new Gson().toJson(requestData), "措施验证", now);

                //10.保存文件
                if (files != null) {
                    List<SaveFileDataBean> filePathList = FileUtils.saveFileBackPath(files, basePath, "measureVerification", urlPath);

                    if (filePathList.size() < files.size()) {
                        throw new RuntimeException(MsgConstant.COMMISSIONER_SUBMIT_SAVE_FILE_FAIL);
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
                returnData = new ReturnDataBean<>(MsgConstant.CODE_FAIL, "", MsgConstant.COMMON_STEP_DEALED);
            }
        } else {
            returnData = new ReturnDataBean<>(MsgConstant.CODE_FAIL, "", MsgConstant.COMMON_SAVE_FAIL);
        }
        return new EncryptUtils<>().encryptObj(returnData);
    }

    @Override
    public String getDetail(String data) {
        RequestShowLoadBaseBean requestData = new EncryptUtils<RequestShowLoadBaseBean>().decryptObj(data, RequestShowLoadBaseBean.class);
        MeasureVerificationQE detail = measureVerificationQEDao.selectByPrimaryKey(requestData.getTableId());
        return new EncryptUtils<>().encryptObj(new ReturnDataBean<>(MsgConstant.CODE_SUCCESS, detail, MsgConstant.MSG_SUCCESS));
    }
}
