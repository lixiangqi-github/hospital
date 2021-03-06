package com.neusoft.hs.domain.order;

import java.util.Map;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.neusoft.hs.domain.organization.AbstractUser;
import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.domain.visit.VisitDomainService;
import com.neusoft.hs.domain.visit.VisitException;

@Entity
@DiscriminatorValue("OutHospitalBalance")
public class OutHospitalBalanceOrderExecute extends OrderExecute {

	@Override
	protected void doFinish(Map<String, Object> params, AbstractUser user) throws OrderExecuteException {
		super.doFinish(params, user);

		Visit visit = this.getVisit();
		try {
			this.getService(VisitDomainService.class).outHospitalBalance(visit, user);
		} catch (VisitException e) {
			e.printStackTrace();
			throw new OrderExecuteException(this, e);
		}
	}

	@Override
	protected void calTip() {

		StringBuilder builder = new StringBuilder();

		builder.append("当前余额:");
		builder.append(this.getVisit().getChargeBill().getBalance());

		this.setTip(builder.toString());
	}
}
