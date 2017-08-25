//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\order\\DrugOrderType.java

package com.neusoft.hs.domain.order;

import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.neusoft.hs.domain.pharmacy.DrugTypeSpec;
import com.neusoft.hs.domain.pharmacy.DrugUseMode;
import com.neusoft.hs.domain.pharmacy.PharmacyException;

/**
 * 药品医嘱类型
 * 
 * @author kingbox
 *
 */
@Entity
@DiscriminatorValue("Drug")
public class DrugOrderType extends OrderType {

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "drug_type_spec_id")
	private DrugTypeSpec drugTypeSpec;

	@Override
	public void check(Order order) throws OrderException {
		DrugOrderTypeApp drugOrderTypeApp = (DrugOrderTypeApp) order.getTypeApp();
		// 临嘱预扣
		if (order instanceof TemporaryOrder) {
			try {
				drugOrderTypeApp.withhold(drugTypeSpec, order.getCount());
			} catch (PharmacyException e) {
				throw new OrderException(order, e);
			}
		}

	}

	@Override
	public void delete(Order order) throws OrderException {
		// 解除临嘱预扣
		if (order instanceof TemporaryOrder) {
			DrugOrderTypeApp drugOrderTypeApp = (DrugOrderTypeApp) order.getTypeApp();
			try {
				drugOrderTypeApp.unWithhold();
			} catch (PharmacyException e) {
				throw new OrderException(order, e);
			}
		}
	}

	@Override
	protected List<OrderExecuteTeam> createExecuteTeams(Order order) throws OrderException {
		DrugUseMode drugUseMode = this.getService(DrugOrderTypeAppRepo.class).findOne(order.getTypeApp().getId())
				.getDrugUseMode();

		return drugUseMode.createExecuteTeams(order);
	}

	public DrugTypeSpec getDrugTypeSpec() {
		return drugTypeSpec;
	}

	public void setDrugTypeSpec(DrugTypeSpec drugTypeSpec) {
		this.drugTypeSpec = drugTypeSpec;
	}
}
