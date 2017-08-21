package com.neusoft.hs.listener.registration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

import com.neusoft.hs.domain.registration.RegistrationDAO;
import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.domain.visit.VisitRemoveBeforeEvent;

@Service
public class RegistrationVisitRemoveBeforeEventListener implements ApplicationListener<VisitRemoveBeforeEvent> {

	@Autowired
	private RegistrationDAO registrationDAO;

	@Override
	public void onApplicationEvent(VisitRemoveBeforeEvent event) {
		registrationDAO.delete((Visit) event.getSource());
	}
}
