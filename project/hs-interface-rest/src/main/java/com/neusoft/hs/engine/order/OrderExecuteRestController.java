package com.neusoft.hs.engine.order;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderExecuteRestController {

	@Autowired
	private OrderExecuteFacade orderExecuteFacade;

	@RequestMapping(method = RequestMethod.GET, value = "/order/execute/{userId}/need/execute/find/{pageNumber}/pageNumber/{pageSize}/pageSize")
	public OrderExecuteDTOSet findNeedExecute(@PathVariable("userId") String userId,
			@PathVariable("pageNumber") Integer pageNumber,
			@PathVariable("pageSize") Integer pageSize) throws OrderExecuteDTOException {
		return orderExecuteFacade.findNeedExecute(userId, pageNumber, pageSize);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/order/execute/{executeId}/userId/{userId}/finish")
	public OrderExecuteDTO finish(@PathVariable("executeId") String executeId,
			@RequestBody Map<String, Object> params, @PathVariable("userId") String userId)
			throws OrderExecuteDTOException {
		return orderExecuteFacade.finish(executeId, params, userId);
	}
}
