//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\visit\\Visit.java

package com.neusoft.hs.domain.history.visit;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

import com.neusoft.hs.platform.entity.HisEntity;

/**
 * 患者一次就诊历史信息代表了患者与医院之间具有连续行为并有短期目标的一次诊疗活动，它可能是门诊诊疗活动和住院诊疗活动的集合;
 * Visit在患者挂号时创建，或者在患者住院时创建； 一次诊疗活动会关联多次挂号； Visit通过@VisitDomainService.create创建;
 * 每一个Visit都会关联一个@Patient，通过cardNumber进行关联
 * 
 * @author kingbox
 *
 */
@Entity
@Table(name = "his_domain_visit", indexes = { @Index(columnList = "card_number") })
public class VisitHis extends HisEntity {

	@Column(name = "card_number", length = 64)
	private String cardNumber;

	@NotEmpty(message = "名称不能为空")
	@Column(length = 32)
	private String name;

	@NotEmpty(message = "状态不能为空")
	@Column(length = 32)
	private String state;

	@Column(name = "state_desc", length = 128)
	private String stateDesc;

	@Column(length = 16)
	private String bed;

	@Column(name = "create_date")
	private Date createDate;

	private Boolean last;

	@Column(name = "voucher_date")
	private Date voucherDate;

	@Column(name = "into_ward_date")
	private Date intoWardDate;

	@Column(name = "plan_leave_ward_date")
	private Date planLeaveWardDate;

	@Column(name = "leave_ward_date")
	private Date leaveWardDate;

	@Column(name = "nurse_id", length = 36)
	private String respNurseId;

	@Column(name = "nurse_name", length = 32)
	private String respNurseName;

	@Column(name = "doctor_id", length = 36)
	private String respDoctorId;

	@Column(name = "doctor_name", length = 32)
	private String respDoctorName;

	@Column(name = "dept_id", length = 36)
	private String deptId;

	@Column(name = "dept_name", length = 32)
	private String deptName;

	@Column(name = "previous_dept_id", length = 36)
	private String previousDeptId;

	@Column(name = "previous_dept_name", length = 32)
	private String previousDeptName;

	@Column(name = "area_id", length = 36)
	private String areaId;

	@Column(name = "area_name", length = 32)
	private String areaName;

	@Column(name = "patient_id", length = 36)
	private String patientId;

	@Column(name = "patient_name", length = 32)
	private String patientName;

	@Column(name = "charge_bill_id", length = 36)
	private String chargeBillId;

	@Column(name = "clip_id", length = 36)
	private String medicalRecordClipId;

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

	public String getPreviousDeptId() {
		return previousDeptId;
	}

	public void setPreviousDeptId(String previousDeptId) {
		this.previousDeptId = previousDeptId;
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

	public String getChargeBillId() {
		return chargeBillId;
	}

	public void setChargeBillId(String chargeBillId) {
		this.chargeBillId = chargeBillId;
	}

	public String getMedicalRecordClipId() {
		return medicalRecordClipId;
	}

	public void setMedicalRecordClipId(String medicalRecordClipId) {
		this.medicalRecordClipId = medicalRecordClipId;
	}
}
