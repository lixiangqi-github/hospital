//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\order\\OrderType.java

package com.neusoft.hs.domain.order;

import java.util.Date;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.NotEmpty;

import com.neusoft.hs.domain.cost.ChargeItem;
import com.neusoft.hs.platform.entity.SuperEntity;
import com.neusoft.hs.platform.util.DateUtil;

/**
 * 医嘱类型
 * 
 * 通过子类化，负责编写该类型医嘱操作过程中的回调逻辑
 * 
 * @author kingbox
 *
 */
@Entity
@Table(name = "domain_order_type")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region = "orderTypeCache")
public abstract class OrderType extends SuperEntity {

	@Id
	@Column(name = "id", unique = true, nullable = false, length = 36)
	private String id;

	@NotEmpty(message = "代码不能为空")
	@Column(length = 32)
	private String code;

	@NotEmpty(message = "名称不能为空")
	@Column(length = 64)
	private String name;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_id")
	private OrderType parent;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "charge_item_id")
	private ChargeItem chargeItem;

	@OneToMany(mappedBy = "parent")
	private List<OrderType> children;

	@OneToMany(mappedBy = "orderType")
	private List<Order> orders;

	/**
	 * 医嘱创建时的检查回调函数
	 * 
	 * @param order
	 * @throws OrderException
	 * @throws OrderExecuteException
	 * @roseuid 584E66D50265
	 */
	protected void check(Order order) throws OrderException, OrderExecuteException {

	}

	/**
	 * 医嘱创建后的回调函数
	 * 
	 * @param order
	 * @throws OrderException
	 */
	protected void create(Order order) throws OrderException {
	}

	/**
	 * 医嘱分解逻辑
	 * 
	 * @param order
	 * @return
	 * @throws OrderException
	 * @roseuid 584F4A3201B9
	 */
	public void resolveOrder(OrderTypeApp orderTypeApp) throws OrderException {

		Order order = orderTypeApp.getOrder();

		if (order instanceof TemporaryOrder) {
			List<OrderExecuteTeam> teams = this.createExecuteTeams(order, order.getPlanStartDate());
			for (OrderExecuteTeam team : teams) {
				order.addExecuteTeam(team);
			}
			// 设置执行时间
			for (OrderExecute execute : order.getResolveOrderExecutes()) {
				execute.fillPlanDate(order.getPlanStartDate(), order.getPlanStartDate());
			}
			if (order.getResolveOrderExecutes().size() == 0) {
				throw new OrderException(order, "没有分解出执行条目");
			}
		} else {
			LongOrder longOrder = (LongOrder) order;

			int resolveDays;
			if (order.isInPatient()) {
				resolveDays = LongOrder.ResolveDays;// 住院长嘱分解指定天数
			} else {
				resolveDays = DateUtil.calDay(longOrder.getPlanStartDate(), longOrder.getPlanEndDate());// 门诊长嘱一次性分解完
			}

			for (int day = 0; day < resolveDays; day++) {
				// 计算执行时间
				List<Date> executeDates = longOrder.calExecuteDates(day);

				for (Date executeDate : executeDates) {
					// 清空上一频次的执行条目集合
					order.clearResolveFrequencyOrderExecutes();
					// 创建一个频次的执行条目集合
					List<OrderExecuteTeam> teams = this.createExecuteTeams(order, executeDate);
					for (OrderExecuteTeam team : teams) {
						// 设置执行时间
						for (OrderExecute execute : team.getExecutes()) {
							execute.fillPlanDate(executeDate, executeDate);
						}
						// 收集执行条目
						order.addExecuteTeam(team);
					}
					if (order.getResolveFrequencyOrderExecutes().size() == 0) {
						throw new OrderException(order, "没有分解出执行条目");
					}
				}
			}

			if (order.isInPatient()) {
				// 没有分解出执行条目，设置之前分解的最后一条为last
				if (order.getResolveOrderExecutes().size() == 0 && longOrder.getPlanEndDate() != null
						&& longOrder.getPlanEndDate().before(DateUtil.getSysDate())) {
					OrderExecute lastOrderExecute = order.getLastOrderExecute();
					if (lastOrderExecute != null) {
						lastOrderExecute.setLast(true);
						lastOrderExecute.save();
					}
				}
			} else {
				// 门诊长嘱，分解的最后一条就是last
				int size = order.getResolveOrderExecutes().size();
				if (size > 0) {
					order.getResolveOrderExecutes().get(size - 1).setLast(true);
				}
			}
		}
	}

	/**
	 * 在医嘱分解时创建一组执行条目组
	 * 
	 * @param order
	 * @param planExecuteDate
	 * @return
	 * @throws OrderException
	 */
	protected List<OrderExecuteTeam> createExecuteTeams(Order order, Date planExecuteDate) throws OrderException {
		throw new OrderException(order, "该方法没有被子类实现");
	}

	/**
	 * 医嘱核对后的回调函数
	 * 
	 * @param order
	 * @throws OrderException
	 */
	protected void verify(Order order) throws OrderException {
	}

	/**
	 * 医嘱删除后的回调函数
	 * 
	 * @param order
	 * @throws OrderException
	 */
	public void delete(Order order) throws OrderException {

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ChargeItem getChargeItem() {
		return chargeItem;
	}

	public void setChargeItem(ChargeItem chargeItem) {
		this.chargeItem = chargeItem;
	}

	public OrderType getParent() {
		return parent;
	}

	public void setParent(OrderType parent) {
		this.parent = parent;
	}

	public List<OrderType> getChildren() {
		return children;
	}

	public void setChildren(List<OrderType> children) {
		this.children = children;
	}

	@Override
	public String toString() {
		return name;
	}
}
