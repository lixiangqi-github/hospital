package com.neusoft.hs.domain.order;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.neusoft.hs.domain.cost.ChargeItem;
import com.neusoft.hs.domain.cost.ChargeOrderExecute;
import com.neusoft.hs.domain.organization.OrganizationAdminDomainService;
import com.neusoft.hs.domain.pharmacy.DrugUseMode;
import com.neusoft.hs.domain.pharmacy.Pharmacy;
import com.neusoft.hs.domain.visit.Visit;

@Entity
@DiscriminatorValue("Oral")
public class OralOrderUseMode extends DrugUseMode {

	@Override
	public List<OrderExecuteTeam> createExecuteTeams(Order order) {

		List<OrderExecuteTeam> teams = new ArrayList<OrderExecuteTeam>();

		OrderExecuteTeam team = new OrderExecuteTeam();

		DrugOrderTypeApp drugOrderTypeApp = this.getService(DrugOrderTypeAppRepo.class)
				.findOne(order.getTypeApp().getId());

		Visit visit = order.getVisit();
		Pharmacy pharmacy = drugOrderTypeApp.getPharmacy();

		OrganizationAdminDomainService organizationAdminDomainService = this
				.getService(OrganizationAdminDomainService.class);

		ChargeItem chargeItem = drugOrderTypeApp.getDrugTypeSpec().getChargeItem();

		ChargeOrderExecute chargeOrderExecute = null;
		if (!order.isInPatient()) {
			// 收费执行条目
			chargeOrderExecute = new ChargeOrderExecute();
			chargeOrderExecute.setOrder(order);
			chargeOrderExecute.setVisit(visit);
			chargeOrderExecute.setBelongDept(order.getBelongDept());
			chargeOrderExecute.setType(OrderExecute.Type_Change);
			chargeOrderExecute.addChargeItem(chargeItem);

			chargeOrderExecute.setExecuteDept(organizationAdminDomainService.getOutChargeDept(visit.getDept()));
			chargeOrderExecute.setChargeDept(pharmacy);
			chargeOrderExecute.setState(OrderExecute.State_Executing);

			team.addOrderExecute(chargeOrderExecute);
		}

		// 摆药执行条目
		DispensingDrugOrderExecute dispensingDrugExecute = new DispensingDrugOrderExecute();
		dispensingDrugExecute.setOrder(order);
		dispensingDrugExecute.setVisit(visit);
		dispensingDrugExecute.setBelongDept(order.getBelongDept());
		dispensingDrugExecute.setType(OrderExecute.Type_Dispense_Drug);
		dispensingDrugExecute.addChargeItem(chargeItem);
		dispensingDrugExecute.setCount(order.getCount());

		dispensingDrugExecute.setExecuteDept(pharmacy);
		dispensingDrugExecute.setChargeDept(pharmacy);

		if (dispensingDrugExecute.needSend()) {
			dispensingDrugExecute.setState(OrderExecute.State_NeedSend);
		} else {
			dispensingDrugExecute.setState(OrderExecute.State_NeedExecute);
		}
		dispensingDrugExecute.setPharmacy(pharmacy);
		dispensingDrugExecute.setDrugTypeSpec(drugOrderTypeApp.getDrugTypeSpec());

		team.addOrderExecute(dispensingDrugExecute);

		// 发药执行条目
		DistributeDrugOrderExecute distributeDrugExecute = new DistributeDrugOrderExecute();
		distributeDrugExecute.setOrder(order);
		distributeDrugExecute.setVisit(visit);
		distributeDrugExecute.setBelongDept(order.getBelongDept());
		distributeDrugExecute.setType(OrderExecute.Type_Distribute_Drug);
		distributeDrugExecute.setExecuteDept(pharmacy);
		distributeDrugExecute.setMain(true);
		distributeDrugExecute.setState(OrderExecute.State_NeedExecute);
		distributeDrugExecute.setCount(order.getCount());

		distributeDrugExecute.setPharmacy(pharmacy);
		distributeDrugExecute.setDrugTypeSpec(drugOrderTypeApp.getDrugTypeSpec());

		team.addOrderExecute(distributeDrugExecute);

		teams.add(team);

		return teams;
	}

}
