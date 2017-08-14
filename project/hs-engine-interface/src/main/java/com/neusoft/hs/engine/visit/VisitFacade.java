package com.neusoft.hs.engine.visit;

import com.neusoft.hs.platform.exception.HsException;

public interface VisitFacade {

//	public Visit create(CreateVisitDTO createVisitDto);
//
//	public void intoWard(ReceiveVisitDTO receiveVisitDto);

	/**
	 * 患者离院
	 * 
	 * @param leaveHospitalDTO
	 * @throws HsException
	 */
	public void leaveHospital(LeaveHospitalDTO leaveHospitalDTO)
			throws HsException;

	public VisitDTO find(String visitId);
//
//	public Visit findLastVisit(String cardNumber);
//
//	public List<Visit> listInPatientVisit(int pageNumber, int pageSize);
//
//	public List<Visit> findByStateAndDept(String state, String deptId,
//			int pageNumber, int pageSize);
//
//	public List<Visit> listVisit(String respDeptId, int pageNumber, int pageSize);

}
