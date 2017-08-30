package com.neusoft.hs.portal.swing.ui.forms.order.controller;

import com.neusoft.hs.domain.order.Order;
import com.neusoft.hs.domain.organization.AbstractUser;
import com.neusoft.hs.domain.pharmacy.DrugUseMode;

public class CreateOrderInfo {

	private Order order;

	private DrugUseMode drugUseMode;

	private AbstractUser user;

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public DrugUseMode getDrugUseMode() {
		return drugUseMode;
	}

	public void setDrugUseMode(DrugUseMode drugUseMode) {
		this.drugUseMode = drugUseMode;
	}

	public AbstractUser getUser() {
		return user;
	}

	public void setUser(AbstractUser user) {
		this.user = user;
	}

}
