package com.neusoft.hs.platform.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class HisEntity extends SuperEntity {

	@Id
	@Column(name = "id", unique = true, nullable = false, length = 36)
	private String id;

	@Column(name = "create_history_date")
	private Date createHistoryDate;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getCreateHistoryDate() {
		return createHistoryDate;
	}

	public void setCreateHistoryDate(Date createHistoryDate) {
		this.createHistoryDate = createHistoryDate;
	}

}
