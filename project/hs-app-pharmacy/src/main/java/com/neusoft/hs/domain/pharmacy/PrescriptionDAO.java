package com.neusoft.hs.domain.pharmacy;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neusoft.hs.domain.visit.Visit;

@Service
@Transactional(rollbackFor = Exception.class)
public class PrescriptionDAO {

	@Autowired
	private PrescriptionRepo prescriptionRepo;

	public void delete(Visit visit) {
		List<Prescription> prescriptions = prescriptionRepo.findByVisit(visit);
		if (prescriptions != null) {
			for (Prescription prescription : prescriptions) {
				prescriptionRepo.delete(prescription);
			}
		}
	}
}
