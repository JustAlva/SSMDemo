package com.zkd.service.impl;

import com.zkd.common.bean.back.ReturnDataBean;
import com.zkd.common.bean.back.ReturnProcessDataBean;
import com.zkd.common.bean.back.ReturnProcessDealDataBean;
import com.zkd.common.bean.back.ReturnProcessTotalDetailDataBean;
import com.zkd.common.bean.back.tablebean.CommissionFillDataBean;
import com.zkd.common.bean.other.UserDataBean;
import com.zkd.common.bean.request.RequestLoadBaseDataBean;
import com.zkd.common.bean.request.RequestTotalProcessSearchDataBean;
import com.zkd.common.constant.MsgConstant;
import com.zkd.dao.map.CommissionerFillMapper;
import com.zkd.dao.map.CurrentDealStepMapper;
import com.zkd.dao.map.TotalFlowMapper;
import com.zkd.dao.map.UserInfoMapper;
import com.zkd.entity.CommissionerFill;
import com.zkd.entity.CurrentDealStep;
import com.zkd.entity.TotalFlow;
import com.zkd.service.IProcessService;
import com.zkd.utils.EncryptUtils;
import com.zkd.utils.MyDateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("processService")
public class ProcessService implements IProcessService {

    @Autowired
    TotalFlowMapper flowDao;
    @Autowired
    UserInfoMapper userInfoDao;
    @Autowired
    CommissionerFillMapper commissionerFillDao;
    @Autowired
    CurrentDealStepMapper currentDealStepDao;


    @Override
    public String getAllProcess() {
        List<TotalFlow> processList = flowDao.selectAllFlow();
        List<ReturnProcessDataBean> returnProcessList = new ArrayList<>();
        for (TotalFlow flow : processList) {
            ReturnProcessDataBean data = new ReturnProcessDataBean(flow.getStatus(), flow.getQrqcIdentifier(), MyDateUtils.getDate2String(flow.getCreateDate(), MyDateUtils.FORMAT_TYPE_3), flow.getIsClosedPre(), MyDateUtils.getDate2String(flow.getUpdateDate(), MyDateUtils.FORMAT_TYPE_3));
            List<UserDataBean> createUserList = userInfoDao.selectUserInfoById(flow.getCreateUser());
            if (createUserList != null && createUserList.size() > 0) {
                data.setCreateUser(createUserList.get(0));
            }
            if (!flow.getUpdateUser().equals("")) {
                List<UserDataBean> updateUserList = userInfoDao.selectUserInfoById(flow.getUpdateUser());
                if (updateUserList != null && updateUserList.size() > 0) {
                    data.setLastDealUser(updateUserList.get(0));
                }
            }
            List<CommissionerFill> commissionerFillList = commissionerFillDao.selectByFlowId(flow.getQrqcIdentifier());
            if (commissionerFillList != null && commissionerFillList.size() > 0) {
                data.setCommissionerFill(new CommissionFillDataBean(commissionerFillList.get(0)));
            }
            returnProcessList.add(data);
        }
        ReturnDataBean<List<ReturnProcessDataBean>> returnDataBean = new ReturnDataBean<>(200, returnProcessList, MsgConstant.MSG_SUCCESS);
        return new EncryptUtils<>().encryptObj(returnDataBean);
    }

    @Override
    public String getProcessAllDetail(String data) {
        RequestLoadBaseDataBean requestData = new EncryptUtils<RequestLoadBaseDataBean>().decryptObj(data, RequestLoadBaseDataBean.class);
        ReturnProcessTotalDetailDataBean returnData = new ReturnProcessTotalDetailDataBean();
        if (requestData != null) {
            List<CurrentDealStep> stepList = currentDealStepDao.selectAllStep(requestData.getFlowID());
            returnData.setStepList(stepList);
            List<CommissionerFill> commissionerList = commissionerFillDao.selectByFlowId(requestData.getFlowID());
            if (commissionerList != null & commissionerList.size() > 0) {
                CommissionFillDataBean commissionFill = new CommissionFillDataBean(commissionerList.get(0));
                returnData.setCommissionerFill(commissionFill);
            }
        }
        return new EncryptUtils<>().encryptObj(new ReturnDataBean<>(MsgConstant.CODE_SUCCESS, returnData, MsgConstant.MSG_SUCCESS));
    }

