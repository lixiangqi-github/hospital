package com.neusoft.hs.notice.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neusoft.hs.application.order.OrderExecuteAppService;

@Service
public class OrderExecuteNoticeService {

	@Autowired
	private OrderExecuteAppService orderExecuteAppService;

	public static final int noticeMinute = 5;

	public void notice(){
		
		//orderExecuteAppService.findNeedNoticeOrderExecutes(noticeMinute, pageable)
		
	}

}
