//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\cost\\ChargeRecord.java

package com.neusoft.hs.engine.cost;

import java.util.Date;

/**
 * 费用条目
 * 
 * @author kingbox
 *
 */
public class CreateChargeRecordDTO {

	private Float amount;

	private Integer count;

	private Float price;

	private String type;

	private Date createDate;

	private String chargeItemId;

	private String orderExecuteId;

	private String chargeDeptId;;

	private String belongDeptId;;

	private boolean haveCost;

	public static final String Type_PreCharge = "预存";

	public static final String Type_ShouldCharge = "应扣";

	public static final String Type_Charged = "已扣";

	public static final String Type_BackCharge = "退费";

	public static final String Type_Balance = "结账";

	public Float getAmount() {
		return amount;
	}

	public void setAmount(Float amount) {
		this.amount = amount;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getChargeItemId() {
		return chargeItemId;
	}

	public void setChargeItemId(String chargeItemId) {
		this.chargeItemId = chargeItemId;
	}

	public String getOrderExecuteId() {
		return orderExecuteId;
	}

	public void setOrderExecuteId(String orderExecuteId) {
		this.orderExecuteId = orderExecuteId;
	}

	public String getChargeDeptId() {
		return chargeDeptId;
	}

	public void setChargeDeptId(String chargeDeptId) {
		this.chargeDeptId = chargeDeptId;
	}

	public String getBelongDeptId() {
		return belongDeptId;
	}

	public void setBelongDeptId(String belongDeptId) {
		this.belongDeptId = belongDeptId;
	}

	public boolean isHaveCost() {
		return haveCost;
	}

	public void setHaveCost(boolean haveCost) {
		this.haveCost = haveCost;
	}

}
