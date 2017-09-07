package com.neusoft.hs.domain.order;

import java.util.Date;
import java.util.Map;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.neusoft.hs.domain.organization.AbstractUser;
import com.neusoft.hs.domain.surgery.SurgeryApply;
import com.neusoft.hs.domain.visit.VisitDomainService;
import com.neusoft.hs.domain.visit.VisitPlanRecord;
import com.neusoft.hs.platform.util.DateUtil;

@Entity
@DiscriminatorValue("SurgeryArrange")
public class SurgeryArrangeOrderExecute extends OrderExecute {

	public static final String PlanExecuteDate = "PlanExecuteDate";

	public static final String SurgeryPlace = "SurgeryPlace";

	@Override
	protected void calTip() {

		StringBuilder builder = new StringBuilder();

		if (this.getOrder().getDescribe() != null) {
			builder.append(this.getOrder().getDescribe());
		}
		builder.append(" 申请手术时间：");
		builder.append(DateUtil.toString(this.getOrder().getPlanStartDate()));

		this.setTip(builder.toString());
	}

	@Override
	protected void doFinish(Map<String, Object> params, AbstractUser user)
			throws OrderExecuteException {

		if (params == null || !params.containsKey(PlanExecuteDate)) {
			throw new OrderExecuteException(this, "params没有设置Key为[%s]计划检查时间", PlanExecuteDate);

		}
		Date planExecuteDate = (Date) params.get(PlanExecuteDate);

		if (params == null || !params.containsKey(SurgeryPlace)) {
			throw new OrderExecuteException(this, "params没有设置Key为[%s]计划检查地点", SurgeryPlace);
		}
		String surgeryPlace = (String) params.get(SurgeryPlace);

		OrderInteraction orderInteraction = new OrderInteraction();
		orderInteraction.setCreator(user);

		StringBuilder messageBuilder = new StringBuilder();
		messageBuilder.append("手术时间：");
		messageBuilder.append(DateUtil.toString(planExecuteDate));
		messageBuilder.append(" 手术地点：");
		messageBuilder.append(surgeryPlace);
		orderInteraction.setMessage(messageBuilder.toString());

		this.getOrder().addOrderInteraction(orderInteraction);

		SurgeryApply surgeryApply = (SurgeryApply) this.getOrder().getApply();
		surgeryApply.setPlanExecuteDate(planExecuteDate);
		surgeryApply.setSurgeryPlace(surgeryPlace);

		// 创建计划日程
		VisitPlanRecord visitPlanRecord = new VisitPlanRecord();
		visitPlanRecord.setVisit(this.getVisit());
		visitPlanRecord.setType(this.getType());
		visitPlanRecord.setDescribe(orderInteraction.getMessage());
		visitPlanRecord.setPlanExecuteDate(planExecuteDate);
		visitPlanRecord.setOperator(user);
		visitPlanRecord.setDept(user.getDept());

		this.getService(VisitDomainService.class).createVisitPlanRecord(visitPlanRecord);

		// 修改之后的两条执行条目计划执行时间
		OrderExecute next = this.getNext();
		next.setPlanStartDate(planExecuteDate);
		next.setPlanEndDate(planExecuteDate);

		next.save();

		next = next.getNext();
		next.setPlanStartDate(planExecuteDate);
		next.setPlanEndDate(planExecuteDate);

		next.save();

	}
}
