//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\order\\OrderDomainService.java

package com.neusoft.hs.domain.order;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neusoft.hs.domain.organization.AbstractUser;
import com.neusoft.hs.domain.organization.Admin;
import com.neusoft.hs.domain.organization.Dept;
import com.neusoft.hs.domain.organization.Doctor;
import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.domain.visit.VisitDomainService;
import com.neusoft.hs.platform.exception.HsException;
import com.neusoft.hs.platform.log.LogUtil;
import com.neusoft.hs.platform.util.DateUtil;

@Service
@Transactional(rollbackFor = Exception.class)
public class OrderDomainService {

	@Autowired
	private OrderRepo orderRepo;

	@Autowired
	private VisitDomainService visitDomainService;

	@Autowired
	private OrderResolver orderResolver;

	@Autowired
	private ApplicationContext applicationContext;

	private int resolveOrderCount;

	/**
	 * 创建医嘱条目
	 * 
	 * @param doctor
	 * @param order
	 * @throws OrderExecuteException
	 * @throws OrderException
	 * @roseuid 584E526102FB
	 */
	public List<Order> create(OrderCreateCommand orderCommand, Doctor doctor)
			throws OrderException, OrderExecuteException {

		if (orderCommand.getVisit() == null) {
			throw new OrderException(null, "没有患者一次就诊信息");
		}
		// 更新为数据库最新的患者一次就诊信息
		Visit visit = visitDomainService.find(orderCommand.getVisit().getId());
		// 状态校验
		if (visit.getState().equals(Visit.State_OutHospital)
				|| visit.getState().equals(Visit.State_LeaveHospital)
				|| visit.getState().equals(Visit.State_Archived)
				|| visit.getState().equals(Visit.State_IntoRecordRoom)) {
			throw new OrderException(null, "患者一次就诊[%s]状态为[%s],不能创建医嘱", visit.getName(),
					visit.getState());
		}

		orderCommand.setVisit(visit);

		for (Order order : orderCommand.getOrders()) {
			// 计算创建时间
			if (order.getCreateDate() == null) {
				order.setCreateDate(DateUtil.getSysDate());
			}
			// 设置创建者
			order.setCreator(doctor);
			// 根据患者所在部门计算医嘱所属部门
			if (order.getBelongDept() == null) {
				order.setBelongDept(orderCommand.getVisit().getDept());
			}
			// 根据患者状态计算医嘱开立位置
			if (order.getPlaceType() == null) {
				String visitState = order.getVisit().getState();
				if (visitState.equals(Visit.State_Diagnosing)
						|| visitState.equals(Visit.State_Diagnosed_Executing)) {
					order.setPlaceType(Order.PlaceType_OutPatient);
				} else if (visitState.equals(Visit.State_Surgerying)) {
					order.setPlaceType(Order.PlaceType_Surgery);
				} else {
					order.setPlaceType(Order.PlaceType_InPatient);
				}
			}
			// 根据医嘱placeType计算状态
			if (order.getState() == null) {
				if (order.isInPatient()) {
					order.setState(Order.State_Created);
				} else {
					order.setState(Order.State_Executing);
				}
			}
		}
		// 计算创建时间
		if (orderCommand.getCreateDate() == null) {
			orderCommand.setCreateDate(DateUtil.getSysDate());
		}
		// 设置创建者
		orderCommand.setCreator(doctor);
		// 检查
		orderCommand.check();
		// 保存医嘱
		orderCommand.save();
		// 发送事件
		applicationContext.publishEvent(new OrderCreatedEvent(orderCommand));

		// 对于执行中的医嘱自动分解
		for (Order order : orderCommand.getOrders()) {
			if (order.getState().equals(Order.State_Executing)) {
				order.resolve(doctor);
			}
		}

		// 创建操作回调
		for (Order order : orderCommand.getOrders()) {
			order.doCreate();
		}

		// 写日志
		List<String> orderIds = new ArrayList<String>();
		List<String> orderTypes = new ArrayList<String>();
		for (Order order : orderCommand.getOrders()) {
			orderIds.add(order.getId());
			orderTypes.add(order.getOrderType().getId());
		}

		LogUtil.log(this.getClass(), "医生[{}]给患者一次就诊[{}]创建医嘱条目{},医嘱类型为{}", doctor.getId(),
				orderCommand.getVisit().getName(), orderIds, orderTypes);

		return orderCommand.getOrders();
	}

	/**
	 * 合并医嘱
	 * 
	 * @param compsiteOrder
	 * @param doctor
	 * @throws OrderException
	 */
	public void comsite(CompsiteOrder compsiteOrder, Doctor doctor) throws OrderException {

		if (compsiteOrder.getCreateDate() == null) {
			compsiteOrder.setCreateDate(DateUtil.getSysDate());
		}
		if (compsiteOrder.getCreator() == null) {
			compsiteOrder.setCreator(doctor);
		}

		compsiteOrder.checkSelf();

		compsiteOrder.save();

		List<String> orderIds = new ArrayList<String>();
		for (Order order : compsiteOrder.getOrders()) {
			orderIds.add(order.getId());
		}

		LogUtil.log(this.getClass(), "医生[{}]将患者一次就诊[{}]的医嘱条目合并为一条组合医嘱[{}]", doctor.getId(),
				compsiteOrder.getVisit().getName(), orderIds, compsiteOrder.getId());
	}

