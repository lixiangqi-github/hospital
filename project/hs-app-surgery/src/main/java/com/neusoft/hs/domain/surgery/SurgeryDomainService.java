package com.neusoft.hs.domain.surgery;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class SurgeryDomainService {
	
	@Autowired
	private SurgeryTypeRepo surgeryTypeRepo;
	
	
	public void createSurgeryTypes(List<SurgeryType> surgeryTypes) {
		surgeryTypeRepo.save(surgeryTypes);
	}
	
	
	public void clearSurgeryTypes() {
		surgeryTypeRepo.deleteAll();
	}
}
