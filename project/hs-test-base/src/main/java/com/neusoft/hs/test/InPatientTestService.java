package com.neusoft.hs.test;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.neusoft.hs.domain.medicalrecord.MedicalRecord;
import com.neusoft.hs.domain.medicalrecord.MedicalRecordClip;
import com.neusoft.hs.domain.order.EnterHospitalIntoWardOrderExecute;
import com.neusoft.hs.domain.order.EnterHospitalSupplyCostOrderExecute;
import com.neusoft.hs.domain.order.NursingOrderBuilder;
import com.neusoft.hs.domain.order.Order;
import com.neusoft.hs.domain.order.OrderCreateCommand;
import com.neusoft.hs.domain.order.OrderExecute;
import com.neusoft.hs.domain.order.TemporaryOrder;
import com.neusoft.hs.domain.surgery.SurgeryApply;
import com.neusoft.hs.domain.surgery.SurgeryApplyItem;
import com.neusoft.hs.domain.treatment.TreatmentItem;
import com.neusoft.hs.domain.visit.CreateVisitVO;
import com.neusoft.hs.domain.visit.ReceiveVisitVO;
import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.platform.exception.HsException;
import com.neusoft.hs.platform.util.DateUtil;

@Service
public abstract class InPatientTestService extends AppTestService {

	protected Visit visit001;

	protected Visit visit004;

	protected Visit visitttt;

	protected List<Visit> visitList = new ArrayList<Visit>();

	protected int dayCount = 0;

	private static final int runCount = 1;// 入院次数

	@Override
	public void init() {
		super.init();

		MedicalRecordTestService.temporaryOrderCount = 3;
	}

	/**
	 * 
	 * @throws HsException
	 */
	@Override
	public void execute() throws HsException {

		for (int count = 0; count < runCount; count++) {

			dayCount = count * 20;

			this.createVisit001();

			this.createVisit004();

			this.doExecute();
		}
	}

	public void doExecute() throws HsException {

		this.intoWard();

		this.treatment();

		this.outWard();

		this.followUp();
	}

	protected abstract void treatment() throws HsException;

	protected void createVisit001() throws HsException {

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-28 09:50", dayCount));
		// 创建测试患者
		CreateVisitVO createVisitVO = new CreateVisitVO();
		createVisitVO.setCardNumber("xxx");
		createVisitVO.setName("测试患者001");
		createVisitVO.setBirthday(DateUtil.createDay("1978-01-27"));
		createVisitVO.setSex("男");
		createVisitVO.setOperator(user002);
		createVisitVO.setDept(dept000);
		createVisitVO.setArea(dept000n);
		createVisitVO.setRespDoctor(user002);
		// 送诊
		visit001 = registerAppService.register(createVisitVO);

		visitList.add(visit001);
	}

	protected void createVisit004() throws HsException {

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-28 09:52", dayCount));
		// 创建测试患者
		CreateVisitVO createVisitVO = new CreateVisitVO();
		createVisitVO.setCardNumber("aaa");
		createVisitVO.setName("测试患者004");
		createVisitVO.setBirthday(DateUtil.createDay("1980-01-22"));
		createVisitVO.setSex("男");
		createVisitVO.setOperator(user002);
		createVisitVO.setDept(dept000);
		createVisitVO.setArea(dept000n);
		createVisitVO.setRespDoctor(user002);
		// 送诊
		visit004 = registerAppService.register(createVisitVO);

		visitList.add(visit004);
	}

