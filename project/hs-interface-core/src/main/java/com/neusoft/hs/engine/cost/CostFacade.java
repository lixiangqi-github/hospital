package com.neusoft.hs.engine.cost;

import java.util.List;

public interface CostFacade {

	public Float charge(String visitId, List<CreateChargeRecordDTO> records)
			throws CostDTOException;

}
