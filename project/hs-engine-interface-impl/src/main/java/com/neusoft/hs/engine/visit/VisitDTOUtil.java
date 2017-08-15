package com.neusoft.hs.engine.visit;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;

import com.neusoft.hs.domain.visit.Visit;

public class VisitDTOUtil {

	public static VisitDTO convert(Visit visit) throws IllegalAccessException,
			InvocationTargetException {
		VisitDTO visitDTO = new VisitDTO();
		BeanUtils.copyProperties(visitDTO, visit);

		visitDTO.setDeptId(visit.getDept().getId());
		visitDTO.setRespDoctorId(visit.getRespDoctor().getId());
		visitDTO.setRespNurseId(visit.getRespNurse().getId());
		visitDTO.setPatientId(visit.getPatient().getId());
		visitDTO.setPatientName(visit.getPatient().getName());
		visitDTO.setAreaId(visit.getArea().getId());

		return visitDTO;

	}

}
