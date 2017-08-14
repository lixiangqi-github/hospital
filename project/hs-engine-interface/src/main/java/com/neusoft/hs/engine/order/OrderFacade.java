package com.neusoft.hs.engine.order;

import java.util.List;

public interface OrderFacade {

	public List<OrderDTO> create(CreateOrderDTO createOrderDTO) throws OrderDTOException,
			OrderExecuteDTOException;

}
