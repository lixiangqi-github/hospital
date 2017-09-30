package com.neusoft.hs.test.notice.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.neusoft.hs.engine.order.OrderExecuteDTO;
import com.neusoft.hs.platform.log.LogUtil;

@Component
public class InspectReceiverService {

	private int orderExecuteNoticeCount = 0;

	@RabbitListener(queues = "${test.custom.inspect.orderExecute.notice.queue}")
	public void receiveVisitQueue(OrderExecuteDTO orderExecuteDTO) {
		orderExecuteNoticeCount++;
		LogUtil.log(InspectReceiverService.class, "接收到检查科室[dept444]需要执行的执行条目[{}]",
				orderExecuteDTO.getId());
	}

	public int getOrderExecuteNoticeCount() {
		return orderExecuteNoticeCount;
	}

}
