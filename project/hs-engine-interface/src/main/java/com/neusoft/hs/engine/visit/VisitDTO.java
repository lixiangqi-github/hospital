package com.neusoft.hs.engine.visit;

import java.util.Date;

public class VisitDTO {

	private String id;

	private String cardNumber;

	private String name;

	private String state;

	private String stateDesc;

	private String bed;

	private Date createDate;

	private Boolean last;

	private Date voucherDate;

	private Date intoWardDate;

	private Date planLeaveWardDate;

	private Date leaveWardDate;

	private String respNurseId;

	private String respNurseName;

	private String respDoctorId;

	private String respDoctorName;

	private String deptId;

	private String deptName;

	private String previousDept;

	private String previousDeptName;

	private String areaId;

	private String areaName;

	private String patientId;

	private String patientName;

	public VisitDTO() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getStateDesc() {
		return stateDesc;
	}

	public void setStateDesc(String stateDesc) {
		this.stateDesc = stateDesc;
	}

	public String getBed() {
		return bed;
	}

	public void setBed(String bed) {
		this.bed = bed;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Boolean getLast() {
		return last;
	}

	public void setLast(Boolean last) {
		this.last = last;
	}

	public Date getVoucherDate() {
		return voucherDate;
	}

	public void setVoucherDate(Date voucherDate) {
		this.voucherDate = voucherDate;
	}

	public Date getIntoWardDate() {
		return intoWardDate;
	}

	public void setIntoWardDate(Date intoWardDate) {
		this.intoWardDate = intoWardDate;
	}

	public Date getPlanLeaveWardDate() {
		return planLeaveWardDate;
	}

	public void setPlanLeaveWardDate(Date planLeaveWardDate) {
		this.planLeaveWardDate = planLeaveWardDate;
	}

	public Date getLeaveWardDate() {
		return leaveWardDate;
	}

	public void setLeaveWardDate(Date leaveWardDate) {
		this.leaveWardDate = leaveWardDate;
	}

	public String getRespNurseId() {
		return respNurseId;
	}

	public void setRespNurseId(String respNurseId) {
		this.respNurseId = respNurseId;
	}

	public String getRespNurseName() {
		return respNurseName;
	}

	public void setRespNurseName(String respNurseName) {
		this.respNurseName = respNurseName;
	}

	public String getRespDoctorId() {
		return respDoctorId;
	}

	public void setRespDoctorId(String respDoctorId) {
		this.respDoctorId = respDoctorId;
	}

	public String getRespDoctorName() {
		return respDoctorName;
	}

	public void setRespDoctorName(String respDoctorName) {
		this.respDoctorName = respDoctorName;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getPreviousDept() {
		return previousDept;
	}

	public void setPreviousDept(String previousDept) {
		this.previousDept = previousDept;
	}

	public String getPreviousDeptName() {
		return previousDeptName;
	}

	public void setPreviousDeptName(String previousDeptName) {
		this.previousDeptName = previousDeptName;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getPatientId() {
		return patientId;
	}

	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}

	public String getPatientName() {
		return patientName;
	}

	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}
}
