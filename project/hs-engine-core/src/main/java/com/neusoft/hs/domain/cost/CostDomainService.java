//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\cost\\CostDomainService.java

package com.neusoft.hs.domain.cost;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neusoft.hs.domain.order.OrderExecute;
import com.neusoft.hs.domain.order.OrderExecuteDomainService;
import com.neusoft.hs.domain.order.OrderExecuteException;
import com.neusoft.hs.domain.organization.AbstractUser;
import com.neusoft.hs.domain.organization.Admin;
import com.neusoft.hs.domain.organization.Dept;
import com.neusoft.hs.domain.organization.Nurse;
import com.neusoft.hs.domain.organization.Staff;
import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.domain.visit.VisitDomainService;
import com.neusoft.hs.platform.entity.EntityUtil;
import com.neusoft.hs.platform.exception.HsException;
import com.neusoft.hs.platform.log.LogUtil;
import com.neusoft.hs.platform.util.DateUtil;

@Service
@Transactional(rollbackFor = Exception.class)
public class CostDomainService {

	@Autowired
	private ChargeBillRepo chargeBillRepo;

	@Autowired
	private ChargeRecordRepo chargeRecordRepo;

	@Autowired
	private CostRecordRepo costRecordRepo;

	@Autowired
	private VisitChargeItemRepo visitChargeItemRepo;

	@Autowired
	private VisitDomainService visitDomainService;

	@Autowired
	private OrderExecuteDomainService orderExecuteDomainService;

	@Autowired
	private ApplicationContext applicationContext;

	/**
	 * 创建财务账户
	 * 
	 * @throws HsException
	 * @roseuid 584DFD470092
	 */
	public ChargeBill createChargeBill(Visit visit, float balance, AbstractUser user) {
		ChargeBill chargeBill = visit.initAccount(balance, user);

		applicationContext.publishEvent(new ChargeBillCreatedEvent(chargeBill));

		return chargeBill;
	}

	/**
	 * 预存费用
	 * 
	 * @param visit
	 * @param balance
	 * @param user
	 * @return
	 * @throws HsException
	 */
	public ChargeRecord addCost(Visit visit, float balance, AbstractUser user) throws HsException {

		if (!visit.isInitedAccount()) {
			throw new HsException("visitName=[%s]未初始化账户", visit.getName());
		}

		ChargeRecord chargeRecord = new ChargeRecord();
		chargeRecord.setAmount(balance);
		chargeRecord.setCreateDate(DateUtil.getSysDate());
		chargeRecord.setHaveCost(false);
		chargeRecord.setChargeDept(user.getDept());
		chargeRecord.setType(ChargeRecord.Type_PreCharge);

		ChargeBill chargeBill = visit.getChargeBill();
		chargeBill.addChargeRecord(chargeRecord);
		if (chargeBill.getChargeMode().equals(ChargeBill.ChargeMode_NoPreCharge)) {
			chargeBill.setChargeMode(ChargeBill.ChargeMode_PreCharge);
		}
		chargeBill.setBalance(chargeBill.getBalance() + balance);

		applicationContext.publishEvent(new ChargeBillAddCostEvent(chargeRecord));

		LogUtil.log(this.getClass(), "用户[{}]给账户[{}]续费{}", user.getId(), chargeBill.getId(),
				balance);

		return chargeRecord;
	}

	/**
	 * 创建自动收费项目
	 * 
	 * @param visit
	 * @roseuid 584E2630028C
	 */
	public void createVisitChargeItem(Visit visit, ChargeItem item, Date startDate) {

		VisitChargeItem visitChargeItem = new VisitChargeItem();

		visitChargeItem.setChargeItem(item);
		visitChargeItem.setVisit(visit);
		visitChargeItem.setState(VisitChargeItem.State_Normal);
		visitChargeItem.setStartDate(startDate);

		visitChargeItem.save();

		applicationContext.publishEvent(new VisitChargeItemCreatedEvent(visitChargeItem));

		LogUtil.log(this.getClass(), "系统给患者一次就诊[{}]增加收费项目{}", visit.getName(), item.getId());
	}

