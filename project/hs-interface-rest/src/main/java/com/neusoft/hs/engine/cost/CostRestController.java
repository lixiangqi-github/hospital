package com.neusoft.hs.engine.cost;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CostRestController {

	@Autowired
	private CostFacade costFacade;

	@RequestMapping(method = RequestMethod.POST, value = "/cost/visit/{visitId}/chargerecord/create")
	public Float charge(@PathVariable("visitId") String visitId,
			@RequestBody List<CreateChargeRecordDTO> records) throws CostDTOException {
		return costFacade.charge(visitId, records);
	}
}