package com.neusoft.hs.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neusoft.hs.domain.order.OrderExecute;
import com.neusoft.hs.domain.order.OrderExecuteDomainService;
import com.neusoft.hs.domain.surgery.SurgeryApply;
import com.neusoft.hs.domain.surgery.SurgeryApplyItem;
import com.neusoft.hs.domain.surgery.SurgeryDomainService;
import com.neusoft.hs.domain.surgery.SurgeryType;

@Service
@Transactional(rollbackFor = Exception.class)
public class SurgeryTestService {

	@Autowired
	private SurgeryDomainService surgeryDomainService;

	@Autowired
	private OrderExecuteDomainService orderExecuteDomainService;

	public void addSurgeryApplyItem(String executeId, SurgeryType surgeryType) {

		OrderExecute execute = orderExecuteDomainService.find(executeId);

		SurgeryApply surgeryApply = surgeryDomainService
				.findSurgeryApply(execute.getOrder().getApply().getId());

	}

}
