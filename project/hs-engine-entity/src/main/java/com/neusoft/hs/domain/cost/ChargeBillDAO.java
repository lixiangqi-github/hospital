package com.neusoft.hs.domain.cost;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class ChargeBillDAO {

	@Autowired
	private ChargeBillRepo chargeBillRepo;

	public void delete(ChargeBill chargeBill) {
		chargeBillRepo.delete(chargeBill);
	}
}
