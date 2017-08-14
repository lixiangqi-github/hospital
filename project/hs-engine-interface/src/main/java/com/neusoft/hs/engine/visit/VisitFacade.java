package com.neusoft.hs.engine.visit;


public interface VisitFacade {
	/**
	 * 获取患者一次就诊信息
	 * 
	 * @param visitId
	 * @return
	 */
	public VisitDTO find(String visitId);

}
