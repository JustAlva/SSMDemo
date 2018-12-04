package com.zkd.service.impl;

import com.google.gson.Gson;
import com.zkd.common.bean.back.ReturnDataBean;
import com.zkd.common.bean.back.ReturnIsCloseSureLoadDataBean;
import com.zkd.common.bean.other.SaveFileDataBean;
import com.zkd.common.bean.other.StepJumpBean;
import com.zkd.common.bean.request.RequestIsCloseSureSubmitDataBean;
import com.zkd.common.bean.request.RequestLoadBaseDataBean;
import com.zkd.common.constant.MsgConstant;
import com.zkd.common.constant.StepConstant;
import com.zkd.dao.map.*;
import com.zkd.entity.*;
import com.zkd.service.IIsCloseSureService;
import com.zkd.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service("isCloseSureService")
public class IsCloseSureService implements IIsCloseSureService {

    @Autowired
    RootCauseAnalysisMapper rootCauseAnalysisDao;
    @Autowired
    TotalFlowMapper totalFlowDao;
    @Autowired
    CurrentDealStepMapper currentDealStepDao;
    @Autowired
    CommissionerFillMapper commissionerFillDao;
    @Autowired
    MeasureVerificationQEMapper measureVerificationQEDao;
    @Autowired
    QEAuditMapper qeAuditDao;
    @Autowired
    QEChiefAuditMapper qeChiefAuditDao;
    @Autowired
    StepDealUserMapper stepDealUserDao;
    @Autowired
    RecordSubmitMapper recordSubmitDao;
    @Autowired
    UploadImagesMapper uploadImagesDao;
    @Autowired
    QEIsClosedMapper qeIsClosedDao;
    @Autowired
    UserInfoMapper userInfoDao;

