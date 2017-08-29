package com.neusoft.hs.engine.cost;

import java.lang.reflect.InvocationTargetException;

import com.neusoft.hs.engine.DTOException;

public class CostDTOException extends DTOException {

	public CostDTOException(IllegalAccessException e) {
		super(e);
	}

	public CostDTOException(InvocationTargetException e) {
		super(e);
	}

	public CostDTOException(String arg0, Throwable arg1, Object... params) {
		super(String.format(arg0, params), arg1);
	}

	public CostDTOException(String arg0, Object... params) {
		super(String.format(arg0, params));
	}

	public CostDTOException(Throwable arg0) {
		super(arg0);
	}
}
