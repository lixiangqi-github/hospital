package com.neusoft.hs.portal.swing.ui.forms.order.controller;

import java.awt.event.ItemEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.swing.JTable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;

import com.neusoft.hs.application.order.OrderAppService;
import com.neusoft.hs.application.outpatientdept.OutPatientDeptAppService;
import com.neusoft.hs.domain.inspect.InspectApply;
import com.neusoft.hs.domain.inspect.InspectApplyItem;
import com.neusoft.hs.domain.inspect.InspectDomainService;
import com.neusoft.hs.domain.inspect.InspectItem;
import com.neusoft.hs.domain.order.DescribeOrderType;
import com.neusoft.hs.domain.order.DrugOrderType;
import com.neusoft.hs.domain.order.DrugOrderTypeApp;
import com.neusoft.hs.domain.order.EnterHospitalOrderType;
import com.neusoft.hs.domain.order.InspectOrderType;
import com.neusoft.hs.domain.order.LongOrder;
import com.neusoft.hs.domain.order.Order;
import com.neusoft.hs.domain.order.OrderAdminDomainService;
import com.neusoft.hs.domain.order.OrderFrequencyType;
import com.neusoft.hs.domain.order.OrderType;
import com.neusoft.hs.domain.order.TemporaryOrder;
import com.neusoft.hs.domain.order.TransferDeptOrderType;
import com.neusoft.hs.domain.organization.AbstractUser;
import com.neusoft.hs.domain.organization.Dept;
import com.neusoft.hs.domain.organization.Doctor;
import com.neusoft.hs.domain.organization.InPatientDept;
import com.neusoft.hs.domain.organization.OrganizationAdminDomainService;
import com.neusoft.hs.domain.outpatientoffice.OutPatientRoom;
import com.neusoft.hs.domain.pharmacy.DrugUseMode;
import com.neusoft.hs.domain.pharmacy.Pharmacy;
import com.neusoft.hs.domain.pharmacy.PharmacyAdminService;
import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.platform.exception.HsException;
import com.neusoft.hs.platform.util.DateUtil;
import com.neusoft.hs.portal.businessview.visit.VisitBusinessView;
import com.neusoft.hs.portal.framework.exception.UIException;
import com.neusoft.hs.portal.framework.security.UserUtil;
import com.neusoft.hs.portal.swing.ui.forms.order.view.CreateOrderFrame;
import com.neusoft.hs.portal.swing.ui.forms.order.view.InspectApplyDialog;
import com.neusoft.hs.portal.swing.ui.shared.controller.AbstractFrameController;
import com.neusoft.hs.portal.swing.ui.shared.model.DeptComboBoxModel;
import com.neusoft.hs.portal.swing.ui.shared.model.DrugUseModeComboBoxModel;
import com.neusoft.hs.portal.swing.ui.shared.model.OrderFrequencyTypeComboBoxModel;
import com.neusoft.hs.portal.swing.ui.shared.model.OrderTableModel;
import com.neusoft.hs.portal.swing.ui.shared.model.OrderTypeComboBoxModel;
import com.neusoft.hs.portal.swing.ui.shared.model.VisitComboBoxModel;
import com.neusoft.hs.portal.swing.util.Notifications;
import com.neusoft.hs.portal.swing.validation.ValidationError;

@Controller
public class CreateOrderController extends AbstractFrameController {

	@Autowired
	private CreateOrderFrame createOrderFrame;

	@Autowired
	private InspectApplyDialog inspectApplyDialog;

	@Autowired
	private OrderAppService orderAppService;

	@Autowired
	private OrderAdminDomainService orderAdminDomainService;

	@Autowired
	private PharmacyAdminService pharmacyAdminService;

	@Autowired
	private OrganizationAdminDomainService organizationAdminDomainService;

	@Autowired
	private OutPatientDeptAppService outPatientDeptAppService;

	@Autowired
	private InspectDomainService inspectDomainService;

	@Autowired
	private VisitBusinessView visitBusinessView;

	@Autowired
	private CreateOrderValidator validator;

	private VisitComboBoxModel visitComboBoxModel;

	private OrderFrequencyTypeComboBoxModel frequencyTypeComboBoxModel;

	private DrugUseModeComboBoxModel orderUseModeComboBoxModel;

	private OrderTypeComboBoxModel orderTypeComboBoxModel;

	private DeptComboBoxModel executeDeptComboBoxModel;

	private InspectApply inspectApply;

