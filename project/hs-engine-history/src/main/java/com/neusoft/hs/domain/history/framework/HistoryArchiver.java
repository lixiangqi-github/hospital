package com.neusoft.hs.domain.history.framework;

import java.lang.reflect.InvocationTargetException;

import com.neusoft.hs.domain.visit.Visit;

public interface HistoryArchiver {

	public void archive(Visit visit) throws IllegalAccessException, InvocationTargetException;

}
