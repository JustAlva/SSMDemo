package com.zkd.utils;

import com.zkd.common.bean.other.StepJumpBean;
import com.zkd.dao.map.CurrentDealStepMapper;
import com.zkd.dao.map.RecordSubmitMapper;
import com.zkd.dao.map.StepDealUserMapper;
import com.zkd.entity.CurrentDealStep;
import com.zkd.entity.RecordSubmit;
import com.zkd.entity.StepDealUser;

import java.util.Date;

public class ProcessDealUtils {


    /**
     * 将当前节点处理表中该步骤结束
     *
     * @param currentDealStepDao CurrentDealStepMapper
     * @param currentStepId      当前处理表id
     * @param userCode           处理人id
     */
    public void endCurrentStep(CurrentDealStepMapper currentDealStepDao, int currentStepId,boolean isAdopt ,String userCode, Date time) {
        CurrentDealStep endStep = new CurrentDealStep(currentStepId,(byte)1, (byte)(isAdopt?1:0),userCode, time);
        currentDealStepDao.updateEndStep(endStep);
    }

    /**
     * 在当前处理表中插入新的节点信息
     * 在处理人表中插入新节点的处理人
     *
     * @param currentDealStepDao CurrentDealStepMapper
     * @param stepDealUserDao    StepDealUserMapper
     * @param nextStep           StepJumpBean
     * @param time  Date
     * @param currentDealId 当前步骤处理表的id
     * @param currentStepId 当前步骤对应信息表的id
     */
    public void newCurrentStep(CurrentDealStepMapper currentDealStepDao, StepDealUserMapper stepDealUserDao, StepJumpBean nextStep, Date time,int currentDealId,int currentStepId) {

        //CurrentDealStep nextDealStep = new CurrentDealStep((byte) 0, nextStep.getStartUser(), time, nextStep.getStartCode(), nextStep.getFlowID(), nextStep.getEndCode(), nextStep.getEndName(), nextStep.getEndUser(), nextStep.getEndTableId(), nextStep.getStartUser(), time);
        CurrentDealStep nextDealStep = new CurrentDealStep((byte) 0,currentDealId, nextStep.getStartUser(), time, nextStep.getStartCode(),nextStep.getStartName(),currentStepId, nextStep.getFlowID(), nextStep.getEndCode(), nextStep.getEndName(), nextStep.getEndUser(), nextStep.getEndTableId(), nextStep.getStartUser(), time);
        currentDealStepDao.insertSelective(nextDealStep);

        StepDealUser qrqcDealUser = new StepDealUser(nextStep.getFlowID(), nextStep.getEndCode() + "", nextStep.getEndUser(), nextStep.getStartUser(), time);
        stepDealUserDao.insertSelective(qrqcDealUser);
    }

    /**
     * 保存记录
     *
     * @param recordSubmitDao RecordSubmitMapper
     * @param nextStep        StepJumpBean
     * @param json            上传内容的json
     * @param stepName        当前节点的名称
     */
    public void saveRecord(RecordSubmitMapper recordSubmitDao, StepJumpBean nextStep, String json, String stepName, Date time) {
        RecordSubmit recordSubmit = new RecordSubmit(null, nextStep.getFlowID(), stepName, nextStep.getStartUser(), time, json);
        recordSubmitDao.insertSelective(recordSubmit);
    }



}
