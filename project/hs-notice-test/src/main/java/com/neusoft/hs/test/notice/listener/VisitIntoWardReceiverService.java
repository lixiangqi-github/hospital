package com.neusoft.hs.test.notice.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.neusoft.hs.engine.visit.VisitDTO;
import com.neusoft.hs.notice.config.MQConstant;
import com.neusoft.hs.platform.log.LogUtil;
import com.neusoft.hs.platform.util.DateUtil;

@Component
public class VisitIntoWardReceiverService {

	private int intoWardCount = 0;

	@RabbitListener(queues = MQConstant.VisitIntoWardQueue)
	public void receiveVisitQueue(VisitDTO visitDTO) {
		intoWardCount++;
		LogUtil.log(VisitIntoWardReceiverService.class, "接收到患者一次就诊[{}]入院消息,入院时间为[{}]",
				visitDTO.getId(), DateUtil.toString(visitDTO.getIntoWardDate()));
	}

	public int getIntoWardCount() {
		return intoWardCount;
	}

}
