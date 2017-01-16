package com.neusoft.hs.domain.order;

import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.neusoft.hs.domain.cost.ChargeRecord;
import com.neusoft.hs.domain.organization.AbstractUser;
import com.neusoft.hs.domain.pharmacy.DrugType;
import com.neusoft.hs.platform.exception.HsException;

@Entity
@DiscriminatorValue("ConfigureFluid")
public class ConfigureFluidOrderExecute extends OrderExecute {

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "drug_type_id")
	private DrugType drugType;

	@Override
	protected void doFinish(AbstractUser user) throws OrderExecuteException {
		try {
			drugType.send(getCount());
		} catch (HsException e) {
			throw new OrderExecuteException(this, e);
		}
	}

	@Override
	protected void doCancel() throws OrderExecuteException {
		try {
			drugType.unSend(getCount());
		} catch (HsException e) {
			throw new OrderExecuteException(this, e);
		}
	}

	public DrugType getDrugType() {
		return drugType;
	}

	public void setDrugType(DrugType drugType) {
		this.drugType = drugType;
	}
}
