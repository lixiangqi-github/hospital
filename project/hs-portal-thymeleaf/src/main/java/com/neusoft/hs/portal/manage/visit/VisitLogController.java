package com.neusoft.hs.portal.manage.visit;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.domain.visit.VisitAdminDomainService;
import com.neusoft.hs.domain.visit.VisitLog;
import com.neusoft.hs.platform.exception.HsException;

@Controller
@RequestMapping(value = "/manage/visit/log")
public class VisitLogController {

	@Autowired
	private VisitAdminDomainService visitAdminDomainService;

	@RequestMapping(value = "/{id}/list", method = RequestMethod.GET)
	public String list(Model model, @PathVariable String id) throws HsException {

		Visit visit = visitAdminDomainService.find(id);
		if (visit == null) {
			throw new HsException("患者一次就诊[%s]不存在", id);
		}
		Sort sort = new Sort("createDate");
		Pageable pageable = new PageRequest(0, Integer.MAX_VALUE, sort);
		List<VisitLog> logs = visitAdminDomainService.getVisitLogs(visit, pageable);

		model.addAttribute("logs", logs);

		return "manage/visit/visit_log_list";
	}

}
