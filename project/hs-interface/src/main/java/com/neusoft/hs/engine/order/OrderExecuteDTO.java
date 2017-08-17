package com.neusoft.hs.engine.order;

import java.util.Date;
import java.util.List;

import com.neusoft.hs.engine.cost.ChargeItemNameDTO;

public class OrderExecuteDTO {

	private String id;

	private String state;

	private String type;

	private Integer count;

	private String teamId;

	private String compsiteOrderId;

	private String previousId;

	private String nextId;

	private boolean isMain;

	private boolean isLast;

	private boolean isAlone;

	private Integer teamSequence;

	private String chargeState;

	private String costState;

	private Date planStartDate;

	private Date planEndDate;

	private Date sendDate;

	private Date startDate;

	private Date endDate;

	private String executeRoleId;

	private String actualExecutorId;

	private String actualExecutorName;

	private String orderId;

	private String belongDeptId;

	private String belongDeptName;

	private String executeDeptId;

	private String executeDeptName;

	private String chargeDeptId;

	private String chargeDeptName;

	private String visitId;

	private String visitName;

	private String orderCategory;

	private String drugTypeSpecId;

	private String drugTypeSpecName;

	private String dispenseDrugWinId;

	private String dispenseDrugWinName;

	private String dispensingDrugOrderId;

	private String fluidOrderId;

	private List<ChargeItemNameDTO> chargeItemIds;

	private List<String> chargeRecordIds;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public String getTeamId() {
		return teamId;
	}

	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}

	public String getCompsiteOrderId() {
		return compsiteOrderId;
	}

	public void setCompsiteOrderId(String compsiteOrderId) {
		this.compsiteOrderId = compsiteOrderId;
	}

	public String getPreviousId() {
		return previousId;
	}

	public void setPreviousId(String previousId) {
		this.previousId = previousId;
	}

	public String getNextId() {
		return nextId;
	}

	public void setNextId(String nextId) {
		this.nextId = nextId;
	}

	public boolean isMain() {
		return isMain;
	}

	public void setMain(boolean isMain) {
		this.isMain = isMain;
	}

	public boolean isLast() {
		return isLast;
	}

	public void setLast(boolean isLast) {
		this.isLast = isLast;
	}

	public boolean isAlone() {
		return isAlone;
	}

	public void setAlone(boolean isAlone) {
		this.isAlone = isAlone;
	}

	public Integer getTeamSequence() {
		return teamSequence;
	}

	public void setTeamSequence(Integer teamSequence) {
		this.teamSequence = teamSequence;
	}

	public String getChargeState() {
		return chargeState;
	}

	public void setChargeState(String chargeState) {
		this.chargeState = chargeState;
	}

	public String getCostState() {
		return costState;
	}

	public void setCostState(String costState) {
		this.costState = costState;
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

	public Date getSendDate() {
		return sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getExecuteRoleId() {
		return executeRoleId;
	}

	public void setExecuteRoleId(String executeRoleId) {
		this.executeRoleId = executeRoleId;
	}

	public String getActualExecutorId() {
		return actualExecutorId;
	}

	public void setActualExecutorId(String actualExecutorId) {
		this.actualExecutorId = actualExecutorId;
	}

	public String getActualExecutorName() {
		return actualExecutorName;
	}

	public void setActualExecutorName(String actualExecutorName) {
		this.actualExecutorName = actualExecutorName;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
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

	public String getChargeDeptId() {
		return chargeDeptId;
	}

	public void setChargeDeptId(String chargeDeptId) {
		this.chargeDeptId = chargeDeptId;
	}

	public String getChargeDeptName() {
		return chargeDeptName;
	}

	public void setChargeDeptName(String chargeDeptName) {
		this.chargeDeptName = chargeDeptName;
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

	public String getOrderCategory() {
		return orderCategory;
	}

	public void setOrderCategory(String orderCategory) {
		this.orderCategory = orderCategory;
	}

	public String getDrugTypeSpecId() {
		return drugTypeSpecId;
	}

	public void setDrugTypeSpecId(String drugTypeSpecId) {
		this.drugTypeSpecId = drugTypeSpecId;
	}

	public String getDrugTypeSpecName() {
		return drugTypeSpecName;
	}

	public void setDrugTypeSpecName(String drugTypeSpecName) {
		this.drugTypeSpecName = drugTypeSpecName;
	}

	public String getDispenseDrugWinId() {
		return dispenseDrugWinId;
	}

	public void setDispenseDrugWinId(String dispenseDrugWinId) {
		this.dispenseDrugWinId = dispenseDrugWinId;
	}

	public String getDispenseDrugWinName() {
		return dispenseDrugWinName;
	}

	public void setDispenseDrugWinName(String dispenseDrugWinName) {
		this.dispenseDrugWinName = dispenseDrugWinName;
	}

	public String getDispensingDrugOrderId() {
		return dispensingDrugOrderId;
	}

	public void setDispensingDrugOrderId(String dispensingDrugOrderId) {
		this.dispensingDrugOrderId = dispensingDrugOrderId;
	}

	public String getFluidOrderId() {
		return fluidOrderId;
	}

	public void setFluidOrderId(String fluidOrderId) {
		this.fluidOrderId = fluidOrderId;
	}

	public List<ChargeItemNameDTO> getChargeItemIds() {
		return chargeItemIds;
	}

	public void setChargeItemIds(List<ChargeItemNameDTO> chargeItemIds) {
		this.chargeItemIds = chargeItemIds;
	}

	public List<String> getChargeRecordIds() {
		return chargeRecordIds;
	}

	public void setChargeRecordIds(List<String> chargeRecordIds) {
		this.chargeRecordIds = chargeRecordIds;
	}

}
