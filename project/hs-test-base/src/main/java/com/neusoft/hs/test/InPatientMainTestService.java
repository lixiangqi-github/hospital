package com.neusoft.hs.test;

import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.neusoft.hs.data.init.ChoiceItem;
import com.neusoft.hs.domain.cost.ChargeRecord;
import com.neusoft.hs.domain.inspect.InspectApply;
import com.neusoft.hs.domain.inspect.InspectApplyItem;
import com.neusoft.hs.domain.medicalrecord.MedicalRecord;
import com.neusoft.hs.domain.order.InspectArrangeOrderExecute;
import com.neusoft.hs.domain.order.InspectOrderBuilder;
import com.neusoft.hs.domain.order.LongDrugOrderBuilder;
import com.neusoft.hs.domain.order.LongOrder;
import com.neusoft.hs.domain.order.NursingOrderBuilder;
import com.neusoft.hs.domain.order.Order;
import com.neusoft.hs.domain.order.OrderCreateCommand;
import com.neusoft.hs.domain.order.OrderExecute;
import com.neusoft.hs.domain.order.SurgeryArrangeOrderExecute;
import com.neusoft.hs.domain.order.TemporaryDrugOrderBuilder;
import com.neusoft.hs.domain.order.TemporaryOrder;
import com.neusoft.hs.domain.order.TransferDeptConfirmOrderExecute;
import com.neusoft.hs.domain.order.TransferDeptOrderBuilder;
import com.neusoft.hs.domain.pharmacy.ConfigureFluidOrder;
import com.neusoft.hs.domain.pharmacy.DispensingDrugOrder;
import com.neusoft.hs.domain.surgery.SurgeryApply;
import com.neusoft.hs.domain.surgery.SurgeryApplyItem;
import com.neusoft.hs.domain.treatment.Itemable;
import com.neusoft.hs.domain.treatment.SimpleTreatmentItemValue;
import com.neusoft.hs.domain.treatment.TreatmentItem;
import com.neusoft.hs.domain.treatment.TreatmentItemSpec;
import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.platform.exception.HsException;
import com.neusoft.hs.platform.util.DateUtil;

@Service
public class InPatientMainTestService extends InPatientTestService {

