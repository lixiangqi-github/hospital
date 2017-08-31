package com.neusoft.hs.domain.order;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.neusoft.hs.domain.order.LongOrder;
import com.neusoft.hs.domain.order.Order;
import com.neusoft.hs.domain.order.OrderExecute;
import com.neusoft.hs.domain.order.OrderExecuteTeam;
import com.neusoft.hs.domain.order.OrderType;
import com.neusoft.hs.domain.order.OrderTypeApp;

@Entity
@DiscriminatorValue("Nursing")
public class NursingOrderType extends OrderType {

	@NotEmpty(message = "护理类型不能为空")
	@Column(name = "nursing_type", length = 32)
	private String nursingType;

	@Override
	protected void check(Order order) throws OrderException {
		Pageable pageable = new PageRequest(0, Integer.MAX_VALUE);
		List<Order> orders = this.getService(OrderDAO.class).findByVisitAndOrderTypeAndState(
				order.getVisit(), this, Order.State_Executing, pageable);

		if (orders.size() > 0) {
			throw new OrderException(order, "患者[%s]有在执行的护理医嘱[%s]", order.getVisit().getName(),
					orders.get(0).getId());
		}
	}

	@Override
	protected List<OrderExecuteTeam> createExecuteTeams(Order order) throws OrderException {

		List<OrderExecuteTeam> teams = new ArrayList<OrderExecuteTeam>();
		OrderExecuteTeam team = new OrderExecuteTeam();

		NursingOrderExecute execute = new NursingOrderExecute();
		execute.setOrder(order);
		execute.setVisit(order.getVisit());
		execute.setBelongDept(order.getBelongDept());
		execute.setExecuteDept(order.getBelongDept());
		execute.setChargeDept(order.getBelongDept());
		execute.setType(nursingType);
		execute.addChargeItem(this.getChargeItem());
		execute.setState(OrderExecute.State_NeedExecute);

		team.addOrderExecute(execute);

		teams.add(team);

		return teams;
	}

	public String getNursingType() {
		return nursingType;
	}

	public void setNursingType(String nursingType) {
		this.nursingType = nursingType;
	}

}
