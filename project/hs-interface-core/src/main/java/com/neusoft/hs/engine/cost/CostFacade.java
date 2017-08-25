package com.neusoft.hs.engine.cost;

import java.util.List;

public interface CostFacade {

	public void charge(String visitId, List<CreateChargeRecordDTO> records);

}
