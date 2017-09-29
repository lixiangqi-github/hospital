package com.neusoft.hs.test.notice;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.neusoft.hs.engine.DTOException;
import com.neusoft.hs.platform.exception.HsException;
import com.neusoft.hs.platform.util.DateUtil;
import com.neusoft.hs.test.AppTestService;
import com.neusoft.hs.test.notice.listener.VisitCreateReceiverService;
import com.neusoft.hs.test.notice.listener.VisitIntoWardReceiverService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class PatientMainWithListenerTest {

	@Autowired
	@Qualifier(value = "patientMainTestService")
	private AppTestService appTestService;
	
	@Autowired
	private VisitCreateReceiverService visitCreateReceiverService;
	
	@Autowired
	private VisitIntoWardReceiverService visitIntoWardReceiverService;

	@Before
	public void testInit() throws HsException {

		DateUtil.setSysDate(DateUtil.createDay("2016-12-27"));

		appTestService.init();
	}

	@Test
	public void testExecute() throws HsException, DTOException {
		appTestService.execute();
		
		assertTrue(visitCreateReceiverService.getCreateCount() == 5);
		
		assertTrue(visitIntoWardReceiverService.getIntoWardCount() == 3);
	}

}
