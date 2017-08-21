package com.neusoft.hs.domain.recordroom;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

import com.neusoft.hs.domain.medicalrecord.MedicalRecordClip;
import com.neusoft.hs.domain.organization.AbstractUser;
import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.platform.entity.HisEntity;

/**
 * 病案
 * 
 * @author kingbox
 *
 */
@Entity
@Table(name = "his_app_recordroom_medical_case")
public class MedicalCaseHis extends HisEntity {

	@Column(name = "case_number", length = 36)
	private String caseNumber;

	@Column(name = "clip_id", length = 36)
	private String clipId;

	@Column(name = "visit_id", length = 36)
	private String visitId;

	@Column(name = "visit_name", length = 16)
	private String visitName;

	@Column(length = 32)
	private String position;

	@NotEmpty(message = "状态不能为空")
	@Column(length = 32)
	private String state;

	@Column(name = "creator_id", length = 36)
	private String creatorId;

	@Column(name = "create_date")
	private Date createDate;

	public static final String State_InRoom = "在病案室";

	public MedicalCaseHis() {
	}

	public String getCaseNumber() {
		return caseNumber;
	}

	public void setCaseNumber(String caseNumber) {
		this.caseNumber = caseNumber;
	}

	public String getClipId() {
		return clipId;
	}

	public void setClipId(String clipId) {
		this.clipId = clipId;
	}

	public String getVisitId() {
		return visitId;
	}

	public void setVisitId(String visitId) {
		this.visitId = visitId;
	}

	public String getVisitName() {
		return visitName;
	}

	public void setVisitName(String visitName) {
		this.visitName = visitName;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

}
