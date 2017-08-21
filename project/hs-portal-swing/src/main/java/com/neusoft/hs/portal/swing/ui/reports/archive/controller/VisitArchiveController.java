package com.neusoft.hs.portal.swing.ui.reports.archive.controller;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;

import com.neusoft.hs.data.history.visit.VisitHistoryAdminService;
import com.neusoft.hs.data.history.visit.VisitHistoryAppService;
import com.neusoft.hs.domain.history.visit.VisitHis;
import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.domain.visit.VisitAdminDomainService;
import com.neusoft.hs.platform.exception.HsException;
import com.neusoft.hs.portal.framework.security.UserUtil;
import com.neusoft.hs.portal.swing.ui.reports.archive.view.VisitArchiveReportFrame;
import com.neusoft.hs.portal.swing.ui.shared.controller.AbstractFrameController;
import com.neusoft.hs.portal.swing.ui.shared.model.VisitComboBoxModel;
import com.neusoft.hs.portal.swing.ui.shared.model.VisitHisTableModel;
import com.neusoft.hs.portal.swing.util.Notifications;

@Controller
public class VisitArchiveController extends AbstractFrameController {

	@Autowired
	private VisitArchiveReportFrame visitArchiveReportFrame;

	@Autowired
	private VisitHistoryAppService visitHistoryAppService;

	@Autowired
	private VisitHistoryAdminService visitHistoryAdminService;

	@Autowired
	private VisitAdminDomainService visitAdminDomainService;

	@PostConstruct
	private void prepareListeners() {
		registerAction(visitArchiveReportFrame.getNextPageBtn(), (e) -> nextPage());
		registerAction(visitArchiveReportFrame.getArchiveBtn(), (e) -> archiveVisit());
		registerAction(visitArchiveReportFrame.getCloseBtn(), (e) -> closeWindow());

	}

	@Override
	public void prepareAndOpenFrame() throws HsException {

		loadVisits();

		loadVisitHis();

		visitArchiveReportFrame.setVisible(true);
	}

	public void nextPage() {
		this.visitArchiveReportFrame.nextPage();
		try {
			this.loadVisits();
		} catch (HsException e) {
			e.printStackTrace();
			Notifications.showFormValidationAlert(e.getMessage());
		}
	}

	private void loadVisits() throws HsException {
		Pageable pageable = new PageRequest(this.visitArchiveReportFrame.getPageNumber(), 15);

		List<Visit> entities = visitAdminDomainService.find(pageable);

		VisitComboBoxModel visitComboBoxModel = visitArchiveReportFrame.getVisitComboBoxModel();

		visitComboBoxModel.clear();
		visitComboBoxModel.addElement(null);
		visitComboBoxModel.addElements(entities);
	}

	public void archiveVisit() {

		Visit visit = this.visitArchiveReportFrame.getVisitComboBoxModel().getSelectedItem();
		if (visit != null) {
			try {
				visitHistoryAppService.archive(visit.getId(), UserUtil.getUser());

				loadVisits();
				loadVisitHis();
			} catch (HsException e) {
				e.printStackTrace();
				Notifications.showFormValidationAlert(e.getMessage());
			}
		}
	}

	public void loadVisitHis() {

		VisitHisTableModel visitHisTableModel = visitArchiveReportFrame.getVisitHisTableModel();
		visitHisTableModel.clear();

		Sort sort = new Sort("createHistoryDate");
		Pageable pageable = new PageRequest(0, 15, sort);

		List<VisitHis> entities = visitHistoryAdminService.findVisitHis(pageable);

		visitHisTableModel.addEntities(entities);
	}

	private void closeWindow() {
		visitArchiveReportFrame.dispose();
	}

}
