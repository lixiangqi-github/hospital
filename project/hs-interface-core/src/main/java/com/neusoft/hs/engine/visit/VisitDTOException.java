package com.neusoft.hs.engine.visit;

import java.lang.reflect.InvocationTargetException;

import com.neusoft.hs.engine.DTOException;

public class VisitDTOException extends DTOException {

	private String visitId;
		
	public VisitDTOException(IllegalAccessException e) {
		super(e);
	}

	public VisitDTOException(InvocationTargetException e) {
		super(e);
	}

	public VisitDTOException(String visitId, String arg0, Throwable arg1,
			Object... params) {
		super(String.format(arg0, params), arg1);
		this.visitId = visitId;
	}

	public VisitDTOException(String visitId, String arg0, Object... params) {
		super(String.format(arg0, params));
		this.visitId = visitId;
	}

	public VisitDTOException(String visitId, Throwable arg0) {
		super(arg0);
		this.visitId = visitId;
	}

	public String getVisitId() {
		return visitId;
	}

}
