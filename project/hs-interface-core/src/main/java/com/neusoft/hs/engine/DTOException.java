package com.neusoft.hs.engine;

public class DTOException extends Exception {

	public DTOException(Throwable e) {
		super(e);
	}

	public DTOException(String message, Throwable e) {
		super(message, e);
	}

	public DTOException(String message) {
		super(message);
	}

}
