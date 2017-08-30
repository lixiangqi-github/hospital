package com.neusoft.hs.application.inspect;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neusoft.hs.domain.inspect.InspectApply;
import com.neusoft.hs.domain.inspect.InspectApplyItem;
import com.neusoft.hs.domain.inspect.InspectDomainService;
import com.neusoft.hs.domain.inspect.InspectException;
import com.neusoft.hs.domain.order.ApplyDomainService;
import com.neusoft.hs.domain.order.InspectArrangeOrderExecute;
import com.neusoft.hs.domain.order.InspectConfirmOrderExecute;
import com.neusoft.hs.domain.order.OrderExecute;
import com.neusoft.hs.domain.order.OrderExecuteDomainService;
import com.neusoft.hs.domain.order.OrderExecuteException;
import com.neusoft.hs.domain.organization.AbstractUser;

@Service
@Transactional(rollbackFor = Exception.class)
public class InspectAppService {

	@Autowired
	private ApplyDomainService applyDomainService;

	@Autowired
	private InspectDomainService inspectDomainService;

	@Autowired
	private OrderExecuteDomainService orderExecuteDomainService;

	public InspectApply find(String applyId) {
		return (InspectApply) applyDomainService.find(applyId);
	}

	public void arrange(String executeId, Date planExecuteDate, String inspectPlace,
			AbstractUser user) throws InspectException, OrderExecuteException {

		OrderExecute execute = orderExecuteDomainService.find(executeId);
		if (execute == null) {
			throw new OrderExecuteException(null, "executeId=[%s]不存在", executeId);
		}

		Map<String, Object> params = new HashMap<String, Object>();
		params.put(InspectArrangeOrderExecute.PlanExecuteDate, planExecuteDate);
		params.put(InspectArrangeOrderExecute.InspectPlace, inspectPlace);

		orderExecuteDomainService.finish(execute, params, user);
	}

	public void confirm(String executeId, Map<InspectApplyItem, String> results, AbstractUser user)
			throws InspectException, OrderExecuteException {

		OrderExecute execute = orderExecuteDomainService.find(executeId);
		if (execute == null) {
			throw new OrderExecuteException(null, "executeId=[%s]不存在", executeId);
		}

		inspectDomainService.confirm(execute, results, user);
		orderExecuteDomainService.finish(execute, null, user);
	}

	public void cancel(String inspectApplyItemId, AbstractUser user) throws InspectException {
		InspectApplyItem inspectApplyItem = inspectDomainService
				.findInspectApplyItem(inspectApplyItemId);
		if (inspectApplyItem == null) {
			throw new InspectException("检查项目inspectApplyItemId=[%s]不存在", inspectApplyItemId);
		}
		inspectDomainService.cancel(inspectApplyItem, user);

		InspectArrangeOrderExecute arrange = inspectApplyItem.getInspectArrangeOrderExecute();
		if (arrange != null) {
			try {
				orderExecuteDomainService.cancel(arrange, user);
			} catch (OrderExecuteException e) {
				throw new InspectException(e);
			}
		}
		InspectConfirmOrderExecute confirm = inspectApplyItem.getInspectConfirmOrderExecute();
		if (confirm != null) {
			try {
				orderExecuteDomainService.cancel(confirm, user);
			} catch (OrderExecuteException e) {
				throw new InspectException(e);
			}
		}

	}
}
