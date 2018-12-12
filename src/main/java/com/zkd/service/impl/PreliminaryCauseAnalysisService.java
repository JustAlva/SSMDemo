package com.zkd.service.impl;

import com.google.gson.Gson;
import com.zkd.common.bean.back.ReturnDataBean;
import com.zkd.common.bean.back.ReturnPreliminaryCauseLoadDataBean;
import com.zkd.common.bean.back.bean.ReturnPreliminaryCauseNextStepDealDataBean;
import com.zkd.common.bean.other.StepJumpBean;
import com.zkd.common.bean.other.UserDataBean;
import com.zkd.common.bean.request.RequestPreliminaryCauseAnalysisLoadDataBean;
import com.zkd.common.bean.request.RequestPreliminaryCauseSubmitDataBean;
import com.zkd.common.bean.request.show.RequestShowLoadBaseBean;
import com.zkd.common.constant.MsgConstant;
import com.zkd.common.constant.StepConstant;
import com.zkd.dao.map.*;
import com.zkd.entity.*;
import com.zkd.service.IPreliminaryCauseAnalysisService;
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

@Service("preliminaryCauseAnalysisService")
public class PreliminaryCauseAnalysisService implements IPreliminaryCauseAnalysisService {

    @Autowired
    ConfDealStepMapper confDealStepDao;
    @Autowired
    PreliminaryCauseAnalysisMapper preliminaryCauseAnalysisDao;
    @Autowired
    ConfExceptionTypeMapper confExceptionTypeDao;
    @Autowired
    ConfResTypeMapper confResTypeDao;
    @Autowired
    CurrentDealStepMapper currentDealStepDao;
    @Autowired
    TotalFlowMapper totalFlowDao;
    @Autowired
    ContainmentPartsMapper containmentPartsDao;
    @Autowired
    ContainmentFinishedMapper containmentFinishedDao;
    @Autowired
    RootCauseAnalysisMapper rootCauseAnalysisDao;
    @Autowired
    StepDealUserMapper stepDealUserDao;
    @Autowired
    RecordSubmitMapper recordSubmitDao;


