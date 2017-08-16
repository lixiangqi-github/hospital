package com.neusoft.hs.engine.order;

import java.util.Date;

public class OrderDTO {

	private String id;

	private String name;

	private String visitId;

	private String visitName;

	private String placeType;

	private String orderTypeId;

	private String orderTypeName;

	private boolean isLong;

	private Boolean executeNeedSend;

	private Date planStartDate;

	private Date planEndDate;

	private String frequencyTypeId;

	private String frequencyTypeName;

	private String belongDeptId;

	private String belongDeptName;

	private String executeDeptId;

	private String executeDeptName;

	private String drugUseModeId;

	private String drugUseModeName;

	private Integer count;

	private String state;

	private String stateDesc;

	private Date endDate;

	private Date executeDate;

	private String creatorId;

	private String creatorName;

	private Date createDate;

	private CreateOrderApplyDTO apply;

	public OrderDTO() {

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getPlaceType() {
		return placeType;
	}

	public void setPlaceType(String placeType) {
		this.placeType = placeType;
	}

	public String getOrderTypeId() {
		return orderTypeId;
	}

	public void setOrderTypeId(String orderTypeId) {
		this.orderTypeId = orderTypeId;
	}

	public String getOrderTypeName() {
		return orderTypeName;
	}

	public void setOrderTypeName(String orderTypeName) {
		this.orderTypeName = orderTypeName;
	}

	public boolean isLong() {
		return isLong;
	}

	public void setLong(boolean isLong) {
		this.isLong = isLong;
	}

	public Boolean getExecuteNeedSend() {
		return executeNeedSend;
	}

	public void setExecuteNeedSend(Boolean executeNeedSend) {
		this.executeNeedSend = executeNeedSend;
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

	public String getFrequencyTypeName() {
		return frequencyTypeName;
	}

	public void setFrequencyTypeName(String frequencyTypeName) {
		this.frequencyTypeName = frequencyTypeName;
	}

	public String getBelongDeptId() {
		return belongDeptId;
	}

	public void setBelongDeptId(String belongDeptId) {
		this.belongDeptId = belongDeptId;
	}

	public String getBelongDeptName() {
		return belongDeptName;
	}

	public void setBelongDeptName(String belongDeptName) {
		this.belongDeptName = belongDeptName;
	}

	public String getExecuteDeptId() {
		return executeDeptId;
	}

	public void setExecuteDeptId(String executeDeptId) {
		this.executeDeptId = executeDeptId;
	}

	public String getExecuteDeptName() {
		return executeDeptName;
	}

	public void setExecuteDeptName(String executeDeptName) {
		this.executeDeptName = executeDeptName;
	}

	public String getDrugUseModeId() {
		return drugUseModeId;
	}

	public void setDrugUseModeId(String drugUseModeId) {
		this.drugUseModeId = drugUseModeId;
	}

	public String getDrugUseModeName() {
		return drugUseModeName;
	}

	public void setDrugUseModeName(String drugUseModeName) {
		this.drugUseModeName = drugUseModeName;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
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

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Date getExecuteDate() {
		return executeDate;
	}

	public void setExecuteDate(Date executeDate) {
		this.executeDate = executeDate;
	}

	public String getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public CreateOrderApplyDTO getApply() {
		return apply;
	}

	public void setApply(CreateOrderApplyDTO apply) {
		this.apply = apply;
	}

}
