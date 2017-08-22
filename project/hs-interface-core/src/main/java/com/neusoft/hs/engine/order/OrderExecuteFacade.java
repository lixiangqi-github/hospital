package com.neusoft.hs.engine.order;

import java.util.List;

public interface OrderExecuteFacade {

	public List<OrderExecuteDTO> findNeedExecute(String userId, Integer pageNumber, Integer pageSize)
			throws OrderExecuteDTOException;

	public OrderExecuteDTO finish(String executeId, String userId) throws OrderExecuteDTOException;
}
