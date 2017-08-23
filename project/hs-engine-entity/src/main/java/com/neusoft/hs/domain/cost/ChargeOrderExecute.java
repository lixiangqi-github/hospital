package com.neusoft.hs.domain.cost;

import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.neusoft.hs.domain.order.OrderExecute;

/**
 * 收费执行条目
 * 
 * @author kingbox
 *
 */
@Entity
@DiscriminatorValue("Charge")
public class ChargeOrderExecute extends OrderExecute {

	@Override
	public List<ChargeRecord> createChargeRecords() {
		List<ChargeRecord> chargeRecords = super.createChargeRecords();
		for(ChargeRecord chargeRecord : chargeRecords){
			//将费用改变成整数，表示预存
			chargeRecord.setAmount(-chargeRecord.getAmount());
		}
		return chargeRecords;
	}
}
