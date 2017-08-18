//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\visit\\VisitIntoWardedEvent.java

package com.neusoft.hs.domain.visit;

import org.springframework.context.ApplicationEvent;

/**
 * 门诊离院事件
 * 
 * @author kingbox
 *
 */
public class VisitRemovedEvent extends ApplicationEvent {

	public VisitRemovedEvent(Object source) {
		super(source);
	}
}
