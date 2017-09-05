package com.neusoft.hs.domain.order;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.neusoft.hs.domain.surgery.SurgeryApply;
import com.neusoft.hs.domain.surgery.SurgeryApplyItem;
import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.platform.util.DateUtil;

@Entity
@DiscriminatorValue("Surgery")
public class SurgeryOrderType extends OrderType {

	@Override
	protected List<OrderExecuteTeam> createExecuteTeams(Order order) throws OrderException {

		Visit visit = order.getVisit();

		List<OrderExecuteTeam> teams = new ArrayList<OrderExecuteTeam>();

		OrderExecuteTeam team = new OrderExecuteTeam();

		// 安排手术
		SurgeryArrangeOrderExecute arrange = new SurgeryArrangeOrderExecute();
		arrange.setOrder(order);
		arrange.setVisit(visit);
		arrange.setBelongDept(order.getBelongDept());
		arrange.setExecuteDept(order.getExecuteDept());

		arrange.setPlanStartDate(DateUtil.getSysDate());
		arrange.setPlanEndDate(DateUtil.getSysDate());

		arrange.setType(OrderExecute.Type_Arrange_Surgery);
		arrange.setState(OrderExecute.State_NeedSend);

		team.addOrderExecute(arrange);

		// 术前操作
		SurgeryBeforeOrderExecute before = new SurgeryBeforeOrderExecute();
		before.setOrder(order);
		before.setVisit(visit);
		before.setBelongDept(order.getBelongDept());
		before.setExecuteDept(order.getBelongDept());
		before.setType(OrderExecute.Type_Before_Surgery);
		before.setState(OrderExecute.State_NeedExecute);

		team.addOrderExecute(before);

		teams.add(team);

		// 术后操作
		SurgeryAfterOrderExecute after = new SurgeryAfterOrderExecute();
		after.setOrder(order);
		after.setVisit(visit);
		after.setBelongDept(order.getBelongDept());
		after.setExecuteDept(order.getExecuteDept());
		after.setType(OrderExecute.Type_After_Surgery);
		after.setState(OrderExecute.State_NeedExecute);

		SurgeryApply surgeryApply = (SurgeryApply) order.getApply();
		for (SurgeryApplyItem surgeryApplyItem : surgeryApply.getSurgeryApplyItems()) {
			after.addChargeItem(surgeryApplyItem.getSurgeryType().getChargeItem());
		}

		team.addOrderExecute(after);

		teams.add(team);

		return teams;
	}

}
