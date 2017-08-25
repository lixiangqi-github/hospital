package com.neusoft.hs.domain.cost;

import org.springframework.context.ApplicationEvent;

public class ChargeRecordCreatedEvent extends ApplicationEvent {

	public ChargeRecordCreatedEvent(Object source) {
		super(source);
	}
}
