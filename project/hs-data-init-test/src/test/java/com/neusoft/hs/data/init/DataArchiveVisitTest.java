package com.neusoft.hs.data.init;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.neusoft.hs.data.history.visit.VisitHistoryAppService;
import com.neusoft.hs.domain.organization.AbstractUser;
import com.neusoft.hs.domain.organization.UserAdminDomainService;
import com.neusoft.hs.platform.exception.HsException;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class DataArchiveVisitTest {

	@Autowired
	private VisitHistoryAppService visitHistoryAppService;

	@Autowired
	private UserAdminDomainService userAdminDomainService;

	@Test
	public void delete() throws HsException {
		
		Pageable pageable = new PageRequest(0, Integer.MAX_VALUE);
		AbstractUser user = userAdminDomainService.findAdmin(pageable).get(0);
		
		visitHistoryAppService.archive("xxx", user);
	}

}
