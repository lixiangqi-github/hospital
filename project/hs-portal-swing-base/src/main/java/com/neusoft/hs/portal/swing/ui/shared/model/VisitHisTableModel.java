package com.neusoft.hs.portal.swing.ui.shared.model;

import com.neusoft.hs.domain.history.visit.VisitHis;
import com.neusoft.hs.portal.swing.util.ConstMessagesCN;
import com.neusoft.hs.portal.swing.util.DateFormatter;

public class VisitHisTableModel extends DefaultTableModel<VisitHis> {

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		VisitHis visitHis = entities.get(rowIndex);
		switch (columnIndex) {
		case 0:
			return visitHis.getId();
		case 1:
			return visitHis.getName();
		case 2:
			return visitHis.getState();
		case 3:
			return DateFormatter.formatDate(visitHis.getCreateHistoryDate());
		default:
			return "";
		}
	}

	@Override
	public String[] getColumnLabels() {
		return new String[] { ConstMessagesCN.Labels.ID, ConstMessagesCN.Labels.VisitName, ConstMessagesCN.Labels.State,
				ConstMessagesCN.Labels.IntoWardDate };
	}
}
