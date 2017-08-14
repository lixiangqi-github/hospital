package com.neusoft.hs.engine.order;

import com.neusoft.hs.domain.order.OrderExecuteException;
import com.neusoft.hs.platform.exception.HsException;

public class OrderExecuteDTOException extends HsException {

	private String executeId;

	public OrderExecuteDTOException(OrderExecuteException e) {
		super(e.getMessage());
		if (e.getExecute() != null) {
			this.executeId = e.getExecute().getId();
		}
	}

	public OrderExecuteDTOException(String executeId, String arg0,
			Throwable arg1, Object... params) {
		super(String.format(arg0, params), arg1);
		this.executeId = executeId;
	}

	public OrderExecuteDTOException(String executeId, String arg0,
			Object... params) {
		super(String.format(arg0, params));
		this.executeId = executeId;
	}

	public OrderExecuteDTOException(String executeId, Throwable arg0) {
		super(arg0);
		this.executeId = executeId;
	}

	public String getExecuteId() {
		return executeId;
	}

}
