package com.neusoft.hs.domain.surgery;

import org.springframework.context.ApplicationEvent;

public class SurgeryApplyItemAddedEvent extends ApplicationEvent {

	public SurgeryApplyItemAddedEvent(Object source) {
		super(source);
	}
}