	@PostConstruct
	private void prepareListeners() {
		registerAction(createOrderFrame.getCreateOrderPanel().getOrderTypeCB(),
				(e) -> changeOrderType(e));
		registerAction(createOrderFrame.getCompsiteBtn(), (e) -> compsite());
		registerAction(createOrderFrame.getConfirmBtn(), (e) -> create());
		registerAction(createOrderFrame.getCloseBtn(), (e) -> closeWindow());

		registerAction(inspectApplyDialog.getConfirmBtn(), (e) -> createInspectApply());
		registerAction(inspectApplyDialog.getCloseBtn(), (e) -> closeInspectApplyDialog());

	}

	@Override
	public void prepareAndOpenFrame() throws HsException {
		loadOrders();
		loadVisits();
		loadOrderTypes();
		loadFrequencyTypes();
		loadDepts();
		loadOrderUseModes();

		inspectApply = null;
		createOrderFrame.setVisible(true);
	}

	private void loadOrders() throws HsException {

		Sort sort = new Sort(Direction.DESC, "createDate");
		Pageable pageable = new PageRequest(0, Integer.MAX_VALUE, sort);

		AbstractUser user = UserUtil.getUser();

		List<Dept> depts = new ArrayList<Dept>();
		depts.add(user.getDept());

		OutPatientRoom outPatientRoom = outPatientDeptAppService.findOutPatientRoom(user,
				DateUtil.getSysDate());
		if (outPatientRoom != null) {
			depts.add(outPatientRoom);
		}

		List<Order> entities = orderAppService.findByBelongDepts(depts, pageable);

		OrderTableModel orderTableModel = createOrderFrame.getOrderListPanel().getOrderTableModel();
		orderTableModel.clear();
		orderTableModel.addEntities(entities);
	}

	private void loadVisits() throws HsException {

		Pageable pageable = new PageRequest(0, Integer.MAX_VALUE);

		AbstractUser user = UserUtil.getUser();

		List<Visit> entities = visitBusinessView.findVisits(user, pageable);

		visitComboBoxModel = this.createOrderFrame.getCreateOrderPanel().getVisitComboBoxModel();
		visitComboBoxModel.clear();
		visitComboBoxModel.addElements(entities);
	}

	private void loadOrderTypes() throws HsException {
		Pageable pageable = new PageRequest(0, Integer.MAX_VALUE);

		List<Dept> depts = new ArrayList<Dept>();
		Dept dept = UserUtil.getUser().getDept();
		depts.add(dept);

		List<OrderType> orderTypes = this.orderAdminDomainService.findOrderType(pageable);

		orderTypeComboBoxModel = this.createOrderFrame.getCreateOrderPanel()
				.getOrderTypeComboBoxModel();

		orderTypeComboBoxModel.clear();
		orderTypeComboBoxModel.addElement(null);
		orderTypeComboBoxModel.addElements(orderTypes);
	}

	private void loadFrequencyTypes() throws HsException {
		Pageable pageable = new PageRequest(0, Integer.MAX_VALUE);

		List<OrderFrequencyType> entities = orderAdminDomainService.findFrequencyType(pageable);

		frequencyTypeComboBoxModel = this.createOrderFrame.getCreateOrderPanel()
				.getFrequencyTypeComboBoxModel();
		frequencyTypeComboBoxModel.clear();
		frequencyTypeComboBoxModel.addElement(null);
		frequencyTypeComboBoxModel.addElements(entities);
	}

	private void loadDepts() {
		Pageable pageable = new PageRequest(0, Integer.MAX_VALUE);
		List<Dept> depts = organizationAdminDomainService.findDept(pageable);

		executeDeptComboBoxModel = this.createOrderFrame.getCreateOrderPanel()
				.getExecuteDeptComboBoxModel();
		executeDeptComboBoxModel.clear();
		executeDeptComboBoxModel.addElement(null);
		executeDeptComboBoxModel.addElements(depts);
	}

	private void loadOrderUseModes() throws HsException {
		Pageable pageable = new PageRequest(0, Integer.MAX_VALUE);

		List<DrugUseMode> entities = pharmacyAdminService.findDrugUseMode(pageable);

		orderUseModeComboBoxModel = this.createOrderFrame.getCreateOrderPanel()
				.getOrderUseModeComboBoxModel();
		orderUseModeComboBoxModel.clear();
		orderUseModeComboBoxModel.addElement(null);
		orderUseModeComboBoxModel.addElements(entities);
	}

