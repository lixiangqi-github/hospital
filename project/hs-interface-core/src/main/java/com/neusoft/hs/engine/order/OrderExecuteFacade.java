package com.neusoft.hs.engine.order;

import java.util.List;
import java.util.Map;

public interface OrderExecuteFacade {

	public List<OrderExecuteDTO> findNeedExecute(String userId, Integer pageNumber,
			Integer pageSize) throws OrderExecuteDTOException;

	public OrderExecuteDTO finish(String executeId, Map<String, Object> params, String userId)
			throws OrderExecuteDTOException;
}