    @Override
    public String load(String data) {
        RequestLoadBaseDataBean requestData = new EncryptUtils<RequestLoadBaseDataBean>().decryptObj(data, RequestLoadBaseDataBean.class);
        //
        TotalFlow totalFlow = totalFlowDao.selectByFlowId(requestData.getFlowID());

        List<String> datas = new ArrayList<>();
        List<RootCauseAnalysisWithBLOBs> rootList = rootCauseAnalysisDao.selectUseful(requestData.getFlowID());
        if (rootList != null) {
            datas= CommonFunctionUtils.getUploadDataList(rootList);
        }
        ReturnIsCloseSureLoadDataBean dataBean = new ReturnIsCloseSureLoadDataBean(totalFlow.getIsClosedPre() == 1, datas);
        return new EncryptUtils<>().encryptObj(new ReturnDataBean<>(MsgConstant.CODE_SUCCESS, dataBean, MsgConstant.MSG_SUCCESS));
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    @Override
    public String submit(String data, List<MultipartFile> files, String basePath, String urlPath) {
        ReturnDataBean returnData;
        RequestIsCloseSureSubmitDataBean requestData = new EncryptUtils<RequestIsCloseSureSubmitDataBean>().decryptObj(data, RequestIsCloseSureSubmitDataBean.class);
        if (requestData != null) {
            CurrentDealStep currentDealStep = currentDealStepDao.selectByPrimaryKey(requestData.getCurrentStepId());
            if (currentDealStep.getFlag() == 0) {
                Date now = MyDateUtils.getCurrentDate();
                ProcessDealUtils processDealUtils = new ProcessDealUtils();

                StepJumpBean nextStep = new StepJumpBean(StepConstant.QRQC_QE_SURE_CLOSED_CODE, StepConstant.QRQC_QE_SURE_CLOSED_NAME, requestData.getUserCode(), requestData.getFlowID(), requestData.isAdopt());
                if (requestData.isAdopt()) {
                    UserInfo leaderInfo = userInfoDao.selectLeader(requestData.getUserCode());
                    nextStep.setEndStep(StepConstant.QRQC_QE_CHIEF_AUDIT_CODE, StepConstant.QRQC_QE_CHIEF_AUDIT_NAME, leaderInfo.getUserNo());
                } else {
                    List<CommissionerFill> commissionerList = commissionerFillDao.selectByFlowId(requestData.getFlowID());

                    switch (requestData.getSelectBackStep()) {
                        case StepConstant.QRQC_MEASURE_VERIFICATION_QE_CODE + "":
                            nextStep.setEndStep(StepConstant.QRQC_MEASURE_VERIFICATION_QE_CODE, StepConstant.QRQC_MEASURE_VERIFICATION_QE_NAME, commissionerList.get(0).getSelectQe());
                            measureVerificationQEDao.updateIsMain(requestData.getFlowID());

                            CurrentDealStep lastStep = new CurrentDealStep(requestData.getFlowID(), StepConstant.QRQC_MEASURE_VERIFICATION_QE_CODE);
                            currentDealStepDao.updateIsMain(lastStep);
                            break;
                        case StepConstant.QRQC_QE_PLAN_AUDIT_CODE + "":
                            nextStep.setEndStep(StepConstant.QRQC_QE_PLAN_AUDIT_CODE, StepConstant.QRQC_QE_PLAN_AUDIT_NAME, commissionerList.get(0).getSelectQe());
                            qeAuditDao.updateIsMain(requestData.getFlowID());

                            CurrentDealStep lastQEStep = new CurrentDealStep(requestData.getFlowID(), StepConstant.QRQC_QE_PLAN_AUDIT_CODE);
                            currentDealStepDao.updateIsMain(lastQEStep);
                            break;
                    }

                }

                boolean isUpload = false;
                if (files != null && files.size() > 0) {
                    isUpload = true;
                }
                //1.保存提交的数据
                QEIsClosed qeIsClosed = new QEIsClosed(requestData.getStepTableId(), 5, requestData.isAdopt() ? (byte) 1 : (byte) 0, requestData.getFlowID(), requestData.getChangeDate(), requestData.isAdopt() ? (byte) 1 : (byte) 0, requestData.getSelectBackStep(), isUpload ? (byte) 1 : (byte) 0, requestData.getUserCode(), now);
                qeIsClosedDao.updateByPrimaryKeySelective(qeIsClosed);

                //2.将当前节点处理表中该步骤结束
                processDealUtils.endCurrentStep(currentDealStepDao, requestData.getCurrentStepId(), requestData.isAdopt(), requestData.getUserCode(), now);

                //3.在下个节点表中插入一条记录，并返回id
                switch (nextStep.getEndCode()) {
                    case StepConstant.QRQC_QE_CHIEF_AUDIT_CODE:
                        QEChiefAudit qeChiefAudit = new QEChiefAudit(1, nextStep.getStartUser(), now, nextStep.getFlowID());
                        qeChiefAuditDao.insertBackId(qeChiefAudit);
                        nextStep.setEndTableId(qeChiefAudit.getId());
                        break;
                    case StepConstant.QRQC_QE_PLAN_AUDIT_CODE:
                        QEAudit qeAudit = new QEAudit(1, nextStep.getStartUser(), now, (byte) 1, nextStep.getStartUser(), nextStep.getFlowID());
                        qeAuditDao.insertBackId(qeAudit);
                        nextStep.setEndTableId(qeAudit.getId());
                        break;
                    case StepConstant.QRQC_MEASURE_VERIFICATION_QE_CODE:
                        MeasureVerificationQE measureVerificationQE = new MeasureVerificationQE(1, nextStep.getStartUser(), now, (byte) 1, nextStep.getStartUser(), nextStep.getFlowID());
                        measureVerificationQEDao.insertBackId(measureVerificationQE);
                        nextStep.setEndTableId(measureVerificationQE.getId());
                        break;
                }
                //4.5.
                processDealUtils.newCurrentStep(currentDealStepDao, stepDealUserDao, nextStep, now, requestData.getCurrentStepId(), requestData.getStepTableId());

                //6.保存记录
                processDealUtils.saveRecord(recordSubmitDao, nextStep, new Gson().toJson(requestData), "部门审核", now);

                //10.保存文件
                if (files != null) {
                    List<SaveFileDataBean> uploadFilePathList = FileUtils.saveFileBackPath(files, basePath, "IsCloseSure", urlPath);

                    if (uploadFilePathList.size() < files.size()) {
                        throw new RuntimeException(MsgConstant.COMMISSIONER_SUBMIT_SAVE_FILE_FAIL + "QE确认");
                    } else {
                        //11.保存文件地址
                        for (SaveFileDataBean fileData : uploadFilePathList) {
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
}
