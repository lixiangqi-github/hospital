package com.neusoft.hs.engine.order;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neusoft.hs.domain.order.CompsiteOrder;
import com.neusoft.hs.domain.order.DrugOrderType;
import com.neusoft.hs.domain.order.DrugOrderTypeApp;
import com.neusoft.hs.domain.order.LongOrder;
import com.neusoft.hs.domain.order.Order;
import com.neusoft.hs.domain.order.OrderAdminDomainService;
import com.neusoft.hs.domain.order.OrderCreateCommand;
import com.neusoft.hs.domain.order.OrderDomainService;
import com.neusoft.hs.domain.order.OrderException;
import com.neusoft.hs.domain.order.OrderExecuteException;
import com.neusoft.hs.domain.order.OrderFrequencyType;
import com.neusoft.hs.domain.order.OrderType;
import com.neusoft.hs.domain.order.TemporaryOrder;
import com.neusoft.hs.domain.organization.Dept;
import com.neusoft.hs.domain.organization.Doctor;
import com.neusoft.hs.domain.organization.OrganizationAdminDomainService;
import com.neusoft.hs.domain.organization.UserAdminDomainService;
import com.neusoft.hs.domain.pharmacy.DrugUseMode;
import com.neusoft.hs.domain.pharmacy.Pharmacy;
import com.neusoft.hs.domain.pharmacy.PharmacyAdminService;
import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.domain.visit.VisitDomainService;

@Service
@Transactional(rollbackFor = Exception.class)
public class OrderFacadeImpl implements OrderFacade {

	@Autowired
	private OrderDomainService orderDomainService;

	@Autowired
	private UserAdminDomainService userAdminAppService;

	@Autowired
	private OrderAdminDomainService orderAdminDomainService;

	@Autowired
	private OrganizationAdminDomainService organizationAdminDomainService;

	@Autowired
	private VisitDomainService visitDomainService;

	@Autowired
	private PharmacyAdminService pharmacyAdminService;

	@Autowired
	private OrderDTOUtil orderDTOUtil;

	@Override
	public List<OrderDTO> create(CreateOrderDTO createOrderDTO)
			throws OrderDTOException, OrderExecuteDTOException {

		OrderCreateCommand orderCommand;
		if (createOrderDTO.getItems().size() == 1) {
			orderCommand = this.createOrder(createOrderDTO, createOrderDTO
					.getItems().get(0));
		} else {
			CompsiteOrder compsiteOrder = new CompsiteOrder();
			Order order;
			for (CreateOrderItemDTO item : createOrderDTO.getItems()) {
				order = this.createOrder(createOrderDTO, item);
				try {
					compsiteOrder.addOrder(order);
				} catch (OrderException e) {
					e.printStackTrace();
					if (e.getOrder() != null) {
						throw new OrderDTOException(e.getOrder().getId(),
								e.getMessage());
					} else {
						throw new OrderDTOException(null, e.getMessage());
					}
				}
			}

			orderCommand = compsiteOrder;
		}

		Doctor doctor = userAdminAppService.findDoctor(createOrderDTO
				.getCreator());
		if (doctor == null) {
			throw new OrderDTOException(null, "创建者医生[%s]不存在",
					createOrderDTO.getCreator());
		}

		try {
			List<Order> orders = orderDomainService
					.create(orderCommand, doctor);

			List<OrderDTO> orderDTOs = new ArrayList<OrderDTO>();
			for (Order order : orders) {
				orderDTOs.add(orderDTOUtil.convert(order));
			}

			return orderDTOs;

		} catch (OrderException e) {
			e.printStackTrace();
			if (e.getOrder() != null) {
				throw new OrderDTOException(e.getOrder().getId(),
						e.getMessage());
			} else {
				throw new OrderDTOException(null, e.getMessage());
			}
		} catch (OrderExecuteException e) {
			e.printStackTrace();
			if (e.getExecute() != null) {
				throw new OrderDTOException(e.getExecute().getId(),
						e.getMessage());
			} else {
				throw new OrderDTOException(null, e.getMessage());
			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			throw new OrderDTOException(e);
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			throw new OrderDTOException(e);
		}
	}

	@Override
	public OrderDTO find(String id) throws OrderDTOException {
		Order order = this.orderDomainService.find(id);
		if (order != null) {
			try {
				return orderDTOUtil.convert(order);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
				throw new OrderDTOException(e);
			} catch (InvocationTargetException e) {
				e.printStackTrace();
				throw new OrderDTOException(e);
			}
		} else {
			return null;
		}
	}

	private Order createOrder(CreateOrderDTO createOrderDTO,
			CreateOrderItemDTO item) throws OrderDTOException {
		Order order;
		if (createOrderDTO.isLong()) {
			LongOrder longOrder = new LongOrder();

			OrderFrequencyType frequencyType = orderAdminDomainService
					.findFrequencyType(createOrderDTO.getFrequencyTypeId());
			if (frequencyType == null) {
				throw new OrderDTOException(null, "医嘱频次[%s]不存在",
						createOrderDTO.getFrequencyTypeId());
			}

			longOrder.setFrequencyType(frequencyType);
			longOrder.setPlanEndDate(createOrderDTO.getPlanEndDate());

			order = longOrder;
		} else {
			TemporaryOrder temporaryOrder = new TemporaryOrder();
			order = temporaryOrder;
		}
		order.setState(createOrderDTO.getState());
		order.setExecuteNeedSend(createOrderDTO.getExecuteNeedSend());

		order.setPlaceType(createOrderDTO.getPlaceType());
		order.setPlanStartDate(createOrderDTO.getPlanStartDate());

		Visit visit;
		if (createOrderDTO.getVisitId() != null) {
			visit = visitDomainService.find(createOrderDTO.getVisitId());
			if (visit == null) {
				throw new OrderDTOException(null, "患者一次就诊Id[%s]不存在",
						createOrderDTO.getVisitId());
			}
		} else {
			visit = visitDomainService.findLastVisit(createOrderDTO
					.getVisitCardNumber());
			if (visit == null) {
				throw new OrderDTOException(null, "患者一次就诊cardNumber[%s]不存在",
						createOrderDTO.getVisitCardNumber());
			}
		}
		order.setVisit(visit);

		if (createOrderDTO.getExecuteDeptId() != null) {
			Dept executeDept = organizationAdminDomainService
					.findTheDept(createOrderDTO.getExecuteDeptId());
			if (executeDept == null) {
				throw new OrderDTOException(null, "执行部门[%s]不存在",
						createOrderDTO.getExecuteDeptId());
			}
			order.setExecuteDept(executeDept);
		}

		order.setCount(item.getCount());

		String orderTypeId = item.getOrderTypeId();
		OrderType orderType = orderAdminDomainService
				.findOrderType(orderTypeId);
		if (orderType == null) {
			throw new OrderDTOException(null, "医嘱类型[%s]不存在",
					item.getOrderTypeId());
		}
		order.setOrderType(orderType);
		order.setName(orderType.getName());

		if (orderType instanceof DrugOrderType) {
			Pharmacy pharmacy = (Pharmacy) order.getExecuteDept();
			if (pharmacy == null) {
				throw new OrderDTOException(null, "没有指定执行科室（药房）");
			}

			DrugUseMode drugUseMode = pharmacyAdminService
					.findDrugUseMode(createOrderDTO.getDrugUseModeId());
			if (drugUseMode == null) {
				throw new OrderDTOException(null, "药品用法[%s]不存在",
						createOrderDTO.getDrugUseModeId());
			}

			order.setTypeApp(new DrugOrderTypeApp(pharmacy, drugUseMode));
		}

		return order;
	}
}
