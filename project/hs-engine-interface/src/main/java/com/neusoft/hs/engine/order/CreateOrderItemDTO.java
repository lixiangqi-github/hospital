package com.neusoft.hs.engine.order;

public class CreateOrderItemDTO {

	private String orderTypeId;

	private Integer count;

	public String getOrderTypeId() {
		return orderTypeId;
	}

	public void setOrderTypeId(String orderTypeId) {
		this.orderTypeId = orderTypeId;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}
}