	protected void intoWard() throws HsException {

		Pageable pageable;
		List<Visit> visits;
		Visit visit;
		ReceiveVisitVO receiveVisitVO;
		List<OrderExecute> executes;
		Map<String, Object> params;

		pageable = new PageRequest(0, Integer.MAX_VALUE);
		visits = cashierAppService.getNeedInitAccountVisits(pageable);

		assertTrue(visits.size() == 2);
		for (Visit v : visits) {
			assertTrue(visitList.contains(v));
		}

		visit = visitDomainService.find(visit001.getId());

		assertTrue(visit.getState().equals(Visit.State_NeedInitAccount));

		visit = visitDomainService.find(visit004.getId());

		assertTrue(visit.getState().equals(Visit.State_NeedInitAccount));

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-28 10:10", dayCount));

		boolean onlyInpatient = false;// 是否仅为住院场景启动
		// 预存费用
		if (!visit001.isInitedAccount()) {
			cashierAppService.initAccount(visit001.getId(), 2000F, user201);
			onlyInpatient = true;
		} else {
			pageable = new PageRequest(0, Integer.MAX_VALUE);
			executes = orderExecuteAppService.findNeedExecuteOrderExecutes(user201, pageable);

			assertTrue(executes.size() == 1);

			params = new HashMap<String, Object>();
			params.put(EnterHospitalSupplyCostOrderExecute.Balance, 2000F);
			// 完成住院预存住院费执行条目
			orderExecuteAppService.finish(executes.get(0).getId(), params, user201);
			// 采用API预存费用
			// cashierAppService.addCost(visit001.getId(), 2000F, user201);
		}

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-28 10:12", dayCount));

		cashierAppService.initAccount(visit004.getId(), 3000F, user201);

		pageable = new PageRequest(0, Integer.MAX_VALUE);
		visits = inPatientAppService.getNeedReceiveVisits(user001, pageable);

		assertTrue(visits.size() == 2);

		visit = visitDomainService.find(visit001.getId());

		assertTrue(visit.getState().equals(Visit.State_NeedIntoWard));

		visit = visitDomainService.find(visit004.getId());

		assertTrue(visit.getState().equals(Visit.State_NeedIntoWard));

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-28 10:30", dayCount));

		// 接诊
		if (onlyInpatient) {
			receiveVisitVO = new ReceiveVisitVO();
			receiveVisitVO.setVisit(visit001);
			receiveVisitVO.setBed("bed001");
			receiveVisitVO.setNurse(user003);

			inPatientAppService.receive(receiveVisitVO, user001);
		} else {
			pageable = new PageRequest(0, Integer.MAX_VALUE);
			executes = orderExecuteAppService.findNeedExecuteOrderExecutes(user001, pageable);

			assertTrue(executes.size() == 1);

			params = new HashMap<String, Object>();
			params.put(EnterHospitalIntoWardOrderExecute.Bed, "bed001");
			params.put(EnterHospitalIntoWardOrderExecute.RespNurse, user003);
			// 完成住院接诊执行条目
			orderExecuteAppService.finish(executes.get(0).getId(), params, user001);
		}

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-28 10:32", dayCount));

		receiveVisitVO = new ReceiveVisitVO();
		receiveVisitVO.setVisit(visit004);
		receiveVisitVO.setBed("bed004");
		receiveVisitVO.setNurse(user003);

		inPatientAppService.receive(receiveVisitVO, user001);

		pageable = new PageRequest(0, Integer.MAX_VALUE);
		visits = inPatientAppService.InWardVisits(dept000, pageable);

		assertTrue(visits.size() == 2);

		visit = visitDomainService.find(visit001.getId());

		assertTrue(visit.getState().equals(Visit.State_IntoWard));

		visit = visitDomainService.find(visit004.getId());

		assertTrue(visit.getState().equals(Visit.State_IntoWard));
	}

