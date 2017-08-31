package com.neusoft.hs.domain.surgery;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.neusoft.hs.platform.entity.IdEntity;

/**
 * 手术申请项目
 * 
 * @author kingbox
 *
 */
@Entity
@Table(name = "app_surgery_apply_item")
public class SurgeryApplyItem extends IdEntity {

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "surgery_apply_id")
	private SurgeryApply surgeryApply;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "surgery_type_id")
	private SurgeryType surgeryType;

	public SurgeryApply getSurgeryApply() {
		return surgeryApply;
	}

	public void setSurgeryApply(SurgeryApply surgeryApply) {
		this.surgeryApply = surgeryApply;
	}

	public SurgeryType getSurgeryType() {
		return surgeryType;
	}

	public void setSurgeryType(SurgeryType surgeryType) {
		this.surgeryType = surgeryType;
	}

	public void save() {
		this.getService(SurgeryApplyItemRepo.class).save(this);
	}

}
