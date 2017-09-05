package com.neusoft.hs.portal.swing.ui.forms.order.execute;

import java.awt.BorderLayout;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JLabel;

import com.neusoft.hs.domain.order.OrderExecute;
import com.neusoft.hs.domain.order.SurgeryArrangeOrderExecute;
import com.neusoft.hs.portal.framework.exception.UIException;
import com.neusoft.hs.portal.swing.util.ConstMessagesCN;
import com.neusoft.hs.portal.swing.widget.SpinnerDate;

public class SurgeryArrangeOrderExecutePanel extends OrderExecutePanel {

	private SpinnerDate executeDateSD;

	public SurgeryArrangeOrderExecutePanel(OrderExecute orderExecute) {
		super(orderExecute);

		this.setLayout(new BorderLayout());

		JLabel balanceLbl = new JLabel(ConstMessagesCN.Labels.PlanSurgeryDate);
		add(balanceLbl, BorderLayout.WEST);

		executeDateSD = new SpinnerDate("yyyy-MM-dd HH:mm");
		add(executeDateSD, BorderLayout.CENTER);
	}

	@Override
	public Map<String, Object> getParams() throws UIException {
		Map<String, Object> params = new HashMap<String, Object>();

		if (executeDateSD.getDate() == null) {
			throw new UIException("请录入计划手术时间");
		}
		params.put(SurgeryArrangeOrderExecute.PlanExecuteDate, executeDateSD.getDate());

		return params;
	}

}