	protected void outWard() throws HsException {

		Pageable pageable;
		List<OrderExecute> executes;
		List<Order> orders;
		Visit visit;

		NursingOrderBuilder nursingOrderBuilder;

		// 2017-01-07
		DateUtil.setSysDate(DateUtil.createDay("2017-01-07", dayCount));
		patientNightTestService.calculate(admin001);

		DateUtil.setSysDate(DateUtil.createMinute("2017-01-07 10:10", dayCount));

		// 开立出院临时医嘱
		Order leaveHospitalOrder001 = new TemporaryOrder();
		leaveHospitalOrder001.setVisit(visit001);
		leaveHospitalOrder001.setName("出院医嘱");
		leaveHospitalOrder001.setPlanStartDate(DateUtil.createDay("2017-01-09", dayCount));
		leaveHospitalOrder001.setPlaceType(OrderCreateCommand.PlaceType_InPatient);

		leaveHospitalOrder001.setOrderType(outHospitalOrderType);

		orderAppService.create(leaveHospitalOrder001, user002);

		pageable = new PageRequest(0, Integer.MAX_VALUE);
		orders = orderAppService.findNeedVerifyOrders(user003, pageable);

		assertTrue(orders.size() == 1);
		assertTrue(orders.get(0).getId().equals(leaveHospitalOrder001.getId()));

		DateUtil.setSysDate(DateUtil.createMinute("2017-01-07 10:30", dayCount));

		// 核对医嘱
		orderAppService.verify(leaveHospitalOrder001.getId(), user003);

		DateUtil.setSysDate(DateUtil.createMinute("2017-01-07 10:40", dayCount));

		pageable = new PageRequest(0, Integer.MAX_VALUE);
		executes = orderExecuteAppService.findNeedExecuteOrderExecutes(user003, pageable);

		assertTrue(executes.size() == 3);

		// 完成医嘱执行条目
		for (OrderExecute execute : executes) {
			if (execute.getVisitName().equals(visit004.getName())
					&& execute.getType().equals(secondNursingOrderType.getName())) {
				orderExecuteAppService.cancel(execute.getId(), user003);
			} else {
				orderExecuteAppService.finish(execute.getId(), null, user003);
			}
		}

		DateUtil.setSysDate(DateUtil.createMinute("2017-01-07 15:00", dayCount));

		pageable = new PageRequest(0, Integer.MAX_VALUE);
		executes = orderExecuteAppService.findNeedExecuteOrderExecutes(user003, pageable);

		assertTrue(executes.size() == 1);

		// 完成术前操作执行条目
		for (OrderExecute execute : executes) {
			orderExecuteAppService.finish(execute.getId(), null, user003);
		}

		visit = visitDomainService.find(visit004.getId());

		assertTrue(visit.getState().equals(Visit.State_Surgerying));
		
		DateUtil.setSysDate(DateUtil.createMinute("2017-01-07 16:40", dayCount));
		
		pageable = new PageRequest(0, Integer.MAX_VALUE);
		executes = orderExecuteAppService.findNeedExecuteOrderExecutes(usere01, pageable);

		assertTrue(executes.size() == 1);
		
		surgeryAppService.addSurgeryApplyItem(executes.get(0).getId(), surgeryType002);

		DateUtil.setSysDate(DateUtil.createMinute("2017-01-07 17:00", dayCount));

		// 完成术后操作执行条目
		for (OrderExecute execute : executes) {
			orderExecuteAppService.finish(execute.getId(), null, usere01);
		}

		visit = visitDomainService.find(visit004.getId());

		assertTrue(visit.getState().equals(Visit.State_IntoWard));

		DateUtil.setSysDate(DateUtil.createMinute("2017-01-07 17:10", dayCount));

		// 为004开立一级护理长期医嘱
		nursingOrderBuilder = new NursingOrderBuilder();
		nursingOrderBuilder.setVisit(visit004);
		nursingOrderBuilder.setFrequencyType(orderFrequencyType_0H);
		nursingOrderBuilder.setOrderType(firstNursingOrderType);

		orderAppService.create(nursingOrderBuilder, userd02);

		DateUtil.setSysDate(DateUtil.createMinute("2017-01-07 17:20", dayCount));

		pageable = new PageRequest(0, Integer.MAX_VALUE);
		orders = orderAppService.findNeedVerifyOrders(user003, pageable);

		assertTrue(orders.size() == 1);

		// 核对医嘱
		for (Order order : orders) {
			orderAppService.verify(order.getId(), user003);
		}

		DateUtil.setSysDate(DateUtil.createMinute("2017-01-07 17:30", dayCount));

		pageable = new PageRequest(0, Integer.MAX_VALUE);
		executes = orderExecuteAppService.findNeedExecuteOrderExecutes(user003, pageable);

		assertTrue(executes.size() == 1);

		// 完成医嘱执行条目
		for (OrderExecute execute : executes) {
			orderExecuteAppService.finish(execute.getId(), null, user003);
		}

		// 2017-01-08
		DateUtil.setSysDate(DateUtil.createDay("2017-01-08", dayCount));
		patientNightTestService.calculate(admin001);

		DateUtil.setSysDate(DateUtil.createMinute("2017-01-08 09:10", dayCount));

		pageable = new PageRequest(0, Integer.MAX_VALUE);
		executes = orderExecuteAppService.findNeedExecuteOrderExecutes(user003, pageable);

		assertTrue(executes.size() == 2);

		// 完成医嘱执行条目
		for (OrderExecute execute : executes) {
			orderExecuteAppService.finish(execute.getId(), null, user003);
		}

		// 2017-01-09
		DateUtil.setSysDate(DateUtil.createDay("2017-01-09", dayCount));
		patientNightTestService.calculate(admin001);

		DateUtil.setSysDate(DateUtil.createMinute("2017-01-09 09:30", dayCount));

		pageable = new PageRequest(0, Integer.MAX_VALUE);
		executes = orderExecuteAppService.findNeedExecuteOrderExecutes(user003, pageable);

		assertTrue(executes.size() == 2);

		// 完成医嘱执行条目
		for (OrderExecute execute : executes) {
			orderExecuteAppService.finish(execute.getId(), null, user003);
		}

		visit = visitDomainService.find(visit001.getId());

		assertTrue(visit.getState().equals(Visit.State_NeedLeaveHospitalBalance));

		pageable = new PageRequest(0, Integer.MAX_VALUE);
		executes = orderExecuteAppService.findNeedExecuteOrderExecutes(user201, pageable);

		assertTrue(executes.size() == 1);

		DateUtil.setSysDate(DateUtil.createMinute("2017-01-09 10:30", dayCount));

		// 完成出院结算医嘱执行条目
		orderExecuteAppService.finish(executes.get(0).getId(), null, user201);

		visit = visitDomainService.find(visit001.getId());

		assertTrue(visit.getState().equals(Visit.State_OutHospital));

		DateUtil.setSysDate(DateUtil.createMinute("2017-01-09 10:40", dayCount));

		// 开立出院临时医嘱
		Order leaveHospitalOrder002 = new TemporaryOrder();
		leaveHospitalOrder002.setVisit(visit004);
		leaveHospitalOrder002.setName("出院医嘱");
		leaveHospitalOrder002.setPlanStartDate(DateUtil.createDay("2017-01-09", dayCount));
		leaveHospitalOrder002.setExecuteDept(dept222);
		leaveHospitalOrder002.setPlaceType(OrderCreateCommand.PlaceType_InPatient);

		leaveHospitalOrder002.setOrderType(outHospitalOrderType);

		orderAppService.create(leaveHospitalOrder002, userd02);

		pageable = new PageRequest(0, Integer.MAX_VALUE);
		orders = orderAppService.findNeedVerifyOrders(user003, pageable);

		assertTrue(orders.size() == 1);
		assertTrue(orders.get(0).getId().equals(leaveHospitalOrder002.getId()));

		DateUtil.setSysDate(DateUtil.createMinute("2017-01-09 11:00", dayCount));

		// 核对医嘱
		orderAppService.verify(leaveHospitalOrder002.getId(), user003);

		pageable = new PageRequest(0, Integer.MAX_VALUE);
		executes = orderExecuteAppService.findNeedExecuteOrderExecutes(user003, pageable);

		DateUtil.setSysDate(DateUtil.createMinute("2017-01-09 11:02", dayCount));

		// 完成医嘱执行条目
		for (OrderExecute execute : executes) {
			orderExecuteAppService.finish(execute.getId(), null, user003);
		}

		visit = visitDomainService.find(visit004.getId());

		assertTrue(visit.getState().equals(Visit.State_NeedLeaveHospitalBalance));

		DateUtil.setSysDate(DateUtil.createMinute("2017-01-09 11:30", dayCount));

		pageable = new PageRequest(0, Integer.MAX_VALUE);
		executes = orderExecuteAppService.findNeedExecuteOrderExecutes(user201, pageable);

		assertTrue(executes.size() == 1);

		// 完成出院结算医嘱执行条目
		orderExecuteAppService.finish(executes.get(0).getId(), null, user201);

		visit = visitDomainService.find(visit004.getId());

		assertTrue(visit.getState().equals(Visit.State_OutHospital));
	}

