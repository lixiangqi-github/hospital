package com.neusoft.hs.application.surgery;

import org.hibernate.Hibernate;
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
public class SurgeryAppService {

	@Autowired
	private SurgeryDomainService surgeryDomainService;

	@Autowired
	private OrderExecuteDomainService orderExecuteDomainService;

	public SurgeryApply findSurgeryApply(String id) {
		SurgeryApply surgeryApply = surgeryDomainService.findSurgeryApply(id);
		if (surgeryApply != null) {
			for (SurgeryApplyItem surgeryApplyItem : surgeryApply.getSurgeryApplyItems()) {
				Hibernate.initialize(surgeryApplyItem.getSurgeryType());
			}
		}
		return surgeryApply;
	}

	public void addSurgeryApplyItem(String executeId, SurgeryType surgeryType) {

		OrderExecute execute = orderExecuteDomainService.find(executeId);

		SurgeryApply surgeryApply = surgeryDomainService
				.findSurgeryApply(execute.getOrder().getApply().getId());

		SurgeryApplyItem surgeryApplyItem = new SurgeryApplyItem();
		surgeryApplyItem.setSurgeryType(surgeryType);

		surgeryDomainService.addSurgeryApplyItem(surgeryApply, surgeryApplyItem);

		execute.addChargeItem(surgeryType.getChargeItem());
	}

}
