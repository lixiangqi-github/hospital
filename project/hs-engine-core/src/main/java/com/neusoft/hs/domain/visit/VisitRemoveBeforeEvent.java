//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\visit\\VisitIntoWardedEvent.java

package com.neusoft.hs.domain.visit;

import org.springframework.context.ApplicationEvent;

/**
 * 门诊离院事件
 * 
 * @author kingbox
 *
 */
public class VisitRemoveBeforeEvent extends ApplicationEvent {

	public VisitRemoveBeforeEvent(Object source) {
		super(source);
	}
}
