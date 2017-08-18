//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\pharmacy\\DrugTypeRepo.java

package com.neusoft.hs.domain.recordroom;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.neusoft.hs.domain.visit.Visit;

interface MedicalCaseRepo extends PagingAndSortingRepository<MedicalCase, String> {

	MedicalCase findByVisit(Visit visit);

}