	/**
	 * 根据医嘱执行条目创建费用条目
	 * 
	 * @param execute
	 * @throws CostException
	 * @roseuid 584FBC02036D
	 */
	public ExecuteResultVO charging(OrderExecute execute) throws CostException {

		ExecuteResultVO result = new ExecuteResultVO();
		result.setExecuteId(execute.getId());
		// 生成收费项目
		List<ChargeRecord> chargeRecords = execute.createChargeRecords();
		if (chargeRecords.size() > 0) {
			Date sysDate = DateUtil.getSysDate();
			// 处理费用
			if (!execute.getChargeState().equals(OrderExecute.ChargeState_Charge)) {

				Float amount = 0F;
				// 设置数据
				for (ChargeRecord chargeRecord : chargeRecords) {
					chargeRecord.setOrderExecute(execute);
					chargeRecord.setCreateDate(sysDate);
					chargeRecord.setChargeDept(execute.getChargeDept());
					chargeRecord.setBelongDept(execute.getBelongDept());
				}
				// 生成费用记录
				amount = this.charging(execute.getVisit(), chargeRecords);
				// 修改执行条目状态
				if (execute.getChargeState().equals(OrderExecute.ChargeState_NoCharge)) {
					execute.setChargeState(OrderExecute.ChargeState_Charge);
				}

				result.setCharge(amount);
				LogUtil.log(this.getClass(), "系统:医嘱执行条目[{}]产生费用{}", execute.getId(), amount);
			}
			// 记录成本
			if (execute.getCostState().equals(OrderExecute.CostState_NoCost)) {

				Float amount = 0F;

				List<CostRecord> costRecords = new ArrayList<CostRecord>();
				CostRecord costRecord;
				for (ChargeRecord chargeRecord : chargeRecords) {
					costRecord = chargeRecord.createCostRecord();
					if (costRecord != null) {
						costRecords.add(costRecord);
					}
					amount += costRecord.getCost();
				}
				if (costRecords.size() > 0) {
					costRecordRepo.save(costRecords);
					execute.setCostState(OrderExecute.CostState_Cost);
				}

				result.setCost(amount);
				LogUtil.log(this.getClass(), "系统:医嘱执行条目[{}]产生成本{}", execute.getId(), amount);
			}

		}

		return result;
	}

	/**
	 * 取消医嘱执行条目对应的费用条目
	 * 
	 * @param user
	 * @param execute
	 * @throws OrderExecuteException
	 * @roseuid 5850BC9B0098
	 */
	public void unCharging(OrderExecute execute, boolean isBackCost, Nurse nurse)
			throws OrderExecuteException {

		List<ChargeRecord> chargeRecords = execute.getChargeRecords();

		Float amount = this.unCharging(chargeRecords, nurse);

		execute.setChargeState(OrderExecute.ChargeState_BackCharge);

		if (isBackCost) {
			CostRecord costRecord;
			execute.setCostState(OrderExecute.CostState_NoCost);
			for (ChargeRecord chargeRecord : chargeRecords) {
				costRecord = chargeRecord.getCostRecord();
				if (costRecord != null) {
					costRecord.setState(CostRecord.State_Back);
				}
			}
		}

		LogUtil.log(this.getClass(), "护士[{}]将医嘱执行条目[{}]产生的费用{}撤回", nurse.getId(), execute.getId(),
				amount);
	}

