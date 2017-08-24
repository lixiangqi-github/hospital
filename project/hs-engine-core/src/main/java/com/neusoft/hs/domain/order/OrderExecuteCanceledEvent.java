package com.neusoft.hs.domain.order;

import org.springframework.context.ApplicationEvent;

public class OrderExecuteCanceledEvent extends ApplicationEvent {

	public OrderExecuteCanceledEvent(Object source) {
		super(source);
	}

}
