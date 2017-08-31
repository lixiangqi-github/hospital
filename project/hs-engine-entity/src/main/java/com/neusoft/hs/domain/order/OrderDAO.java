package com.neusoft.hs.domain.order;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neusoft.hs.domain.visit.Visit;

@Service
@Transactional(rollbackFor = Exception.class)
public class OrderDAO {

	@Autowired
	private OrderRepo orderRepo;

	public List<Order> findByVisitAndOrderTypeAndState(Visit visit, OrderType orderType,
			String state, Pageable pageable) {
		return orderRepo.findByVisitAndOrderTypeAndState(visit, orderType, state, pageable);
	}

	public List<Order> findByVisitAndState(Visit visit, String state, Pageable pageable) {
		return orderRepo.findByVisitAndState(visit, state, pageable);
	}

}
