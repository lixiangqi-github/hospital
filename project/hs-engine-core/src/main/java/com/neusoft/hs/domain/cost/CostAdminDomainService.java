package com.neusoft.hs.domain.cost;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class CostAdminDomainService {

	@Autowired
	private ChargeItemRepo chargeItemRepo;

	@Autowired
	private CostRecordRepo costRecordRepo;
	
	@Autowired
	private ChargeRecordRepo chargeRecordRepo;

	@Autowired
	private ChargeBillRepo chargeBillRepo;
	
	@Autowired
	private VisitChargeItemRepo visitChargeItemRepo;

	public void create(List<ChargeItem> chargeItems) {
		chargeItemRepo.save(chargeItems);
	}

	public ChargeItem find(String id) {
		return chargeItemRepo.findOne(id);
	}

	public void clearChargeItems() {
		chargeItemRepo.deleteAll();
	}

	public void clearVisitChargeItems() {
		visitChargeItemRepo.deleteAll();
	}
	
	public void clearChargeRecords() {
		chargeRecordRepo.deleteAll();
	}
	
	public void clearCostRecords() {
		costRecordRepo.deleteAll();
	}

	public void clearChargeBill() {
		chargeBillRepo.deleteAll();
	}

	public void delete(String id) {
		ChargeItem chargeItem = chargeItemRepo.findOne(id);
		if(chargeItem != null){
			chargeItem.delete();
		}
	}

}
