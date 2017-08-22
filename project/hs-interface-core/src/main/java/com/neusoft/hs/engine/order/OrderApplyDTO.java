package com.neusoft.hs.engine.order;

import java.util.Date;
import java.util.List;

public class OrderApplyDTO {

	private String id;

	private String goal;

	private Date planExecuteDate;

	private Date executeDate;

	private String visitId;

	private List<InspectApplyItemDTO> inspectApplyItems;

	private List<InspectResultDTO> inspectResults;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getGoal() {
		return goal;
	}

	public void setGoal(String goal) {
		this.goal = goal;
	}

	public Date getPlanExecuteDate() {
		return planExecuteDate;
	}

	public void setPlanExecuteDate(Date planExecuteDate) {
		this.planExecuteDate = planExecuteDate;
	}

	public String getVisitId() {
		return visitId;
	}

	public void setVisitId(String visitId) {
		this.visitId = visitId;
	}

	public Date getExecuteDate() {
		return executeDate;
	}

	public void setExecuteDate(Date executeDate) {
		this.executeDate = executeDate;
	}

	public List<InspectApplyItemDTO> getInspectApplyItems() {
		return inspectApplyItems;
	}

	public void setInspectApplyItems(List<InspectApplyItemDTO> inspectApplyItems) {
		this.inspectApplyItems = inspectApplyItems;
	}

	public List<InspectResultDTO> getInspectResults() {
		return inspectResults;
	}

	public void setInspectResults(List<InspectResultDTO> inspectResults) {
		this.inspectResults = inspectResults;
	}
}
