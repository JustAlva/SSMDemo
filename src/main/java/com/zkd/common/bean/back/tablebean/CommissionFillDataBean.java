package com.zkd.common.bean.back.tablebean;

import com.zkd.entity.CommissionerFill;
import com.zkd.utils.MyDateUtils;

public class CommissionFillDataBean {

    private String id ;
    private String status ;
    private String createUser ;
    private String createDate ;
    private String flowId ;
    private String model ;
    private String productLine ;
    private String customer ;
    private String occurrenceSource ;
    private String occurrenceDate ;
    private String worksheet ;
    private String reportDate ;
    private String receiveDate ;
    private String reportDepartment ;
    private String reportUser ;
    private String confirmUser ;
    private String productDate ;
    private String producedNumber ;
    private String unhealthyNumber ;
    private String unhealthyRate ;
    private String selectQePe ;
    private String selectQe ;
    private String phenomenaProcess ;

    public CommissionFillDataBean(CommissionerFill data) {
        this.id = data.getId()+"";
        this.status = data.getStatus()+"";
        this.createUser = data.getCreateUser();
        this.createDate = MyDateUtils.getDate2String(data.getCreateDate(),MyDateUtils.FORMAT_TYPE_3) ;
        this.flowId = data.getFlowId();
        this.model = data.getModel();
        this.productLine = data.getProductLine();
        this.customer = data.getCustomer();
        this.occurrenceSource = data.getOccurrenceSource();
        this.occurrenceDate = MyDateUtils.getDate2String(data.getOccurrenceDate());
        this.worksheet = data.getWorksheet();
        this.reportDate = MyDateUtils.getDate2String(data.getReportDate(),MyDateUtils.FORMAT_TYPE_6);
        this.receiveDate = MyDateUtils.getDate2String(data.getReceiveDate(),MyDateUtils.FORMAT_TYPE_6);
        this.reportDepartment = data.getReportDepartment();
        this.reportUser = data.getReportUser();
        this.confirmUser = data.getConfirmUser();
        this.productDate = MyDateUtils.getDate2String(data.getProductDate(),MyDateUtils.FORMAT_TYPE_6);
        this.producedNumber = data.getProducedNumber()+"";
        this.unhealthyNumber = data.getUnhealthyNumber()+"";
        this.unhealthyRate = data.getUnhealthyRate()+"";
        this.selectQePe = data.getSelectQePe();
        this.selectQe = data.getSelectQe();
        this.phenomenaProcess = data.getPhenomenaProcess();
    }
    public CommissionFillDataBean(String id, String status, String createUser, String createDate, String flowId, String model, String productLine, String customer, String occurrenceSource, String occurrenceDate, String worksheet, String reportDate, String receiveDate, String reportDepartment, String reportUser, String confirmUser, String productDate, String producedNumber, String unhealthyNumber, String unhealthyRate, String selectQePe, String selectQe, String phenomenaProcess) {
        this.id = id;
        this.status = status;
        this.createUser = createUser;
        this.createDate = createDate;
        this.flowId = flowId;
        this.model = model;
        this.productLine = productLine;
        this.customer = customer;
        this.occurrenceSource = occurrenceSource;
        this.occurrenceDate = occurrenceDate;
        this.worksheet = worksheet;
        this.reportDate = reportDate;
        this.receiveDate = receiveDate;
        this.reportDepartment = reportDepartment;
        this.reportUser = reportUser;
        this.confirmUser = confirmUser;
        this.productDate = productDate;
        this.producedNumber = producedNumber;
        this.unhealthyNumber = unhealthyNumber;
        this.unhealthyRate = unhealthyRate;
        this.selectQePe = selectQePe;
        this.selectQe = selectQe;
        this.phenomenaProcess = phenomenaProcess;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getFlowId() {
        return flowId;
    }

    public void setFlowId(String flowId) {
        this.flowId = flowId;
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

    public String getReportDepartment() {
        return reportDepartment;
    }

    public void setReportDepartment(String reportDepartment) {
        this.reportDepartment = reportDepartment;
    }

    public String getReportUser() {
        return reportUser;
    }

    public void setReportUser(String reportUser) {
        this.reportUser = reportUser;
    }

    public String getConfirmUser() {
        return confirmUser;
    }

    public void setConfirmUser(String confirmUser) {
        this.confirmUser = confirmUser;
    }

    public String getProductDate() {
        return productDate;
    }

    public void setProductDate(String productDate) {
        this.productDate = productDate;
    }

    public String getProducedNumber() {
        return producedNumber;
    }

    public void setProducedNumber(String producedNumber) {
        this.producedNumber = producedNumber;
    }

    public String getUnhealthyNumber() {
        return unhealthyNumber;
    }

    public void setUnhealthyNumber(String unhealthyNumber) {
        this.unhealthyNumber = unhealthyNumber;
    }

    public String getUnhealthyRate() {
        return unhealthyRate;
    }

    public void setUnhealthyRate(String unhealthyRate) {
        this.unhealthyRate = unhealthyRate;
    }

    public String getSelectQePe() {
        return selectQePe;
    }

    public void setSelectQePe(String selectQePe) {
        this.selectQePe = selectQePe;
    }

    public String getSelectQe() {
        return selectQe;
    }

    public void setSelectQe(String selectQe) {
        this.selectQe = selectQe;
    }

    public String getPhenomenaProcess() {
        return phenomenaProcess;
    }

    public void setPhenomenaProcess(String phenomenaProcess) {
        this.phenomenaProcess = phenomenaProcess;
    }
}
