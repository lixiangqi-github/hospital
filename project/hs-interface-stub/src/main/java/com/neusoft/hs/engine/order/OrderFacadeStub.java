package com.neusoft.hs.engine.order;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("hs-service")
public interface OrderFacadeStub extends OrderFacade {

	@RequestMapping(method = RequestMethod.POST, value = "/order/create")
	public OrderDTOSet create(@RequestBody CreateOrderDTO createOrderDTO)
			throws OrderDTOException, OrderExecuteDTOException;
	
	@RequestMapping(method = RequestMethod.GET, value = "/order/{orderId}/find")
	public OrderDTO find(@PathVariable("orderId") String orderId);

}
