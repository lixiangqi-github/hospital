package com.neusoft.hs.portal.security;

import org.springframework.context.ApplicationEvent;

public class LogoutEvent extends ApplicationEvent {

	public LogoutEvent(Object source) {
		super(source);
	}
}
