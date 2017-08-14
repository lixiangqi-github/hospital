package com.neusoft.hs.engine.order;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderRestController {

	@Autowired
	private OrderFacade orderFacade;

	@RequestMapping(method = RequestMethod.GET, value = "/order/create")
	public List<OrderDTO> create(@RequestBody CreateOrderDTO createOrderDTO)
			throws OrderDTOException, OrderExecuteDTOException {
		return orderFacade.create(createOrderDTO);
	}
}
