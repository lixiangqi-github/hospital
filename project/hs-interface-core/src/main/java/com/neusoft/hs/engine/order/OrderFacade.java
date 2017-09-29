package com.neusoft.hs.engine.order;

public interface OrderFacade {

	public OrderDTOSet create(CreateOrderDTO createOrderDTO) throws OrderDTOException,
			OrderExecuteDTOException;
	
	public OrderDTO find(String id) throws OrderDTOException;

}
