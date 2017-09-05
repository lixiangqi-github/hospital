package com.neusoft.hs.portal.swing.ui.forms.order.execute;

import java.util.HashMap;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JTextField;

import com.neusoft.hs.domain.order.OrderExecute;
import com.neusoft.hs.domain.order.SurgeryArrangeOrderExecute;
import com.neusoft.hs.portal.framework.exception.UIException;
import com.neusoft.hs.portal.swing.util.ConstMessagesCN;
import com.neusoft.hs.portal.swing.widget.SpinnerDate;

public class SurgeryArrangeOrderExecutePanel extends OrderExecutePanel {

	private SpinnerDate executeDateSD;

	private JTextField placeTF;
	
	private static final int TEXT_FIELD_COLUMNS = 20;

	public SurgeryArrangeOrderExecutePanel(OrderExecute orderExecute) {
		super(orderExecute);

		JLabel balanceLbl = new JLabel(ConstMessagesCN.Labels.PlanSurgeryDate);
		add(balanceLbl);

		executeDateSD = new SpinnerDate("yyyy-MM-dd HH:mm");
		add(executeDateSD);

		JLabel placeLbl = new JLabel(ConstMessagesCN.Labels.PlanSurgeryPlace);
		add(placeLbl);

		placeTF = new JTextField(TEXT_FIELD_COLUMNS);
		add(placeTF);
	}

	@Override
	public Map<String, Object> getParams() throws UIException {
		Map<String, Object> params = new HashMap<String, Object>();

		if (executeDateSD.getDate() == null) {
			throw new UIException("请录入手术时间");
		}
		params.put(SurgeryArrangeOrderExecute.PlanExecuteDate, executeDateSD.getDate());

		if (placeTF.getText() == null) {
			throw new UIException("请录入手术地点");
		}
		params.put(SurgeryArrangeOrderExecute.SurgeryPlace, placeTF.getText());

		return params;
	}

}
