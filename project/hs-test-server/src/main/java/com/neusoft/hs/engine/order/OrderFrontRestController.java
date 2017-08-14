package com.neusoft.hs.engine.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.neusoft.hs.platform.exception.HsException;

@RestController
public class OrderFrontRestController {

	@Autowired
	private OrderFacade orderFacade;

	@RequestMapping(method = RequestMethod.POST, value = "/order/create")
	public void create(@RequestBody CreateOrderDTO createOrderDTO)
			throws HsException {
		orderFacade.create(createOrderDTO);
	}
}
