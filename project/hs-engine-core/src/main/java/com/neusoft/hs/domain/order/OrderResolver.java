package com.neusoft.hs.domain.order;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neusoft.hs.domain.organization.Admin;

@Service
@Transactional(rollbackFor = Exception.class)
class OrderResolver {

	@Autowired
	private OrderRepo orderRepo;

	public int resolve(String orderId, Admin admin) {
		try {
			LongOrder longOrder = (LongOrder) orderRepo.findOne(orderId);
			List<OrderExecute> orderExecutes = longOrder.resolve(admin);
			return orderExecutes.size();
		} catch (OrderException e) {
			e.printStackTrace();
			return 0;
		} catch (OrderExecuteException e) {
			e.printStackTrace();
			return 0;
		}
	}
}