	public void followUp() throws HsException {

		Visit visit;
		Pageable pageable = new PageRequest(0, Integer.MAX_VALUE);

		DateUtil.setSysDate(DateUtil.createMinute("2017-01-09 14:30", dayCount));

		// 整理病历
		arrangementMedicalRecord();

		DateUtil.setSysDate(DateUtil.createMinute("2017-01-09 15:00", dayCount));

		// 病历移交档案室
		inPatientAppService.transfer(visit001.getId(), user003);

		visit = visitAppService.find(visit001.getId());

		assertTrue(visit.getState().equals(Visit.State_IntoRecordRoom));
		assertTrue(
				visit.getMedicalRecordClip().getState().equals(MedicalRecordClip.State_Checking));

		DateUtil.setSysDate(DateUtil.createMinute("2017-01-10 09:30", dayCount));

		List<MedicalRecordClip> clips = qualityControlAppService.findNeedCheckRecordClips(user601,
				pageable);

		assertTrue(clips.size() == 1);

		// 审查病历
		qualityControlAppService.pass(clips.get(0).getId(), user601);

		visit = visitAppService.find(visit001.getId());

		assertTrue(visit.getState().equals(Visit.State_IntoRecordRoom));
		assertTrue(
				visit.getMedicalRecordClip().getState().equals(MedicalRecordClip.State_Archiving));

		DateUtil.setSysDate(DateUtil.createMinute("2017-01-11 10:30", dayCount));

		// 归档病历
		String position = "Num001";
		recordRoomDomainService.archive(clips.get(0).getId(), position, user602);

		visit = visitAppService.find(visit001.getId());
		assertTrue(visit.getState().equals(Visit.State_Archived));
		assertTrue(
				visit.getMedicalRecordClip().getState().equals(MedicalRecordClip.State_Archived));

	}

