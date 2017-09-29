package com.neusoft.hs.test.notice.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.neusoft.hs.engine.visit.VisitDTO;
import com.neusoft.hs.notice.config.MQConstant;
import com.neusoft.hs.platform.log.LogUtil;
import com.neusoft.hs.platform.util.DateUtil;

@Component
public class ReceiverService {

	@RabbitListener(queues = MQConstant.VisitQueue)
	public void receiveFooQueue(VisitDTO visitDTO) {
		LogUtil.log(ReceiverService.class, "接收到患者一次就诊[{}]创建消息,创建时间为[{}]", visitDTO.getId(),
				DateUtil.toString(visitDTO.getCreateDate()));
	}
}
