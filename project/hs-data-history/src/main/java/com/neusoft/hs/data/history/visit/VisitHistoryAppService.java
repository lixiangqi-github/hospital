package com.neusoft.hs.data.history.visit;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neusoft.hs.domain.history.visit.VisitHis;
import com.neusoft.hs.domain.history.visit.VisitHisRepo;
import com.neusoft.hs.domain.organization.AbstractUser;
import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.domain.visit.VisitAdminDomainService;
import com.neusoft.hs.platform.exception.HsException;

@Service
@Transactional(rollbackFor = Exception.class)
public class VisitHistoryAppService {

	@Autowired
	private VisitAdminDomainService visitAdminDomainService;

	@Autowired
	private VisitHisRepo visitHisRepo;

	@Autowired
	private VisitHisUtil visitHisUtil;

	public void archive(String cardNumber, AbstractUser user) throws HsException {
		try {
			List<Visit> visits = visitAdminDomainService.findByCardNumber(cardNumber);
			if (visits != null && visits.size() > 0) {
				// 复制患者一次就诊信息
				List<VisitHis> VisitHises = new ArrayList<VisitHis>();
				for (Visit visit : visits) {
					VisitHises.add(visitHisUtil.convert(visit));
				}
				visitHisRepo.save(VisitHises);
			}

			visitAdminDomainService.delete(cardNumber);
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
			throw new HsException(e);
		}
	}

}
