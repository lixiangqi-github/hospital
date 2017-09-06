//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\visit\\VisitLogRepo.java

package com.neusoft.hs.domain.visit;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

interface VisitPlanRecordRepo extends PagingAndSortingRepository<VisitPlanRecord, String> {

	List<VisitPlanRecord> findByVisit(Visit visit, Pageable pageable);

}
