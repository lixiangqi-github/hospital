package com.neusoft.hs.engine.order;

import java.util.Date;

public class InspectApplyItemDTO {

	private String id;

	private String inspectItemId;

	private String arrangeDeptId;

	private String inspectDeptId;

	private String inspectPlace;

	private Date planExecuteDate;

	private Date executeDate;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getExecuteDate() {
		return executeDate;
	}

	public void setExecuteDate(Date executeDate) {
		this.executeDate = executeDate;
	}

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