	@Override
	protected void treatment() throws HsException {

		Pageable pageable;
		List<OrderExecute> executes;
		List<Order> orders;
		Date sysDate;
		Date startDate;
		DispensingDrugOrder dispensingDrugOrder;
		Order drug001Order1;
		Order drug001Order4;
		TreatmentItem item;
		Visit visit;
		ConfigureFluidOrder fluidOrder;
		Map<String, Object> params;

		NursingOrderBuilder nursingOrderBuilder;
		TemporaryDrugOrderBuilder drugOrderBuilder;
		LongDrugOrderBuilder longDrugOrderBuilder;
		InspectOrderBuilder inspectOrderBuilder;

		LongOrder describeLongOrder;
		TemporaryOrder describeTemporaryOrder;
		TemporaryOrder surgeryTemporaryOrder;

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-28 10:40", dayCount));

		// 为001开立一级护理长期医嘱
		nursingOrderBuilder = new NursingOrderBuilder();
		nursingOrderBuilder.setVisit(visit001);
		nursingOrderBuilder.setFrequencyType(orderFrequencyType_0H);
		nursingOrderBuilder.setOrderType(firstNursingOrderType);

		orderAppService.create(nursingOrderBuilder, user002);

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-28 10:42", dayCount));

		// 为004开立二级护理长期医嘱
		nursingOrderBuilder = new NursingOrderBuilder();
		nursingOrderBuilder.setVisit(visit004);
		nursingOrderBuilder.setFrequencyType(orderFrequencyType_0H);
		nursingOrderBuilder.setOrderType(secondNursingOrderType);

		orderAppService.create(nursingOrderBuilder, user002);

		pageable = new PageRequest(0, Integer.MAX_VALUE);
		orders = orderAppService.findNeedVerifyOrders(user003, pageable);

		assertTrue(orders.size() == 2);

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-28 10:45", dayCount));

		// 核对医嘱
		for (Order order : orders) {
			orderAppService.verify(order.getId(), user003);
		}

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-28 10:55", dayCount));

		// 为患者001开立药品临时医嘱
		drugOrderBuilder = new TemporaryDrugOrderBuilder();
		drugOrderBuilder.setVisit(visit001);
		drugOrderBuilder.setCount(2);
		drugOrderBuilder.setPlaceType(OrderCreateCommand.PlaceType_InPatient);
		drugOrderBuilder.setOrderType(drugOrderType001);
		drugOrderBuilder.setPharmacy(deptccc);
		drugOrderBuilder.setDrugUseMode(oralOrderUseMode);

		orders = orderAppService.create(drugOrderBuilder, user002);

		assertTrue(orders.size() == 1);

		drug001Order1 = orders.get(0);

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-28 10:56", dayCount));

		// 为患者004开立药品临时医嘱
		drugOrderBuilder = new TemporaryDrugOrderBuilder();
		drugOrderBuilder.setVisit(visit004);
		drugOrderBuilder.setCount(4);
		drugOrderBuilder.setPlaceType(OrderCreateCommand.PlaceType_InPatient);
		drugOrderBuilder.setOrderType(drugOrderType001);
		drugOrderBuilder.setPharmacy(deptccc);
		drugOrderBuilder.setDrugUseMode(oralOrderUseMode);

		orders = orderAppService.create(drugOrderBuilder, user002);

		assertTrue(orders.size() == 1);

		drug001Order4 = orders.get(0);

		pageable = new PageRequest(0, Integer.MAX_VALUE);
		orders = orderAppService.findNeedVerifyOrders(user003, pageable);

		assertTrue(orders.size() == 2);

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-28 11:00", dayCount));

		// 核对医嘱
		for (Order order : orders) {
			orderAppService.verify(order.getId(), user003);
		}

		pageable = new PageRequest(0, Integer.MAX_VALUE);
		executes = orderExecuteAppService.findNeedSendOrderExecutes(user003, pageable);

		assertTrue(executes.size() == 2);

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-28 11:05", dayCount));

		// 发送医嘱执行条目
		for (OrderExecute execute : executes) {
			orderExecuteAppService.send(execute.getId(), user003);
		}

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-28 11:15", dayCount));

		pageable = new PageRequest(0, Integer.MAX_VALUE);
		executes = orderExecuteAppService.findNeedExecuteOrderExecutes(userc01, pageable);

		assertTrue(executes.size() == 2);

		// 完成摆药医嘱执行条目
		for (OrderExecute execute : executes) {
			orderExecuteAppService.finish(execute.getId(), null, userc01);
		}

		// dispensingDrugOrder = patientPharmacyAppService.print(dept000n,
		// dayDispensingDrugBatch, userc01);
		//
		// assertTrue(dispensingDrugOrder.getExecutes().size() == 2);
		//
		// patientPharmacyAppService
		// .dispense(dispensingDrugOrder.getId(), userc01);

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-28 11:30", dayCount));

		pageable = new PageRequest(0, Integer.MAX_VALUE);
		executes = orderExecuteAppService.findNeedExecuteOrderExecutes(userc03, pageable);

		assertTrue(executes.size() == 2);

		// 完成发药医嘱执行条目
		for (OrderExecute execute : executes) {
			orderExecuteAppService.finish(execute.getId(), null, userc03);
		}

		// dispensingDrugOrder = patientPharmacyAppService.distribute(
		// dispensingDrugOrder.getId(), userc01);
		//
		// assertTrue(dispensingDrugOrder.getExecutes().size() == 2);

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-28 11:32", dayCount));

		pageable = new PageRequest(0, Integer.MAX_VALUE);
		executes = orderExecuteAppService.findNeedExecuteOrderExecutes(user003, pageable);

		assertTrue(executes.size() == 2);

		// 完成当天的一级护理医嘱
		for (OrderExecute execute : executes) {
			orderExecuteAppService.finish(execute.getId(), null, user003);
		}

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-28 11:45", dayCount));

		// 取消医嘱条目
		orderAppService.cancel(drug001Order1.getId(), user002);

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-28 13:45", dayCount));

		pageable = new PageRequest(0, Integer.MAX_VALUE);
		executes = costDomainService.getNeedBackChargeOrderExecutes(user201, pageable);

		assertTrue(executes.size() == 1);

		costAppService.unCharging(executes.get(0).getId(), true, user003);

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-28 14:05", dayCount));

		Visit currentVisit = visitDomainService.find(visit001.getId());

		List<TreatmentItemSpec> treatmentItemSpecs = treatmentAppService
				.getShouldTreatmentItemSpecs(currentVisit, user002);

		assertTrue(treatmentItemSpecs.size() == 1);

		// 创建主诉
		SimpleTreatmentItemValue value = new SimpleTreatmentItemValue();
		value.setInfo("患者咳嗽发烧三天");

		treatmentAppService.create(visit001, mainDescribeTreatmentItemSpec, value, user002);

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-28 14:20", dayCount));

		// 创建入院记录
		MedicalRecord intoWardRecord = medicalRecordTestService.createIntoWardRecord(visit001,
				intoWardRecordMedicalRecordType, user002);

		intoWardRecord = medicalRecordAppService.find(intoWardRecord.getId());

		Map<String, Itemable> datas = intoWardRecord.getDatas();

		assertTrue(datas.get("患者姓名").getValues().get(0).toString().equals("测试患者001"));
		assertTrue(datas.get("主诉").getValues().get(0).toString().equals("患者咳嗽发烧三天，体温38.5"));

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-28 15:00", dayCount));

		medicalRecordAppService.sign(intoWardRecord.getId(), user002);

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-28 17:00", dayCount));

		item = treatmentDomainService.getTheTreatmentItem(visit001, mainDescribeTreatmentItemSpec);
		((SimpleTreatmentItemValue) item.getValues().get(0)).setInfo("患者咳嗽发烧三天，体温38.5，嗜睡");
		treatmentDomainService.update(item, user002);

		intoWardRecord = medicalRecordAppService.find(intoWardRecord.getId());

		datas = intoWardRecord.getDatas();

		assertTrue(intoWardRecord.getState().equals(MedicalRecord.State_Signed));
		assertTrue(datas.get("主诉").getValues().get(0).toString().equals("患者咳嗽发烧三天，体温38.5"));

		// 2016-12-29
		DateUtil.setSysDate(DateUtil.createDay("2016-12-29", dayCount));
		patientNightTestService.calculate(admin001);
		patientNightTestService.calculate(admin001);

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-29 09:10", dayCount));

		pageable = new PageRequest(0, Integer.MAX_VALUE);
		executes = orderExecuteAppService.findNeedExecuteOrderExecutes(user003, pageable);

		assertTrue(executes.size() == 2);

		// 完成当天的一级护理医嘱
		for (OrderExecute execute : executes) {
			orderExecuteAppService.finish(execute.getId(), null, user003);
		}

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-29 10:10", dayCount));

		// 创建药品002/003长期医嘱
		sysDate = DateUtil.getSysDate();
		startDate = DateUtil.getSysDateStart();

		longDrugOrderBuilder = new LongDrugOrderBuilder();
		longDrugOrderBuilder.setVisit(visit001);
		longDrugOrderBuilder.setPlaceType(OrderCreateCommand.PlaceType_InPatient);
		longDrugOrderBuilder.setFrequencyType(orderFrequencyType_9H15H);
		longDrugOrderBuilder.setPlanEndDate(DateUtil.addDay(sysDate, 2));
		longDrugOrderBuilder.setPharmacy(deptbbb);
		longDrugOrderBuilder.setDrugUseMode(infusionOrderUseModeToInPatient);

		longDrugOrderBuilder.addCount(drugOrderType002, 2);
		longDrugOrderBuilder.addCount(drugOrderType003, 1);

		orderAppService.create(longDrugOrderBuilder, user002);

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-29 10:30", dayCount));

		pageable = new PageRequest(0, Integer.MAX_VALUE);
		orders = orderAppService.findNeedVerifyOrders(user003, pageable);

		assertTrue(orders.size() == 2);

		// 核对医嘱
		for (Order order : orders) {
			orderAppService.verify(order.getId(), user003);
		}

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-29 11:05", dayCount));

		pageable = new PageRequest(0, Integer.MAX_VALUE);
		executes = orderExecuteAppService.findNeedSendOrderExecutes(user003, pageable);

		assertTrue(executes.size() == 4);

		// 发送医嘱执行条目
		for (OrderExecute execute : executes) {
			orderExecuteAppService.send(execute.getId(), user003);
		}

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-29 14:00", dayCount));

		// 打印配液单
		fluidOrder = configureFluidAppService.print(dept000n, afternoonConfigureFluidBatch,
				userb02);

		assertTrue(fluidOrder.getConfigureFluidExecutes().size() == 2);

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-29 14:30", dayCount));

		// 配液完成
		fluidOrder = configureFluidAppService.finish(fluidOrder.getId(), userb02);

		assertTrue(fluidOrder.getConfigureFluidExecutes().size() == 2);

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-29 14:35", dayCount));

		// 发送配液单
		fluidOrder = configureFluidAppService.distribute(fluidOrder.getId(), userb03);

		assertTrue(fluidOrder.getDistributeDrugExecutes().size() == 2);

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-29 15:30", dayCount));

		pageable = new PageRequest(0, Integer.MAX_VALUE);
		executes = orderExecuteAppService.findNeedExecuteOrderExecutes(user003, pageable);

		assertTrue(executes.size() == 2);

		// 完成医嘱执行条目
		for (OrderExecute execute : executes) {
			orderExecuteAppService.finish(execute.getId(), null, user003);
		}

		// 2016-12-30
		DateUtil.setSysDate(DateUtil.createDay("2016-12-30", dayCount));
		patientNightTestService.calculate(admin001);

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-30 08:30", dayCount));

		pageable = new PageRequest(0, Integer.MAX_VALUE);
		executes = orderExecuteAppService.findNeedSendOrderExecutes(user003, pageable);

		assertTrue(executes.size() == 4);

		// 发送医嘱执行条目
		for (OrderExecute execute : executes) {
			orderExecuteAppService.send(execute.getId(), user003);
		}

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-30 08:50", dayCount));

		// 打印配液单
		fluidOrder = configureFluidAppService.print(dept000n, morningConfigureFluidBatch, userb02);

		assertTrue(fluidOrder.getConfigureFluidExecutes().size() == 2);

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-30 08:55", dayCount));

		// 完成配液医嘱执行条目
		configureFluidAppService.finish(fluidOrder.getId(), userb02);

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-30 09:00", dayCount));

		// 发送配液单
		fluidOrder = configureFluidAppService.distribute(fluidOrder.getId(), userb03);

		assertTrue(fluidOrder.getDistributeDrugExecutes().size() == 2);

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-30 09:10", dayCount));

		pageable = new PageRequest(0, Integer.MAX_VALUE);
		executes = orderExecuteAppService.findNeedExecuteOrderExecutes(user003, pageable);

		assertTrue(executes.size() == 4);

		// 完成医嘱执行条目
		for (OrderExecute execute : executes) {
			orderExecuteAppService.finish(execute.getId(), null, user003);
		}

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-30 14:00", dayCount));

		// 打印配液单
		fluidOrder = configureFluidAppService.print(dept000n, afternoonConfigureFluidBatch,
				userb02);

		assertTrue(fluidOrder.getConfigureFluidExecutes().size() == 2);

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-30 14:30", dayCount));

		// 完成配液
		configureFluidAppService.finish(fluidOrder.getId(), userb02);

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-30 14:35", dayCount));

		// 发送配液单
		fluidOrder = configureFluidAppService.distribute(fluidOrder.getId(), userb03);

		assertTrue(fluidOrder.getDistributeDrugExecutes().size() == 2);

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-30 15:10", dayCount));

		pageable = new PageRequest(0, Integer.MAX_VALUE);
		executes = orderExecuteAppService.findNeedExecuteOrderExecutes(user003, pageable);

		assertTrue(executes.size() == 2);

		// 完成医嘱执行条目
		for (OrderExecute execute : executes) {
			orderExecuteAppService.finish(execute.getId(), null, user003);
		}

		// 2016-12-31
		DateUtil.setSysDate(DateUtil.createDay("2016-12-31", dayCount));
		patientNightTestService.calculate(admin001);

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-31 08:30", dayCount));

		pageable = new PageRequest(0, Integer.MAX_VALUE);
		executes = orderExecuteAppService.findNeedSendOrderExecutes(user003, pageable);

		assertTrue(executes.size() == 0);

		// 发送医嘱执行条目
		for (OrderExecute execute : executes) {
			orderExecuteAppService.send(execute.getId(), user003);
		}

		pageable = new PageRequest(0, Integer.MAX_VALUE);
		executes = orderExecuteAppService.findNeedExecuteOrderExecutes(userb02, pageable);

		assertTrue(executes.size() == 2);

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-31 08:50", dayCount));

		// 打印配液单
		fluidOrder = configureFluidAppService.print(dept000n, morningConfigureFluidBatch, userb02);

		assertTrue(fluidOrder.getConfigureFluidExecutes().size() == 2);

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-31 09:00", dayCount));

		// 配液完成
		configureFluidAppService.finish(fluidOrder.getId(), userb02);

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-31 09:05", dayCount));

		// 发送配液单
		fluidOrder = configureFluidAppService.distribute(fluidOrder.getId(), userb03);

		assertTrue(fluidOrder.getDistributeDrugExecutes().size() == 2);

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-31 09:10", dayCount));

		pageable = new PageRequest(0, Integer.MAX_VALUE);
		executes = orderExecuteAppService.findNeedExecuteOrderExecutes(user003, pageable);

		assertTrue(executes.size() == 4);

		// 完成医嘱执行条目
		for (OrderExecute execute : executes) {
			orderExecuteAppService.finish(execute.getId(), null, user003);
		}

		// 2017-01-01
		DateUtil.setSysDate(DateUtil.createDay("2017-01-01", dayCount));
		patientNightTestService.calculate(admin001);

		DateUtil.setSysDate(DateUtil.createMinute("2017-01-01 09:10", dayCount));

		pageable = new PageRequest(0, Integer.MAX_VALUE);
		executes = orderExecuteAppService.findNeedExecuteOrderExecutes(user003, pageable);

		assertTrue(executes.size() == 2);

		// 完成当天的护理医嘱
		for (OrderExecute execute : executes) {
			orderExecuteAppService.finish(execute.getId(), null, user003);
		}

		DateUtil.setSysDate(DateUtil.createMinute("2017-01-01 09:30", dayCount));

		inspectOrderBuilder = new InspectOrderBuilder();
		inspectOrderBuilder.setVisit(visit001);
		inspectOrderBuilder.setOrderType(inspectOrderType);
		inspectOrderBuilder.setPlaceType(OrderCreateCommand.PlaceType_InPatient);

		InspectApply inspectApply = new InspectApply();
		inspectApply.setGoal("查查是否有问题");

		InspectApplyItem brainCTInspectApplyItem = new InspectApplyItem();
		brainCTInspectApplyItem.setInspectItem(brainCTInspectItem);
		brainCTInspectApplyItem.setArrangeDept(dept444);
		brainCTInspectApplyItem.setInspectDept(dept444);

		inspectApply.addInspectApplyItem(brainCTInspectApplyItem);

		InspectApplyItem brainHCInspectApplyItem = new InspectApplyItem();
		brainHCInspectApplyItem.setInspectItem(brainHCInspectItem);
		brainHCInspectApplyItem.setArrangeDept(dept555);
		brainHCInspectApplyItem.setInspectDept(dept555);

		inspectApply.addInspectApplyItem(brainHCInspectApplyItem);

		inspectOrderBuilder.setInspectApply(inspectApply);

		Order brainInspectOrder = orderAppService.create(inspectOrderBuilder, user002).get(0);

		DateUtil.setSysDate(DateUtil.createMinute("2016-01-01 09:40", dayCount));

		pageable = new PageRequest(0, Integer.MAX_VALUE);
		orders = orderAppService.findNeedVerifyOrders(user003, pageable);

		assertTrue(orders.size() == 1);

		// 核对医嘱
		for (Order order : orders) {
			orderAppService.verify(order.getId(), user003);
		}

		DateUtil.setSysDate(DateUtil.createMinute("2017-01-01 09:45", dayCount));

		pageable = new PageRequest(0, Integer.MAX_VALUE);
		executes = orderExecuteAppService.findNeedSendOrderExecutes(user003, pageable);

		assertTrue(executes.size() == 2);

		// 发送医嘱执行条目
		for (OrderExecute execute : executes) {
			orderExecuteAppService.send(execute.getId(), user003);
		}

		DateUtil.setSysDate(DateUtil.createMinute("2017-01-01 10:10", dayCount));

		pageable = new PageRequest(0, Integer.MAX_VALUE);
		executes = orderExecuteAppService.findNeedExecuteOrderExecutes(user401, pageable);

		assertTrue(executes.size() == 1);

		// 安排检查时间
		inspectAppService.arrange(executes.get(0).getId(),
				DateUtil.createMinute("2017-01-02 14:00", dayCount), "CT检查室（一）", user401);

		DateUtil.setSysDate(DateUtil.createMinute("2017-01-01 10:30", dayCount));

		pageable = new PageRequest(0, Integer.MAX_VALUE);
		executes = orderExecuteAppService.findNeedExecuteOrderExecutes(user501, pageable);

		assertTrue(executes.size() == 1);

		// 安排检查时间
		inspectAppService.arrange(executes.get(0).getId(),
				DateUtil.createMinute("2017-01-03 14:00", dayCount), "核磁检查室（一）", user501);

		// 2017-01-02
		DateUtil.setSysDate(DateUtil.createDay("2017-01-02", dayCount));
		patientNightTestService.calculate(admin001);

		DateUtil.setSysDate(DateUtil.createMinute("2017-01-02 09:10", dayCount));

		pageable = new PageRequest(0, Integer.MAX_VALUE);
		executes = orderExecuteAppService.findNeedExecuteOrderExecutes(user003, pageable);

		assertTrue(executes.size() == 2);

		// 完成当天的护理医嘱
		for (OrderExecute execute : executes) {
			orderExecuteAppService.finish(execute.getId(), null, user003);
		}

		DateUtil.setSysDate(DateUtil.createMinute("2017-01-02 14:40", dayCount));

		pageable = new PageRequest(0, Integer.MAX_VALUE);
		executes = orderExecuteAppService.findNeedExecuteOrderExecutes(user402, pageable);

		assertTrue(executes.size() == 1);

		// 确认CT检查结果
		Map<InspectApplyItem, String> CTResults = new HashMap<InspectApplyItem, String>();
		CTResults.put(brainCTInspectApplyItem, "没啥问题");

		inspectAppService.confirm(executes.get(0).getId(), CTResults, user402);
		// params = new HashMap<String, Object>();
		// params.put(InspectConfirmOrderExecute.InspectResultKey, CTResults);
		// orderExecuteAppService.finish(executes.get(0).getId(), params ,
		// user402);

		DateUtil.setSysDate(DateUtil.createMinute("2017-01-02 15:00", dayCount));

		testUtil.testInspectResult(brainInspectOrder.getId(), 1);

		if ((Boolean) choices.get(ChoiceItem.CancelHC)) {
			inspectAppService.cancel(brainHCInspectApplyItem.getId(), user002);
		}

		// 2017-01-03
		DateUtil.setSysDate(DateUtil.createDay("2017-01-03", dayCount));
		patientNightTestService.calculate(admin001);

		DateUtil.setSysDate(DateUtil.createMinute("2017-01-03 09:10", dayCount));

		pageable = new PageRequest(0, Integer.MAX_VALUE);
		executes = orderExecuteAppService.findNeedExecuteOrderExecutes(user003, pageable);

		assertTrue(executes.size() == 2);

		// 完成当天的护理医嘱
		for (OrderExecute execute : executes) {
			orderExecuteAppService.finish(execute.getId(), null, user003);
		}

		DateUtil.setSysDate(DateUtil.createMinute("2017-01-03 15:00", dayCount));

		if (!(Boolean) choices.get(ChoiceItem.CancelHC)) {
			pageable = new PageRequest(0, Integer.MAX_VALUE);
			executes = orderExecuteAppService.findNeedExecuteOrderExecutes(user502, pageable);

			assertTrue(executes.size() == 1);

			Map<InspectApplyItem, String> HCResults = new HashMap<InspectApplyItem, String>();
			HCResults.put(brainHCInspectApplyItem, "没啥问题");
			inspectAppService.confirm(executes.get(0).getId(), HCResults, user502);

			DateUtil.setSysDate(DateUtil.createMinute("2017-01-03 16:00", dayCount));

			testUtil.testInspectResult(brainInspectOrder.getId(), 2);
		}

		// 2017-01-04
		DateUtil.setSysDate(DateUtil.createDay("2017-01-04", dayCount));
		patientNightTestService.calculate(admin001);

		DateUtil.setSysDate(DateUtil.createMinute("2017-01-04 09:10", dayCount));

		pageable = new PageRequest(0, Integer.MAX_VALUE);
		executes = orderExecuteAppService.findNeedExecuteOrderExecutes(user003, pageable);

		assertTrue(executes.size() == 2);

		// 完成当天的护理医嘱
		for (OrderExecute execute : executes) {
			orderExecuteAppService.finish(execute.getId(), null, user003);
		}

		// 2017-01-05
		DateUtil.setSysDate(DateUtil.createDay("2017-01-05", dayCount));
		patientNightTestService.calculate(admin001);

		DateUtil.setSysDate(DateUtil.createMinute("2017-01-05 08:20", dayCount));

		orders = this.orderDAO.findByVisitAndOrderTypeAndState(visit001, firstNursingOrderType,
				Order.State_Executing, pageable);

		assertTrue(orders.size() == 1);

		orderAppService.stop(orders.get(0).getId(), user002);

		DateUtil.setSysDate(DateUtil.createMinute("2017-01-05 08:30", dayCount));

		// 开立二级护理长期医嘱
		nursingOrderBuilder = new NursingOrderBuilder();
		nursingOrderBuilder.setVisit(visit001);
		nursingOrderBuilder.setFrequencyType(orderFrequencyType_0H);
		nursingOrderBuilder.setOrderType(secondNursingOrderType);

		orderAppService.create(nursingOrderBuilder, user002);

		DateUtil.setSysDate(DateUtil.createMinute("2017-01-05 08:40", dayCount));

		pageable = new PageRequest(0, Integer.MAX_VALUE);
		orders = orderAppService.findNeedVerifyOrders(user003, pageable);

		assertTrue(orders.size() == 1);

		// 核对医嘱
		for (Order order : orders) {
			orderAppService.verify(order.getId(), user003);
		}

		DateUtil.setSysDate(DateUtil.createMinute("2017-01-05 09:10", dayCount));

		pageable = new PageRequest(0, Integer.MAX_VALUE);
		executes = orderExecuteAppService.findNeedExecuteOrderExecutes(user003, pageable);

		assertTrue(executes.size() == 2);

		// 完成当天的护理医嘱
		for (OrderExecute execute : executes) {
			orderExecuteAppService.finish(execute.getId(), null, user003);
		}

		// 2017-01-06
		DateUtil.setSysDate(DateUtil.createDay("2017-01-06", dayCount));
		patientNightTestService.calculate(admin001);

		DateUtil.setSysDate(DateUtil.createMinute("2017-01-06 09:10", dayCount));

		pageable = new PageRequest(0, Integer.MAX_VALUE);
		executes = orderExecuteAppService.findNeedExecuteOrderExecutes(user003, pageable);

		assertTrue(executes.size() == 2);

		// 完成当天的护理医嘱
		for (OrderExecute execute : executes) {
			orderExecuteAppService.finish(execute.getId(), null, user003);
		}

		DateUtil.setSysDate(DateUtil.createMinute("2017-01-06 09:30", dayCount));

		// 将004转科到眼科一
		TransferDeptOrderBuilder transferDeptOrderBuilder = new TransferDeptOrderBuilder();
		transferDeptOrderBuilder.setVisit(visit004);
		transferDeptOrderBuilder.setOrderType(transferDeptOrderType);
		transferDeptOrderBuilder.setExecuteDept(deptddd);

		orderAppService.create(transferDeptOrderBuilder, user002);

		DateUtil.setSysDate(DateUtil.createMinute("2017-01-06 09:32", dayCount));

		pageable = new PageRequest(0, Integer.MAX_VALUE);
		orders = orderAppService.findNeedVerifyOrders(user003, pageable);

		assertTrue(orders.size() == 1);

		// 核对医嘱
		for (Order order : orders) {
			orderAppService.verify(order.getId(), user003);
		}

		DateUtil.setSysDate(DateUtil.createMinute("2017-01-06 09:33", dayCount));

		// 将当天已收的二级护理给退了费
		List<ChargeRecord> records = costDomainService.getChargeRecords(visit004,
				user003.getOperationDepts(), pageable);

		List<ChargeRecord> theRecords = records.stream()
				.filter(record -> DateUtil.isDayIn(record.getCreateDate(),
						DateUtil.getDateStart(DateUtil.getSysDate()))
						&& record.getChargeItemName().equals("二级护理"))
				.collect(Collectors.toList());

		assertTrue(theRecords.size() == 1);

		inPatientAppService.retreat(theRecords.get(0).getId(), false, user003);

		DateUtil.setSysDate(DateUtil.createMinute("2017-01-06 09:35", dayCount));

		pageable = new PageRequest(0, Integer.MAX_VALUE);
		executes = orderExecuteAppService.findNeedSendOrderExecutes(user003, pageable);

		assertTrue(executes.size() == 1);

		// 发送转科申请
		for (OrderExecute execute : executes) {
			orderExecuteAppService.send(execute.getId(), user003);
		}

		visit = visitDomainService.find(visit004.getId());
		assertTrue(visit.getState().equals(Visit.State_TransferDepting));

		DateUtil.setSysDate(DateUtil.createMinute("2017-01-06 09:40", dayCount));

		pageable = new PageRequest(0, Integer.MAX_VALUE);
		executes = orderExecuteAppService.findNeedExecuteOrderExecutes(user003, pageable);

		assertTrue(executes.size() == 1);

		// 完成转科确认
		params = new HashMap<String, Object>();
		params.put(TransferDeptConfirmOrderExecute.RespDoctor, userd02);
		params.put(TransferDeptConfirmOrderExecute.RespNurse, user003);
		params.put(TransferDeptConfirmOrderExecute.Bed, "bedd04");

		for (OrderExecute execute : executes) {
			orderExecuteAppService.finish(execute.getId(), params, user003);
		}

		visit = visitDomainService.find(visit004.getId());

		assertTrue(visit.getState().equals(Visit.State_IntoWard));
		assertTrue(visit.getDeptName().equals(deptddd.getName()));
		assertTrue(visit.getAreaName().equals(dept000n.getName()));

		DateUtil.setSysDate(DateUtil.createMinute("2017-01-06 09:50", dayCount));

		// 为004开立二级护理长期医嘱
		nursingOrderBuilder = new NursingOrderBuilder();
		nursingOrderBuilder.setVisit(visit004);
		nursingOrderBuilder.setFrequencyType(orderFrequencyType_0H);
		nursingOrderBuilder.setOrderType(secondNursingOrderType);

		orderAppService.create(nursingOrderBuilder, userd02);

		pageable = new PageRequest(0, Integer.MAX_VALUE);
		orders = orderAppService.findNeedVerifyOrders(user003, pageable);

		assertTrue(orders.size() == 1);

		DateUtil.setSysDate(DateUtil.createMinute("2017-01-06 09:55", dayCount));

		// 核对医嘱
		for (Order order : orders) {
			orderAppService.verify(order.getId(), user003);
		}

		DateUtil.setSysDate(DateUtil.createMinute("2017-01-06 09:58", dayCount));

		pageable = new PageRequest(0, Integer.MAX_VALUE);
		executes = orderExecuteAppService.findNeedExecuteOrderExecutes(user003, pageable);

		assertTrue(executes.size() == 1);

		// 完成当天的护理医嘱
		for (OrderExecute execute : executes) {
			orderExecuteAppService.finish(execute.getId(), null, user003);
		}

		DateUtil.setSysDate(DateUtil.createMinute("2017-01-06 10:00", dayCount));

		// 为患者001开一个描述医嘱
		describeLongOrder = new LongOrder();
		describeLongOrder.setVisit(visit001);
		describeLongOrder.setOrderType(describeOrderType);
		describeLongOrder.setName(describeOrderType.getName());

		describeLongOrder.setBelongDept(dept000);
		describeLongOrder.setExecuteDept(dept000);
		describeLongOrder.setDescribe("辅助患者翻身");
		describeLongOrder.setFrequencyType(orderFrequencyType_11H);
		describeLongOrder.setPlanStartDate(DateUtil.getSysDate());
		describeLongOrder.setPlanEndDate(DateUtil.addDay(DateUtil.getSysDate(), 2));

		orderAppService.create(describeLongOrder, user002);

		DateUtil.setSysDate(DateUtil.createMinute("2017-01-06 10:02", dayCount));

		pageable = new PageRequest(0, Integer.MAX_VALUE);
		orders = orderAppService.findNeedVerifyOrders(user003, pageable);

		assertTrue(orders.size() == 1);

		for (Order order : orders) {
			orderAppService.verify(order.getId(), user003);
		}

		DateUtil.setSysDate(DateUtil.createMinute("2017-01-06 11:00", dayCount));

		pageable = new PageRequest(0, Integer.MAX_VALUE);
		executes = orderExecuteAppService.findNeedExecuteOrderExecutes(user003, pageable);

		assertTrue(executes.size() == 1);

		// 完成当天的描述医嘱
		for (OrderExecute execute : executes) {
			orderExecuteAppService.finish(execute.getId(), null, user003);
		}

		DateUtil.setSysDate(DateUtil.createMinute("2017-01-06 14:00", dayCount));

		// 为患者004开一个描述医嘱
		describeTemporaryOrder = new TemporaryOrder();
		describeTemporaryOrder.setVisit(visit004);
		describeTemporaryOrder.setOrderType(describeOrderType);
		describeTemporaryOrder.setName(describeOrderType.getName());

		describeTemporaryOrder.setBelongDept(deptddd);
		describeTemporaryOrder.setExecuteDept(deptddd);
		describeTemporaryOrder.setDescribe("辅助患者看天");
		describeTemporaryOrder.setPlanStartDate(DateUtil.getSysDate());

		orderAppService.create(describeTemporaryOrder, userd02);

		DateUtil.setSysDate(DateUtil.createMinute("2017-01-06 14:10", dayCount));

		pageable = new PageRequest(0, Integer.MAX_VALUE);
		orders = orderAppService.findNeedVerifyOrders(user003, pageable);

		assertTrue(orders.size() == 1);

		for (Order order : orders) {
			orderAppService.verify(order.getId(), user003);
		}

		DateUtil.setSysDate(DateUtil.createMinute("2017-01-06 14:30", dayCount));

		pageable = new PageRequest(0, Integer.MAX_VALUE);
		executes = orderExecuteAppService.findNeedExecuteOrderExecutes(user003, pageable);

		assertTrue(executes.size() == 1);

		// 完成描述临嘱
		for (OrderExecute execute : executes) {
			orderExecuteAppService.finish(execute.getId(), null, user003);
		}

		DateUtil.setSysDate(DateUtil.createMinute("2017-01-06 15:00", dayCount));

		// 为患者004开立手术医嘱
		surgeryTemporaryOrder = new TemporaryOrder();
		surgeryTemporaryOrder.setVisit(visit004);
		surgeryTemporaryOrder.setOrderType(surgeryOrderType);
		surgeryTemporaryOrder.setName(surgeryOrderType.getName());

		surgeryTemporaryOrder.setBelongDept(deptddd);
		surgeryTemporaryOrder.setExecuteDept(depteee);
		surgeryTemporaryOrder.setPlanStartDate(DateUtil.addDay(DateUtil.getSysDate(), 1));

		SurgeryApply surgeryApply = new SurgeryApply();

		SurgeryApplyItem surgeryApplyItem = new SurgeryApplyItem();
		surgeryApplyItem.setSurgeryType(surgeryType001);
		surgeryApply.addSurgeryApplyItem(surgeryApplyItem);

		surgeryTemporaryOrder.setApply(surgeryApply);

		orderAppService.create(surgeryTemporaryOrder, userd02);

		DateUtil.setSysDate(DateUtil.createMinute("2017-01-06 15:10", dayCount));

		pageable = new PageRequest(0, Integer.MAX_VALUE);
		orders = orderAppService.findNeedVerifyOrders(user003, pageable);

		assertTrue(orders.size() == 1);

		for (Order order : orders) {
			orderAppService.verify(order.getId(), user003);
		}

		DateUtil.setSysDate(DateUtil.createMinute("2017-01-06 15:12", dayCount));

		pageable = new PageRequest(0, Integer.MAX_VALUE);
		executes = orderExecuteAppService.findNeedSendOrderExecutes(user003, pageable);

		assertTrue(executes.size() == 1);

		// 发送医嘱执行条目
		for (OrderExecute execute : executes) {
			orderExecuteAppService.send(execute.getId(), user003);
		}

		DateUtil.setSysDate(DateUtil.createMinute("2017-01-06 16:00", dayCount));

		pageable = new PageRequest(0, Integer.MAX_VALUE);
		executes = orderExecuteAppService.findNeedExecuteOrderExecutes(usere01, pageable);

		assertTrue(executes.size() == 1);

		// 完成手术安排临嘱
		params = new HashMap<String, Object>();
		params.put(SurgeryArrangeOrderExecute.PlanExecuteDate,
				surgeryTemporaryOrder.getPlanStartDate());
		params.put(SurgeryArrangeOrderExecute.SurgeryPlace, "眼科第一手术室");

		orderExecuteAppService.finish(executes.get(0).getId(), params, usere01);

	}
}