    @Override
    public String load(String data) {
        try {
            ReturnPreliminaryCauseLoadDataBean returnBean = new ReturnPreliminaryCauseLoadDataBean();
            RequestPreliminaryCauseAnalysisLoadDataBean requestData = new EncryptUtils<RequestPreliminaryCauseAnalysisLoadDataBean>().decryptObj(data, RequestPreliminaryCauseAnalysisLoadDataBean.class);
            List<ReturnPreliminaryCauseNextStepDealDataBean> stepDepartmentList = new ArrayList<>();
            //21零部件 22在制品 31 根本原因分析
            List<ReturnPreliminaryCauseNextStepDealDataBean> stepList21 = confDealStepDao.selectByStepCode(StepConstant.QRQC_CONTAINMENT_ACTION_PARTS_CODE);
            List<ReturnPreliminaryCauseNextStepDealDataBean> stepList22 = confDealStepDao.selectByStepCode(StepConstant.QRQC_CONTAINMENT_ACTION_FINISHED_CODE);
            List<ReturnPreliminaryCauseNextStepDealDataBean> stepList31 = confDealStepDao.selectByStepCode(StepConstant.QRQC_CAUSE_ANALYSIS_ROOT_CODE);
            if (stepList21 != null) {
                stepDepartmentList.addAll(stepList21);
            }
            if (stepList22 != null) {
                stepDepartmentList.addAll(stepList22);
            }
            if (stepList31 != null) {
                stepDepartmentList.addAll(stepList31);
            }
            returnBean.setSelectDepartment(stepDepartmentList);

            PreliminaryCauseAnalysis currentBean = preliminaryCauseAnalysisDao.selectByPrimaryKey(requestData.getStepTableId());
            if (currentBean != null) {
                returnBean.setBackFlag(currentBean.getBackFlag());
            }

            List<String> exList = confExceptionTypeDao.selectAll();
            if (exList != null) {
                returnBean.setExceptionType(exList);
            }
            List<String> resList = confResTypeDao.selectAll();
            if (resList != null) {
                returnBean.setResType(resList);
            }
            return new EncryptUtils<>().encryptObj(new ReturnDataBean<>(MsgConstant.CODE_SUCCESS, returnBean, MsgConstant.MSG_SUCCESS));

        } catch (Exception e) {
            return new EncryptUtils<>().encryptObj(new ReturnDataBean<>(MsgConstant.CODE_ERROR, e.toString(), MsgConstant.MSG_ERROR));
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    @Override
    public String submit(String data) {
        ReturnDataBean returnData = null;
        RequestPreliminaryCauseSubmitDataBean requestData = new EncryptUtils<RequestPreliminaryCauseSubmitDataBean>().decryptObj(data, RequestPreliminaryCauseSubmitDataBean.class);
        if (requestData != null) {
            CurrentDealStep currentDealStep = currentDealStepDao.selectByPrimaryKey(requestData.getCurrentStepId());
            if (currentDealStep.getFlag() == 0) {
                Date now = MyDateUtils.getCurrentDate();
                ProcessDealUtils processDealUtils = new ProcessDealUtils();
                List<UserDataBean> selectParts = requestData.getSelectParts();
                List<UserDataBean> selectFinished = requestData.getSelectFinished();
                List<UserDataBean> selectRootCauseList = requestData.getSelectRootCauseList();

                StringBuffer partsStr = new StringBuffer();
                StringBuffer finishedStr = new StringBuffer();
                StringBuffer rootCauseStr = new StringBuffer();
                List<StepJumpBean> nextSteps = new ArrayList<>();
                if (selectParts != null && selectParts.size() > 0) {
                    for (int i = 0; i < selectParts.size(); i++) {
                        UserDataBean nextUser = selectParts.get(i);
                        nextSteps.add(getNextStepBean(requestData.getUserCode(), StepConstant.QRQC_CONTAINMENT_ACTION_PARTS_CODE, StepConstant.QRQC_CONTAINMENT_ACTION_PARTS_NAME, nextUser.getUserNo(), requestData.getFlowID()));
                        partsStr.append(appendString(i, selectParts.size(), nextUser.getUserNo()));
                    }
                }
                if (selectFinished != null && selectFinished.size() > 0) {
                    for (int i = 0; i < selectFinished.size(); i++) {
                        UserDataBean nextUser = selectFinished.get(i);
                        nextSteps.add(getNextStepBean(requestData.getUserCode(), StepConstant.QRQC_CONTAINMENT_ACTION_FINISHED_CODE, StepConstant.QRQC_CONTAINMENT_ACTION_FINISHED_NAME, nextUser.getUserNo(), requestData.getFlowID()));
                        finishedStr.append(appendString(i, selectFinished.size(), nextUser.getUserNo()));
                    }
                }
                if (selectRootCauseList != null && selectRootCauseList.size() > 0) {
                    for (int i = 0; i < selectRootCauseList.size(); i++) {
                        UserDataBean nextUser = selectRootCauseList.get(i);
                        nextSteps.add(getNextStepBean(requestData.getUserCode(), StepConstant.QRQC_CAUSE_ANALYSIS_ROOT_CODE, StepConstant.QRQC_CAUSE_ANALYSIS_ROOT_NAME, nextUser.getUserNo(), requestData.getFlowID()));
                        rootCauseStr.append(appendString(i, selectRootCauseList.size(), nextUser.getUserNo()));
                    }
                }
                StringBuffer exStr = new StringBuffer();
                StringBuffer resStr = new StringBuffer();

                for (int i = 0; i < requestData.getExceptionTypeList().size(); i++) {
                    exStr.append(appendString(i, requestData.getExceptionTypeList().size(), requestData.getExceptionTypeList().get(i)));
                }

                for (int i = 0; i < requestData.getResponsibilityTypeList().size(); i++) {
                    resStr.append(appendString(i, requestData.getResponsibilityTypeList().size(), requestData.getResponsibilityTypeList().get(i)));
                }

                //back_flag back_user 需要添加

                //1.将提交的数据提交
                PreliminaryCauseAnalysis preliminaryCauseAnalysis = new PreliminaryCauseAnalysis(requestData.getStepTableId(), 5, requestData.getFlowID(), exStr.toString(), exStr.toString(), resStr.toString(), resStr.toString(), requestData.getSupplier(), partsStr.toString(), finishedStr.toString(), rootCauseStr.toString(), null, requestData.getUserCode(), now, requestData.getCauseAnalysis());
                preliminaryCauseAnalysisDao.updateById(preliminaryCauseAnalysis);

                //2.更新总表下个节点处理人
                //TotalFlow totalFlow = new TotalFlow(requestData.getFlowID(),requestData.getUserCode(),);
                //totalFlowDao.updateByFlowId(totalFlow);

                //3.将当前节点处理表中该步骤结束
                CurrentDealStep endStep = new CurrentDealStep(requestData.getCurrentStepId(),(byte)1, (byte)1, requestData.getUserCode(), now);
                currentDealStepDao.updateEndStep(endStep);

                //4.插入下个节点信息
                for (StepJumpBean nextStep : nextSteps) {
                    //(1) 在下个节点的表中插入一条新的纪录，并返回id
                    switch (nextStep.getEndCode()) {
                        case StepConstant.QRQC_CONTAINMENT_ACTION_PARTS_CODE:
                            ContainmentParts containmentParts = new ContainmentParts(1, nextStep.getStartUser(), now, nextStep.getFlowID());
                            containmentPartsDao.insertBackId(containmentParts);
                            nextStep.setEndTableId(containmentParts.getId());
                            break;
                        case StepConstant.QRQC_CONTAINMENT_ACTION_FINISHED_CODE:
                            ContainmentFinished containmentFinished = new ContainmentFinished(1, nextStep.getStartUser(), now, nextStep.getFlowID());
                            containmentFinishedDao.insertBackId(containmentFinished);
                            nextStep.setEndTableId(containmentFinished.getId());
                            break;
                        case StepConstant.QRQC_CAUSE_ANALYSIS_ROOT_CODE:
                            RootCauseAnalysis rootCauseAnalysis = new RootCauseAnalysis(1, nextStep.getStartUser(), now, nextStep.getFlowID());
                            rootCauseAnalysisDao.insertBackId(rootCauseAnalysis);
                            nextStep.setEndTableId(rootCauseAnalysis.getId());
                            break;
                    }


                    processDealUtils.newCurrentStep(currentDealStepDao, stepDealUserDao, nextStep, now, requestData.getCurrentStepId(), requestData.getStepTableId());

                    //(2) 在当前处理表中插入新的节点处理信息
                    // CurrentDealStep nextDealStep = new CurrentDealStep((byte) 0, nextStep.getStartUser(), now, nextStep.getStartCode(), nextStep.getFlowID(), nextStep.getEndCode(), nextStep.getEndName(), nextStep.getEndUser(), nextStep.getEndTableId(), nextStep.getStartUser(), now);
                    //currentDealStepDao.insertSelective(nextDealStep);

                    //(3) 在处理人表中插入新节点的处理人
                    // StepDealUser qrqcDealUser = new StepDealUser(nextStep.getFlowID(), nextStep.getEndCode() + "", nextStep.getEndUser(), nextStep.getStartUser(), now);
                    // stepDealUserDao.insertSelective(qrqcDealUser);
                }
                //5.保存记录
                RecordSubmit recordSubmit = new RecordSubmit(null, requestData.getFlowID(), "初步原因分析", requestData.getUserCode(), now, new Gson().toJson(requestData));
                recordSubmitDao.insertSelective(recordSubmit);

                returnData = new ReturnDataBean<>(MsgConstant.CODE_SUCCESS, "", MsgConstant.COMMON_SAVE_SUCCESS);
            } else {
                returnData = new ReturnDataBean<>(MsgConstant.CODE_FAIL, "", MsgConstant.COMMON_STEP_DEALED);
            }
        } else {
            returnData = new ReturnDataBean<>(MsgConstant.CODE_FAIL, "", MsgConstant.COMMON_SAVE_FAIL);
        }
        return new EncryptUtils<>().encryptObj(returnData);
    }

    private String appendString(int position, int size, String value) {
        if (position == 0 || ((position + 1) == size && size > 2)) {
            return value;
        } else {
            return "," + value;
        }
    }


    private StepJumpBean getNextStepBean(String startUser, int endStepCode, String endStepName, String endUser, String flowId) {
        StepJumpBean stepJumpBean = new StepJumpBean();
        stepJumpBean.setStartCode(StepConstant.QRQC_CAUSE_ANALYSIS_PRELIMINARY_CODE);
        stepJumpBean.setStartName(StepConstant.QRQC_CAUSE_ANALYSIS_PRELIMINARY_NAME);
        stepJumpBean.setStartUser(startUser);
        stepJumpBean.setEndCode(endStepCode);
        stepJumpBean.setEndName(endStepName);
        stepJumpBean.setEndUser(endUser);
        stepJumpBean.setFlowID(flowId);
        return stepJumpBean;
    }

    @Override
    public String getDetail(String data) {
        RequestShowLoadBaseBean requestData = new EncryptUtils<RequestShowLoadBaseBean>().decryptObj(data, RequestShowLoadBaseBean.class);
        PreliminaryCauseAnalysis detail = preliminaryCauseAnalysisDao.selectByPrimaryKey(requestData.getTableId());
        return new EncryptUtils<>().encryptObj(new ReturnDataBean<>(MsgConstant.CODE_SUCCESS, detail, MsgConstant.MSG_SUCCESS));
    }
}
