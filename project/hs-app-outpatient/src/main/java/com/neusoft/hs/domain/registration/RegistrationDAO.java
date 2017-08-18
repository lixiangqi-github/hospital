package com.neusoft.hs.domain.registration;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neusoft.hs.domain.visit.Visit;

@Service
@Transactional(rollbackFor = Exception.class)
public class RegistrationDAO {

	@Autowired
	private VoucherRepo voucherRepo;

	public void delete(Visit visit) {
		List<Voucher> vouchers = voucherRepo.findByVisit(visit);
		if (vouchers != null) {
			voucherRepo.delete(vouchers);
		}
	}
}