    @Override
    public String getDealProcess(String data) {
        RequestLoadBaseDataBean requestData = new EncryptUtils<RequestLoadBaseDataBean>().decryptObj(data, RequestLoadBaseDataBean.class);
        List<ReturnProcessDealDataBean> returnList = new ArrayList<>();
        List<CurrentDealStep> dealList = currentDealStepDao.selectDealByUserNo(requestData);
        if (dealList != null) {
            // for (CurrentDealStep currentStep : dealList) {
            //UserDataBean createUser = userInfoDao.selectByUserNo(currentStep.getCreateUser());
            // switch (currentStep.getCreateStepCode()) {
            //  case StepConstant.QRQC_COMMISSIONER_FILL_CODE:
            //CommissionerFill commissionerFill = commissionerFillDao.selectByPrimaryKey(data.);
            // ReturnProcessDealDataBean<CommissionerFill> totalData = new ReturnProcessDealDataBean<CommissionerFill>(currentStep, createUser, commissionerFill);
            //   break;

            // }

            // }
        }
        return new EncryptUtils<>().encryptObj(new ReturnDataBean<>(MsgConstant.CODE_SUCCESS, dealList, MsgConstant.MSG_SUCCESS));
    }

    @Override
    public String getFinishedProcess(String data) {
        RequestLoadBaseDataBean requestData = new EncryptUtils<RequestLoadBaseDataBean>().decryptObj(data, RequestLoadBaseDataBean.class);
        List<CurrentDealStep> finishList = currentDealStepDao.selectFinishedByUserNo(requestData);
        return new EncryptUtils<>().encryptObj(new ReturnDataBean<>(MsgConstant.CODE_SUCCESS, finishList, MsgConstant.MSG_SUCCESS));
    }

    @Override
    public String search(String data) {
        RequestTotalProcessSearchDataBean requestData = new EncryptUtils<RequestTotalProcessSearchDataBean>().decryptObj(data, RequestTotalProcessSearchDataBean.class);
        List<ReturnProcessDataBean> returnProcessList = new ArrayList<>();
        if (requestData != null) {
            List<CommissionerFill> commissionerFillList = commissionerFillDao.selectTotalSearch("%"+requestData.getSearch()+"%");
            for (CommissionerFill commissioner : commissionerFillList) {
                TotalFlow totalFlow = flowDao.selectByFlowId(commissioner.getFlowId());
                ReturnProcessDataBean dataBean = new ReturnProcessDataBean(totalFlow.getStatus(), totalFlow.getQrqcIdentifier(), MyDateUtils.getDate2String(totalFlow.getCreateDate(), MyDateUtils.FORMAT_TYPE_3), totalFlow.getIsClosedPre(), MyDateUtils.getDate2String(totalFlow.getUpdateDate(), MyDateUtils.FORMAT_TYPE_3));
                List<UserDataBean> createUserList = userInfoDao.selectUserInfoById(totalFlow.getCreateUser());
                if (createUserList != null && createUserList.size() > 0) {
                    dataBean.setCreateUser(createUserList.get(0));
                }
                if (!totalFlow.getUpdateUser().equals("")) {
                    List<UserDataBean> updateUserList = userInfoDao.selectUserInfoById(totalFlow.getUpdateUser());
                    if (updateUserList != null && updateUserList.size() > 0) {
                        dataBean.setLastDealUser(updateUserList.get(0));
                    }
                }
                dataBean.setCommissionerFill(new CommissionFillDataBean(commissioner));
                returnProcessList.add(dataBean);
            }
        }
        ReturnDataBean<List<ReturnProcessDataBean>> returnDataBean = new ReturnDataBean<>(200, returnProcessList, MsgConstant.MSG_SUCCESS);
        return new EncryptUtils<>().encryptObj(returnDataBean);
    }

    @Override
    public String searchDeal(String data) {
        RequestTotalProcessSearchDataBean requestData = new EncryptUtils<RequestTotalProcessSearchDataBean>().decryptObj(data, RequestTotalProcessSearchDataBean.class);
        List<CurrentDealStep> dealList = currentDealStepDao.selectSearchDeal(requestData.getUserCode(),"%"+requestData.getSearch()+"%");
        return new EncryptUtils<>().encryptObj(new ReturnDataBean<>(MsgConstant.CODE_SUCCESS, dealList, MsgConstant.MSG_SUCCESS));
    }

    @Override
    public String searchFinished(String data) {
        RequestTotalProcessSearchDataBean requestData = new EncryptUtils<RequestTotalProcessSearchDataBean>().decryptObj(data, RequestTotalProcessSearchDataBean.class);
        List<CurrentDealStep> finishList = currentDealStepDao.selectSearchFinished(requestData.getUserCode(),"%"+requestData.getSearch()+"%");
        return new EncryptUtils<>().encryptObj(new ReturnDataBean<>(MsgConstant.CODE_SUCCESS, finishList, MsgConstant.MSG_SUCCESS));
    }
}
