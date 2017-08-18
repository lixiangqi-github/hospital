package com.neusoft.hs.data.history.visit;

import org.springframework.stereotype.Service;

import com.neusoft.hs.domain.history.visit.VisitHis;
import com.neusoft.hs.domain.visit.Visit;

@Service
public class VisitHisUtil {

	public VisitHis convert(Visit visit) {
		
		VisitHis visitHis = new VisitHis();

		return visitHis;
	}

}
