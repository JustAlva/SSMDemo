package com.zkd.service.impl;

import com.google.gson.Gson;
import com.zkd.common.bean.back.ReturnDataBean;
import com.zkd.common.bean.back.ReturnQEAuditLoadDataBean;
import com.zkd.common.bean.other.StepJumpBean;
import com.zkd.common.bean.other.UserDataBean;
import com.zkd.common.bean.request.RequestLoadBaseDataBean;
import com.zkd.common.bean.request.RequestQeAuditSubmitDataBean;
import com.zkd.common.bean.request.show.RequestShowLoadBaseBean;
import com.zkd.common.constant.MsgConstant;
import com.zkd.common.constant.StepConstant;
import com.zkd.dao.map.*;
import com.zkd.entity.*;
import com.zkd.service.IQEAuditService;
import com.zkd.utils.EncryptUtils;
import com.zkd.utils.MyDateUtils;
import com.zkd.utils.ProcessDealUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("qeAuditService")
public class QEAuditService implements IQEAuditService {
    @Autowired
    RootCauseAnalysisMapper rootCauseAnalysisDao;
    @Autowired
    CurrentDealStepMapper currentDealStepDao;
    @Autowired
    UserInfoMapper userInfoDao;
    @Autowired
    CommissionerFillMapper commissionerFillDao;
    @Autowired
    QEAuditMapper qeAuditDao;
    @Autowired
    QEIsClosedMapper qeIsClosedDao;
    @Autowired
    StepDealUserMapper stepDealUserDao;
    @Autowired
    RecordSubmitMapper recordSubmitDao;
    @Autowired
    DepartmentAuditMapper departmentAuditDao;

