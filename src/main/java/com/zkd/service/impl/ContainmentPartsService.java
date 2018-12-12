package com.zkd.service.impl;

import com.google.gson.Gson;
import com.zkd.common.bean.back.ReturnContainmentPartsLoadDataBean;
import com.zkd.common.bean.back.ReturnDataBean;
import com.zkd.common.bean.other.StepJumpBean;
import com.zkd.common.bean.request.RequestContainmentPartsLoadDataBean;
import com.zkd.common.bean.request.RequestContainmentPartsSubmitDataBean;
import com.zkd.common.bean.request.show.RequestShowLoadBaseBean;
import com.zkd.common.constant.MsgConstant;
import com.zkd.common.constant.StepConstant;
import com.zkd.dao.map.*;
import com.zkd.entity.*;
import com.zkd.service.IContainmentPartsService;
import com.zkd.utils.EncryptUtils;
import com.zkd.utils.MyDateUtils;
import com.zkd.utils.ProcessDealUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service("containmentPartsService")
public class ContainmentPartsService implements IContainmentPartsService {

    @Autowired
    PreliminaryCauseAnalysisMapper preliminaryCauseAnalysisDao;
    @Autowired
    CurrentDealStepMapper currentDealStepDao;
    @Autowired
    CommissionerFillMapper commissionerFillDao;
    @Autowired
    ContainmentPartsMapper containmentPartsDao;
    @Autowired
    MeasureVerificationQEMapper measureVerificationQEDao;
    @Autowired
    StepDealUserMapper stepDealUserDao;
    @Autowired
    RecordSubmitMapper recordSubmitDao;
    @Autowired
    TotalFlowMapper totalFlowDao;
    @Autowired
    QEIsClosedMapper qeIsClosedDao;

    @Override
    public String load(String data) {
        ReturnContainmentPartsLoadDataBean returnData = new ReturnContainmentPartsLoadDataBean();
        RequestContainmentPartsLoadDataBean requestData = new EncryptUtils<RequestContainmentPartsLoadDataBean>().decryptObj(data, RequestContainmentPartsLoadDataBean.class);

        PreliminaryCauseAnalysis preliminaryCauseAnalysis = new PreliminaryCauseAnalysis();
        preliminaryCauseAnalysis.setFlowId(requestData.getFlowId());
        List<PreliminaryCauseAnalysis> selectList = preliminaryCauseAnalysisDao.selectByFlowId(preliminaryCauseAnalysis);
        if (selectList != null && selectList.size() > 0) {
            returnData.setExceptionType(selectList.get(0).getExceptionTypeName());
        }
        return new EncryptUtils<>().encryptObj(new ReturnDataBean<>(MsgConstant.CODE_SUCCESS, returnData, MsgConstant.MSG_SUCCESS));
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    @Override
    public String submit(String data) {
        ReturnDataBean returnData;
        RequestContainmentPartsSubmitDataBean requestData = new EncryptUtils<RequestContainmentPartsSubmitDataBean>().decryptObj(data, RequestContainmentPartsSubmitDataBean.class);
        if (requestData != null) {
            CurrentDealStep currentDealStep = currentDealStepDao.selectByPrimaryKey(requestData.getCurrentStepId());
            if (currentDealStep!=null&&currentDealStep.getFlag() == 0) {
                StepJumpBean nextStep = new StepJumpBean(StepConstant.QRQC_CONTAINMENT_ACTION_PARTS_CODE, StepConstant.QRQC_CONTAINMENT_ACTION_PARTS_NAME, requestData.getUserCode(), requestData.getFlowID(), requestData.isAdopt());
                boolean isClose = false;
                if (requestData.getExceptionType().equals("外观") && requestData.getUnhealthyRate() < 5.0) {
                    isClose = true;
                }
                if (isClose) {
                    //提前关闭，qe确认
                    List<CommissionerFill> commissionerList = commissionerFillDao.selectByFlowId(requestData.getFlowID());
                    nextStep.setEndStep(StepConstant.QRQC_QE_SURE_CLOSED_CODE, StepConstant.QRQC_QE_SURE_CLOSED_NAME, commissionerList.get(0).getSelectQe());
                } else {
                    if (requestData.isAdopt()) {
                        List<CommissionerFill> commissionerList = commissionerFillDao.selectByFlowId(requestData.getFlowID());
                        nextStep.setEndStep(StepConstant.QRQC_MEASURE_VERIFICATION_QE_CODE, StepConstant.QRQC_MEASURE_VERIFICATION_QE_NAME, commissionerList.get(0).getSelectQe());
                    } else {
                        nextStep.setEndStep(StepConstant.QRQC_CAUSE_ANALYSIS_PRELIMINARY_CODE, StepConstant.QRQC_CAUSE_ANALYSIS_PRELIMINARY_NAME, currentDealStep.getCreateUser());
                    }
                }

                Date now = MyDateUtils.getCurrentDate();
                ProcessDealUtils processDealUtils = new ProcessDealUtils();
                //1.保存提交的数据
                ContainmentPartsWithBLOBs containmentParts = new ContainmentPartsWithBLOBs(requestData.getStepTableId(), 5,(byte) (requestData.isAdopt() ? 1 : 0),   requestData.getInventory(), (byte) (requestData.isSupplement() ? 1 : 0), MyDateUtils.getDate(requestData.getSupplementDate()), requestData.getUnhealthyNumber(), requestData.getUnhealthyRate(),(byte) (requestData.isAdopt() ? 1 : 0) , requestData.getUserCode(), now, requestData.getDisposeMeasure(), requestData.getDealResult());
                containmentPartsDao.updateByPrimaryKeySelective(containmentParts);

                //2.将当前节点处理表中该步骤结束
                processDealUtils.endCurrentStep(currentDealStepDao, requestData.getCurrentStepId(),requestData.isAdopt(), requestData.getUserCode(), now);

                //更新总表的计划完成时间？？ 原来是改善措施完成时间，但是有多个根本原因分析，取哪个？

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
                        PreliminaryCauseAnalysis preliminaryCauseAnalysis = new PreliminaryCauseAnalysis(1, nextStep.getStartUser(), now, "1", nextStep.getStartUser(), nextStep.getFlowID());
                        preliminaryCauseAnalysisDao.insertBackId(preliminaryCauseAnalysis);
                        nextStep.setEndTableId(preliminaryCauseAnalysis.getId());
                        break;
                    case StepConstant.QRQC_QE_SURE_CLOSED_CODE:
                        QEIsClosed qeIsClosed = new QEIsClosed(1, nextStep.getStartUser(), now, nextStep.getFlowID());
                        qeIsClosedDao.insertBackId(qeIsClosed);

                        TotalFlow totalFlow = new TotalFlow(nextStep.getFlowID(), (byte) 1, now, nextStep.getStartUser());
                        totalFlowDao.updateByFlowId(totalFlow);
                        break;
                }
                if (tag) {
                    //processDealUtils.newCurrentStep(currentDealStepDao, stepDealUserDao, nextStep, now);
                    processDealUtils.newCurrentStep(currentDealStepDao, stepDealUserDao, nextStep, now,requestData.getCurrentStepId(),requestData.getStepTableId());
                }
                processDealUtils.saveRecord(recordSubmitDao, nextStep, new Gson().toJson(requestData), "围堵措施-零部件", now);

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
        ContainmentParts detail = containmentPartsDao.selectByPrimaryKey(requestData.getTableId());
        return new EncryptUtils<>().encryptObj(new ReturnDataBean<>(MsgConstant.CODE_SUCCESS, detail, MsgConstant.MSG_SUCCESS));
    }
}
