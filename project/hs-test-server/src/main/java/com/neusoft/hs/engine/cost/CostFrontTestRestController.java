package com.neusoft.hs.engine.cost;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CostFrontTestRestController {

	@Autowired
	private CostFacade costFacade;

	@RequestMapping(method = RequestMethod.GET, value = "/test/cost/visit/{visitId}/chargerecord/create")
	public Float charge(@PathVariable("visitId") String visitId) throws CostDTOException {
		List<CreateChargeRecordDTO> records = new ArrayList<CreateChargeRecordDTO>();

		CreateChargeRecordDTO record = new CreateChargeRecordDTO();
		record.setType(CreateChargeRecordDTO.Type_Charged);
		record.setChargeItemId("transportFluidMaterialChargeItem");
		record.setCount(1);
		record.setBelongDeptId("dept000");
		record.setChargeDeptId("dept000");
		record.setHaveCost(true);

		records.add(record);

		return costFacade.charge(visitId, records);
	}

}
