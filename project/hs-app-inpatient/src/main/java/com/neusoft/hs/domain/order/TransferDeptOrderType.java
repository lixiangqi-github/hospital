package com.neusoft.hs.domain.order;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("TransferDept")
public class TransferDeptOrderType extends OrderType {

	@Override
	protected void check(Order order) throws OrderException, OrderExecuteException {
		if (order.getExecuteDept() == null) {
			throw new OrderException(order, "没有指定转科科室");
		}
	}

	@Override
	protected List<OrderExecuteTeam> createExecuteTeams(Order order) throws OrderException {

		List<OrderExecuteTeam> teams = new ArrayList<OrderExecuteTeam>();
		OrderExecuteTeam team = new OrderExecuteTeam();

		// 转科确认执行条目
		TransferDeptConfirmOrderExecute transferConfirm = new TransferDeptConfirmOrderExecute();
		transferConfirm.setOrder(order);
		transferConfirm.setVisit(order.getVisit());
		transferConfirm.setBelongDept(order.getBelongDept());
		transferConfirm.setExecuteDept(order.getExecuteDept());
		transferConfirm.setType(OrderExecute.Type_Transfer_Dept);
		transferConfirm.setMain(true);
		transferConfirm.setState(OrderExecute.State_NeedSend);

		team.addOrderExecute(transferConfirm);

		teams.add(team);

		return teams;
	}

}
