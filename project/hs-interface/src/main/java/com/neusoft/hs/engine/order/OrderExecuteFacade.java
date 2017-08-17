package com.neusoft.hs.engine.order;

import java.util.List;

public interface OrderExecuteFacade {

	public List<OrderExecuteDTO> findNeedExecute(String userId, int pageNumber, int pageSize)
			throws OrderExecuteDTOException;

	public OrderExecuteDTO finish(String executeId, String userId) throws OrderExecuteDTOException;
}
