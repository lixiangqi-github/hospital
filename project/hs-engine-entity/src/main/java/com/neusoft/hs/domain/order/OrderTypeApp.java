//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\order\\OrderTypeApp.java

package com.neusoft.hs.domain.order;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.neusoft.hs.platform.entity.IdEntity;

/**
 * 创建医嘱条目时关联医嘱类型后的附属属性集合父类
 * 
 * @author kingbox
 *
 */
@Entity
@Table(name = "domain_order_type_app")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class OrderTypeApp extends IdEntity {

	@OneToOne(mappedBy = "typeApp", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE })
	private Order order;

	@Column(length = 32)
	protected String category;

	public OrderTypeApp() {
		super();
	}

	public String getCategory() {
		return category;
	}

	protected void setCategory(String category) {
		this.category = category;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	/**
	 * 医嘱分解时回调方法
	 * 
	 * @throws OrderException
	 */
	void doResolve() throws OrderException {
		if (order instanceof TemporaryOrder) {
			OrderExecuteTeam team = order.getOrderType().createExecuteTeam(order, order.getPlanStartDate());
			order.addExecuteTeam(team);
		} else {
			LongOrder longOrder = (LongOrder) order;
			for (int day = 0; day < LongOrder.ResolveDays; day++) {
				// 计算执行时间
				List<Date> executeDates = longOrder.calExecuteDates(day);

				for (Date executeDate : executeDates) {
					OrderExecuteTeam executeTeam = order.getOrderType().createExecuteTeam(order, executeDate);
					// 设置执行时间
					for (OrderExecute execute : executeTeam.getExecutes()) {
						execute.fillPlanDate(executeDate, executeDate);
					}
					// 收集执行条目
					order.addExecuteTeam(executeTeam);
				}
			}
		}
	}

	public void save() {
		this.getService(OrderTypeAppRepo.class).save(this);
	}
}
