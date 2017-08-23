package com.neusoft.hs.portal.swing.ui.forms.order.execute;

import java.awt.BorderLayout;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JLabel;

import com.neusoft.hs.domain.order.InspectArrangeOrderExecute;
import com.neusoft.hs.domain.order.OrderExecute;
import com.neusoft.hs.portal.framework.exception.UIException;
import com.neusoft.hs.portal.swing.util.ConstMessagesCN;
import com.neusoft.hs.portal.swing.widget.SpinnerDate;

public class InspectArrangeOrderExecutePanel extends OrderExecutePanel {

	private SpinnerDate inspectSD;

	public InspectArrangeOrderExecutePanel(OrderExecute orderExecute) {
		super(orderExecute);

		this.setLayout(new BorderLayout());

		JLabel balanceLbl = new JLabel(ConstMessagesCN.Labels.PlanInspectDate);
		add(balanceLbl, BorderLayout.WEST);

		inspectSD = new SpinnerDate("yyyy-MM-dd HH:mm");
		add(inspectSD, BorderLayout.CENTER);
	}

	@Override
	public Map<String, Object> getParams() throws UIException {
		Map<String, Object> params = new HashMap<String, Object>();

		if (inspectSD.getDate() == null) {
			throw new UIException("请录入计划检查时间");
		}
		params.put(InspectArrangeOrderExecute.PlanExecuteDate, inspectSD.getDate());

		return params;
	}

}
