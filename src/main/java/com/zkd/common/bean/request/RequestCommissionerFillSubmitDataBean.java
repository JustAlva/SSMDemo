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
    private  int productedNum;//已生产数量
    private  int unhealthyNum;//不良数量
    private  double unhealthyRate;//不良比率
    private  String selectPeOrQe;//选择的qe或pe
    private  String selectQE;//选择的QE

    public RequestCommissionerFillSubmitDataBean(String userCode, String model, String productLine, String customer, String occurrenceSource, String occurrenceDate, String worksheet, String supplier, String reportDate, String receiveDate, int reportDepartment, String personal, String surePersonal, String phenomena, String productDate, int productedNum, int unhealthyNum, double unhealthyRate, String selectPeOrQe, String selectQE) {
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

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getProductLine() {
        return productLine;
    }

    public void setProductLine(String productLine) {
        this.productLine = productLine;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getOccurrenceSource() {
        return occurrenceSource;
    }

    public void setOccurrenceSource(String occurrenceSource) {
        this.occurrenceSource = occurrenceSource;
    }

    public String getOccurrenceDate() {
        return occurrenceDate;
    }

    public void setOccurrenceDate(String occurrenceDate) {
        this.occurrenceDate = occurrenceDate;
    }

    public String getWorksheet() {
        return worksheet;
    }

    public void setWorksheet(String worksheet) {
        this.worksheet = worksheet;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getReportDate() {
        return reportDate;
    }

    public void setReportDate(String reportDate) {
        this.reportDate = reportDate;
    }

    public String getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(String receiveDate) {
        this.receiveDate = receiveDate;
    }

    public int getReportDepartment() {
        return reportDepartment;
    }

    public void setReportDepartment(int reportDepartment) {
        this.reportDepartment = reportDepartment;
    }

    public String getPersonal() {
        return personal;
    }

    public void setPersonal(String personal) {
        this.personal = personal;
    }

    public String getSurePersonal() {
        return surePersonal;
    }

    public void setSurePersonal(String surePersonal) {
        this.surePersonal = surePersonal;
    }

    public String getPhenomena() {
        return phenomena;
    }

    public void setPhenomena(String phenomena) {
        this.phenomena = phenomena;
    }

    public String getProductDate() {
        return productDate;
    }

    public void setProductDate(String productDate) {
        this.productDate = productDate;
    }

    public int getProductedNum() {
        return productedNum;
    }

    public void setProductedNum(int productedNum) {
        this.productedNum = productedNum;
    }

    public int getUnhealthyNum() {
        return unhealthyNum;
    }

    public void setUnhealthyNum(int unhealthyNum) {
        this.unhealthyNum = unhealthyNum;
    }

    public double getUnhealthyRate() {
        return unhealthyRate;
    }

    public void setUnhealthyRate(double unhealthyRate) {
        this.unhealthyRate = unhealthyRate;
    }

    public String getSelectPeOrQe() {
        return selectPeOrQe;
    }

    public void setSelectPeOrQe(String selectPeOrQe) {
        this.selectPeOrQe = selectPeOrQe;
    }

    public String getSelectQE() {
        return selectQE;
    }

    public void setSelectQE(String selectQE) {
        this.selectQE = selectQE;
    }
}
