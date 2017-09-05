package com.neusoft.hs.portal.swing.ui.forms.order.controller;

import java.awt.event.ItemEvent;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import com.neusoft.hs.domain.inspect.InspectApply;
import com.neusoft.hs.domain.inspect.InspectApplyItem;
import com.neusoft.hs.domain.inspect.InspectDomainService;
import com.neusoft.hs.domain.inspect.InspectItem;
import com.neusoft.hs.domain.order.Apply;
import com.neusoft.hs.domain.order.InspectOrderType;
import com.neusoft.hs.domain.order.OrderType;
import com.neusoft.hs.domain.order.SurgeryOrderType;
import com.neusoft.hs.domain.surgery.SurgeryApply;
import com.neusoft.hs.domain.surgery.SurgeryApplyItem;
import com.neusoft.hs.domain.surgery.SurgeryDomainService;
import com.neusoft.hs.domain.surgery.SurgeryType;
import com.neusoft.hs.platform.exception.HsException;
import com.neusoft.hs.portal.swing.ui.forms.order.view.InspectApplyDialog;
import com.neusoft.hs.portal.swing.ui.forms.order.view.SurgeryApplyDialog;
import com.neusoft.hs.portal.swing.ui.shared.controller.AbstractFrameController;
import com.neusoft.hs.portal.swing.util.Notifications;

@Controller
public class ApplyController extends AbstractFrameController {

	private InspectApply inspectApply;

	private SurgeryApply surgeryApply;

	@Autowired
	private InspectApplyDialog inspectApplyDialog;

	@Autowired
	private SurgeryApplyDialog surgeryApplyDialog;

	@Autowired
	private InspectDomainService inspectDomainService;

	@Autowired
	private SurgeryDomainService surgeryDomainService;

	@PostConstruct
	private void prepareListeners() {

		registerAction(inspectApplyDialog.getConfirmBtn(), (e) -> createInspectApply());
		registerAction(inspectApplyDialog.getCloseBtn(), (e) -> closeInspectApplyDialog());

		registerAction(surgeryApplyDialog.getConfirmBtn(), (e) -> createSurgeryApply());
		registerAction(surgeryApplyDialog.getCloseBtn(), (e) -> closeSurgeryApplyDialog());

	}

	public void changeOrderType(OrderType orderType, ItemEvent e) {

		if (orderType != null) {
			if (orderType instanceof InspectOrderType) {
				Pageable pageable = new PageRequest(0, Integer.MAX_VALUE);
				List<InspectItem> items = inspectDomainService.findInspectItem(pageable);

				inspectApplyDialog.setInspectApply(inspectApply);
				inspectApplyDialog.refreshItems(items);
				inspectApplyDialog.load();

				inspectApplyDialog.setVisible(true);
			} else if (orderType instanceof SurgeryOrderType) {
				Pageable pageable = new PageRequest(0, Integer.MAX_VALUE);
				List<SurgeryType> items = surgeryDomainService.findSurgeryType(pageable);

				surgeryApplyDialog.setSurgeryApply(surgeryApply);
				surgeryApplyDialog.refreshItems(items);
				surgeryApplyDialog.load();

				surgeryApplyDialog.setVisible(true);
			}
		}

	}

	public void createInspectApply() {

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

	public void closeInspectApplyDialog() {
		inspectApplyDialog.dispose();
	}

	public void createSurgeryApply() {

		List<SurgeryType> surgeryTypes = surgeryApplyDialog.getSelectedSurgeryTypes();
		if (surgeryTypes == null || surgeryTypes.size() == 0) {
			Notifications.showFormValidationAlert("请选择手术项目");
			return;
		}
		surgeryApply = new SurgeryApply();
		surgeryApply.setGoal(surgeryApplyDialog.getDescribeTA().getText());

		SurgeryApplyItem surgeryApplyItem;
		for (SurgeryType surgeryType : surgeryTypes) {
			surgeryApplyItem = new SurgeryApplyItem();
			surgeryApplyItem.setSurgeryType(surgeryType);

			surgeryApply.addSurgeryApplyItem(surgeryApplyItem);
		}

		surgeryApplyDialog.dispose();
	}

	public void closeSurgeryApplyDialog() {
		surgeryApplyDialog.dispose();
	}

	public InspectApply getInspectApply() {
		return inspectApply;
	}

	public SurgeryApply getSurgeryApply() {
		return surgeryApply;
	}

	public void clear() {
		inspectApply = null;
		surgeryApply = null;
	}

	@Override
	public void prepareAndOpenFrame() throws HsException {
	}
}
