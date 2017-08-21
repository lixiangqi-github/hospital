package com.neusoft.hs.data.init;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.neusoft.hs.data.history.visit.VisitHistoryAdminService;
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
	private VisitHistoryAdminService visitHistoryAdminService;

	@Autowired
	private UserAdminDomainService userAdminDomainService;

	@Before
	public void clear() {
		visitHistoryAdminService.clear();
	}

	@Test
	public void delete() throws HsException {

		Pageable pageable = new PageRequest(0, Integer.MAX_VALUE);
		AbstractUser user = userAdminDomainService.findAdmin(pageable).get(0);

		visitHistoryAppService.archive("402812e45e028dad015e028e3ad00015", user);
	}

}
