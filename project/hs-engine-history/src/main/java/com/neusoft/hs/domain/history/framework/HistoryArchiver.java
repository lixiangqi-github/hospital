package com.neusoft.hs.domain.history.framework;

import com.neusoft.hs.domain.visit.Visit;

/**
 * 业务数据归档器
 * 
 * @author kingbox
 *
 */
public interface HistoryArchiver {

	/**
	 * 归档业务数据
	 * 
	 * @param visit
	 * @throws HistoryArchiveExecution
	 */
	public void archive(Visit visit) throws HistoryArchiveExecution;

}
