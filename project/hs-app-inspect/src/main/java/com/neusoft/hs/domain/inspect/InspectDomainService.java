package com.neusoft.hs.domain.inspect;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neusoft.hs.domain.order.Apply;
import com.neusoft.hs.domain.order.ApplyDomainService;
import com.neusoft.hs.domain.order.InspectArrangeOrderExecute;
import com.neusoft.hs.domain.order.InspectConfirmOrderExecute;
import com.neusoft.hs.domain.order.Order;
import com.neusoft.hs.domain.order.OrderExecute;
import com.neusoft.hs.domain.order.OrderExecuteDomainService;
import com.neusoft.hs.domain.order.OrderExecuteException;
import com.neusoft.hs.domain.organization.AbstractUser;
import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.platform.log.LogUtil;
import com.neusoft.hs.platform.util.DateUtil;

@Service
@Transactional(rollbackFor = Exception.class)
public class InspectDomainService {

	@Autowired
	private InspectItemRepo inspectItemRepo;

	@Autowired
	private InspectApplyItemRepo inspectApplyItemRepo;

	@Autowired
	private InspectResultRepo inspectResultRepo;

	@Autowired
	private ApplicationContext applicationContext;

	/**
	 * 确认检查结果
	 * 
	 * @param executeId
	 * @param results
	 * @param user
	 * @throws InspectException
	 */
	public void confirm(OrderExecute orderExecute, Map<InspectApplyItem, String> results, AbstractUser user)
			throws InspectException {

		Order order = orderExecute.getOrder();
		Apply apply = order.getApply();
		if (apply == null) {
			throw new InspectException("医嘱条目[%s]不存在申请单", order.getId());
		}

		InspectApply inspectApply = (InspectApply) apply;

		List<InspectApplyItem> inspectApplyItems = inspectApply.getInspectApplyItems();

		for (InspectApplyItem item : inspectApplyItems) {
			String resultStr = results.get(item);
			if (resultStr != null) {
				InspectResult inspectResult = new InspectResult();
				inspectResult.setInspectDept((InspectDept) user.getDept());
				inspectResult.setInspectApplyItem(item);
				inspectResult.setResult(resultStr);
				inspectResult.setCreateDate(DateUtil.getSysDate());

				inspectApply.addInspectResult(inspectResult);

				item.setExecuteDate(DateUtil.getSysDate());
				item.setState(InspectApplyItem.State_Finished);
			}
		}

		LogUtil.log(this.getClass(), "用户[{}]为患者一次就诊[{}]确认检查结果[{}]", user.getId(), orderExecute.getVisit().getName(),
				inspectApply.getId());
	}

	/**
	 * 取消检查
	 * 
	 * @param inspectApplyItem
	 * @param user
	 * @throws InspectException
	 */
	public void cancel(InspectApplyItem inspectApplyItem, AbstractUser user) throws InspectException {

		if (inspectApplyItem.getState().equals(InspectApplyItem.State_Finished)) {
			throw new InspectException("检查项目[%s]已完成", inspectApplyItem.getInspectItem().getCode());
		}

		inspectApplyItem.setState(InspectApplyItem.State_Canceled);

		applicationContext.publishEvent(new InspectApplyItemCanceledEvent(inspectApplyItem));

		LogUtil.log(this.getClass(), "用户[{}]为患者一次就诊[{}]取消检查项目[{}]", user.getId(),
				inspectApplyItem.getInspectApply().getVisitName(), inspectApplyItem.getId());
	}

	public InspectApplyItem findInspectApplyItem(String inspectApplyItemId) {
		return inspectApplyItemRepo.findOne(inspectApplyItemId);
	}

	public void createInspectItems(List<InspectItem> inspectItems) {
		inspectItemRepo.save(inspectItems);
	}

	public List<InspectItem> findInspectItem(Pageable pageable) {
		return inspectItemRepo.findAll(pageable).getContent();
	}

	public List<InspectResult> findInspectResults(Visit visit) {
		return inspectResultRepo.findByVisit(visit);
	}

	public void clearInspectApplyItems() {
		inspectApplyItemRepo.deleteAll();
	}

	public void clearInspectItems() {
		inspectItemRepo.deleteAll();
	}
}
