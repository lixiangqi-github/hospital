package com.neusoft.hs.platform.exception;

public class ExceptionUtil {

	public static Throwable getRootCause(Exception e) {

		Throwable e1 = e;
		while (e1.getCause() != null) {
			e1 = e1.getCause();
		}
		return e1;
	}

}
