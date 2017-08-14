package com.neusoft.hs.engine.order;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;

import com.neusoft.hs.domain.order.DrugOrderTypeApp;
import com.neusoft.hs.domain.order.LongOrder;
import com.neusoft.hs.domain.order.Order;

public class OrderDTOUtil {

	public static OrderDTO convert(Order order) throws IllegalAccessException,
			InvocationTargetException {
		OrderDTO orderDTO = new OrderDTO();
		BeanUtils.copyProperties(orderDTO, order);

		orderDTO.setVisitId(order.getVisit().getId());
		orderDTO.setOrderTypeId(order.getOrderType().getId());
		orderDTO.setOrderTypeName(order.getOrderType().getName());
		if (order instanceof LongOrder) {
			LongOrder longOrder = (LongOrder) order;
			orderDTO.setLong(true);
			orderDTO.setFrequencyTypeId(longOrder.getFrequencyType().getId());
			orderDTO.setFrequencyTypeName(longOrder.getFrequencyType()
					.getName());
		} else {
			orderDTO.setLong(false);
		}
		orderDTO.setBelongDeptId(order.getBelongDept().getId());
		orderDTO.setExecuteDeptId(order.getExecuteDept().getId());

		if (order.getTypeApp() instanceof DrugOrderTypeApp) {
			DrugOrderTypeApp drugOrderTypeApp = (DrugOrderTypeApp) order
					.getTypeApp();

			orderDTO.setDrugUseModeId(drugOrderTypeApp.getDrugUseMode().getId());
		}

		return orderDTO;

	}

}
