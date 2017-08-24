package com.neusoft.hs.domain.order;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("Describe")
public class DescribeOrderExecute extends OrderExecute {

	@Override
	protected void calTip() {
		this.setTip(this.getOrder().getDescribe());
	}

}
