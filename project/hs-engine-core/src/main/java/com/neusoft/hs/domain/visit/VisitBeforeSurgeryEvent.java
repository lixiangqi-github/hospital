package com.neusoft.hs.domain.visit;

import org.springframework.context.ApplicationEvent;

public class VisitBeforeSurgeryEvent extends ApplicationEvent {

	public VisitBeforeSurgeryEvent(Object source) {
		super(source);
	}

}
