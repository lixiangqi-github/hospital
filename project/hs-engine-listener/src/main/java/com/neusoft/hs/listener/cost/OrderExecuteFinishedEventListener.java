//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\listener\\ConstListenter.java

package com.neusoft.hs.listener.cost;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

import com.neusoft.hs.domain.cost.CostDomainService;
import com.neusoft.hs.domain.cost.CostException;
import com.neusoft.hs.domain.order.OrderExecute;
import com.neusoft.hs.domain.order.OrderExecuteFinishedEvent;

@Service(value = "engine-cost-orderExecuteFinishedEventListener")
public class OrderExecuteFinishedEventListener
		implements ApplicationListener<OrderExecuteFinishedEvent> {

	@Autowired
	private CostDomainService costDomainService;

	@Override
	public void onApplicationEvent(OrderExecuteFinishedEvent event) {

		OrderExecute execute = (OrderExecute) event.getSource();

		try {
			costDomainService.charging(execute);
		} catch (CostException e) {
			e.printStackTrace();
		}
	}
}
