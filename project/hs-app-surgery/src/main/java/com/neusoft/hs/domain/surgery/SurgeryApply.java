package com.neusoft.hs.domain.surgery;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.neusoft.hs.domain.order.Apply;
import com.neusoft.hs.domain.organization.Dept;

@Entity
@Table(name = "app_surgery_apply")
public class SurgeryApply extends Apply {

	@OneToMany(mappedBy = "surgeryApply", cascade = { CascadeType.PERSIST, CascadeType.MERGE,
			CascadeType.REMOVE })
	private List<SurgeryApplyItem> surgeryApplyItems;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "arrange_dept_id")
	private Dept arrangeDept;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "surgery_dept_id")
	private Dept surgeryDept;

	@Column(name = "surgery_place", length = 64)
	private String surgeryPlace;

	@Column(name = "plan_execute_date")
	private Date planExecuteDate;

	@Column(name = "execute_date")
	private Date executeDate;

	public List<SurgeryApplyItem> getSurgeryApplyItems() {
		return surgeryApplyItems;
	}

	public void setSurgeryApplyItems(List<SurgeryApplyItem> surgeryApplyItems) {
		this.surgeryApplyItems = surgeryApplyItems;
	}

	public Dept getArrangeDept() {
		return arrangeDept;
	}

	public void setArrangeDept(Dept arrangeDept) {
		this.arrangeDept = arrangeDept;
	}

	public Dept getSurgeryDept() {
		return surgeryDept;
	}

	public void setSurgeryDept(Dept surgeryDept) {
		this.surgeryDept = surgeryDept;
	}

	public String getSurgeryPlace() {
		return surgeryPlace;
	}

	public void setSurgeryPlace(String surgeryPlace) {
		this.surgeryPlace = surgeryPlace;
	}

	public Date getPlanExecuteDate() {
		return planExecuteDate;
	}

	public void setPlanExecuteDate(Date planExecuteDate) {
		this.planExecuteDate = planExecuteDate;
	}

	public Date getExecuteDate() {
		return executeDate;
	}

	public void setExecuteDate(Date executeDate) {
		this.executeDate = executeDate;
	}
}
