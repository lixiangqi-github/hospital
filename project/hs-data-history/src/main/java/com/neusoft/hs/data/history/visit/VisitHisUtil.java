package com.neusoft.hs.data.history.visit;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Service;

import com.neusoft.hs.domain.history.visit.VisitHis;
import com.neusoft.hs.domain.visit.Visit;

@Service
public class VisitHisUtil {

	public VisitHis convert(Visit visit) throws IllegalAccessException, InvocationTargetException {

		VisitHis visitHis = new VisitHis();

		BeanUtils.copyProperties(visitHis, visit);

		return visitHis;
	}

}
