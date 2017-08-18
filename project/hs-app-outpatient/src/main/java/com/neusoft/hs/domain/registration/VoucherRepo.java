//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\order\\OrderRepo.java

package com.neusoft.hs.domain.registration;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.neusoft.hs.domain.outpatientoffice.OutPatientPlanRecord;
import com.neusoft.hs.domain.visit.Visit;

interface VoucherRepo extends PagingAndSortingRepository<Voucher, String> {

	Voucher findByPlanRecordAndNumber(OutPatientPlanRecord record,
			Integer number);

	List<Voucher> findByVisit(Visit visit);

}
