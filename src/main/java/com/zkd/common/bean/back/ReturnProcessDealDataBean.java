package com.zkd.common.bean.back;

import com.zkd.common.bean.other.UserDataBean;
import com.zkd.entity.CurrentDealStep;

public class ReturnProcessDealDataBean<T> {

    CurrentDealStep currentDealStep;
    UserDataBean createUser;
    T lastStepData;


    public ReturnProcessDealDataBean(CurrentDealStep currentDealStep, UserDataBean createUser, T lastStepData) {
        this.currentDealStep = currentDealStep;
        this.createUser = createUser;
        this.lastStepData = lastStepData;
    }

    public CurrentDealStep getCurrentDealStep() {
        return currentDealStep;
    }

    public void setCurrentDealStep(CurrentDealStep currentDealStep) {
        this.currentDealStep = currentDealStep;
    }

    public UserDataBean getCreateUser() {
        return createUser;
    }

    public void setCreateUser(UserDataBean createUser) {
        this.createUser = createUser;
    }

    public T getLastStepData() {
        return lastStepData;
    }

    public void setLastStepData(T lastStepData) {
        this.lastStepData = lastStepData;
    }
}
