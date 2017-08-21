package com.neusoft.hs.domain.history.framework;

import com.neusoft.hs.platform.exception.HsException;

public class HistoryArchiveExecution extends HsException {

	public HistoryArchiveExecution() {
		super();
	}

	public HistoryArchiveExecution(String arg0, Object... params) {
		super(arg0, params);
	}

	public HistoryArchiveExecution(String arg0, Throwable arg1, Object... params) {
		super(arg0, arg1, params);
	}

	public HistoryArchiveExecution(Throwable arg0) {
		super(arg0);
	}

}