	/**
	 * 核对医嘱条目
	 * 
	 * @param nurse
	 * @param order
	 * @throws OrderExecuteException
	 * @throws HsException
	 * @roseuid 584F489E03D2
	 */
	public Order verify(Order order, AbstractUser nurse)
			throws OrderException, OrderExecuteException {

		order.verify(nurse);

		applicationContext.publishEvent(new OrderVerifyedEvent(order));

		LogUtil.log(this.getClass(), "护士[{}]核对患者一次就诊[[{}]的医嘱条目[{}],类型为[{}]", nurse.getId(),
				order.getVisit().getName(), order.getId(), order.getOrderType().getId());

		return order;
	}

	/**
	 * 分解全部待分解的医嘱条目
	 * 
	 * @roseuid 584F49010391
	 */
	public int resolve(Admin admin) {
		// 获得执行中的长嘱
		Pageable pageable = new PageRequest(0, Integer.MAX_VALUE);
		List<LongOrder> longOrders = orderRepo.findLongOrder(Order.State_Executing, pageable);

		resolveOrderCount = 0;
		// 采用并行计算处理医嘱分解
		longOrders.parallelStream().forEach(longOrder -> {
			resolveOrderCount += orderResolver.resolve(longOrder.getId(), admin);
		});

		return resolveOrderCount;
	}

	/**
	 * 作废医嘱条目
	 * 
	 * @param doctor
	 * @param orderId
	 * @throws OrderException
	 * @roseuid 5850AE8E022C
	 */
	public void cancel(Order order, Doctor doctor) throws OrderException {
		try {
			order.cancel(doctor);
		} catch (OrderExecuteException e) {
			throw new OrderException(order, e);
		}

		applicationContext.publishEvent(new OrderCanceledEvent(order));

		LogUtil.log(this.getClass(), "医生[{}]作废了患者一次就诊[[{}]的医嘱条目{},类型为[{}]", doctor.getId(),
				order.getVisit().getName(), order.getId(), order.getOrderType().getId());

	}

	/**
	 * 停止一个长期医嘱
	 * 
	 * @param order
	 * @param doctor
	 * @throws OrderException
	 */
	public void stop(LongOrder order, Doctor doctor) throws OrderException {
		try {
			order.stop(doctor);
		} catch (OrderExecuteException e) {
			throw new OrderException(order, e);
		}

		applicationContext.publishEvent(new OrderStopedEvent(order));

		LogUtil.log(this.getClass(), "医生[{}]停止了患者一次就诊[[{}]的医嘱条目{},类型为[{}]", doctor.getId(),
				order.getVisit().getName(), order.getId(), order.getOrderType().getId());
	}

	/**
	 * 删除医嘱条目
	 * 
	 * @param orderId
	 * @param doctor
	 * @throws OrderException
	 */
	public void delete(Order order, Doctor doctor) throws OrderException {

		order.delete();

		applicationContext.publishEvent(new OrderDeletedEvent(order));

		LogUtil.log(this.getClass(), "医生[{}]删除了核对患者一次就诊[[{}]的医嘱条目{},类型为[{}]", doctor.getId(),
				order.getVisit().getName(), order.getId(), order.getOrderType().getId());
	}

	/**
	 * @roseuid 585250700266
	 */
	public Order find(String orderId) {
		return orderRepo.findOne(orderId);
	}

	/**
	 * @roseuid 585250700266
	 */
	public Iterator<Order> find(List<String> orderIds) {
		return orderRepo.findAll(orderIds).iterator();
	}

	public List<Order> find(Visit visit, Pageable pageable) {
		return orderRepo.findByVisit(visit, pageable);
	}

	public List<Order> find(Visit visit, String state, Pageable pageable) {
		return orderRepo.findByVisitAndState(visit, state, pageable);
	}

	public List<LongOrder> findLong(Visit visit, String state, Pageable pageable) {
		return orderRepo.findLongOrder(visit, state, pageable);
	}

	public List<Order> findByBelongDept(Dept dept, Pageable pageable) {
		return orderRepo.findByBelongDept(dept, pageable);
	}

	public List<Order> findByBelongDepts(List<Dept> depts, Pageable pageable) {
		return orderRepo.findByBelongDeptIn(depts, pageable);
	}

	/**
	 * 得到指定用户可核对的医嘱列表
	 * 
	 * @param user
	 * @param pageable
	 * @return
	 */
	public List<Order> getNeedVerifyOrders(AbstractUser user, Pageable pageable) {
		return orderRepo.findByStateAndBelongDeptIn(Order.State_Created, user.getOperationDepts(),
				pageable);
	}

}