    @Override
    public String load(String data) {
        RequestLoadBaseDataBean requestData = new EncryptUtils<RequestLoadBaseDataBean>().decryptObj(data, RequestLoadBaseDataBean.class);

        List<ReturnQEAuditLoadDataBean> dataList = new ArrayList<>();
        List<CurrentDealStep> listDepartmentAudit = currentDealStepDao.selectDepartmentAuditList(requestData.getFlowID());
        for (CurrentDealStep step :listDepartmentAudit) {
            UserDataBean leader = userInfoDao.selectByUserNo(step.getCurrentDealUser());
            UserDataBean dealUser = userInfoDao.selectByUserNo(step.getCreateUser());
            RootCauseAnalysisWithBLOBs rootCause= rootCauseAnalysisDao.selectByPrimaryKey(step.getCreateStepTableId());

            ReturnQEAuditLoadDataBean totalData = new ReturnQEAuditLoadDataBean(leader, dealUser, step, rootCause);
            dataList.add(totalData);
        }

        return new EncryptUtils<>().encryptObj(new ReturnDataBean<>(MsgConstant.CODE_SUCCESS, dataList, MsgConstant.MSG_SUCCESS));
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    @Override
    public String submit(String data) {
        ReturnDataBean returnData;
        RequestQeAuditSubmitDataBean requestData = new EncryptUtils<RequestQeAuditSubmitDataBean>().decryptObj(data, RequestQeAuditSubmitDataBean.class);
        if (requestData != null) {
            CurrentDealStep currentDealStep = currentDealStepDao.selectByPrimaryKey(requestData.getCurrentStepId());
            if (currentDealStep.getFlag() == 0) {
                Date now = MyDateUtils.getCurrentDate();
                ProcessDealUtils processDealUtils = new ProcessDealUtils();

                StepJumpBean nextStep = new StepJumpBean(StepConstant.QRQC_QE_PLAN_AUDIT_CODE, StepConstant.QRQC_QE_PLAN_AUDIT_NAME, requestData.getUserCode(), requestData.getFlowID(), requestData.isAdopt());
                if (requestData.isAdopt()) {
                    List<CommissionerFill> commissionerList = commissionerFillDao.selectByFlowId(requestData.getFlowID());
                    nextStep.setEndStep(StepConstant.QRQC_QE_SURE_CLOSED_CODE, StepConstant.QRQC_QE_SURE_CLOSED_NAME, commissionerList.get(0).getSelectQe());

                } else {
                    nextStep.setEndCode(StepConstant.QRQC_CAUSE_ANALYSIS_ROOT_CODE);
                    nextStep.setEndName(StepConstant.QRQC_CAUSE_ANALYSIS_ROOT_NAME);
                }

                //1.保存提交的数据
                QEAudit qeAudit = new QEAudit(requestData.getStepTableId(), 5, requestData.isAdopt() ? (byte) 1 : (byte) 0, requestData.getFlowID(), requestData.isAdopt() ? (byte) 1 : (byte) 0, requestData.isExport() ? 1 : 0, requestData.getUserCode(), now);
                qeAuditDao.updateByPrimaryKeySelective(qeAudit);

                //2.将当前节点处理表中该步骤结束
                processDealUtils.endCurrentStep(currentDealStepDao, requestData.getCurrentStepId(), requestData.isAdopt(), requestData.getUserCode(), now);

                boolean tag = true;
                //3.在下个节点表中插入一条记录，并返回id
                switch (nextStep.getEndCode()) {
                    case StepConstant.QRQC_QE_SURE_CLOSED_CODE:
                        List<CurrentDealStep> stepList = currentDealStepDao.selectQEAuditCanDo(requestData.getFlowID());
                        for (CurrentDealStep step : stepList) {
                            if (step.getFlag().toString().equals("0")) {
                                tag = false;
                            }
                        }
                        if (tag) {
                            QEIsClosed qeIsClosed = new QEIsClosed(1, nextStep.getStartUser(), now, nextStep.getFlowID());
                            qeIsClosedDao.insertBackId(qeIsClosed);
                            nextStep.setEndTableId(qeIsClosed.getId());
                            //4.5.
                            processDealUtils.newCurrentStep(currentDealStepDao, stepDealUserDao, nextStep, now, requestData.getCurrentStepId(), requestData.getStepTableId());
                        }
                        break;
                    case StepConstant.QRQC_CAUSE_ANALYSIS_ROOT_CODE:
                        List<ReturnQEAuditLoadDataBean> selectBackData = requestData.getSelectBackData();
                        for (ReturnQEAuditLoadDataBean stepData:selectBackData) {
                            RootCauseAnalysis rootCauseAnalysis = new RootCauseAnalysis(1, nextStep.getStartUser(), now, (byte) 1, nextStep.getStartUser(), nextStep.getFlowID());
                            rootCauseAnalysisDao.insertBackId(rootCauseAnalysis);
                            nextStep.setEndTableId(rootCauseAnalysis.getId());
                            nextStep.setEndUser(stepData.getDealUser().getUserNo());

                            //将回退对应的步骤失效
                            CurrentDealStep departmentAudit = stepData.getDepartmentAudit();
                            RootCauseAnalysisWithBLOBs rootCauseAnalysisBack = new RootCauseAnalysisWithBLOBs();
                            rootCauseAnalysisBack.setId(departmentAudit.getCreateStepTableId());
                            rootCauseAnalysisBack.setIsMain((byte) 0);
                            rootCauseAnalysisDao.updateByPrimaryKeySelective(rootCauseAnalysisBack);
                            DepartmentAudit departmentAuditBack = new DepartmentAudit();
                            departmentAuditBack.setId(departmentAudit.getCurrentStepTableId());
                            departmentAuditBack.setIsMain((byte) 0);
                            departmentAuditDao.updateByPrimaryKeySelective(departmentAuditBack);

                            CurrentDealStep rootStep = new CurrentDealStep();
                            rootStep.setId(departmentAudit.getCreateId());
                            rootStep.setIsMain((byte) 0);
                            currentDealStepDao.updateByPrimaryKeySelective(rootStep );
                            CurrentDealStep deaprtStep = new CurrentDealStep();
                            deaprtStep.setId(departmentAudit.getId());
                            deaprtStep.setIsMain((byte) 0);
                            currentDealStepDao.updateByPrimaryKeySelective(deaprtStep );

                            //4.5.
                            processDealUtils.newCurrentStep(currentDealStepDao, stepDealUserDao, nextStep, now, requestData.getCurrentStepId(), requestData.getStepTableId());
                        }
                        break;
                }
                //6.保存记录
                processDealUtils.saveRecord(recordSubmitDao, nextStep, new Gson().toJson(requestData), "方案审核确认", now);

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
        QEAudit detail = qeAuditDao.selectByPrimaryKey(requestData.getTableId());
        return new EncryptUtils<>().encryptObj(new ReturnDataBean<>(MsgConstant.CODE_SUCCESS, detail, MsgConstant.MSG_SUCCESS));
    }
}
