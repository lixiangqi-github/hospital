//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\order\\OrderRepo.java

package com.neusoft.hs.domain.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class OrderTypeAppDAO  {
	
	@Autowired
	private OrderTypeAppRepo orderTypeAppRepo;

	public OrderTypeApp find(String id) {
		return orderTypeAppRepo.findOne(id);
	}

}
