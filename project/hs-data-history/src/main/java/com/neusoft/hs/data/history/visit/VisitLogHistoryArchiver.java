package com.neusoft.hs.data.history.visit;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neusoft.hs.domain.history.visit.VisitHis;
import com.neusoft.hs.domain.history.visit.VisitHisRepo;
import com.neusoft.hs.domain.history.visit.VisitLogHis;
import com.neusoft.hs.domain.history.visit.VisitLogHisRepo;
import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.domain.visit.VisitLog;
import com.neusoft.hs.platform.log.LogUtil;
import com.neusoft.hs.platform.util.DateUtil;

@Service
@Transactional(rollbackFor = Exception.class)
public class VisitLogHistoryArchiver {

	@Autowired
	private VisitLogHisRepo visitLogHisRepo;

	@Autowired
	private VisitLogHisUtil visitLogHisUtil;

	public void archive(Visit visit) throws IllegalAccessException, InvocationTargetException {

		List<VisitLogHis> visitLogHises = new ArrayList<VisitLogHis>();
		VisitLogHis visitLogHis;

		Date sysDate = DateUtil.getSysDate();
		
		List<VisitLog> logs = visit.getLogs();
		if (logs != null) {
			for (VisitLog log : logs) {
				visitLogHis = visitLogHisUtil.convert(log);
				visitLogHis.setCreateHistoryDate(sysDate);

				visitLogHises.add(visitLogHis);
			}
		}
		visitLogHisRepo.save(visitLogHises);

		LogUtil.log(this.getClass(), "复制患者一次就诊日志信息[{}]个", visitLogHises.size());
	}

}
