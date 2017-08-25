package com.neusoft.hs.engine.cost;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class CostFacadeImpl implements CostFacade {

	@Override
	public void charge(String visitId, List<CreateChargeRecordDTO> records) {
		// TODO Auto-generated method stub
		
	}

}
