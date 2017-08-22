package com.neusoft.hs.engine.order;

import java.util.Date;

public class CreateInspectApplyItemDTO {

	private String inspectItemId;

	private String arrangeDeptId;

	private String inspectDeptId;

	private String inspectPlace;

	private Date planExecuteDate;

	public String getInspectItemId() {
		return inspectItemId;
	}

	public void setInspectItemId(String inspectItemId) {
		this.inspectItemId = inspectItemId;
	}

	public String getArrangeDeptId() {
		return arrangeDeptId;
	}

	public void setArrangeDeptId(String arrangeDeptId) {
		this.arrangeDeptId = arrangeDeptId;
	}

	public String getInspectDeptId() {
		return inspectDeptId;
	}

	public void setInspectDeptId(String inspectDeptId) {
		this.inspectDeptId = inspectDeptId;
	}

	public String getInspectPlace() {
		return inspectPlace;
	}

	public void setInspectPlace(String inspectPlace) {
		this.inspectPlace = inspectPlace;
	}

	public Date getPlanExecuteDate() {
		return planExecuteDate;
	}

	public void setPlanExecuteDate(Date planExecuteDate) {
		this.planExecuteDate = planExecuteDate;
	}
}
