package com.neusoft.hs.engine.cost;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neusoft.hs.domain.cost.ChargeRecord;
import com.neusoft.hs.domain.cost.CostDomainService;
import com.neusoft.hs.domain.order.OrderExecuteDomainService;
import com.neusoft.hs.domain.organization.OrganizationAdminDomainService;

@Service
public class ChargeRecordDTOUtil {

	@Autowired
	private CostDomainService costDomainService;

	@Autowired
	private OrderExecuteDomainService orderExecuteDomainService;

	@Autowired
	private OrganizationAdminDomainService orgAdminDomainService;

	public ChargeRecord convert(CreateChargeRecordDTO createChargeRecordDTO)
			throws IllegalAccessException, InvocationTargetException {

		ChargeRecord record = new ChargeRecord();

		BeanUtils.copyProperties(record, createChargeRecordDTO);

		if (createChargeRecordDTO.getOrderExecuteId() != null) {
			record.setOrderExecute(
					orderExecuteDomainService.find(createChargeRecordDTO.getOrderExecuteId()));
		}
		record.setChargeItem(
				costDomainService.findTheChargeItem(createChargeRecordDTO.getChargeItemId()));
		record.setChargeDept(
				orgAdminDomainService.findTheDept(createChargeRecordDTO.getChargeDeptId()));
		record.setBelongDept(
				orgAdminDomainService.findTheDept(createChargeRecordDTO.getBelongDeptId()));

		return record;

	}

}
