package com.neusoft.hs.domain.cost;

import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.neusoft.hs.domain.order.OrderExecute;
import com.neusoft.hs.domain.order.OrderExecuteChargeItemRecord;

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
		for (ChargeRecord chargeRecord : chargeRecords) {
			// 将费用改变成整数，表示预存
			chargeRecord.setAmount(-chargeRecord.getAmount());
		}
		return chargeRecords;
	}

	@Override
	protected void calTip() {
		StringBuilder builder = new StringBuilder();
		Float amount = 0F;
		for (OrderExecuteChargeItemRecord record : this.getChargeItemRecords()) {

			int count = record.getCount() == null ? 1 : record.getCount();

			builder.append(record.getChargeItem().getName());
			builder.append("(");
			builder.append(record.getChargeItem().getPrice());
			builder.append(")");
			builder.append(":");
			builder.append(count);
			builder.append(";");

			amount += record.getChargeItem().getPrice() * count;
		}

		builder.append("合计:");
		builder.append(amount);

		this.setTip(builder.toString());
	}

}
