package com.neusoft.hs.engine.visit;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("engine-service")
public interface VisitFacadeStub extends VisitFacade {
	
	@RequestMapping(method = RequestMethod.GET, value = "/visit/{visitId}/find")
	public VisitDTO find(@PathVariable("visitId") String visitId);

}
