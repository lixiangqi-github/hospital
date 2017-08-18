package com.neusoft.hs.listener.recordroom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

import com.neusoft.hs.domain.recordroom.MedicalCaseDAO;
import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.domain.visit.VisitRemoveBeforeEvent;

@Service
public class RecordroomVisitRemoveBeforeEventListener implements ApplicationListener<VisitRemoveBeforeEvent> {

	@Autowired
	private MedicalCaseDAO medicalCaseDAO;

	@Override
	public void onApplicationEvent(VisitRemoveBeforeEvent event) {
		medicalCaseDAO.delete((Visit) event.getSource());
	}

}
