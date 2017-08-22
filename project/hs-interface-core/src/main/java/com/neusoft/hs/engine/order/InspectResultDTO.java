package com.neusoft.hs.engine.order;

import java.util.Date;

/**
 * 检查结果
 * 
 * @author kingbox
 *
 */

public class InspectResultDTO {

	private String id;

	private String inspectApplyItemId;

	private String result;

	private String inspectDeptId;

	private Date createDate;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getInspectApplyItemId() {
		return inspectApplyItemId;
	}

	public void setInspectApplyItemId(String inspectApplyItemId) {
		this.inspectApplyItemId = inspectApplyItemId;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getInspectDeptId() {
		return inspectDeptId;
	}

	public void setInspectDeptId(String inspectDeptId) {
		this.inspectDeptId = inspectDeptId;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

}
