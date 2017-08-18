package com.neusoft.hs.domain.medicalrecord;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class MedicalRecordClipDAO {

	@Autowired
	private MedicalRecordClipRepo medicalRecordClipRepo;

	public void delete(MedicalRecordClip medicalRecordClip) {
		medicalRecordClipRepo.delete(medicalRecordClip);
	}
}
