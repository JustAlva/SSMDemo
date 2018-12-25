package com.zkd.service.impl;

import com.google.gson.Gson;
import com.zkd.common.bean.back.ReturnDataBean;
import com.zkd.common.bean.back.ReturnIsCloseSureLoadDataBean;
import com.zkd.common.bean.back.ReturnQEChiefAuditLoadDataBean;
import com.zkd.common.bean.other.StepJumpBean;
import com.zkd.common.bean.request.RequestLoadBaseDataBean;
import com.zkd.common.bean.request.RequestQeChiefAuditSubmitDataBean;
import com.zkd.common.bean.request.show.RequestShowLoadBaseBean;
import com.zkd.common.constant.MsgConstant;
import com.zkd.common.constant.StepConstant;
import com.zkd.dao.map.*;
import com.zkd.entity.*;
import com.zkd.service.IQEChiefAuditService;
import com.zkd.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service("qeChiefAuditService")
public class QEChiefAuditService implements IQEChiefAuditService {

    @Autowired
    QEIsClosedMapper qeIsClosedDao;
    @Autowired
    UploadImagesMapper uploadImagesDao;
    @Autowired
    CurrentDealStepMapper currentDealStepDao;
    @Autowired
    QEChiefAuditMapper qeChiefAuditDao;
    @Autowired
    StepDealUserMapper stepDealUserDao;
    @Autowired
    RecordSubmitMapper recordSubmitDao;
    @Autowired
    TotalFlowMapper totalFlowDao;
    @Autowired
    RootCauseAnalysisMapper rootCauseAnalysisDao;

    @Override
    public String load(String data) {
        RequestLoadBaseDataBean requestData = new EncryptUtils<RequestLoadBaseDataBean>().decryptObj(data, RequestLoadBaseDataBean.class);

        ReturnQEChiefAuditLoadDataBean returnData = new ReturnQEChiefAuditLoadDataBean();
        QEIsClosed qeIsClosed = qeIsClosedDao.selectData(requestData);
        returnData.setQeIsClosed(qeIsClosed);
        if (qeIsClosed.getIsUpload() == (byte) 1) {
            List<UploadImagesWithBLOBs> list = uploadImagesDao.selectStepIsCloseFiles();
            returnData.setFileList(list);
        }

        TotalFlow totalFlow = totalFlowDao.selectByFlowId(requestData.getFlowID());

        List<String> datas = new ArrayList<>();
        List<RootCauseAnalysisWithBLOBs> rootList = rootCauseAnalysisDao.selectUseful(requestData.getFlowID());
        if (rootList != null) {
           datas= CommonFunctionUtils.getUploadDataList(rootList);
        }
        ReturnIsCloseSureLoadDataBean dataBean = new ReturnIsCloseSureLoadDataBean(totalFlow.getIsClosedPre() == 1, datas);
        returnData.setLoadData(dataBean);
        return new EncryptUtils<>().encryptObj(new ReturnDataBean<>(MsgConstant.CODE_SUCCESS, returnData, MsgConstant.MSG_SUCCESS));
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    @Override
    public String submit(String data) {
        ReturnDataBean returnData;
        RequestQeChiefAuditSubmitDataBean requestData = new EncryptUtils<RequestQeChiefAuditSubmitDataBean>().decryptObj(data, RequestQeChiefAuditSubmitDataBean.class);
        if (requestData != null) {
            CurrentDealStep currentDealStep = currentDealStepDao.selectByPrimaryKey(StringUtils.parseString2Int(requestData.getCurrentStepId()));
            if (currentDealStep.getFlag() == 0) {
                Date now = MyDateUtils.getCurrentDate();
                ProcessDealUtils processDealUtils = new ProcessDealUtils();

                StepJumpBean nextStep = new StepJumpBean(StepConstant.QRQC_QE_CHIEF_AUDIT_CODE, StepConstant.QRQC_QE_CHIEF_AUDIT_NAME, requestData.getUserCode(), requestData.getFlowID(), requestData.isAdoptOrBack());
                if (requestData.isAdoptOrBack()) {
                    TotalFlow totalFlow = new TotalFlow();
                    totalFlow.setStatus(5);
                    totalFlow.setQrqcIdentifier(requestData.getFlowID());
                    totalFlowDao.updateByFlowId(totalFlow);
                } else {
                    nextStep.setEndStep(StepConstant.QRQC_QE_SURE_CLOSED_CODE, StepConstant.QRQC_QE_SURE_CLOSED_NAME, currentDealStep.getCreateUser());

                    QEIsClosed qeIsClosedLast = new QEIsClosed();
                    qeIsClosedLast.setIsMain((byte) 0);
                    qeIsClosedLast.setId(currentDealStep.getCreateStepTableId());
                    qeIsClosedDao.updateByPrimaryKeySelective(qeIsClosedLast);
                    currentDealStepDao.updateIsMainById(currentDealStep.getCreateId());
                }

                //1.保存提交的数据
                QEChiefAudit qeChiefAudit = new QEChiefAudit(StringUtils.parseString2Int(requestData.getCurrentStepId()), 5, requestData.getFlowID(), requestData.isAdoptOrBack() ? (byte) 1 : (byte) 0, requestData.getUserCode(), now);
                qeChiefAuditDao.updateByPrimaryKeySelective(qeChiefAudit);

                //2.将当前节点处理表中该步骤结束
                processDealUtils.endCurrentStep(currentDealStepDao, StringUtils.parseString2Int(requestData.getCurrentStepId()), requestData.isAdoptOrBack(), requestData.getUserCode(), now);

                //3.在下个节点表中插入一条记录，并返回id
                switch (nextStep.getEndCode()) {
                    case StepConstant.QRQC_QE_SURE_CLOSED_CODE:
                        QEIsClosed qeIsClosed = new QEIsClosed(1, nextStep.getStartUser(), now, (byte) 1, nextStep.getStartUser(), nextStep.getFlowID());
                        qeIsClosedDao.insertBackId(qeIsClosed);
                        nextStep.setEndTableId(qeIsClosed.getId());
                        break;
                }
                //4.5.
                if (!requestData.isAdoptOrBack()) {
                    processDealUtils.newCurrentStep(currentDealStepDao, stepDealUserDao, nextStep, now, StringUtils.parseString2Int(requestData.getCurrentStepId()),StringUtils.parseString2Int( requestData.getStepTableId()));
                } else {
                    StepDealUser qrqcDealUser = new StepDealUser(nextStep.getFlowID(), nextStep.getEndCode() + "", nextStep.getEndUser(), nextStep.getStartUser(), now);
                    stepDealUserDao.insertSelective(qrqcDealUser);
                }
                processDealUtils.updateTotalFlowData(totalFlowDao,nextStep,now);
                //6.保存记录
                processDealUtils.saveRecord(recordSubmitDao, nextStep, new Gson().toJson(requestData), "议长审核", now);

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
        QEChiefAudit detail = qeChiefAuditDao.selectByPrimaryKey(requestData.getTableId());
        return new EncryptUtils<>().encryptObj(new ReturnDataBean<>(MsgConstant.CODE_SUCCESS, detail, MsgConstant.MSG_SUCCESS));
    }
}
