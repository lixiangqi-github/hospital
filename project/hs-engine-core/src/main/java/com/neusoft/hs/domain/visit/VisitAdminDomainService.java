package com.neusoft.hs.domain.visit;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neusoft.hs.domain.organization.Admin;
import com.neusoft.hs.platform.log.LogUtil;
import com.neusoft.hs.platform.util.DateUtil;

@Service
@Transactional(rollbackFor = Exception.class)
public class VisitAdminDomainService {

	@Autowired
	private VisitRepo visitRepo;

	@Autowired
	private VisitLogRepo visitLogRepo;

	@Autowired
	private ApplicationContext applicationContext;

	public void clear() {
		visitRepo.deleteAll();
	}

	/**
	 * @param visitId
	 * @roseuid 584E03140020
	 */
	public Visit find(String visitId) {
		return visitRepo.findOne(visitId);
	}

	public List<Visit> findByCardNumber(String cardNumber) {
		return this.visitRepo.findByCardNumber(cardNumber);
	}

	public List<Visit> find(Pageable pageable) {
		return visitRepo.findAll(pageable).getContent();
	}

	public List<VisitLog> getVisitLogs(Visit visit, Pageable pageable) {
		return visitLogRepo.findByVisit(visit, pageable);
	}

	public void delete(String visitId) {
		Visit visit = this.visitRepo.findOne(visitId);
		if (visit != null) {

			applicationContext.publishEvent(new VisitRemoveBeforeEvent(visit));

			visit.delete();

			applicationContext.publishEvent(new VisitRemovedEvent(visit));

			LogUtil.log(this.getClass(), "患者一次就诊[{}][{}]被删除", visit.getId(), visit.getName());
		}
	}

	/**
	 * 自动切换患者一次就诊状态
	 * 
	 * @return
	 */
	public int changeVisitState(Admin admin) {
		Date changeDate = DateUtil.reduceHour(DateUtil.getSysDate(), 10);
		List<Visit> visits = visitRepo
				.findByStateAndVoucherDateLessThan(Visit.State_Diagnosed_Executing, changeDate);
		for (Visit visit : visits) {
			visit.setState(Visit.State_LeaveHospital);
			visit.save();

			VisitLog visitLog = new VisitLog();
			visitLog.setVisit(visit);
			visitLog.setType(VisitLog.Type_LeaveHospital);
			visitLog.setOperator(admin);
			visitLog.setCreateDate(DateUtil.getSysDate());

			visitLog.save();

		}

		LogUtil.log(this.getClass(), "系统修改患者状态由[{}]到[{}]{}个", Visit.State_Diagnosed_Executing,
				Visit.State_LeaveHospital, visits.size());

		return visits.size();
	}
}
