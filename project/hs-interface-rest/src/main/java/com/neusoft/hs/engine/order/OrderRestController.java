package com.neusoft.hs.engine.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderRestController {

	@Autowired
	private OrderFacade orderFacade;

	@RequestMapping(method = RequestMethod.POST, value = "/order/create")
	public OrderDTOSet create(@RequestBody CreateOrderDTO createOrderDTO)
			throws OrderDTOException, OrderExecuteDTOException {
		return orderFacade.create(createOrderDTO);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/order/{orderId}/find")
	public OrderDTO find(@PathVariable("orderId") String orderId)
			throws OrderDTOException {
		return orderFacade.find(orderId);
	}
}
