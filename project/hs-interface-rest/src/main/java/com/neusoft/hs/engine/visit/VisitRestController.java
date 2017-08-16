package com.neusoft.hs.engine.visit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VisitRestController {

	@Autowired
	private VisitFacade visitFacade;

	@RequestMapping(method = RequestMethod.GET, value = "/visit/{visitId}/find")
	public VisitDTO find(@PathVariable("visitId") String visitId) throws VisitDTOException {
		return visitFacade.find(visitId);
	}

}
