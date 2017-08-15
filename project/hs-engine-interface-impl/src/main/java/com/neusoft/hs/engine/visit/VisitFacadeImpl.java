package com.neusoft.hs.engine.visit;

import java.lang.reflect.InvocationTargetException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.domain.visit.VisitDomainService;

@Service
@Transactional(rollbackFor = Exception.class)
public class VisitFacadeImpl implements VisitFacade {

	@Autowired
	private VisitDomainService visitDomainService;

	@Override
	public VisitDTO find(String visitId) throws VisitDTOException{
		Visit visit = visitDomainService.find(visitId);
		if (visit != null) {
			try {
				return VisitDTOUtil.convert(visit);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
				throw new VisitDTOException(e);
			} catch (InvocationTargetException e) {
				e.printStackTrace();
				throw new VisitDTOException(e);
			}
		} else {
			return null;
		}
	}

}
