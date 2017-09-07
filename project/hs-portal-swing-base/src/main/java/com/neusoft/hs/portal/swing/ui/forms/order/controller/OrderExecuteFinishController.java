package com.neusoft.hs.portal.swing.ui.forms.order.controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.swing.JCheckBox;
import javax.swing.JTable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;

import com.neusoft.hs.application.order.OrderExecuteAppService;
import com.neusoft.hs.domain.order.OrderExecute;
import com.neusoft.hs.domain.order.SurgeryAfterOrderExecute;
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
import com.neusoft.hs.portal.swing.ui.shared.model.OrderExecuteTableModel;
import com.neusoft.hs.portal.swing.util.Notifications;

@Controller
public class OrderExecuteFinishController extends AbstractFrameController {

	@Autowired
	private OrderExecuteFinishFrame orderExecuteFinishFrame;

	@Autowired
	private OrderExecuteAppService orderExecuteAppService;

	@Autowired
	private OrderExecuteOpenController orderExecuteOpenController;

	@PostConstruct
	private void prepareListeners() {
		registerAction(orderExecuteFinishFrame.getDisplayAllCB(), (e) -> loadOrderExecutes());
		registerAction(orderExecuteFinishFrame.getOpenBtn(), (e) -> open());
		registerAction(orderExecuteFinishFrame.getConfirmBtn(), (e) -> finish());
		registerAction(orderExecuteFinishFrame.getCloseBtn(), (e) -> closeWindow());

		registerAction(orderExecuteFinishFrame.getTable(), new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				refreshOperation(e);
			}
		});

	}

	@Override
	public void prepareAndOpenFrame() throws HsException {
		loadOrderExecutes();
		orderExecuteFinishFrame.setVisible(true);
	}

	public void loadOrderExecutes() {
		try {
			Sort sort = new Sort(Direction.DESC, "planStartDate");
			Pageable pageable = new PageRequest(0, Integer.MAX_VALUE, sort);

			List<OrderExecute> entities = null;
			JCheckBox displayAllCB = this.orderExecuteFinishFrame.getDisplayAllCB();
			if (displayAllCB.isSelected()) {
				entities = orderExecuteAppService
						.findAllNeedExecuteOrderExecutes(UserUtil.getUser(), pageable);
			} else {
				entities = orderExecuteAppService.findNeedExecuteOrderExecutes(UserUtil.getUser(),
						pageable);
			}

			OrderExecuteTableModel orderExecuteTableModel = this.orderExecuteFinishFrame
					.getOrderExecuteTableModel();
			orderExecuteTableModel.clear();
			orderExecuteTableModel.addEntities(entities);
		} catch (HsException e) {
			e.printStackTrace();
			Notifications.showFormValidationAlert(e.getMessage());
		}

	}

	private void open() {
		try {
			orderExecuteOpenController.prepareAndOpenFrame();
		} catch (HsException e) {
			e.printStackTrace();
			Notifications.showFormValidationAlert(e.getMessage());
		}
	}

	private void finish() {
		try {
			List<OrderExecute> orderExecutes = this.orderExecuteFinishFrame
					.getSelectedOrderExecutes();

			List<String> orderExecuteIds = new ArrayList<String>();
			for (OrderExecute orderExecute : orderExecutes) {
				orderExecuteIds.add(orderExecute.getId());
			}

			orderExecuteAppService.finish(orderExecuteIds, null, UserUtil.getUser());

			loadOrderExecutes();

		} catch (Exception e) {
			e.printStackTrace();
			Notifications.showFormValidationAlert(e.getMessage());
		}
	}

	private void refreshOperation(MouseEvent e) {
		try {
			List<OrderExecute> orderExecutes = this.orderExecuteFinishFrame
					.getSelectedOrderExecutes();

			if (orderExecutes == null || orderExecutes.size() != 1) {
				Notifications.showFormValidationAlert("请选择一条执行条目");
			}

			OrderExecute orderExecute = orderExecutes.get(0);
			if (orderExecute instanceof SurgeryAfterOrderExecute) {

			}
		} catch (Exception e1) {
			e1.printStackTrace();
			Notifications.showFormValidationAlert(e1.getMessage());
		}
	}

	private void closeWindow() {
		orderExecuteFinishFrame.dispose();
	}
}
