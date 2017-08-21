package com.neusoft.hs.domain.recordroom;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Service;


@Service
public class MedicalCaseHisUtil {

	public MedicalCaseHis convert(MedicalCase medicalCase) throws IllegalAccessException, InvocationTargetException {

		MedicalCaseHis medicalCaseHis = new MedicalCaseHis();

		BeanUtils.copyProperties(medicalCaseHis, medicalCase);

		if (medicalCase.getClip() != null) {
			medicalCaseHis.setClipId(medicalCase.getClip().getId());
		}
		if (medicalCase.getCreator() != null) {
			medicalCaseHis.setCreatorId(medicalCase.getCreator().getId());
		}

		if (medicalCase.getVisit() != null) {
			medicalCaseHis.setVisitId(medicalCase.getVisit().getId());
		}

		return medicalCaseHis;
	}

}
