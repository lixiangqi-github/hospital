package com.neusoft.hs.engine.order;

import com.neusoft.hs.platform.exception.HsException;

public class OrderDTOException extends HsException{
	
	private String orderId;
	
	public OrderDTOException(String orderId) {
		super();
		this.orderId = orderId;
	}

	public OrderDTOException(String orderId, String arg0, Throwable arg1, Object... params) {
		super(String.format(arg0, params), arg1);
		this.orderId = orderId;
	}

	public OrderDTOException(String orderId, String arg0, Object... params) {
		super(String.format(arg0, params));
		this.orderId = orderId;
	}

	public OrderDTOException(String orderId, Throwable arg0) {
		super(arg0);
		this.orderId = orderId;
	}

	public String getOrderId() {
		return orderId;
	}

	

}
