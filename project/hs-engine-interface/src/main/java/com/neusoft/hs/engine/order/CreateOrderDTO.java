package com.neusoft.hs.engine.order;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CreateOrderDTO {

	private String visitId;

	private String visitCardNumber;

	private String placeType;

	private boolean isLong;

	private Date planStartDate;

	private Date planEndDate;

	private String frequencyTypeId;

	private String executeDeptId;

	private String drugUseModeId;

	private String creator;

	private Date createDate;

	private List<CreateOrderItemDTO> items;

	private CreateOrderApplyDTO apply;

	public String getVisitId() {
		return visitId;
	}

	public void setVisitId(String visitId) {
		this.visitId = visitId;
	}

	public String getVisitCardNumber() {
		return visitCardNumber;
	}

	public void setVisitCardNumber(String visitCardNumber) {
		this.visitCardNumber = visitCardNumber;
	}

	public String getPlaceType() {
		return placeType;
	}

	public void setPlaceType(String placeType) {
		this.placeType = placeType;
	}

	public boolean isLong() {
		return isLong;
	}

	public void setLong(boolean isLong) {
		this.isLong = isLong;
	}

	public Date getPlanStartDate() {
		return planStartDate;
	}

	public void setPlanStartDate(Date planStartDate) {
		this.planStartDate = planStartDate;
	}

	public Date getPlanEndDate() {
		return planEndDate;
	}

	public void setPlanEndDate(Date planEndDate) {
		this.planEndDate = planEndDate;
	}

	public String getFrequencyTypeId() {
		return frequencyTypeId;
	}

	public void setFrequencyTypeId(String frequencyTypeId) {
		this.frequencyTypeId = frequencyTypeId;
	}

	public String getExecuteDeptId() {
		return executeDeptId;
	}

	public void setExecuteDeptId(String executeDeptId) {
		this.executeDeptId = executeDeptId;
	}

	public String getDrugUseModeId() {
		return drugUseModeId;
	}

	public void setDrugUseModeId(String drugUseModeId) {
		this.drugUseModeId = drugUseModeId;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public List<CreateOrderItemDTO> getItems() {
		return items;
	}

	public void setItems(List<CreateOrderItemDTO> items) {
		this.items = items;
	}

	public void addItem(CreateOrderItemDTO item) {
		if (this.items == null) {
			this.items = new ArrayList<CreateOrderItemDTO>();
		}
		this.items.add(item);
	}

	public CreateOrderApplyDTO getApply() {
		return apply;
	}

	public void setApply(CreateOrderApplyDTO apply) {
		this.apply = apply;
	}
}
