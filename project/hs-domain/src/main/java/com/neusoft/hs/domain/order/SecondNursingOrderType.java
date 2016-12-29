package com.neusoft.hs.domain.order;

import java.util.Date;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.neusoft.hs.platform.util.DateUtil;

@Entity
@DiscriminatorValue("SecondNursing")
public class SecondNursingOrderType extends OrderType {

	@Override
	public OrderExecuteTeam resolveOrder(Order order) {

		OrderExecuteTeam team = new OrderExecuteTeam();

		// 分解两天的护理执行条目
		OrderExecute execute = this.create(order, order.getPlanStartDate());
		team.addOrderExecute(execute);
		team.addOrderExecute(this.create(order, execute.getPlanEndDate()));
		
		return team;
	}

	private OrderExecute create(Order order, Date startDate) {

		OrderExecute execute = new OrderExecute();

		execute.setOrder(order);
		execute.setVisit(order.getVisit());
		execute.setBelongDept(order.getBelongDept());
		execute.setType(OrderExecute.Type_SecondNursing);
		execute.setChargeItem(this.getChargeItem());

		execute.setExecuteDept(order.getBelongDept());
		execute.setState(OrderExecute.State_NeedExecute);
		execute.setChargeState(OrderExecute.ChargeState_NoCharge);
		execute.setCostState(OrderExecute.CostState_NoCost);

		execute.setPlanStartDate(startDate);
		execute.setPlanEndDate(DateUtil.addDay(startDate));

		return execute;
	}

}
