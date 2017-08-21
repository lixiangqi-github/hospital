package com.neusoft.hs.domain.history.visit;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neusoft.hs.domain.history.framework.HistoryArchiveExecution;
import com.neusoft.hs.domain.history.framework.HistoryArchiver;
import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.platform.log.LogUtil;
import com.neusoft.hs.platform.util.DateUtil;

@Service
@Transactional(rollbackFor = Exception.class)
public class VisitHistoryArchiver implements HistoryArchiver {

	@Autowired
	private VisitHisRepo visitHisRepo;

	@Autowired
	private VisitHisUtil visitHisUtil;

	public void archive(Visit visit) throws HistoryArchiveExecution {
		// 复制患者一次就诊信息
		try {
			VisitHis visitHis = visitHisUtil.convert(visit);

			visitHis.setCreateHistoryDate(DateUtil.getSysDate());

			visitHisRepo.save(visitHis);

			LogUtil.log(this.getClass(), "复制患者一次就诊信息[{}]", visit.getId());

		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
			throw new HistoryArchiveExecution(e);
		}

	}

}
