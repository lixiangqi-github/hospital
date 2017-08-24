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
		Order order = orderTypeApp.getOrder();

		if (order instanceof TemporaryOrder) {
			OrderExecuteTeam team = this.create(order, order.getCreateDate());
			order.addExecuteTeam(team);
		} else {
			LongOrder longOrder = (LongOrder) order;
			for (int day = 0; day < LongOrder.ResolveDays; day++) {
				// 计算执行时间
				List<Date> executeDates = longOrder.calExecuteDates(day);

				for (Date executeDate : executeDates) {
					OrderExecuteTeam executeTeam = this.create(order, executeDate);
					// 设置执行时间
					for (OrderExecute execute : executeTeam.getExecutes()) {
						execute.fillPlanDate(executeDate, executeDate);
					}
					// 收集执行条目
					order.addExecuteTeam(executeTeam);
				}
			}
		}
	}

	private OrderExecuteTeam create(Order order, Date startDate) {

		OrderExecuteTeam team = new OrderExecuteTeam();

		// 描述执行条目
		DescribeOrderExecute execute = new DescribeOrderExecute();
		execute.setOrder(order);
		execute.setVisit(order.getVisit());
		execute.setBelongDept(order.getBelongDept());
		execute.setType(OrderExecute.Type_Describe);

		execute.setPlanStartDate(startDate);
		execute.setPlanEndDate(startDate);

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
