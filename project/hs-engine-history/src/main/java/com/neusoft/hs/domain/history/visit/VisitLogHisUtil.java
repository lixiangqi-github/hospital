package com.neusoft.hs.domain.history.visit;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Service;

import com.neusoft.hs.domain.visit.VisitLog;

@Service
public class VisitLogHisUtil {

	public VisitLogHis convert(VisitLog visitLog)
			throws IllegalAccessException, InvocationTargetException {

		VisitLogHis visitLogHis = new VisitLogHis();

		BeanUtils.copyProperties(visitLogHis, visitLog);

		if (visitLog.getVisit() != null) {
			visitLogHis.setVisitId(visitLog.getVisit().getId());
		}
		if (visitLog.getOperator() != null) {
			visitLogHis.setOperatorId(visitLog.getOperator().getId());
		}
		if (visitLog.getDept() != null) {
			visitLogHis.setDeptId(visitLog.getDept().getId());
		}

		return visitLogHis;
	}

}
