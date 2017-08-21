package com.neusoft.hs.data.history.visit;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neusoft.hs.domain.history.visit.VisitHis;
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

	public List<VisitHis> findVisitHis(Pageable pageable) {
		return visitHisRepo.findAll(pageable).getContent();
	}
}
