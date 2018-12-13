package com.zkd.service.impl;

import com.google.gson.Gson;
import com.zkd.common.bean.back.ReturnDataBean;
import com.zkd.common.bean.other.StepJumpBean;
import com.zkd.common.bean.request.RequestContainmentFinishSubmitDataBean;
import com.zkd.common.bean.request.show.RequestShowLoadBaseBean;
import com.zkd.common.constant.MsgConstant;
import com.zkd.common.constant.StepConstant;
import com.zkd.dao.map.*;
import com.zkd.entity.*;
import com.zkd.service.IContainmentFinishService;
import com.zkd.utils.EncryptUtils;
import com.zkd.utils.MyDateUtils;
import com.zkd.utils.ProcessDealUtils;
import com.zkd.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service("containmentFinishService")
public class ContainmentFinishService implements IContainmentFinishService {

    @Autowired
    CurrentDealStepMapper currentDealStepDao;
    @Autowired
    CommissionerFillMapper commissionerFillDao;
    @Autowired
    ContainmentFinishedMapper containmentFinishedDao;
    @Autowired
    PreliminaryCauseAnalysisMapper preliminaryCauseAnalysisDao;
    @Autowired
    MeasureVerificationQEMapper measureVerificationQEDao;
    @Autowired
    StepDealUserMapper stepDealUserDao;
    @Autowired
    RecordSubmitMapper recordSubmitDao;


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    @Override
    public String submit(String data) {
        ReturnDataBean returnData;
        RequestContainmentFinishSubmitDataBean requestData = new EncryptUtils<RequestContainmentFinishSubmitDataBean>().decryptObj(data, RequestContainmentFinishSubmitDataBean.class);
        if (requestData != null) {
            CurrentDealStep currentDealStep = currentDealStepDao.selectByPrimaryKey(StringUtils.parseString2Int(requestData.getCurrentStepId()));
            if (currentDealStep.getFlag() == 0) {
                StepJumpBean nextStep = new StepJumpBean(StepConstant.QRQC_CONTAINMENT_ACTION_FINISHED_CODE, StepConstant.QRQC_CONTAINMENT_ACTION_FINISHED_NAME, requestData.getUserCode(), requestData.getFlowID(), requestData.isAdopt());
                if (requestData.isAdopt()) {
                    List<CommissionerFill> commissionerList = commissionerFillDao.selectByFlowId(requestData.getFlowID());
                    nextStep.setEndStep(StepConstant.QRQC_MEASURE_VERIFICATION_QE_CODE, StepConstant.QRQC_MEASURE_VERIFICATION_QE_NAME, commissionerList.get(0).getSelectQe());
                } else {
                    nextStep.setEndStep(StepConstant.QRQC_CAUSE_ANALYSIS_PRELIMINARY_CODE, StepConstant.QRQC_CAUSE_ANALYSIS_PRELIMINARY_NAME, currentDealStep.getCreateUser());
                }

                Date now = MyDateUtils.getCurrentDate();
                ProcessDealUtils processDealUtils = new ProcessDealUtils();
                //1.保存提交的数据
                ContainmentFinishedWithBLOBs containmentFinishedWithBLOBs = new ContainmentFinishedWithBLOBs(StringUtils.parseString2Int(requestData.getStepTableId()), 5,(byte)(requestData.isAdopt()?1:0), requestData.getFlowID(), StringUtils.parseString2Int(requestData.getInventoryProduct()), StringUtils.parseString2Int(requestData.getInventoryFinished()), requestData.isAdopt() ? (byte) 1 : (byte) 0, requestData.getUserCode(), now, requestData.getDisposeMeasureProduct(), requestData.getDealResultProduct(), requestData.getDisposeMeasureFinished(), requestData.getDealResultFinished());
                containmentFinishedDao.updateByPrimaryKeySelective(containmentFinishedWithBLOBs);

                //2.将当前节点处理表中该步骤结束
                processDealUtils.endCurrentStep(currentDealStepDao, StringUtils.parseString2Int(requestData.getCurrentStepId()), requestData.isAdopt(), requestData.getUserCode(),now);

                boolean tag = true;
                //3.在下个节点表中插入一条记录，并返回id
                switch (nextStep.getEndCode()) {
                    case StepConstant.QRQC_MEASURE_VERIFICATION_QE_CODE:
                        //查询围堵措施在制品/成品节点是否完成，完成则插入下一节点信息，否则不插入
                        List<CurrentDealStep> containmentList = currentDealStepDao.selectContainmentStep(currentDealStep);

                        for (CurrentDealStep step : containmentList) {
                            if (step.getFlag().toString().equals("0")) {
                                tag = false;
                            }
                        }
                        if (tag) {
                            MeasureVerificationQE measureVerificationQE = new MeasureVerificationQE(1, nextStep.getStartUser(), now, nextStep.getFlowID());
                            measureVerificationQEDao.insertBackId(measureVerificationQE);
                            nextStep.setEndTableId(measureVerificationQE.getId());
                        }
                        break;
                    case StepConstant.QRQC_CAUSE_ANALYSIS_PRELIMINARY_CODE:
                        PreliminaryCauseAnalysis preliminaryCauseAnalysis = new PreliminaryCauseAnalysis(1, nextStep.getStartUser(), now, "2", nextStep.getStartUser(), nextStep.getFlowID());
                        preliminaryCauseAnalysisDao.insertBackId(preliminaryCauseAnalysis);
                        nextStep.setEndTableId(preliminaryCauseAnalysis.getId());
                        break;
                }
                if (tag) {
                    //processDealUtils.newCurrentStep(currentDealStepDao,stepDealUserDao,nextStep,now);
                    processDealUtils.newCurrentStep(currentDealStepDao, stepDealUserDao, nextStep, now,StringUtils.parseString2Int(requestData.getCurrentStepId()),StringUtils.parseString2Int(requestData.getStepTableId()));
                }
                //6.保存记录
                processDealUtils.saveRecord(recordSubmitDao,nextStep,new Gson().toJson(requestData),"围堵措施-在制品/成品",now);

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
        ContainmentFinished detail = containmentFinishedDao.selectByPrimaryKey(requestData.getTableId());
        return new EncryptUtils<>().encryptObj(new ReturnDataBean<>(MsgConstant.CODE_SUCCESS, detail, MsgConstant.MSG_SUCCESS));
    }
}
