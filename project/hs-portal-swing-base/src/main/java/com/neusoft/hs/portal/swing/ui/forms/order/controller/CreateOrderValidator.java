package com.neusoft.hs.portal.swing.ui.forms.order.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.neusoft.hs.domain.inspect.InspectApply;
import com.neusoft.hs.domain.inspect.InspectApplyItem;
import com.neusoft.hs.domain.order.DescribeOrderType;
import com.neusoft.hs.domain.order.DrugOrderType;
import com.neusoft.hs.domain.order.DrugOrderTypeApp;
import com.neusoft.hs.domain.order.EnterHospitalOrderType;
import com.neusoft.hs.domain.order.InspectOrderType;
import com.neusoft.hs.domain.order.Order;
import com.neusoft.hs.domain.order.OrderType;
import com.neusoft.hs.domain.order.SurgeryOrderType;
import com.neusoft.hs.domain.order.TransferDeptOrderType;
import com.neusoft.hs.domain.organization.AbstractUser;
import com.neusoft.hs.domain.organization.InPatientDept;
import com.neusoft.hs.domain.organization.OrganizationAdminDomainService;
import com.neusoft.hs.domain.pharmacy.DrugUseMode;
import com.neusoft.hs.domain.pharmacy.Pharmacy;
import com.neusoft.hs.domain.surgery.SurgeryApply;
import com.neusoft.hs.portal.swing.validation.ValidationError;
import com.neusoft.hs.portal.swing.validation.ValidationSupport;
import com.neusoft.hs.portal.swing.validation.Validator;

@Component
public class CreateOrderValidator extends ValidationSupport implements Validator<CreateOrderInfo> {

	@Autowired
	private OrganizationAdminDomainService organizationAdminDomainService;

	@Override
	public Optional<ValidationError> validate(CreateOrderInfo createOrderInfo) {

		Order order = createOrderInfo.getOrder();
		DrugUseMode drugUseMode = createOrderInfo.getDrugUseMode();
		OrderType orderType = order.getOrderType();
		AbstractUser user = createOrderInfo.getUser();

		if (orderType == null) {
			return Optional.of(new ValidationError("请选择医嘱类型"));
		} else {

			order.setName(orderType.getName());

			if (orderType instanceof DrugOrderType) {
				if (order.getExecuteDept() != null && order.getExecuteDept() instanceof Pharmacy) {
					Pharmacy pharmacy = (Pharmacy) order.getExecuteDept();
					if (drugUseMode == null) {
						return Optional.of(new ValidationError("请选择药品用法"));
					}
					if (order.getCount() == null) {
						return Optional.of(new ValidationError("请录入数量"));
					}

					order.setTypeApp(new DrugOrderTypeApp(pharmacy, drugUseMode));
				} else {
					return Optional.of(new ValidationError("请选择药房"));
				}
			} else if (orderType instanceof EnterHospitalOrderType) {
				if (order.getExecuteDept() != null
						&& order.getExecuteDept() instanceof InPatientDept) {
					InPatientDept inPatientDept = (InPatientDept) order.getExecuteDept();
					order.addParam(EnterHospitalOrderType.WardDept, inPatientDept);
					order.addParam(EnterHospitalOrderType.RespDoctor, user);
					order.addParam(EnterHospitalOrderType.WardArea,
							organizationAdminDomainService.findInPatientArea(inPatientDept).get(0));
				} else {
					return Optional.of(new ValidationError("请选择住院科室"));
				}
			} else if (orderType instanceof TransferDeptOrderType) {
				if (order.getExecuteDept() == null) {
					return Optional.of(new ValidationError("请选择转科科室"));
				}
			} else if (orderType instanceof InspectOrderType) {
				if (order.getApply() == null) {
					return Optional.of(new ValidationError("请创建检查申请单"));
				}
				if (!(order.getApply() instanceof InspectApply)) {
					return Optional.of(new ValidationError("申请单不是检查申请单"));
				}
				if (order.getExecuteDept() == null) {
					return Optional.of(new ValidationError("请选择执行科室"));
				}
				InspectApply inspectApply = (InspectApply) order.getApply();
				for (InspectApplyItem item : inspectApply.getInspectApplyItems()) {
					item.setArrangeDept(order.getExecuteDept());
					item.setInspectDept(order.getExecuteDept());
				}
			} else if (orderType instanceof DescribeOrderType) {
				if (order.getDescribe() == null) {
					return Optional.of(new ValidationError("请录入描述"));
				}
				if (order.getExecuteDept() == null) {
					return Optional.of(new ValidationError("请选择执行科室"));
				}
			} else if (orderType instanceof SurgeryOrderType) {
				if (order.getApply() == null) {
					return Optional.of(new ValidationError("请创建手术申请单"));
				}
				if (!(order.getApply() instanceof SurgeryApply)) {
					return Optional.of(new ValidationError("申请单不是手术申请单"));
				}
				if (order.getExecuteDept() == null) {
					return Optional.of(new ValidationError("请选择执行科室"));
				}
			}
		}
		return Optional.empty();
	}

}
