package com.zkd.service.impl;

import com.zkd.common.bean.back.ReturnDataBean;
import com.zkd.common.bean.back.ReturnProcessDataBean;
import com.zkd.common.bean.back.ReturnProcessDealDataBean;
import com.zkd.common.bean.other.UserDataBean;
import com.zkd.common.bean.request.RequestLoadBaseDataBean;
import com.zkd.common.constant.MsgConstant;
import com.zkd.common.constant.StepConstant;
import com.zkd.dao.map.CommissionerFillMapper;
import com.zkd.dao.map.CurrentDealStepMapper;
import com.zkd.dao.map.TotalFlowMapper;
import com.zkd.dao.map.UserInfoMapper;
import com.zkd.entity.CommissionerFill;
import com.zkd.entity.CurrentDealStep;
import com.zkd.entity.TotalFlow;
import com.zkd.service.IProcessService;
import com.zkd.utils.EncryptUtils;
import org.checkerframework.checker.units.qual.A;
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
    public String getAllProcess() throws Exception {
         List<TotalFlow> processList = flowDao.selectAllFlow();
        List<ReturnProcessDataBean> returnProcessList = new ArrayList<>();
        for (TotalFlow flow : processList) {
            ReturnProcessDataBean data = new ReturnProcessDataBean(flow.getStatus(), flow.getQrqcIdentifier(), flow.getCreateDate(), flow.getIsClosedPre(), flow.getUpdateDate());
            List<UserDataBean> createUserList = userInfoDao.selectUserInfoById(flow.getCreateUser());
            if (createUserList!=null&&createUserList.size()>0) {
                data.setCreateUser(createUserList.get(0));
            }
            if (!flow.getUpdateUser().equals("")) {
                List<UserDataBean> updateUserList = userInfoDao.selectUserInfoById(flow.getUpdateUser());
                if (updateUserList!=null&&updateUserList.size()>0) {
                    data.setLastDealUser(updateUserList.get(0));
                }
            }
            List<CommissionerFill> commissionerFillList = commissionerFillDao.selectByFlowId(flow.getQrqcIdentifier());
            if (commissionerFillList!=null&&commissionerFillList.size()>0) {
                data.setCommissionerFill(commissionerFillList.get(0));
            }
            returnProcessList.add(data);
        }
        ReturnDataBean<List<ReturnProcessDataBean>> returnDataBean = new ReturnDataBean<>(200, returnProcessList, MsgConstant.MSG_SUCCESS);
        return new EncryptUtils<>().encryptObj(returnDataBean);
    }

    @Override
    public String getDealProcess(String data) {
        RequestLoadBaseDataBean requestData = new EncryptUtils<RequestLoadBaseDataBean>().decryptObj(data, RequestLoadBaseDataBean.class);
        List<ReturnProcessDealDataBean> returnList = new ArrayList<>();
        List<CurrentDealStep> dealList = currentDealStepDao.selectDealByUserNo(requestData);
        if (dealList!=null) {
            for (CurrentDealStep currentStep:dealList ) {
                UserDataBean createUser = userInfoDao.selectByUserNo(currentStep.getCreateUser());
                switch (currentStep.getCreateStepCode()) {
                    case StepConstant.QRQC_COMMISSIONER_FILL_CODE:
                        //CommissionerFill commissionerFill = commissionerFillDao.selectByPrimaryKey(data.);
                       // ReturnProcessDealDataBean<CommissionerFill> totalData = new ReturnProcessDealDataBean<CommissionerFill>(currentStep, createUser, commissionerFill);
                        break;

                }

            }
        }
        return new EncryptUtils<>().encryptObj(new ReturnDataBean<>(MsgConstant.CODE_SUCCESS, returnList, MsgConstant.MSG_SUCCESS));
    }

    @Override
    public String getFinishedProcess(String data) {
        RequestLoadBaseDataBean requestData = new EncryptUtils<RequestLoadBaseDataBean>().decryptObj(data, RequestLoadBaseDataBean.class);
        List<CurrentDealStep> finishList = currentDealStepDao.selectFinishedByUserNo(requestData);
        return new EncryptUtils<>().encryptObj(new ReturnDataBean<>(MsgConstant.CODE_SUCCESS, finishList, MsgConstant.MSG_SUCCESS));
    }
}
