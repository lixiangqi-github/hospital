package com.neusoft.hs.domain.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class DrugOrderTypeAppDAO {

	@Autowired
	private DrugOrderTypeAppRepo drugOrderTypeAppRepo;

	public DrugOrderTypeApp find(String id) {
		return this.drugOrderTypeAppRepo.findOne(id);
	}

}
