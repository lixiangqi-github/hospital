package com.neusoft.hs.data.history.visit;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neusoft.hs.domain.organization.AbstractUser;
import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.domain.visit.VisitAdminDomainService;
import com.neusoft.hs.platform.exception.HsException;
import com.neusoft.hs.platform.log.LogUtil;

@Service
@Transactional(rollbackFor = Exception.class)
public class VisitHistoryAppService {

	@Autowired
	private VisitAdminDomainService visitAdminDomainService;

	@Autowired
	private List<HistoryArchiver> historyArchivers;

	public void archive(String visitId, AbstractUser user) throws HsException {
		try {

			LogUtil.log(this.getClass(), "开始visitId=[{}]的数据迁移", visitId);

			Visit visit = visitAdminDomainService.find(visitId);
			if (visit != null) {
				for (HistoryArchiver historyArchiver : historyArchivers) {
					historyArchiver.archive(visit);
				}
			}

			// 删除原数据
			visitAdminDomainService.delete(visitId);

			LogUtil.log(this.getClass(), "完成visitId=[{}]的数据迁移", visitId);

		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
			throw new HsException(e);
		}
	}

}
