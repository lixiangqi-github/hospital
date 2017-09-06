package com.neusoft.hs.domain.visit;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.neusoft.hs.domain.organization.AbstractUser;
import com.neusoft.hs.domain.organization.Dept;

@Entity
@Table(name = "domain_visit_plan_rcord")
public class VisitPlanRecord {

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "visit_id")
	private Visit visit;

	@Column(name = "visit_name", length = 16)
	private String visitName;

	@Column(name = "plan_execute_date")
	private Date planExecuteDate;

	@Column(length = 16)
	private String type;

	@Column(name = "plan_describe", length = 128)
	private String describe;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "dept_id")
	private Dept dept;

	@Column(name = "dept_name", length = 32)
	private String deptName;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "operator_id")
	private AbstractUser operator;

	@Column(name = "operator_name", length = 32)
	private String operatorName;

	public Visit getVisit() {
		return visit;
	}

	public void setVisit(Visit visit) {
		this.visit = visit;
	}

	public String getVisitName() {
		return visitName;
	}

	public void setVisitName(String visitName) {
		this.visitName = visitName;
	}

	public Date getPlanExecuteDate() {
		return planExecuteDate;
	}

	public void setPlanExecuteDate(Date planExecuteDate) {
		this.planExecuteDate = planExecuteDate;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public Dept getDept() {
		return dept;
	}

	public void setDept(Dept dept) {
		this.dept = dept;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public AbstractUser getOperator() {
		return operator;
	}

	public void setOperator(AbstractUser operator) {
		this.operator = operator;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

}
