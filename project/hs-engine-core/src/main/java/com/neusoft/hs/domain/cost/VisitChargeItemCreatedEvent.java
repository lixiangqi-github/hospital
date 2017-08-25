package com.neusoft.hs.domain.cost;

import org.springframework.context.ApplicationEvent;

public class VisitChargeItemCreatedEvent extends ApplicationEvent {

	public VisitChargeItemCreatedEvent(Object source) {
		super(source);
	}
}
