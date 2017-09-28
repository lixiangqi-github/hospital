package com.neusoft.hs.portal.manage.visit;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.domain.visit.VisitAdminDomainService;
import com.neusoft.hs.platform.exception.HsException;

@Controller
@RequestMapping(value = "/manage/visit")
public class VisitController {
	
	@Autowired
	private VisitAdminDomainService visitAdminDomainService;
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Model model) throws HsException {

		Sort sort = new Sort("createDate");
		Pageable pageable = new PageRequest(0, Integer.MAX_VALUE, sort);
		List<Visit> visits = visitAdminDomainService.find(pageable);

		model.addAttribute("visits", visits);

		return "manage/visit/visit_list";
	}

}
