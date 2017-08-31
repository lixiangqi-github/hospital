package com.neusoft.hs.domain.order;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 会诊申请单
 * 
 * @author kingbox
 *
 */
@Entity
@Table(name = "app_inpatient_consultation_apply")
public class GroupConsultationApply extends Apply {

	@Column(length = 32)
	private String type;

	@Column(length = 512)
	private String result;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

}
