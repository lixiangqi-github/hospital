package com.neusoft.hs.test.notice.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.neusoft.hs.engine.visit.VisitDTO;
import com.neusoft.hs.platform.log.LogUtil;
import com.neusoft.hs.platform.util.DateUtil;
import com.neusoft.hs.test.notice.config.MQConsumerConstant;

@Component
public class VisitCreateReceiverService {

	private int createCount = 0;

	@RabbitListener(queues = MQConsumerConstant.VisitCreateQueue)
	public void receiveVisitQueue(VisitDTO visitDTO) {
		createCount++;
		LogUtil.log(VisitCreateReceiverService.class, "接收到患者一次就诊[{}]创建消息,创建时间为[{}]", visitDTO.getId(),
				DateUtil.toString(visitDTO.getCreateDate()));
	}

	public int getCreateCount() {
		return createCount;
	}

}
