package com.neusoft.hs.domain.history.visit;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.neusoft.hs.platform.entity.HisEntity;

@Entity
@Table(name = "his_domain_visit_log")
public class VisitLogHis extends HisEntity {

	@Column(name = "visit_id", length = 36)
	private String visitId;

	@Column(name = "visit_name", length = 16)
	private String visitName;

	@Column(length = 16)
	private String type;

	@Column(length = 256)
	private String info;

	@Column(name = "operator_id", length = 36)
	private String operatorId;

	@Column(name = "operator_name", length = 32)
	private String operatorName;

	@Column(name = "create_date")
	private Date createDate;

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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

}
