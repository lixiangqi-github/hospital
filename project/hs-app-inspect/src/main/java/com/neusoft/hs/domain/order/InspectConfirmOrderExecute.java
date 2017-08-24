package com.neusoft.hs.domain.order;

import java.util.List;
import java.util.Map;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.neusoft.hs.domain.inspect.InspectApplyItem;
import com.neusoft.hs.domain.inspect.InspectDomainService;
import com.neusoft.hs.domain.inspect.InspectException;
import com.neusoft.hs.domain.inspect.InspectResult;
import com.neusoft.hs.domain.organization.AbstractUser;
import com.neusoft.hs.platform.util.DateUtil;

@Entity
@DiscriminatorValue("InspectConfirm")
public class InspectConfirmOrderExecute extends OrderExecute {

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "inspect_apply_item_id")
	private InspectApplyItem inspectApplyItem;

	public final static String InspectResultKey = "InspectResultKey";

	@Override
	protected void doFinish(Map<String, Object> params, AbstractUser user) throws OrderExecuteException {
		super.doFinish(params, user);

		if (params == null) {
			List<InspectResult> results = inspectApplyItem.getInspectApply().getInspectResults();
			if (results == null) {
				throw new OrderExecuteException(this, "检查单[%s]没有录入结果", inspectApplyItem.getInspectApply().getId());
			}

			boolean found = false;
			for (InspectResult result : results) {
				if (result.getInspectApplyItem().equals(inspectApplyItem)) {
					found = true;
					break;
				}
			}
			if (!found) {
				throw new OrderExecuteException(this, "检查单[%s]对应的检查项目[%s]没有录入结果",
						inspectApplyItem.getInspectApply().getId(), inspectApplyItem.getInspectItem().getName());
			}
		} else {
			Map<InspectApplyItem, String> inspectResults = (Map<InspectApplyItem, String>) params.get(InspectResultKey);
			try {
				this.getService(InspectDomainService.class).confirm(this, inspectResults, user);
			} catch (InspectException e) {
				e.printStackTrace();
				throw new OrderExecuteException(this, e);
			}
		}

		inspectApplyItem.setExecuteDate(DateUtil.getSysDate());
		inspectApplyItem.setState(InspectApplyItem.State_Finished);
	}

	@Override
	protected void calTip() {
		StringBuilder builder = new StringBuilder();
		builder.append(inspectApplyItem.getInspectItem().getName());
		this.setTip(builder.toString());
	}

	public InspectApplyItem getInspectApplyItem() {
		return inspectApplyItem;
	}

	public void setInspectApplyItem(InspectApplyItem inspectApplyItem) {
		this.inspectApplyItem = inspectApplyItem;
	}

}
