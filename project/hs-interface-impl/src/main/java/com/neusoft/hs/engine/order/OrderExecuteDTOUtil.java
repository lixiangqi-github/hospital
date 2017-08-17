package com.neusoft.hs.engine.order;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Service;

import com.neusoft.hs.domain.cost.ChargeRecord;
import com.neusoft.hs.domain.order.DrugOrderExecute;
import com.neusoft.hs.domain.order.OrderExecute;
import com.neusoft.hs.domain.order.OrderExecuteChargeItemRecord;

@Service
public class OrderExecuteDTOUtil {

	public OrderExecuteDTO convert(OrderExecute execute) throws IllegalAccessException, InvocationTargetException {
		OrderExecuteDTO executeDTO = new OrderExecuteDTO();
		BeanUtils.copyProperties(executeDTO, execute);

		executeDTO.setVisitId(execute.getVisit().getId());
		executeDTO.setOrderId(execute.getOrder().getId());
		executeDTO.setBelongDeptId(execute.getBelongDept().getId());
		if (execute.getExecuteDept() != null) {
			executeDTO.setExecuteDeptId(execute.getExecuteDept().getId());
		}
		if (execute.getActualExecutor() != null) {
			executeDTO.setActualExecutorId(execute.getActualExecutor().getId());
		}
		if (execute.getChargeDept() != null) {
			executeDTO.setChargeDeptId(execute.getChargeDept().getId());
		}
		if (execute.getCompsiteOrder() != null) {
			executeDTO.setCompsiteOrderId(execute.getCompsiteOrder().getId());
		}
		if (execute.getTeam() != null) {
			executeDTO.setTeamId(execute.getTeam().getId());
		}

		List<OrderExecuteChargeItemRecord> chargeItemRecords = execute.getChargeItemRecords();
		if (chargeItemRecords != null) {
			List<OrderExecuteChargeItemRecordDTO> chargeItemRecordDTOs = new ArrayList<OrderExecuteChargeItemRecordDTO>();
			OrderExecuteChargeItemRecordDTO chargeItemRecordDTO;
			for (OrderExecuteChargeItemRecord chargeItemRecord : chargeItemRecords) {
				chargeItemRecordDTO = new OrderExecuteChargeItemRecordDTO();
				chargeItemRecordDTO.setId(chargeItemRecord.getChargeItem().getId());
				chargeItemRecordDTO.setCode(chargeItemRecord.getChargeItem().getCode());
				chargeItemRecordDTO.setName(chargeItemRecord.getChargeItem().getName());
				chargeItemRecordDTO.setCount(chargeItemRecord.getCount());

				chargeItemRecordDTOs.add(chargeItemRecordDTO);
			}
			executeDTO.setChargeItemRecords(chargeItemRecordDTOs);
		}

		List<ChargeRecord> chargeRecords = execute.getChargeRecords();
		if (chargeRecords != null) {
			for (ChargeRecord chargeRecord : chargeRecords) {
				executeDTO.addChargeRecordId(chargeRecord.getId());
			}
		}
		if (execute instanceof DrugOrderExecute) {
			DrugOrderExecute drugOrderExecute = (DrugOrderExecute) execute;
			executeDTO.setDrugTypeSpecId(drugOrderExecute.getDrugTypeSpec().getId());
			executeDTO.setDrugTypeSpecName(drugOrderExecute.getDrugTypeSpec().getName());
		}

		return executeDTO;

	}

}
