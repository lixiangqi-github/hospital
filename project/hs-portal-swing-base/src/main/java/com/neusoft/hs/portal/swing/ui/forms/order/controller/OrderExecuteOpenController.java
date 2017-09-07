package com.neusoft.hs.portal.swing.ui.forms.order.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import com.neusoft.hs.application.order.OrderExecuteAppService;
import com.neusoft.hs.domain.order.OrderExecute;
import com.neusoft.hs.domain.organization.Dept;
import com.neusoft.hs.domain.organization.Doctor;
import com.neusoft.hs.domain.organization.Nurse;
import com.neusoft.hs.domain.organization.UserAdminDomainService;
import com.neusoft.hs.platform.exception.HsException;
import com.neusoft.hs.portal.framework.security.UserUtil;
import com.neusoft.hs.portal.swing.ui.forms.order.execute.EnterHospitalIntoWardOrderExecutePanel;
import com.neusoft.hs.portal.swing.ui.forms.order.execute.OrderExecuteOpenFrame;
import com.neusoft.hs.portal.swing.ui.forms.order.execute.OrderExecutePanel;
import com.neusoft.hs.portal.swing.ui.forms.order.execute.TransferDeptConfirmOrderExecutePanel;
import com.neusoft.hs.portal.swing.ui.forms.order.model.DoctorComboBoxModelPanel;
import com.neusoft.hs.portal.swing.ui.forms.order.model.NurseComboBoxModelPanel;
import com.neusoft.hs.portal.swing.ui.forms.order.view.OrderExecuteFinishFrame;
import com.neusoft.hs.portal.swing.ui.shared.controller.AbstractFrameController;
import com.neusoft.hs.portal.swing.ui.shared.model.DoctorComboBoxModel;
import com.neusoft.hs.portal.swing.ui.shared.model.NurseComboBoxModel;
import com.neusoft.hs.portal.swing.util.Notifications;

@Controller
public class OrderExecuteOpenController extends AbstractFrameController {

	@Autowired
	private OrderExecuteFinishFrame orderExecuteFinishFrame;

	@Autowired
	private OrderExecuteOpenFrame orderExecuteOpenFrame;

	@Autowired
	private UserAdminDomainService userAdminDomainService;

	@Autowired
	private OrderExecuteAppService orderExecuteAppService;

	@Autowired
	private OrderExecuteFinishController orderExecuteFinishController;

	@PostConstruct
	private void prepareListeners() {
		registerAction(orderExecuteOpenFrame.getFinishBtn(), (e) -> finish());
		registerAction(orderExecuteOpenFrame.getCloseBtn(), (e) -> closeWindow());
	}

	@Override
	public void prepareAndOpenFrame() throws HsException {
		try {
			List<OrderExecute> orderExecutes = this.orderExecuteFinishFrame
					.getSelectedOrderExecutes();
			if (orderExecutes == null || orderExecutes.size() != 1) {
				Notifications.showFormValidationAlert("请选择一条执行条目");
			}

			this.prepareAndOpenPanel(orderExecutes.get(0));

		} catch (Exception e) {
			e.printStackTrace();
			Notifications.showFormValidationAlert(e.getMessage());
		}

	}

	private void prepareAndOpenPanel(OrderExecute orderExecute) throws HsException {

		orderExecuteOpenFrame.init(orderExecute);

		OrderExecutePanel orderExecutePanel = orderExecuteOpenFrame.getPanel();
		if (orderExecutePanel instanceof EnterHospitalIntoWardOrderExecutePanel) {
			loadNurses((EnterHospitalIntoWardOrderExecutePanel) orderExecutePanel);
		} else if (orderExecutePanel instanceof TransferDeptConfirmOrderExecutePanel) {
			loadDoctors((TransferDeptConfirmOrderExecutePanel) orderExecutePanel);
			loadNurses((TransferDeptConfirmOrderExecutePanel) orderExecutePanel);
		}

		orderExecuteOpenFrame.setVisible(true);
	}

	private void loadDoctors(DoctorComboBoxModelPanel panel) throws HsException {
		Pageable pageable = new PageRequest(0, Integer.MAX_VALUE);

		List<Dept> depts = new ArrayList<Dept>();
		Dept dept = panel.getOrderExecute().getExecuteDept();
		depts.add(dept);

		List<Doctor> doctors = this.userAdminDomainService.findDoctor(depts, pageable);

		DoctorComboBoxModel doctorComboBoxModel = panel.getRespDoctorComboBoxModel();

		doctorComboBoxModel.clear();
		doctorComboBoxModel.addElements(doctors);
	}

	private void loadNurses(NurseComboBoxModelPanel panel) throws HsException {
		Pageable pageable = new PageRequest(0, Integer.MAX_VALUE);

		List<Dept> depts = new ArrayList<Dept>();
		Dept dept = UserUtil.getUser().getDept();
		depts.add(dept);

		List<Nurse> nurses = this.userAdminDomainService.findNurse(depts, pageable);

		NurseComboBoxModel nurseComboBoxModel = panel.getRespNurseComboBoxModel();

		nurseComboBoxModel.clear();
		nurseComboBoxModel.addElements(nurses);
	}

	private void finish() {
		try {
			OrderExecutePanel panel = orderExecuteOpenFrame.getPanel();
			OrderExecute orderExecute = panel.getOrderExecute();

			orderExecuteAppService.finish(orderExecute.getId(), panel.getParams(),
					UserUtil.getUser());

			orderExecuteFinishController.loadOrderExecutes();
			closeWindow();
		} catch (Exception e) {
			e.printStackTrace();
			Notifications.showFormValidationAlert(e.getMessage());
		}
	}

	private void closeWindow() {
		orderExecuteOpenFrame.dispose();
	}
}
