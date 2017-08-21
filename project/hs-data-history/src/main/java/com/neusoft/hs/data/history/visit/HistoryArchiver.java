package com.neusoft.hs.data.history.visit;

import java.lang.reflect.InvocationTargetException;

import com.neusoft.hs.domain.visit.Visit;

public interface HistoryArchiver {

	public void archive(Visit visit) throws IllegalAccessException, InvocationTargetException;

}