	private void create() {
		try {

			AbstractUser user = UserUtil.getUser();

			OrderFrequencyType frequencyType = frequencyTypeComboBoxModel.getSelectedItem();

			Date planStartDate = this.createOrderFrame.getPlanStartDate();
			Integer executeDay = this.createOrderFrame.getExecuteDay();

			Integer count = this.createOrderFrame.getCount();

			String describe = this.createOrderFrame.getCreateOrderPanel().getDescribeTF().getText();

			Dept executeDept = executeDeptComboBoxModel.getSelectedItem();

			OrderType orderType = orderTypeComboBoxModel.getSelectedItem();

			DrugUseMode drugUseMode = orderUseModeComboBoxModel.getSelectedItem();

			Order order = null;
			if (frequencyType == null) {
				order = new TemporaryOrder();
				order.setPlanStartDate(planStartDate);
			} else {
				order = new LongOrder();
				LongOrder longOrder = (LongOrder) order;
				longOrder.setFrequencyType(frequencyType);
				longOrder.setPlanStartDate(planStartDate);
				if (executeDay != null) {
					longOrder.setPlanEndDate(
							DateUtil.addDay(longOrder.getPlanStartDate(), executeDay));
				}
			}
			order.setVisit(visitComboBoxModel.getSelectedItem());
			order.setOrderType(orderType);
			order.setCount(count);
			order.setDescribe(describe);
			order.setExecuteDept(executeDept);

			if (this.inspectApply != null) {
				order.setApply(this.inspectApply);
			}

			CreateOrderInfo createOrderInfo = new CreateOrderInfo();
			createOrderInfo.setOrder(order);
			createOrderInfo.setDrugUseMode(drugUseMode);
			createOrderInfo.setUser(user);

			Optional<ValidationError> errors = validator.validate(createOrderInfo);
			if (errors.isPresent()) {
				Notifications.showFormValidationAlert(errors.get().getMessage());
			} else {
				orderAppService.create(order, (Doctor) UserUtil.getUser());
				loadOrders();
			}

		} catch (Exception e) {
			e.printStackTrace();
			Notifications.showFormValidationAlert(e.getMessage());
		}
	}

	public void compsite() {
		OrderTableModel orderTableModel = this.createOrderFrame.getOrderListPanel()
				.getOrderTableModel();
		JTable table = this.createOrderFrame.getOrderListPanel().getTable();
		int[] rows = table.getSelectedRows();
		if (rows != null && rows.length == 2) {
			Order order1 = orderTableModel.getEntityByRow(rows[0]);
			Order order2 = orderTableModel.getEntityByRow(rows[1]);

			try {
				orderAppService.compsite(order1.getId(), order2.getId(),
						(Doctor) UserUtil.getUser());
			} catch (Exception e) {
				e.printStackTrace();
				Notifications.showFormValidationAlert(e.getMessage());
			}
		} else {
			Notifications.showFormValidationAlert("请选择两条医嘱");
		}
	}

	private void changeOrderType(ItemEvent e) {
		OrderType orderType = this.createOrderFrame.getCreateOrderPanel()
				.getOrderTypeComboBoxModel().getSelectedItem();
		if (orderType != null) {
			if (orderType instanceof InspectOrderType) {
				Pageable pageable = new PageRequest(0, Integer.MAX_VALUE);
				List<InspectItem> items = inspectDomainService.findInspectItem(pageable);

				inspectApplyDialog.setInspectApply(inspectApply);
				inspectApplyDialog.refreshItems(items);
				inspectApplyDialog.load();

				inspectApplyDialog.setVisible(true);
			}
		}

	}

	private void createInspectApply() {

		List<InspectItem> inspectItems = inspectApplyDialog.getSelectedInspectItems();
		if (inspectItems == null || inspectItems.size() == 0) {
			Notifications.showFormValidationAlert("请选择检查项目");
			return;
		}
		inspectApply = new InspectApply();
		inspectApply.setGoal(inspectApplyDialog.getGoalTA().getText());

		InspectApplyItem inspectApplyItem;
		for (InspectItem inspectItem : inspectItems) {
			inspectApplyItem = new InspectApplyItem();
			inspectApplyItem.setInspectItem(inspectItem);

			inspectApply.addInspectApplyItem(inspectApplyItem);
		}

		inspectApplyDialog.dispose();
	}

	private void closeInspectApplyDialog() {
		inspectApplyDialog.dispose();
	}

	private void closeWindow() {
		createOrderFrame.dispose();
	}
}
