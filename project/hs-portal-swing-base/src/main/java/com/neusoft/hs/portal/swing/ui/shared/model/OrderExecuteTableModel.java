package com.neusoft.hs.portal.swing.ui.shared.model;

import com.neusoft.hs.domain.order.OrderExecute;
import com.neusoft.hs.portal.swing.util.ConstMessagesCN;
import com.neusoft.hs.portal.swing.util.DateFormatter;

public class OrderExecuteTableModel extends DefaultTableModel<OrderExecute> {

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		OrderExecute orderExecute = entities.get(rowIndex);
		switch (columnIndex) {
		case 0:
			return orderExecute.getId();
		case 1:
			return orderExecute.getVisitName();
		case 2:
			return orderExecute.getType();
		case 3:
			return orderExecute.getTip();
		case 4:
			return orderExecute.getState();
		case 5:
			return orderExecute.getBelongDeptName();
		case 6:
			return orderExecute.getExecuteDeptName();
		case 7:
			return DateFormatter.formatDateTime(orderExecute.getPlanStartDate());
		default:
			return "";
		}
	}

	@Override
	public String[] getColumnLabels() {
		return new String[] { ConstMessagesCN.Labels.ID, ConstMessagesCN.Labels.VisitName, ConstMessagesCN.Labels.Type,
				ConstMessagesCN.Labels.Tip, ConstMessagesCN.Labels.State, ConstMessagesCN.Labels.BelongDept,
				ConstMessagesCN.Labels.ExecuteDept, ConstMessagesCN.Labels.PlanStartDate };
	}
}
