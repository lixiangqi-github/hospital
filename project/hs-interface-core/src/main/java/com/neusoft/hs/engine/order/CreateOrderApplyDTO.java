package com.neusoft.hs.engine.order;

import java.util.Date;
import java.util.List;

public class CreateOrderApplyDTO {

	private String goal;

	private Date planExecuteDate;

	private String visitId;

	private List<CreateInspectApplyItemDTO> inspectApplyItems;

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

	public List<CreateInspectApplyItemDTO> getInspectApplyItems() {
		return inspectApplyItems;
	}

	public void setInspectApplyItems(List<CreateInspectApplyItemDTO> inspectApplyItems) {
		this.inspectApplyItems = inspectApplyItems;
	}
}
