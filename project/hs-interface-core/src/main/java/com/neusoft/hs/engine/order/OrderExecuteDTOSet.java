package com.neusoft.hs.engine.order;

import java.util.List;

public class OrderExecuteDTOSet {

	private List<OrderExecuteDTO> executes;

	public OrderExecuteDTOSet() {
	}

	public OrderExecuteDTOSet(List<OrderExecuteDTO> executes) {
		this.executes = executes;
	}

	public List<OrderExecuteDTO> getExecutes() {
		return executes;
	}

	public void setExecutes(List<OrderExecuteDTO> executes) {
		this.executes = executes;
	}
}
