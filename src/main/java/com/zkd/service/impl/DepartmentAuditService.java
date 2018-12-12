package com.zkd.service.impl;

import com.google.gson.Gson;
import com.zkd.common.bean.back.ReturnDataBean;
import com.zkd.common.bean.other.StepJumpBean;
import com.zkd.common.bean.request.RequestDepartmentAuditSubmitDataBean;
import com.zkd.common.bean.request.RequestLoadBaseDataBean;
import com.zkd.common.bean.request.show.RequestShowLoadBaseBean;
import com.zkd.common.constant.MsgConstant;
import com.zkd.common.constant.StepConstant;
import com.zkd.dao.map.*;
import com.zkd.entity.*;
import com.zkd.service.IDepartmentAuditService;
import com.zkd.utils.EncryptUtils;
import com.zkd.utils.MyDateUtils;
import com.zkd.utils.ProcessDealUtils;
import com.zkd.utils.StringUtils;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.faces.flow.builder.ReturnBuilder;
import java.util.Date;
import java.util.List;

@Service("departmentAuditService")
public class DepartmentAuditService implements IDepartmentAuditService {

    @Autowired
    RootCauseAnalysisMapper rootCauseAnalysisDao;
    @Autowired
    DepartmentAuditMapper departmentAuditDao;
    @Autowired
    CurrentDealStepMapper currentDealStepDao;
    @Autowired
    UserInfoMapper userInfoDao;
    @Autowired
    PreliminaryCauseAnalysisMapper preliminaryCauseAnalysisDao;
    @Autowired
    StepDealUserMapper stepDealUserDao;
    @Autowired
    RecordSubmitMapper recordSubmitDao;
    @Autowired
    CommissionerFillMapper commissionerFillDao;
    @Autowired
    QEAuditMapper qeAuditDao;

    @Override
    public String load(String data) {
        RequestLoadBaseDataBean requestData = new EncryptUtils<RequestLoadBaseDataBean>().decryptObj(data, RequestLoadBaseDataBean.class);
        RootCauseAnalysisWithBLOBs preliminaryCauseAnalysis = rootCauseAnalysisDao.selectDepartmentAudit(requestData);

        return new EncryptUtils<>().encryptObj(new ReturnDataBean<>(MsgConstant.CODE_SUCCESS, preliminaryCauseAnalysis, MsgConstant.MSG_SUCCESS));
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    @Override
    public String submit(String data) {
        ReturnDataBean returnData;
        RequestDepartmentAuditSubmitDataBean requestData = new EncryptUtils<RequestDepartmentAuditSubmitDataBean>().decryptObj(data, RequestDepartmentAuditSubmitDataBean.class);
        if (requestData != null) {
            CurrentDealStep currentDealStep = currentDealStepDao.selectByPrimaryKey(requestData.getCurrentStepId());
            if (currentDealStep.getFlag() == 0) {
                Date now = MyDateUtils.getCurrentDate();
                ProcessDealUtils processDealUtils = new ProcessDealUtils();

                StepJumpBean nextStep = new StepJumpBean(StepConstant.QRQC_DEPARTMENT_LEADER_AUDIT_CODE, StepConstant.QRQC_DEPARTMENT_LEADER_AUDIT_NAME, requestData.getUserCode(), requestData.getFlowID(), requestData.isAdopt());
                if (requestData.isAdopt()) {
                    List<CommissionerFill> commissionerList = commissionerFillDao.selectByFlowId(requestData.getFlowID());
                    nextStep.setEndStep(StepConstant.QRQC_QE_PLAN_AUDIT_CODE, StepConstant.QRQC_QE_PLAN_AUDIT_NAME, commissionerList.get(0).getSelectQe());
                } else {
                    nextStep.setEndStep(StepConstant.QRQC_CAUSE_ANALYSIS_ROOT_CODE, StepConstant.QRQC_CAUSE_ANALYSIS_ROOT_NAME, currentDealStep.getCreateUser());
                    RootCauseAnalysisWithBLOBs lastRootCause = new RootCauseAnalysisWithBLOBs();
                    lastRootCause.setIsMain((byte) 0);
                    lastRootCause.setId(currentDealStep.getCreateStepTableId());
                    rootCauseAnalysisDao.updateByPrimaryKeySelective(lastRootCause);
                    currentDealStepDao.updateIsMainById(currentDealStep.getCreateId());
                }

                //1.保存提交的数据
                DepartmentAudit departmentAudit = new DepartmentAudit(requestData.getStepTableId(), 5, requestData.isAdopt() ? (byte) 1 : (byte) 0, nextStep.getStartUser(), now);
                departmentAuditDao.updateByPrimaryKeySelective(departmentAudit);

                //2.将当前节点处理表中该步骤结束
                processDealUtils.endCurrentStep(currentDealStepDao, requestData.getCurrentStepId(), requestData.isAdopt(), requestData.getUserCode(), now);

                boolean tag = true;
                //3.在下个节点表中插入一条记录，并返回id
                switch (nextStep.getEndCode()) {
                    case StepConstant.QRQC_QE_PLAN_AUDIT_CODE:
                        List<CurrentDealStep> stepList = currentDealStepDao.selectQEAuditCanDo(requestData.getFlowID());
                        for (CurrentDealStep step : stepList) {
                            if (step.getFlag().toString().equals("0")) {
                                tag = false;
                            }
                        }
                        if (tag) {
                            QEAudit qeAudit = new QEAudit(1, nextStep.getStartUser(), now, nextStep.getFlowID());
                            qeAuditDao.insertBackId(qeAudit);
                            nextStep.setEndTableId(qeAudit.getId());
                        }
                        break;
                    case StepConstant.QRQC_CAUSE_ANALYSIS_ROOT_CODE:
                        RootCauseAnalysis rootCauseAnalysis = new RootCauseAnalysis(1, nextStep.getStartUser(), now, (byte) 1, nextStep.getStartUser(), nextStep.getFlowID());
                        rootCauseAnalysisDao.insertBackId(rootCauseAnalysis);
                        nextStep.setEndTableId(rootCauseAnalysis.getId());
                        break;
                }
                //4.5.
                if (tag) {
                    processDealUtils.newCurrentStep(currentDealStepDao, stepDealUserDao, nextStep, now, requestData.getCurrentStepId(), requestData.getStepTableId());
                }

                //6.保存记录
                processDealUtils.saveRecord(recordSubmitDao, nextStep, new Gson().toJson(requestData), "部门审核", now);

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
        DepartmentAudit detail = departmentAuditDao.selectByPrimaryKey(requestData.getTableId());
        return new EncryptUtils<>().encryptObj(new ReturnDataBean<>(MsgConstant.CODE_SUCCESS, detail, MsgConstant.MSG_SUCCESS));
    }
}
