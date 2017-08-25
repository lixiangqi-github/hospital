package com.neusoft.hs.domain.cost;

import org.springframework.context.ApplicationEvent;

public class ChargeBillCreatedEvent extends ApplicationEvent {

	public ChargeBillCreatedEvent(Object source) {
		super(source);
	}

}
