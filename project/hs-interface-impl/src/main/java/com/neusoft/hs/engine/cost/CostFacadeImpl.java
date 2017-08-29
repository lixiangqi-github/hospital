package com.neusoft.hs.engine.cost;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neusoft.hs.domain.cost.ChargeRecord;
import com.neusoft.hs.domain.cost.CostDomainService;
import com.neusoft.hs.domain.cost.CostException;
import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.domain.visit.VisitDomainService;

@Service
@Transactional(rollbackFor = Exception.class)
public class CostFacadeImpl implements CostFacade {

	@Autowired
	private CostDomainService costDomainService;

	@Autowired
	private VisitDomainService visitDomainService;

	@Autowired
	private ChargeRecordDTOUtil chargeRecordDTOUtil;

	@Override
	public Float charge(String visitId, List<CreateChargeRecordDTO> records)
			throws CostDTOException {
		Visit visit = visitDomainService.find(visitId);
		if (visit == null) {
			throw new CostDTOException("患者一次就诊[%s]不存在", visitId);
		}
		try {
			List<ChargeRecord> chargeRecords = new ArrayList<ChargeRecord>();
			for (CreateChargeRecordDTO record : records) {

				chargeRecords.add(chargeRecordDTOUtil.convert(record));

			}
			return costDomainService.charging(visit, chargeRecords);
		} catch (IllegalAccessException | InvocationTargetException | CostException e) {
			e.printStackTrace();
			throw new CostDTOException(e);
		}
	}

}
