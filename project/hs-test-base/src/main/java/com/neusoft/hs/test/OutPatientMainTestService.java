package com.neusoft.hs.test;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.neusoft.hs.application.pharmacy.OutPatientPharmacyAppService;
import com.neusoft.hs.domain.diagnosis.DiagnosisTreatmentItemValue;
import com.neusoft.hs.domain.inspect.InspectApply;
import com.neusoft.hs.domain.inspect.InspectApplyItem;
import com.neusoft.hs.domain.medicalrecord.MedicalRecord;
import com.neusoft.hs.domain.order.EnterHospitalOrderType;
import com.neusoft.hs.domain.order.InspectOrderBuilder;
import com.neusoft.hs.domain.order.LongDrugOrderBuilder;
import com.neusoft.hs.domain.order.Order;
import com.neusoft.hs.domain.order.OrderCreateCommand;
import com.neusoft.hs.domain.order.OrderExecute;
import com.neusoft.hs.domain.order.PrescriptionBuilder;
import com.neusoft.hs.domain.order.TemporaryDrugOrderBuilder;
import com.neusoft.hs.domain.order.TemporaryOrder;
import com.neusoft.hs.domain.pharmacy.Prescription;
import com.neusoft.hs.domain.registration.Voucher;
import com.neusoft.hs.domain.treatment.Itemable;
import com.neusoft.hs.domain.treatment.SimpleTreatmentItemValue;
import com.neusoft.hs.domain.treatment.TreatmentItem;
import com.neusoft.hs.domain.treatment.TreatmentItemValue;
import com.neusoft.hs.domain.visit.CreateVisitVO;
import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.engine.DTOException;
import com.neusoft.hs.engine.visit.VisitDTO;
import com.neusoft.hs.platform.exception.HsException;
import com.neusoft.hs.platform.util.DateUtil;

@Service
public class OutPatientMainTestService extends AppTestService {

	@Autowired
	private OutPatientPharmacyAppService outPatientPharmacyAppService;

	protected Visit visit001;

	protected Visit visit002;

	protected Visit visit003;

	protected DiagnosisTreatmentItemValue hyperthyroidismDTV;

	protected DiagnosisTreatmentItemValue hypoglycemiaDTV;

