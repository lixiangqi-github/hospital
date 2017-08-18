package com.neusoft.hs.data.history.visit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neusoft.hs.domain.organization.AbstractUser;
import com.neusoft.hs.domain.visit.VisitAdminDomainService;

@Service
@Transactional(rollbackFor = Exception.class)
public class VisitHistoryService {

	@Autowired
	private VisitAdminDomainService visitAdminDomainService;

	public void archive(String carNumber, AbstractUser user) {

	}

}
