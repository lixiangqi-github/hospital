package com.neusoft.hs.engine.order;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neusoft.hs.domain.order.DrugOrderTypeApp;
import com.neusoft.hs.domain.order.DrugOrderTypeAppDAO;
import com.neusoft.hs.domain.order.LongOrder;
import com.neusoft.hs.domain.order.Order;

@Service
public class OrderDTOUtil {

	@Autowired
	private DrugOrderTypeAppDAO drugOrderTypeAppDAO;

	public OrderDTO convert(Order order) throws IllegalAccessException,
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
		if (order.getExecuteDept() != null) {
			orderDTO.setExecuteDeptId(order.getExecuteDept().getId());
		}
		orderDTO.setCreatorId(order.getCreator().getId());
		orderDTO.setCreatorName(order.getCreator().getName());

		if (order.getTypeApp().getCategory().equals(DrugOrderTypeApp.Category)) {
			DrugOrderTypeApp drugOrderTypeApp = drugOrderTypeAppDAO.find(order
					.getTypeApp().getId());
			orderDTO.setDrugUseModeId(drugOrderTypeApp.getDrugUseMode().getId());
			orderDTO.setDrugUseModeName(drugOrderTypeApp.getDrugUseMode().getName());
		}

		return orderDTO;

	}

}
