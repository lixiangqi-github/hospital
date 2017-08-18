package com.neusoft.hs.domain.recordroom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neusoft.hs.domain.visit.Visit;

@Service
@Transactional(rollbackFor = Exception.class)
public class MedicalCaseDAO {

	@Autowired
	private MedicalCaseRepo medicalCaseRepo;

	public void delete(Visit visit) {
		MedicalCase medicalCase = medicalCaseRepo.findByVisit(visit);
		if(medicalCase != null){
			medicalCaseRepo.delete(medicalCase);
		}
	}
}
