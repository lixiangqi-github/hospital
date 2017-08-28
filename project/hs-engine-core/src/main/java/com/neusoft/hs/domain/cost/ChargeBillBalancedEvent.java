package com.neusoft.hs.domain.cost;

import org.springframework.context.ApplicationEvent;

public class ChargeBillBalancedEvent extends ApplicationEvent {

	public ChargeBillBalancedEvent(Object source) {
		super(source);
	}
}