	@Override
	public void execute() throws HsException, DTOException {

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-27 09:00"));

		CreateVisitVO createVisitVO;
		Visit theVisit;
		VisitDTO visitDTO;
		Pageable pageable;
		List<OrderExecute> executes;
		int changedCount;
		TreatmentItem item;
		Date sysDate;
		Date startDate;

		TemporaryDrugOrderBuilder drugOrderBuilder;
		LongDrugOrderBuilder longDrugOrderBuilder;
		PrescriptionBuilder prescriptionBuilder;
		InspectOrderBuilder inspectOrderBuilder;

		// 创建测试患者
		createVisitVO = new CreateVisitVO();
		createVisitVO.setCardNumber("xxx");
		createVisitVO.setName("测试患者001");
		createVisitVO.setBirthday(DateUtil.createDay("1978-01-27"));
		createVisitVO.setSex("男");
		createVisitVO.setOperator(user901);

		Voucher voucher001 = registrationAppService.register(createVisitVO, planRecord1.getId(),
				user901);
		visit001 = voucher001.getVisit();

		assertTrue(visit001.getState().equals(Visit.State_WaitingDiagnose));

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-27 09:10"));

		// 创建测试患者
		createVisitVO = new CreateVisitVO();
		createVisitVO.setCardNumber("yyy");
		createVisitVO.setName("测试患者002");
		createVisitVO.setBirthday(DateUtil.createDay("2009-11-03"));
		createVisitVO.setSex("女");
		createVisitVO.setOperator(user901);

		Voucher voucher002 = registrationAppService.register(createVisitVO, planRecord1.getId(),
				user901);
		visit002 = voucher002.getVisit();

		assertTrue(visit002.getState().equals(Visit.State_WaitingDiagnose));

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-27 09:20"));

		boolean rtn;
		rtn = outPatientDeptAppService.nextVoucher(planRecord1.getId());

		assertTrue(rtn);

		theVisit = visitDomainService.find(visit001.getId());

		assertTrue(theVisit.getState().equals(Visit.State_Diagnosing));

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-27 09:25"));

		// 开立药品临时医嘱
		drugOrderBuilder = new TemporaryDrugOrderBuilder();
		drugOrderBuilder.setVisit(visit001);
		drugOrderBuilder.setCount(1);
		drugOrderBuilder.setPlaceType(OrderCreateCommand.PlaceType_OutPatient);
		drugOrderBuilder.setOrderType(drugOrderType001);
		drugOrderBuilder.setPharmacy(dept333);
		drugOrderBuilder.setDrugUseMode(oralOrderUseMode);

		orderAppService.create(drugOrderBuilder, user002);

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-27 09:27"));

		// 创建主诉
		SimpleTreatmentItemValue value = new SimpleTreatmentItemValue();
		value.setInfo("患者咳嗽发烧两天");

		treatmentAppService.create(visit001, mainDescribeTreatmentItemSpec, value, user002);

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-27 09:28"));

		// 创建诊断
		List<TreatmentItemValue> values = new ArrayList<TreatmentItemValue>();

		hyperthyroidismDTV = new DiagnosisTreatmentItemValue();
		hyperthyroidismDTV.setDisease(hyperthyroidismDisease);

		values.add(hyperthyroidismDTV);

		hypoglycemiaDTV = new DiagnosisTreatmentItemValue();
		hypoglycemiaDTV.setDisease(hypoglycemiaDisease);
		hypoglycemiaDTV.setRemark("2.5mmol/L");

		values.add(hypoglycemiaDTV);

		treatmentAppService.create(visit001, diagnosisTreatmentItemSpec, values, user002);

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-27 09:30"));

		// 创建门诊记录
		MedicalRecord outPatientRecord = medicalRecordTestService.createOutPatientRecord(visit001,
				outPatientRecordMedicalRecordType, user002);

		outPatientRecord = medicalRecordAppService.find(outPatientRecord.getId());

		Map<String, Itemable> datas = outPatientRecord.getDatas();

		assertTrue(datas.get("患者姓名").getValues().get(0).toString().equals("测试患者001"));
		assertTrue(datas.get("主诉").getValues().get(0).toString().equals("患者咳嗽发烧两天，体温37.5"));

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-27 09:32"));

		medicalRecordAppService.fix(outPatientRecord.getId(), user002);

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-27 09:33"));

		prescriptionBuilder = new PrescriptionBuilder();
		prescriptionBuilder.setVisit(visit001);
		prescriptionBuilder.setIllustrate("水煎服");
		prescriptionBuilder.setPlaceType(OrderCreateCommand.PlaceType_OutPatient);
		prescriptionBuilder.setPharmacy(dept888);
		prescriptionBuilder.setDrugUseMode(oralOrderUseMode);

		prescriptionBuilder.addDiagnosisTreatmentItemValue(hyperthyroidismDTV);
		prescriptionBuilder.addDiagnosisTreatmentItemValue(hypoglycemiaDTV);

		// 开立药品004\005\006临时医嘱
		prescriptionBuilder.addCount(drugOrderType004, 20);
		prescriptionBuilder.addCount(drugOrderType005, 15);
		prescriptionBuilder.addCount(drugOrderType006, 100);

		orderAppService.create(prescriptionBuilder, user002);

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-27 09:35"));

		rtn = outPatientDeptAppService.nextVoucher(planRecord1.getId());

		assertTrue(rtn);

		theVisit = visitDomainService.find(visit001.getId());

		assertTrue(theVisit.getState().equals(Visit.State_Diagnosed_Executing));

		theVisit = visitDomainService.find(visit002.getId());

		assertTrue(theVisit.getState().equals(Visit.State_Diagnosing));

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-27 09:36"));

		// 创建药品002/003长期医嘱
		sysDate = DateUtil.getSysDate();
		startDate = DateUtil.getSysDateStart();

		longDrugOrderBuilder = new LongDrugOrderBuilder();
		longDrugOrderBuilder.setVisit(visit002);
		longDrugOrderBuilder.setPlaceType(OrderCreateCommand.PlaceType_OutPatient);
		longDrugOrderBuilder.setFrequencyType(orderFrequencyType_11H);
		longDrugOrderBuilder.setPlanEndDate(DateUtil.addDay(startDate, 2));
		longDrugOrderBuilder.setPharmacy(dept333);
		longDrugOrderBuilder.setExecuteDept(deptaaa);
		longDrugOrderBuilder.setDrugUseMode(infusionOrderUseModeToOutPatient);

		longDrugOrderBuilder.addCount(drugOrderType002, 2);
		longDrugOrderBuilder.addCount(drugOrderType003, 1);

		orderAppService.create(longDrugOrderBuilder, user002);

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-27 09:40"));

		rtn = outPatientDeptAppService.nextVoucher(planRecord1.getId());

		assertTrue(!rtn);

		theVisit = visitDomainService.find(visit002.getId());

		assertTrue(theVisit.getState().equals(Visit.State_Diagnosed_Executing));

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-27 09:42"));

		pageable = new PageRequest(0, Integer.MAX_VALUE);
		executes = cashierAppService.getNeedChageExecutes(visit001, user701, pageable);

		assertTrue(executes.size() == 4);

		// 完成药品收费医嘱执行条目
		for (OrderExecute execute : executes) {
			orderExecuteAppService.finish(execute.getId(), null, user701);
		}

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-27 09:43"));

		pageable = new PageRequest(0, Integer.MAX_VALUE);
		executes = cashierAppService.getNeedChageExecutes(visit002, user701, pageable);

		assertTrue(executes.size() == 4);

		// 完成输液费统一收费医嘱执行条目
		for (OrderExecute execute : executes) {
			orderExecuteAppService.finish(execute.getId(), null, user701);
		}

		for (OrderExecute execute : executes) {
			OrderExecute execute1 = orderExecuteDomainService.find(execute.getId());
			assertTrue(execute1.getState().equals(OrderExecute.State_Finished));
		}

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-27 09:46"));

		pageable = new PageRequest(0, Integer.MAX_VALUE);
		executes = orderExecuteAppService.findNeedExecuteOrderExecutes(user301, pageable);

		assertTrue(executes.size() == 5);

		// 完成摆药医嘱执行条目
		for (OrderExecute execute : executes) {
			orderExecuteAppService.finish(execute.getId(), null, user301);
		}

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-27 09:48"));

		pageable = new PageRequest(0, Integer.MAX_VALUE);
		// 通过患者一次就诊得到待取药的任务列表
		executes = outPatientPharmacyAppService.taskDrug(visit001, user303, pageable);

		assertTrue(executes.size() == 1);

		// 完成取药医嘱执行条目
		for (OrderExecute execute : executes) {
			orderExecuteAppService.finish(execute.getId(), null, user303);
		}

		pageable = new PageRequest(0, Integer.MAX_VALUE);
		// 通过患者一次就诊得到待取药的任务列表
		executes = outPatientPharmacyAppService.taskDrug(visit002, user303, pageable);

		assertTrue(executes.size() == 4);

		// 完成取药医嘱执行条目
		for (OrderExecute execute : executes) {
			orderExecuteAppService.finish(execute.getId(), null, user303);
		}

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-27 09:49"));

		pageable = new PageRequest(0, Integer.MAX_VALUE);
		executes = orderExecuteAppService.findNeedExecuteOrderExecutes(user801, pageable);

		assertTrue(executes.size() == 3);

		List<Prescription> prescriptions = pharmacyDomainService.findPrescriptions(visit001);

		assertTrue(prescriptions.size() == 1);
		assertTrue(prescriptions.get(0).getIllustrate().equals("水煎服"));

		// 完成摆药医嘱执行条目
		for (OrderExecute execute : executes) {
			orderExecuteAppService.finish(execute.getId(), null, user801);
		}

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-27 09:50"));

		pageable = new PageRequest(0, Integer.MAX_VALUE);
		// 通过患者一次就诊得到待取药的任务列表
		executes = outPatientPharmacyAppService.taskDrug(visit001, user801, pageable);

		assertTrue(executes.size() == 3);

		// 完成取药医嘱执行条目
		for (OrderExecute execute : executes) {
			orderExecuteAppService.finish(execute.getId(), null, user801);
		}

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-27 09:54"));

		// 创建测试患者
		createVisitVO = new CreateVisitVO();
		createVisitVO.setCardNumber("zzz");
		createVisitVO.setName("测试患者003");
		createVisitVO.setBirthday(DateUtil.createDay("2008-08-03"));
		createVisitVO.setSex("女");
		createVisitVO.setOperator(user901);

		Voucher voucher003 = registrationAppService.register(createVisitVO, planRecord1.getId(),
				user901);
		visit003 = voucher003.getVisit();

		assertTrue(visit003.getState().equals(Visit.State_WaitingDiagnose));

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-27 09:55"));

		rtn = outPatientDeptAppService.nextVoucher(planRecord1.getId());

		assertTrue(rtn);

		theVisit = visitDomainService.find(visit003.getId());

		assertTrue(theVisit.getState().equals(Visit.State_Diagnosing));

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-27 10:00"));

		// 给患者003开立检查医嘱
		inspectOrderBuilder = new InspectOrderBuilder();
		inspectOrderBuilder.setVisit(visit003);
		inspectOrderBuilder.setOrderType(inspectOrderType);
		inspectOrderBuilder.setPlaceType(OrderCreateCommand.PlaceType_OutPatient);

		InspectApply inspectApply = new InspectApply();
		inspectApply.setGoal("查查是否有问题");

		InspectApplyItem spineCTInspectApplyItem = new InspectApplyItem();
		spineCTInspectApplyItem.setInspectItem(spineCTInspectItem);
		spineCTInspectApplyItem.setArrangeDept(dept444);
		spineCTInspectApplyItem.setInspectDept(dept444);

		inspectApply.addInspectApplyItem(spineCTInspectApplyItem);

		inspectOrderBuilder.setInspectApply(inspectApply);

		Order spineInspectOrder = orderAppService.create(inspectOrderBuilder, user002).get(0);

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-27 10:05"));

		registrationAppService.repeatOccupy(voucher001, planRecord1.getId(), user901);

		assertTrue(voucher001.getNumber() == 4);

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-27 10:15"));

		rtn = outPatientDeptAppService.nextVoucher(planRecord1.getId());

		assertTrue(rtn);

		theVisit = visitDomainService.find(visit003.getId());

		assertTrue(theVisit.getState().equals(Visit.State_Diagnosed_Executing));

		theVisit = visitDomainService.find(visit001.getId());

		assertTrue(theVisit.getState().equals(Visit.State_Diagnosing));

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-27 10:20"));

		rtn = outPatientDeptAppService.nextVoucher(planRecord1.getId());

		assertTrue(!rtn);

		theVisit = visitDomainService.find(visit001.getId());

		assertTrue(theVisit.getState().equals(Visit.State_Diagnosed_Executing));

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-27 10:25"));

		// 患者003缴费
		pageable = new PageRequest(0, Integer.MAX_VALUE);
		executes = orderExecuteAppService.findNeedExecuteOrderExecutes(user701, pageable);

		assertTrue(executes.size() == 1);

		// 完成收费医嘱执行条目
		for (OrderExecute execute : executes) {
			orderExecuteAppService.finish(execute.getId(), null, user701);
		}

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-27 10:30"));

		// 患者003安排检查
		pageable = new PageRequest(0, Integer.MAX_VALUE);
		executes = orderExecuteAppService.findNeedExecuteOrderExecutes(user401, pageable);

		assertTrue(executes.size() == 1);

		// 给患者003安排检查时间
		inspectAppService.arrange(executes.get(0).getId(),
				DateUtil.createMinute("2016-12-27 14:00"), "CT检查室（一）", user401);

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-27 11:00"));

		pageable = new PageRequest(0, Integer.MAX_VALUE);
		// 通过患者一次就诊得到输液的任务列表
		executes = orderExecuteAppService.findNeedExecuteOrderExecutes(visit002,
				OrderExecute.Type_Transport_Fluid, usera01, pageable);

		assertTrue(executes.size() == 2);

		// 完成输液医嘱执行条目
		for (OrderExecute execute : executes) {
			orderExecuteAppService.finish(execute.getId(), null, usera01);
		}

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-27 13:30"));

		visitAppService.leaveHospital(visit002.getId(), user002);

		// theVisit = visitDomainService.find(visit002.getId());
		visitDTO = visitFacade.find(visit002.getId());

		assertTrue(visitDTO.getState().equals(Visit.State_LeaveHospital));

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-27 14:00"));

		pageable = new PageRequest(0, Integer.MAX_VALUE);
		executes = orderExecuteAppService.findNeedExecuteOrderExecutes(user402, pageable);

		assertTrue(executes.size() == 1);

		// 确认CT检查结果
		Map<InspectApplyItem, String> CTResults = new HashMap<InspectApplyItem, String>();
		CTResults.put(spineCTInspectApplyItem, "没啥问题");

		inspectAppService.confirm(executes.get(0).getId(), CTResults, user402);

		testUtil.testInspectResult(spineInspectOrder.getId(), 1);

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-27 14:30"));
		// 查看检查结果重新排号
		registrationAppService.repeatOccupy(voucher003, planRecord1.getId(), user901);

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-27 14:35"));

		rtn = outPatientDeptAppService.nextVoucher(planRecord1.getId());

		assertTrue(rtn);

		theVisit = visitDomainService.find(visit003.getId());

		assertTrue(theVisit.getState().equals(Visit.State_Diagnosing));

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-27 15:00"));

		rtn = outPatientDeptAppService.nextVoucher(planRecord1.getId());

		assertTrue(!rtn);

		theVisit = visitDomainService.find(visit003.getId());

		assertTrue(theVisit.getState().equals(Visit.State_Diagnosed_Executing));

		// 2016-12-28
		DateUtil.setSysDate(DateUtil.createDay("2016-12-28"));
		patientNightTestService.calculate(admin001);

		visitDTO = visitFacade.find(visit001.getId());

		assertTrue(visitDTO.getState().equals(Visit.State_LeaveHospital));

		visitDTO = visitFacade.find(visit003.getId());

		assertTrue(visitDTO.getState().equals(Visit.State_LeaveHospital));

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-28 09:00"));

		// 创建测试患者
		createVisitVO = new CreateVisitVO();
		createVisitVO.setCardNumber("xxx");
		createVisitVO.setName("测试患者001");
		createVisitVO.setBirthday(DateUtil.createDay("1978-01-27"));
		createVisitVO.setSex("男");
		createVisitVO.setOperator(user002);
		createVisitVO.setRepeatVisit(true);

		Voucher voucher001x = registrationAppService.register(createVisitVO, planRecord2.getId(),
				user901);
		theVisit = voucher001x.getVisit();

		assertTrue(theVisit.getState().equals(Visit.State_WaitingDiagnose));

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-28 09:05"));

		rtn = outPatientDeptAppService.nextVoucher(planRecord2.getId());

		assertTrue(rtn);

		theVisit = visitDomainService.find(visit001.getId());

		assertTrue(theVisit.getState().equals(Visit.State_Diagnosing));

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-28 09:10"));

		// 开立入院医嘱
		Order enterHospitalOrder = new TemporaryOrder();
		enterHospitalOrder.setVisit(visit001);
		enterHospitalOrder.setName("入院医嘱");
		enterHospitalOrder.setExecuteDept(dept000);
		enterHospitalOrder.setPlanStartDate(DateUtil.getSysDate());
		enterHospitalOrder.setPlaceType(OrderCreateCommand.PlaceType_OutPatient);
		enterHospitalOrder.setOrderType(enterHospitalOrderType);

		enterHospitalOrder.addParam(EnterHospitalOrderType.WardDept, dept000);
		enterHospitalOrder.addParam(EnterHospitalOrderType.WardArea, dept000n);
		enterHospitalOrder.addParam(EnterHospitalOrderType.RespDoctor, user002);

		orderAppService.create(enterHospitalOrder, user002);

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-28 09:15"));

		rtn = outPatientDeptAppService.nextVoucher(planRecord2.getId());

		assertTrue(!rtn);

		theVisit = visitDomainService.find(visit001.getId());

		assertTrue(theVisit.getState().equals(Visit.State_WaitingEnterHospital));

		pageable = new PageRequest(0, Integer.MAX_VALUE);
		executes = orderExecuteAppService.findNeedExecuteOrderExecutes(user101, pageable);

		assertTrue(executes.size() == 1);

		// 完成住院医嘱执行条目
		orderExecuteAppService.finish(executes.get(0).getId(), null, user101);

		theVisit = visitDomainService.find(visit001.getId());

		assertTrue(theVisit.getState().equals(Visit.State_NeedInitAccount));

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-28 11:00"));

		pageable = new PageRequest(0, Integer.MAX_VALUE);
		// 通过患者一次就诊得到输液的任务列表
		executes = orderExecuteAppService.findNeedExecuteOrderExecutes(visit002,
				OrderExecute.Type_Transport_Fluid, usera01, pageable);

		assertTrue(executes.size() == 2);

		// 完成输液医嘱执行条目
		for (OrderExecute execute : executes) {
			orderExecuteAppService.finish(execute.getId(), null, usera01);
		}

	}

	public Visit getVisit001() {
		return visit001;
	}

	public Visit getVisit002() {
		return visit002;
	}

	public Visit getVisit003() {
		return visit003;
	}

}
