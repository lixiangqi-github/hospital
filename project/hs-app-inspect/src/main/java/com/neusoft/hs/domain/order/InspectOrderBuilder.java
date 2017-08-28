package com.neusoft.hs.domain.order;

import com.neusoft.hs.application.order.AbstractOrderBuilder;
import com.neusoft.hs.domain.inspect.InspectApply;

public class InspectOrderBuilder extends AbstractOrderBuilder {

	private InspectApply inspectApply;

	@Override
	public OrderCreateCommand createCommand() throws OrderException {

		TemporaryOrder inspectOrder = new TemporaryOrder();
		inspectOrder.setVisit(this.visit);
		inspectOrder.setName(orderType.getName());
		inspectOrder.setOrderType(orderType);
		inspectOrder.setPlanStartDate(getPlanStartDate());
		inspectOrder.setPlaceType(placeType);

		inspectOrder.setApply(inspectApply);

		return inspectOrder;
	}

	public InspectApply getInspectApply() {
		return inspectApply;
	}

	public void setInspectApply(InspectApply inspectApply) {
		this.inspectApply = inspectApply;
	}

}
