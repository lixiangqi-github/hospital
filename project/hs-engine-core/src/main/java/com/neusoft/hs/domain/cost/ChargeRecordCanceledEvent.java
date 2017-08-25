package com.neusoft.hs.domain.cost;

import org.springframework.context.ApplicationEvent;

public class ChargeRecordCanceledEvent extends ApplicationEvent {

	public ChargeRecordCanceledEvent(Object source) {
		super(source);
	}
}
