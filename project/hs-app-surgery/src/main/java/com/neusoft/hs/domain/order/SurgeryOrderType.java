package com.neusoft.hs.domain.order;

import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("Surgery")
public class SurgeryOrderType extends OrderType {

	@Override
	protected List<OrderExecuteTeam> createExecuteTeams(Order order) throws OrderException {
		// TODO Auto-generated method stub
		return null;
	}

}
