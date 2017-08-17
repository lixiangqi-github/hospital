package com.neusoft.hs.engine.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderExecuteFrontTestRestController {

	@Autowired
	private OrderExecuteFacade orderExecuteFacade;

	@RequestMapping(method = RequestMethod.GET, value = "/test/order/execute/{executeId}/userId/{userId}/finish")
	public OrderExecuteDTO finish(@PathVariable("executeId") String executeId, @PathVariable("userId") String userId)
			throws OrderExecuteDTOException {
		return orderExecuteFacade.finish(executeId, userId);
	}
}
