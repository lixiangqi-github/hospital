package com.neusoft.hs.domain.recordroom;

import java.lang.reflect.InvocationTargetException;

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
public class MedicalCaseHistoryArchiver implements HistoryArchiver {

	@Autowired
	private MedicalCaseHisRepo medicalCaseHisRepo;

	@Autowired
	private MedicalCaseRepo medicalCaseRepo;

	@Autowired
	private MedicalCaseHisUtil medicalCaseHisUtil;

	public void archive(Visit visit) throws HistoryArchiveExecution {
		try {
			MedicalCase medicalCase = medicalCaseRepo.findByVisit(visit);
			if (medicalCase != null) {
				MedicalCaseHis medicalCaseHis = medicalCaseHisUtil.convert(medicalCase);
				medicalCaseHis.setCreateHistoryDate(DateUtil.getSysDate());

				medicalCaseHisRepo.save(medicalCaseHis);

				LogUtil.log(this.getClass(), "复制患者一次就诊[{}]病案信息[{}]", visit.getId(), medicalCase.getId());
			}
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
			throw new HistoryArchiveExecution(e);
		}
	}

}
