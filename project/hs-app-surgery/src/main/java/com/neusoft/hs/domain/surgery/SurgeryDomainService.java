package com.neusoft.hs.domain.surgery;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class SurgeryDomainService {

	@Autowired
	private SurgeryTypeRepo surgeryTypeRepo;

	@Autowired
	private SurgeryApplyRepo surgeryApplyRepo;

	@Autowired
	private SurgeryApplyItemRepo surgeryApplyItemRepo;

	@Autowired
	private ApplicationContext applicationContext;

	public SurgeryApply findSurgeryApply(String id) {
		return surgeryApplyRepo.findOne(id);
	}

	public void addSurgeryApplyItem(SurgeryApply surgeryApply, SurgeryApplyItem surgeryApplyItem) {
		surgeryApply.addSurgeryApplyItem(surgeryApplyItem);
		applicationContext.publishEvent(new SurgeryApplyItemAddedEvent(surgeryApplyItem));
	}

	public List<SurgeryType> findSurgeryType(Pageable pageable) {
		return surgeryTypeRepo.findAll(pageable).getContent();
	}

	public void createSurgeryTypes(List<SurgeryType> surgeryTypes) {
		surgeryTypeRepo.save(surgeryTypes);
	}

	public void clearSurgeryTypes() {
		surgeryTypeRepo.deleteAll();
	}

	public void clearSurgeryApplyItems() {
		surgeryApplyItemRepo.deleteAll();
	}
}
