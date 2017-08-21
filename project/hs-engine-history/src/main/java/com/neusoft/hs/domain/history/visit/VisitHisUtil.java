package com.neusoft.hs.domain.history.visit;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Service;

import com.neusoft.hs.domain.visit.Visit;

@Service
public class VisitHisUtil {

	public VisitHis convert(Visit visit) throws IllegalAccessException, InvocationTargetException {

		VisitHis visitHis = new VisitHis();

		BeanUtils.copyProperties(visitHis, visit);

		if (visit.getArea() != null) {
			visitHis.setAreaId(visit.getArea().getId());
		}
		if (visit.getChargeBill() != null) {
			visitHis.setChargeBillId(visit.getChargeBill().getId());
		}
		if (visit.getDept() != null) {
			visitHis.setDeptId(visit.getDept().getId());
		}
		if (visit.getMedicalRecordClip() != null) {
			visitHis.setMedicalRecordClipId(visit.getMedicalRecordClip().getId());
		}
		if (visit.getPatient() != null) {
			visitHis.setPatientId(visit.getPatient().getId());
			visitHis.setPatientName(visit.getPatient().getName());
		}
		if (visit.getPreviousDept() != null) {
			visitHis.setPreviousDeptId(visit.getPreviousDept().getId());
		}
		if (visit.getRespDoctor() != null) {
			visitHis.setRespDoctorId(visit.getRespDoctor().getId());
		}
		if (visit.getRespNurse() != null) {
			visitHis.setRespNurseId(visit.getRespNurse().getId());
		}
		
		return visitHis;
	}

}
