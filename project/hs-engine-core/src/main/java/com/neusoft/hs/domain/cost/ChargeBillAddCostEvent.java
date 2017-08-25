package com.neusoft.hs.domain.cost;

import org.springframework.context.ApplicationEvent;

public class ChargeBillAddCostEvent extends ApplicationEvent {

	public ChargeBillAddCostEvent(Object source) {
		super(source);
	}

}
