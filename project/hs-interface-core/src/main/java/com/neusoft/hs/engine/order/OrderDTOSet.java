package com.neusoft.hs.engine.order;

import java.util.List;

public class OrderDTOSet {

	private List<OrderDTO> orders;

	public OrderDTOSet() {

	}
	
	public OrderDTOSet(List<OrderDTO> orders) {
		this.orders = orders;
	}

	public List<OrderDTO> getOrders() {
		return orders;
	}

	public void setOrders(List<OrderDTO> orders) {
		this.orders = orders;
	}

}
