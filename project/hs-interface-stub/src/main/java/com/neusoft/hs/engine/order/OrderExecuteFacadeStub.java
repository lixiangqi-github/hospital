package com.neusoft.hs.engine.order;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("hs-service")
public interface OrderExecuteFacadeStub extends OrderExecuteFacade {

	@RequestMapping(method = RequestMethod.GET, value = "/order/execute/{userId}/need/execute/find/{pageNumber}/pageNumber/{pageSize}/pageSize")
	public List<OrderExecuteDTO> findNeedExecute(@PathVariable("userId") String userId,
			@PathVariable("pageNumber") Integer pageNumber,
			@PathVariable("pageSize") Integer pageSize);

	@RequestMapping(method = RequestMethod.POST, value = "/order/execute/{executeId}/userId/{userId}/finish")
	public OrderExecuteDTO finish(@PathVariable("executeId") String executeId,
			@RequestBody Map<String, Object> params, @PathVariable("userId") String userId)
			throws OrderExecuteDTOException;

}
