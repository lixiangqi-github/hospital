package com.neusoft.hs.portal.swing.ui.forms.order.controller;

import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JButton;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import com.neusoft.hs.domain.order.Apply;
import com.neusoft.hs.domain.order.OrderExecute;
import com.neusoft.hs.domain.order.OrderExecuteDomainService;
import com.neusoft.hs.domain.order.SurgeryAfterOrderExecute;
import com.neusoft.hs.domain.surgery.SurgeryApply;
import com.neusoft.hs.domain.surgery.SurgeryDomainService;
import com.neusoft.hs.domain.surgery.SurgeryType;
import com.neusoft.hs.portal.swing.ui.forms.order.view.OrderExecuteFinishFrame;
import com.neusoft.hs.portal.swing.ui.forms.order.view.SurgeryApplyDialog;
import com.neusoft.hs.portal.swing.ui.shared.controller.AbstractController;
import com.neusoft.hs.portal.swing.util.ConstMessagesCN;
import com.neusoft.hs.portal.swing.util.Notifications;

@Controller
public class OrderExecuteOperationController extends AbstractController {

	@Autowired
	private OrderExecuteFinishFrame orderExecuteFinishFrame;

	@Autowired
	private SurgeryDomainService surgeryDomainService;

	@Autowired
	private OrderExecuteDomainService orderExecuteDomainService;

	@Autowired
	private SurgeryApplyDialog surgeryApplyDialog;

	public void refreshOperation(MouseEvent e2) {
		try {
			List<OrderExecute> orderExecutes = this.orderExecuteFinishFrame
					.getSelectedOrderExecutes();

			if (orderExecutes == null || orderExecutes.size() != 1) {
				Notifications.showFormValidationAlert("请选择一条执行条目");
			}

			OrderExecute orderExecute = orderExecutes.get(0);
			if (orderExecute instanceof SurgeryAfterOrderExecute) {
				JButton maintainSurgeryApplyItemBtn = new JButton(
						ConstMessagesCN.Labels.MaintainSurgeryApplyItem);
				registerAction(maintainSurgeryApplyItemBtn,
						(e) -> maintainSurgeryApplyItemWindow(orderExecute));
				orderExecuteFinishFrame.getOperationBtns()
						.put(maintainSurgeryApplyItemBtn.getText(), maintainSurgeryApplyItemBtn);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
			Notifications.showFormValidationAlert(e1.getMessage());
		}
	}

	private void maintainSurgeryApplyItemWindow(OrderExecute orderExecute) {

		Apply apply = orderExecuteDomainService.findApply(orderExecute.getId());
		if (apply == null) {
			Notifications.showFormValidationAlert("执行条目[%s]没有对应的申请单", orderExecute.getId());
			return;
		}

		Pageable pageable = new PageRequest(0, Integer.MAX_VALUE);
		List<SurgeryType> items = surgeryDomainService.findSurgeryType(pageable);

		SurgeryApply surgeryApply = surgeryDomainService.findSurgeryApply(apply.getId());
		surgeryApplyDialog.setSurgeryApply(surgeryApply);
		surgeryApplyDialog.refreshItems(items);
		surgeryApplyDialog.load();

		surgeryApplyDialog.setVisible(true);
	}

}
