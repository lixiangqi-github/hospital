package com.neusoft.hs.domain.order;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.neusoft.hs.domain.cost.ChargeOrderExecute;
import com.neusoft.hs.domain.inspect.InspectApply;
import com.neusoft.hs.domain.inspect.InspectApplyItem;
import com.neusoft.hs.domain.organization.OrganizationAdminDomainService;
import com.neusoft.hs.domain.visit.Visit;

@Entity
@DiscriminatorValue("Inspect")
public class InspectOrderType extends OrderType {

	@Override
	protected List<OrderExecuteTeam> createExecuteTeams(Order order) throws OrderException {

		InspectApply inspectApply = (InspectApply) order.getApply();
		if (inspectApply == null) {
			throw new OrderException(order, "医嘱[%s]没有申请单", order.getId());
		}

		List<InspectApplyItem> inspectApplyItems = inspectApply.getInspectApplyItems();
		if (inspectApplyItems == null || inspectApplyItems.size() == 0) {
			throw new OrderException(order, "申请单[%s]没有申请检查项目", inspectApply.getId());
		}

		OrganizationAdminDomainService organizationAdminDomainService = this
				.getService(OrganizationAdminDomainService.class);

		Visit visit = order.getVisit();

		List<OrderExecuteTeam> teams = new ArrayList<OrderExecuteTeam>();

		for (InspectApplyItem inspectApplyItem : inspectApply.getInspectApplyItems()) {
			OrderExecuteTeam team = new OrderExecuteTeam();

			if (!order.isInPatient()) {
				// 收费执行条目
				ChargeOrderExecute chargeOrderExecute = new ChargeOrderExecute();
				chargeOrderExecute.setOrder(order);
				chargeOrderExecute.setVisit(visit);
				chargeOrderExecute.setBelongDept(order.getBelongDept());
				chargeOrderExecute.setType(OrderExecute.Type_Change);
				chargeOrderExecute.addChargeItem(inspectApplyItem.getInspectItem().getChargeItem());

				chargeOrderExecute.setExecuteDept(
						organizationAdminDomainService.getOutChargeDept(visit.getDept()));
				chargeOrderExecute.setChargeDept(inspectApplyItem.getArrangeDept());
				chargeOrderExecute.setState(OrderExecute.State_Executing);

				team.addOrderExecute(chargeOrderExecute);
			}
			// 安排检查
			InspectArrangeOrderExecute arrange = new InspectArrangeOrderExecute();
			arrange.setOrder(order);
			arrange.setVisit(order.getVisit());
			arrange.setBelongDept(order.getBelongDept());
			arrange.setExecuteDept(inspectApplyItem.getArrangeDept());

			arrange.setType(OrderExecute.Type_Arrange_Inspect);
			arrange.setInspectApplyItem(inspectApplyItem);

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
			confirm.setExecuteDept(inspectApplyItem.getInspectDept());
			confirm.setChargeDept(inspectApplyItem.getInspectDept());
			confirm.setType(OrderExecute.Type_Confirm_Inspect);
			confirm.setInspectApplyItem(inspectApplyItem);
			confirm.setMain(true);
			confirm.setState(OrderExecute.State_NeedExecute);

			confirm.addChargeItem(inspectApplyItem.getInspectItem().getChargeItem());

			team.addOrderExecute(confirm);

			teams.add(team);
		}

		return teams;
	}
}
