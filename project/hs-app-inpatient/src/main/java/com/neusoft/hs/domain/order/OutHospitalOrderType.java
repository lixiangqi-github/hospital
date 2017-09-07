package com.neusoft.hs.domain.order;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.domain.visit.VisitDomainService;
import com.neusoft.hs.domain.visit.VisitPlanRecord;

@Entity
@DiscriminatorValue("OutHospital")
public class OutHospitalOrderType extends OrderType {

	@Override
	protected void verify(Order order) throws OrderException {
		Visit visit = order.getVisit();
		visit.setPlanLeaveWardDate(order.getPlanStartDate());

		// 创建计划日程
		VisitPlanRecord visitPlanRecord = new VisitPlanRecord();
		visitPlanRecord.setVisit(visit);
		visitPlanRecord.setType(this.getName());
		visitPlanRecord.setPlanExecuteDate(order.getPlanStartDate());
		visitPlanRecord.setOperator(order.getCreator());
		visitPlanRecord.setDept(order.getBelongDept());

		this.getService(VisitDomainService.class).createVisitPlanRecord(visitPlanRecord);
	}

	@Override
	protected List<OrderExecuteTeam> createExecuteTeams(Order order) throws OrderException {

		List<OrderExecuteTeam> teams = new ArrayList<OrderExecuteTeam>();
		OrderExecuteTeam team = new OrderExecuteTeam();

		// 出院登记执行条目
		OutHospitalRegisterOrderExecute register = new OutHospitalRegisterOrderExecute();
		register.setOrder(order);
		register.setVisit(order.getVisit());
		register.setBelongDept(order.getBelongDept());
		register.setExecuteDept(order.getBelongDept());
		register.setType(OrderExecute.Type_Leave_Hospital_Register);
		register.setMain(true);
		register.setState(OrderExecute.State_NeedExecute);

		team.addOrderExecute(register);

		// 出院结算执行条目
		OutHospitalBalanceOrderExecute balance = new OutHospitalBalanceOrderExecute();
		balance.setOrder(order);
		balance.setVisit(order.getVisit());
		balance.setBelongDept(order.getBelongDept());
		balance.setExecuteDept(order.getBelongDept().getOrg().getInChargeDept());
		balance.setType(OrderExecute.Type_Leave_Hospital_Balance);
		balance.setState(OrderExecute.State_NeedExecute);

		team.addOrderExecute(balance);

		teams.add(team);

		return teams;
	}

}
