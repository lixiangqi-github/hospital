package com.neusoft.hs.domain.visit;

import org.springframework.context.ApplicationEvent;

public class VisitAfterSurgeryEvent extends ApplicationEvent {

	public VisitAfterSurgeryEvent(Object source) {
		super(source);
	}
}