	public void arrangementMedicalRecord() throws HsException {

		DateUtil.setSysDate(DateUtil.createMinute("2017-01-09 14:30", dayCount));

		medicalRecordTestService.setDayCount(dayCount);

		MedicalRecord medicalRecord;
		List<MedicalRecord> medicalRecords;
		TreatmentItem item;
		// 创建临时医嘱单
		medicalRecord = medicalRecordTestService.createTemporaryOrderListMedicalRecord(visit001,
				temporaryOrderListMedicalRecordType, user002);

		item = treatmentDomainService.getTheTreatmentItem(visit001,
				temporaryOrderListTreatmentItemSpec);

		assertTrue(item.getValues().size() == MedicalRecordTestService.temporaryOrderCount);

		medicalRecordAppService.fix(medicalRecord.getId(), user002);

		item = treatmentDomainService.getTheTreatmentItem(visit001,
				temporaryOrderListTreatmentItemSpec);

		assertTrue(item.getValues().size() == MedicalRecordTestService.temporaryOrderCount);

		// 生成检查单病历
		medicalRecords = medicalRecordTestService.createInspectResultMedicalRecord(visit001,
				inspectResultMedicalRecordType, user002);

		for (MedicalRecord mr : medicalRecords) {
			medicalRecordAppService.fix(mr.getId(), user002);
		}
	}

	public void intoWardTTT() throws HsException {

		Pageable pageable;
		List<Visit> visits;
		Visit visit;
		ReceiveVisitVO receiveVisitVO;

		DateUtil.setSysDate(DateUtil.createMinute("2017-01-12 09:00", dayCount));
		// 创建测试患者
		CreateVisitVO createVisitVO = new CreateVisitVO();
		createVisitVO.setCardNumber("ttt");
		createVisitVO.setName("测试患者ttt");
		createVisitVO.setBirthday(DateUtil.createDay("1990-01-22"));
		createVisitVO.setSex("男");
		createVisitVO.setOperator(user101);
		createVisitVO.setDept(dept000);
		createVisitVO.setArea(dept000n);
		createVisitVO.setRespDoctor(user002);
		// 送诊
		visitttt = registerAppService.register(createVisitVO);

		DateUtil.setSysDate(DateUtil.createMinute("2017-01-12 09:30", dayCount));

		cashierAppService.initAccount(visitttt.getId(), 2000F, user201);

		pageable = new PageRequest(0, Integer.MAX_VALUE);
		visits = inPatientAppService.getNeedReceiveVisits(user001, pageable);

		assertTrue(visits.size() == 1);

		visit = visitDomainService.find(visitttt.getId());

		assertTrue(visit.getState().equals(Visit.State_NeedIntoWard));

		DateUtil.setSysDate(DateUtil.createMinute("2017-01-12 09:50", dayCount));

		receiveVisitVO = new ReceiveVisitVO();
		receiveVisitVO.setVisit(visitttt);
		receiveVisitVO.setBed("bedttt");
		receiveVisitVO.setNurse(user003);

		inPatientAppService.receive(receiveVisitVO, user001);

		pageable = new PageRequest(0, Integer.MAX_VALUE);
		visits = inPatientAppService.InWardVisits(dept000, pageable);

		assertTrue(visits.size() == 1);

		visit = visitDomainService.find(visitttt.getId());

		assertTrue(visit.getState().equals(Visit.State_IntoWard));

	}

	public Visit getVisit001() {
		return visit001;
	}

	public void setVisit001(Visit visit001) {
		this.visit001 = visit001;

		visitList.add(visit001);
	}

	public Visit getVisit004() {
		return visit004;
	}

	public void setVisit004(Visit visit004) {
		this.visit004 = visit004;

		visitList.add(visit004);
	}

	public Visit getVisitttt() {
		return visitttt;
	}

	public void setVisitttt(Visit visitttt) {
		this.visitttt = visitttt;

		visitList.add(visitttt);
	}

}
