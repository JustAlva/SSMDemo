package com.zkd.service.impl;

import com.google.gson.Gson;
import com.zkd.common.bean.back.ReturnDataBean;
import com.zkd.common.bean.back.ReturnRootCauseAnalysisLoadDataBean;
import com.zkd.common.bean.other.StepJumpBean;
import com.zkd.common.bean.request.RequestLoadBaseDataBean;
import com.zkd.common.bean.request.RequestRootCauseAnalysisSubmitDataBean;
import com.zkd.common.bean.request.show.RequestShowLoadBaseBean;
import com.zkd.common.constant.MsgConstant;
import com.zkd.common.constant.StepConstant;
import com.zkd.dao.map.*;
import com.zkd.entity.*;
import com.zkd.service.IRootCauseAnalysisService;
import com.zkd.utils.EncryptUtils;
import com.zkd.utils.MyDateUtils;
import com.zkd.utils.ProcessDealUtils;
import com.zkd.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("rootCauseAnalysisService")
public class RootCauseAnalysisService implements IRootCauseAnalysisService {

    @Autowired
    ConfUploadDataMapper confUploadDataDao;
    @Autowired
    CommissionerFillMapper commissionerFillDao;
    @Autowired
    CurrentDealStepMapper currentDealStepDao;
    @Autowired
    UserInfoMapper userInfoDao;
    @Autowired
    RootCauseAnalysisMapper rootCauseAnalysisDao;
    @Autowired
    PreliminaryCauseAnalysisMapper preliminaryCauseAnalysisDao;
    @Autowired
    DepartmentAuditMapper departmentAuditDao;
    @Autowired
    StepDealUserMapper stepDealUserDao;
    @Autowired
    RecordSubmitMapper recordSubmitDao;

