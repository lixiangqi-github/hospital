package com.neusoft.hs.domain.order;

import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.neusoft.hs.domain.inspect.InspectApply;
import com.neusoft.hs.domain.inspect.InspectApplyItem;
import com.neusoft.hs.domain.order.Order;
import com.neusoft.hs.domain.order.OrderException;
import com.neusoft.hs.domain.order.OrderExecute;
import com.neusoft.hs.domain.order.OrderExecuteTeam;
import com.neusoft.hs.domain.order.OrderType;
import com.neusoft.hs.domain.order.OrderTypeApp;

@Entity
@DiscriminatorValue("Inspect")
public class InspectOrderType extends OrderType {

	@Override
	public void resolveOrder(OrderTypeApp orderTypeApp) throws OrderException {

		Order order = orderTypeApp.getOrder();

		InspectApply inspectApply = (InspectApply) order.getApply();
		if (inspectApply == null) {
			throw new OrderException(order, "医嘱[%s]没有申请单", order.getId());
		}

		List<InspectApplyItem> inspectApplyItems = inspectApply.getInspectApplyItems();
		if (inspectApplyItems == null || inspectApplyItems.size() == 0) {
			throw new OrderException(order, "申请单[%s]没有申请检查项目", inspectApply.getId());
		}
		for (InspectApplyItem inspectApplyItem : inspectApply.getInspectApplyItems()) {
			OrderExecuteTeam team = new OrderExecuteTeam();
			// 安排检查
			InspectArrangeOrderExecute arrange = new InspectArrangeOrderExecute();
			arrange.setOrder(order);
			arrange.setVisit(order.getVisit());
			arrange.setBelongDept(order.getBelongDept());
			arrange.setType(OrderExecute.Type_Arrange_Inspect);
			arrange.setInspectApplyItem(inspectApplyItem);

			arrange.setPlanStartDate(order.getPlanStartDate());
			arrange.setPlanEndDate(order.getPlanStartDate());

			arrange.setExecuteDept(inspectApplyItem.getArrangeDept());
			if (arrange.needSend()) {
				arrange.setState(OrderExecute.State_NeedSend);
			} else {
				arrange.setState(OrderExecute.State_NeedExecute);
			}

			team.addOrderExecute(arrange);

			// 完成检查
			InspectConfirmOrderExecute confirm = new InspectConfirmOrderExecute();
			confirm.setOrder(order);
			confirm.setVisit(order.getVisit());
			confirm.setBelongDept(order.getBelongDept());
			confirm.setType(OrderExecute.Type_Confirm_Inspect);
			confirm.setInspectApplyItem(inspectApplyItem);
			confirm.setMain(true);

			confirm.setExecuteDept(inspectApplyItem.getInspectDept());
			confirm.setChargeDept(inspectApplyItem.getInspectDept());
			confirm.setState(OrderExecute.State_NeedExecute);

			confirm.addChargeItem(inspectApplyItem.getInspectItem().getChargeItem());

			team.addOrderExecute(confirm);

			order.addExecuteTeam(team);
		}
	}
}
