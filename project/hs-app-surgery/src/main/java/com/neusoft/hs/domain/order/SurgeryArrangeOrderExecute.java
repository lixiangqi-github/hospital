package com.neusoft.hs.domain.order;

import java.util.Map;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.neusoft.hs.domain.organization.AbstractUser;
import com.neusoft.hs.platform.util.DateUtil;

@Entity
@DiscriminatorValue("SurgeryArrange")
public class SurgeryArrangeOrderExecute extends OrderExecute {

	@Override
	protected void calTip() {

		StringBuilder builder = new StringBuilder();

		builder.append(this.getOrder().getDescribe());
		builder.append(" 申请手术时间：");
		builder.append(DateUtil.toString(this.getOrder().getPlanStartDate()));

		this.setTip(builder.toString());
	}

	@Override
	protected void doFinish(Map<String, Object> params, AbstractUser user)
			throws OrderExecuteException {
		// TODO Auto-generated method stub
		super.doFinish(params, user);
	}
}
