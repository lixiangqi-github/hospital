package com.neusoft.hs.engine.order;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neusoft.hs.domain.order.OrderExecute;
import com.neusoft.hs.domain.orderexecute.OrderExecuteAppService;
import com.neusoft.hs.domain.organization.AbstractUser;
import com.neusoft.hs.domain.organization.UserAdminDomainService;

@Service
@Transactional(rollbackFor = Exception.class)
public class OrderExecuteFacadeImpl implements OrderExecuteFacade {

	@Autowired
	private OrderExecuteAppService orderExecuteAppService;

	@Autowired
	private UserAdminDomainService userAdminDomainService;

	@Override
	public List<OrderExecuteDTO> findNeedExecute(String userId, int pageNumber, int pageSize)
			throws OrderExecuteDTOException {
		AbstractUser user = userAdminDomainService.find(userId);
		if (user == null) {
			throw new OrderExecuteDTOException(null, "用户userId=[%s]不存在", userId);
		}
		Sort sort = new Sort(Direction.DESC, "planStartDate");
		Pageable pageable = new PageRequest(pageNumber, pageSize, sort);

		List<OrderExecute> executes = orderExecuteAppService.findNeedExecuteOrderExecutes(user, pageable);
		return null;
	}

	@Override
	public OrderExecuteDTO finish(String executeId, String userId) throws OrderExecuteDTOException {
		// TODO Auto-generated method stub
		return null;
	}

}
