package com.neusoft.hs.listener.pharmacy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

import com.neusoft.hs.domain.pharmacy.PrescriptionDAO;
import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.domain.visit.VisitRemoveBeforeEvent;

@Service
public class PharmacyVisitRemoveBeforeEventListener implements ApplicationListener<VisitRemoveBeforeEvent> {

	@Autowired
	private PrescriptionDAO prescriptionDAO;

	@Override
	public void onApplicationEvent(VisitRemoveBeforeEvent event) {
		prescriptionDAO.delete((Visit) event.getSource());
	}

}
