package com.neusoft.hs.domain.medicalrecord;

import com.neusoft.hs.platform.exception.HsException;

public class MedicalRecordException extends HsException {

	private MedicalRecord record;

	public MedicalRecordException(MedicalRecord record) {
		super();
		this.record = record;
	}

	public MedicalRecordException(MedicalRecord record, String arg0,
			Throwable arg1, Object... params) {
		super(String.format(arg0, params), arg1);
		this.record = record;
	}

	public MedicalRecordException(MedicalRecord record, String arg0,
			Object... params) {
		super(String.format(arg0, params));
		this.record = record;
	}

	public MedicalRecordException(MedicalRecord record, Throwable arg0) {
		super(arg0);
		this.record = record;
	}

	public MedicalRecord getRecord() {
		return record;
	}

}
