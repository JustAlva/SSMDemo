package com.zkd.common.bean.request;

public class RequestCommissionerFillSubmitDataBean {
    private  String userCode;//创建用户
    private  String model; //型号
    private  String productLine;//线别
    private  String customer;//客户
    private  String occurrenceSource;//发生源
    private  String occurrenceDate;//发生日期
    private  String worksheet;//工单
    private  String supplier;//供应商
    private  String reportDate;//提报时间
    private  String receiveDate;//接收时间
    private  int reportDepartment;//提出部门
    private  String personal;//人员
    private  String surePersonal;//确认人员
    private  String phenomena;//不良现象/发生过程
    private  String productDate;//生产时间段
    private  String productedNum;//已生产数量
    private  String unhealthyNum;//不良数量
    private  float unhealthyRate;//不良比率
    private  String selectPeOrQe;//选择的qe或pe
    private  String selectQE;//选择的QE

    public RequestCommissionerFillSubmitDataBean(String userCode, String model, String productLine, String customer, String occurrenceSource, String occurrenceDate, String worksheet, String supplier, String reportDate, String receiveDate, int reportDepartment, String personal, String surePersonal, String phenomena, String productDate, String productedNum, String unhealthyNum, float unhealthyRate, String selectPeOrQe, String selectQE) {
        this.userCode = userCode;
        this.model = model;
        this.productLine = productLine;
        this.customer = customer;
        this.occurrenceSource = occurrenceSource;
        this.occurrenceDate = occurrenceDate;
        this.worksheet = worksheet;
        this.supplier = supplier;
        this.reportDate = reportDate;
        this.receiveDate = receiveDate;
        this.reportDepartment = reportDepartment;
        this.personal = personal;
        this.surePersonal = surePersonal;
        this.phenomena = phenomena;
        this.productDate = productDate;
        this.productedNum = productedNum;
        this.unhealthyNum = unhealthyNum;
        this.unhealthyRate = unhealthyRate;
        this.selectPeOrQe = selectPeOrQe;
        this.selectQE = selectQE;
    }
}
