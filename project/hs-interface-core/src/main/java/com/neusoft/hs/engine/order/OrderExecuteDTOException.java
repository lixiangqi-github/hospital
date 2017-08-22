package com.neusoft.hs.engine.order;

import java.lang.reflect.InvocationTargetException;

import com.neusoft.hs.engine.DTOException;


public class OrderExecuteDTOException extends DTOException {

	private String executeId;
	
	public OrderExecuteDTOException(IllegalAccessException e) {
		super(e);
	}

	public OrderExecuteDTOException(InvocationTargetException e) {
		super(e);
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
