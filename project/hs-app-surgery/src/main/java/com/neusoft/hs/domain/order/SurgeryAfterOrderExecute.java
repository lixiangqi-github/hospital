package com.neusoft.hs.domain.order;

import java.util.Map;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.neusoft.hs.domain.organization.AbstractUser;
import com.neusoft.hs.domain.visit.VisitDomainService;
import com.neusoft.hs.domain.visit.VisitException;

@Entity
@DiscriminatorValue("SurgeryAfter")
public class SurgeryAfterOrderExecute extends OrderExecute {

	@Override
	protected void doFinish(Map<String, Object> params, AbstractUser user)
			throws OrderExecuteException {

		try {
			this.getService(VisitDomainService.class).afterSurgery(this.getVisit(), this.getOrder(),
					user);
		} catch (VisitException e) {
			e.printStackTrace();
			throw new OrderExecuteException(this, e);
		}
	}
}
