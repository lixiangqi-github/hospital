package com.neusoft.hs.engine.cost;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("hs-service")
public interface CostFacadeStub extends CostFacade {

	@RequestMapping(method = RequestMethod.POST, value = "/cost/visit/{visitId}/chargerecord/create")
	public Float charge(@PathVariable("visitId") String visitId,
			@RequestBody List<CreateChargeRecordDTO> records) throws CostDTOException;
}
