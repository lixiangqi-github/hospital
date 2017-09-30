//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\listener\\ConstListenter.java

package com.neusoft.hs.notice.visit;

import java.lang.reflect.InvocationTargetException;

import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.domain.visit.VisitCreatedEvent;
import com.neusoft.hs.engine.visit.VisitDTO;
import com.neusoft.hs.engine.visit.VisitDTOUtil;
import com.neusoft.hs.notice.config.MQConstant;

@Service(value = "notice-visit-visitCreatedEventListener")
public class VisitCreatedEventListener implements ApplicationListener<VisitCreatedEvent> {

	@Autowired
	private RabbitMessagingTemplate rabbitMessagingTemplate;
	
	@Autowired
	private VisitDTOUtil visitDTOUtil;

	@Async
	@Override
	public void onApplicationEvent(VisitCreatedEvent event) {
		try {
			Visit visit = (Visit) event.getSource();
			VisitDTO visitDTO = visitDTOUtil.convert(visit);
			rabbitMessagingTemplate.convertAndSend(MQConstant.VisitExchange,
					MQConstant.VisitCreateRoutingKey, visitDTO);
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}
}
