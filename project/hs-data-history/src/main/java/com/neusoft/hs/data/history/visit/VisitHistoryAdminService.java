package com.neusoft.hs.data.history.visit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neusoft.hs.domain.history.visit.VisitHisRepo;
import com.neusoft.hs.domain.history.visit.VisitLogHisRepo;

@Service
@Transactional(rollbackFor = Exception.class)
public class VisitHistoryAdminService {

	@Autowired
	private VisitHisRepo visitHisRepo;

	@Autowired
	private VisitLogHisRepo visitLogHisRepo;

	public void clear() {
		visitHisRepo.deleteAll();
		visitLogHisRepo.deleteAll();
	}
}