	/**
	 * 退一条收费条目
	 * 
	 * @param record
	 * @param isBackCost
	 * @param user
	 */
	public void retreat(ChargeRecord record, boolean isBackCost, AbstractUser user) {
		List<ChargeRecord> chargeRecords = new ArrayList<ChargeRecord>();
		chargeRecords.add(record);

		Float amount = this.unCharging(chargeRecords, user);

		OrderExecute orderExecute = record.getOrderExecute();
		if (orderExecute != null) {
			orderExecute.setChargeState(OrderExecute.ChargeState_BackCharge);

			if (isBackCost) {
				CostRecord costRecord;
				orderExecute.setCostState(OrderExecute.CostState_NoCost);
				for (ChargeRecord chargeRecord : chargeRecords) {
					costRecord = chargeRecord.getCostRecord();
					if (costRecord != null) {
						costRecord.setState(CostRecord.State_Back);
					}
				}
			}
			LogUtil.log(this.getClass(), "护士[{}]将收费条目[{}]撤回，金额为[%s]，并修改对应的执行条目[%s]为已退费",
					user.getId(), record.getId(), amount, orderExecute.getId());
		}
	}

	/**
	 * 创建费用记录
	 * 
	 * @param visit
	 * @param chargeRecords
	 * @return
	 * @throws CostException
	 */
	public Float charging(Visit visit, List<ChargeRecord> chargeRecords) throws CostException {

		Float amount = visit.getChargeBill().charging(chargeRecords);

		applicationContext.publishEvent(new ChargeRecordCreatedEvent(chargeRecords));

		LogUtil.log(this.getClass(), "患者一次就诊[{}]生成产生费用{}", visit.getName(), amount);

		return amount;
	}

	/**
	 * 撤销收费条目
	 * 
	 * @param chargeRecords
	 * @param user
	 */
	public Float unCharging(List<ChargeRecord> chargeRecords, AbstractUser user) {
		if (chargeRecords.size() > 0) {
			Float amount = chargeRecords.get(0).getChargeBill().unCharging(chargeRecords);

			applicationContext.publishEvent(new ChargeRecordCanceledEvent(chargeRecords));

			LogUtil.log(this.getClass(), "用户[{}]将收费条目[{}]撤回，金额为[%s]", user.getId(),
					EntityUtil.listId(chargeRecords), amount);

			return amount;
		} else {
			return 0F;
		}
	}

	/**
	 * 计算滚动费用
	 * 
	 * @throws CostException
	 */
	public int calculate(Admin admin) {
		List<VisitChargeItem> visitChargeItems = visitChargeItemRepo
				.findByState(VisitChargeItem.State_Normal);
		int count = 0;
		if (visitChargeItems != null) {
			for (VisitChargeItem visitChargeItem : visitChargeItems) {
				try {
					this.charging(visitChargeItem.getVisit(),
							visitChargeItem.createChargeRecords());
					count++;
				} catch (CostException e) {
					e.printStackTrace();
					LogUtil.error(CostDomainService.class, e.getMessage());
				}

			}
		}

		LogUtil.log(this.getClass(), "系统自动计算了[{}]张床位费", count);

		return count;
	}

	/**
	 * 得到需初始化账户的患者一次就诊集合
	 * 
	 * @param pageable
	 * @return
	 */
	public List<Visit> getNeedInitAccount(Pageable pageable) {
		return visitDomainService.findByState(Visit.State_NeedInitAccount, pageable);
	}

	public List<ChargeRecord> getChargeRecords(Visit visit, List<Dept> depts, Pageable pageable) {
		return chargeRecordRepo.findByVisitAndBelongDeptIn(visit, depts, pageable);
	}

	public List<ChargeRecord> getChargeRecords(Visit visit, Pageable pageable) {
		return chargeRecordRepo.findByVisit(visit, pageable);
	}

	public ChargeBill getChargeBill(Visit visit) {
		return chargeBillRepo.findByVisit(visit);
	}

	public ChargeRecord findChargeRecord(String recordId) {
		return chargeRecordRepo.findOne(recordId);
	}

	/**
	 * 得到需要退费的执行条目列表
	 * 
	 * @param user
	 * @param pageable
	 * @return
	 */
	public List<OrderExecute> getNeedBackChargeOrderExecutes(Staff user, Pageable pageable) {
		return orderExecuteDomainService.findNeedBackChargeOrderExecutes(user, pageable);
	}
}