    @Override
    public String load(String data) {
        RequestLoadBaseDataBean requestData = new EncryptUtils<RequestLoadBaseDataBean>().decryptObj(data, RequestLoadBaseDataBean.class);
        ReturnRootCauseAnalysisLoadDataBean dataBean = new ReturnRootCauseAnalysisLoadDataBean();
        List<ConfUploadData> confUploadDataList = confUploadDataDao.selectAll();
        if (confUploadDataList != null) {
            List<String> strList = new ArrayList<>();
            for (ConfUploadData confUploadData : confUploadDataList) {
                strList.add(confUploadData.getDataName());
            }
            dataBean.setNeedUpdateData(strList);
        }
        List<CommissionerFill> commissionerFillList = commissionerFillDao.selectByFlowId(requestData.getFlowID());
        if (commissionerFillList != null && commissionerFillList.size() > 0) {
            dataBean.setFlowCreateDate(commissionerFillList.get(0).getCreateDate());
        }

        return new EncryptUtils<>().encryptObj(new ReturnDataBean<>(MsgConstant.CODE_SUCCESS, dataBean, MsgConstant.MSG_SUCCESS));
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    @Override
    public String submit(String data) {
        ReturnDataBean returnData;
        RequestRootCauseAnalysisSubmitDataBean requestData = new EncryptUtils<RequestRootCauseAnalysisSubmitDataBean>().decryptObj(data, RequestRootCauseAnalysisSubmitDataBean.class);
        if (requestData != null) {
            CurrentDealStep currentDealStep = currentDealStepDao.selectByPrimaryKey(StringUtils.parseString2Int(requestData.getCurrentStepId()));
            if (currentDealStep.getFlag() == 0) {
                StepJumpBean nextStep = new StepJumpBean(StepConstant.QRQC_CAUSE_ANALYSIS_ROOT_CODE, StepConstant.QRQC_CAUSE_ANALYSIS_ROOT_NAME, requestData.getUserCode(), requestData.getFlowID(), requestData.isAdopt());
                if (requestData.isAdopt()) {
                    List<UserInfo> leaderInfo = userInfoDao.selectLeader(requestData.getUserCode());
                    nextStep.setEndStep(StepConstant.QRQC_DEPARTMENT_LEADER_AUDIT_CODE, StepConstant.QRQC_DEPARTMENT_LEADER_AUDIT_NAME, leaderInfo.get(0).getUserNo());
                } else {
                    List<CommissionerFill> commissionerList = commissionerFillDao.selectByFlowId(requestData.getFlowID());
                    nextStep.setEndStep(StepConstant.QRQC_CAUSE_ANALYSIS_PRELIMINARY_CODE, StepConstant.QRQC_CAUSE_ANALYSIS_PRELIMINARY_NAME, commissionerList.get(0).getSelectQePe());

                }

                Date now = MyDateUtils.getCurrentDate();
                ProcessDealUtils processDealUtils = new ProcessDealUtils();

                String needUpdateData = StringUtils.list2String(requestData.getUpdateDatasList());

                //1.保存提交的数据
                RootCauseAnalysisWithBLOBs rootCauseAnalysis = new RootCauseAnalysisWithBLOBs(StringUtils.parseString2Int(requestData.getStepTableId()), 5,(byte)(requestData.isAdopt()?1:0), requestData.getFlowID(), requestData.getGenerativeRes(), requestData.getOutFlowRes(), requestData.getImprovementRes(), requestData.getPlanFinishDate(), requestData.getPreventiveRes(), requestData.getPreventiveFinishDate(), requestData.isAdopt() ? (byte) 1 : (byte) 0, requestData.getUserCode(), now, requestData.getGenerativeCause(), requestData.getOutFlowCause(), requestData.getImprovement(), requestData.getPreventiveMeasure(), needUpdateData);
                rootCauseAnalysisDao.updateByPrimaryKeySelective(rootCauseAnalysis);

                //2.将当前节点处理表中该步骤结束
                processDealUtils.endCurrentStep(currentDealStepDao, StringUtils.parseString2Int(requestData.getCurrentStepId()), requestData.isAdopt(), requestData.getUserCode(), now);

                //3.在下个节点表中插入一条记录，并返回id
                switch (nextStep.getEndCode()) {
                    case StepConstant.QRQC_DEPARTMENT_LEADER_AUDIT_CODE:
                        DepartmentAudit departmentAudit = new DepartmentAudit(1, nextStep.getStartUser(), now, nextStep.getFlowID());
                        departmentAuditDao.insertBackId(departmentAudit);
                        nextStep.setEndTableId(departmentAudit.getId());
                        break;
                    case StepConstant.QRQC_CAUSE_ANALYSIS_PRELIMINARY_CODE:
                        PreliminaryCauseAnalysis preliminaryCauseAnalysis = new PreliminaryCauseAnalysis(1, nextStep.getStartUser(), now, "3", nextStep.getStartUser(), nextStep.getFlowID());
                        preliminaryCauseAnalysisDao.insertBackId(preliminaryCauseAnalysis);
                        nextStep.setEndTableId(preliminaryCauseAnalysis.getId());
                        break;
                }
                //4.5.
                //processDealUtils.newCurrentStep(currentDealStepDao, stepDealUserDao, nextStep, now);
                processDealUtils.newCurrentStep(currentDealStepDao, stepDealUserDao, nextStep, now,StringUtils.parseString2Int(requestData.getCurrentStepId()),StringUtils.parseString2Int(requestData.getStepTableId()));

                //6.保存记录
                processDealUtils.saveRecord(recordSubmitDao, nextStep, new Gson().toJson(requestData), "根本原因分析和改善措施", now);

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
        RootCauseAnalysis detail = rootCauseAnalysisDao.selectByPrimaryKey(requestData.getTableId());
        return new EncryptUtils<>().encryptObj(new ReturnDataBean<>(MsgConstant.CODE_SUCCESS, detail, MsgConstant.MSG_SUCCESS));
    }
}
