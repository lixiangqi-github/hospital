package com.neusoft.hs.domain.order;

import java.util.Date;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("Describe")
public class DescribeOrderType extends OrderType {

	@Override
	public void check(Order order) throws OrderException {
		if (order.getDescribe() == null || order.getDescribe().length() == 0) {
			throw new OrderException(order, "请录入描述信息");
		}
	}

	@Override
	public void resolveOrder(OrderTypeApp orderTypeApp) throws OrderException {
		orderTypeApp.doResolve();
	}

	@Override
	protected OrderExecuteTeam createExecuteTeam(Order order, Date planExecuteDate) throws OrderException {

		OrderExecuteTeam team = new OrderExecuteTeam();

		// 描述执行条目
		DescribeOrderExecute execute = new DescribeOrderExecute();
		execute.setOrder(order);
		execute.setVisit(order.getVisit());
		execute.setBelongDept(order.getBelongDept());
		execute.setType(OrderExecute.Type_Describe);

		execute.setPlanStartDate(planExecuteDate);
		execute.setPlanEndDate(planExecuteDate);

		execute.setExecuteDept(order.getExecuteDept());
		if (execute.needSend()) {
			execute.setState(OrderExecute.State_NeedSend);
		} else {
			execute.setState(OrderExecute.State_NeedExecute);
		}

		team.addOrderExecute(execute);

		return team;
	}
}
