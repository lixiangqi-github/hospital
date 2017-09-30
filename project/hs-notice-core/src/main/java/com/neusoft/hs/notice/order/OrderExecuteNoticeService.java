package com.neusoft.hs.notice.order;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.neusoft.hs.application.order.OrderExecuteAppService;
import com.neusoft.hs.domain.order.OrderExecute;
import com.neusoft.hs.engine.order.OrderExecuteDTO;
import com.neusoft.hs.engine.order.OrderExecuteDTOUtil;
import com.neusoft.hs.notice.config.MQConstant;

@Service
public class OrderExecuteNoticeService {

	@Autowired
	private OrderExecuteAppService orderExecuteAppService;

	@Autowired
	private RabbitMessagingTemplate rabbitMessagingTemplate;

	@Autowired
	private OrderExecuteDTOUtil orderExecuteDTOUtil;

	@Value("${custom.order.execute.need.notice.minute}")
	private int noticeMinute;

	public void notice() {

		Pageable pageable = new PageRequest(0, Integer.MAX_VALUE);
		List<OrderExecute> orderExecutes = orderExecuteAppService
				.findNeedNoticeOrderExecutes(noticeMinute, pageable);

		for (OrderExecute orderExecute : orderExecutes) {
			try {
				OrderExecuteDTO orderExecuteDTO = orderExecuteDTOUtil.convert(orderExecute);
				rabbitMessagingTemplate.convertAndSend(MQConstant.OrderExecuteNoticeExchange,
						orderExecuteNoticeRoutingKey(orderExecuteDTO), orderExecuteDTO);
			} catch (IllegalAccessException | InvocationTargetException e) {
				e.printStackTrace();
			}
		}
	}

	private String orderExecuteNoticeRoutingKey(OrderExecuteDTO orderExecuteDTO) {
		return orderExecuteDTO.getExecuteDeptId() + "." + MQConstant.OrderExecuteNoticeRoutingKey;
	}

}
