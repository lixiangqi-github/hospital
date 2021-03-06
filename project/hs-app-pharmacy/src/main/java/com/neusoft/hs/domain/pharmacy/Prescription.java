//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\outpatientdept\\Prescription.java

package com.neusoft.hs.domain.pharmacy;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.neusoft.hs.domain.diagnosis.DiagnosisTreatmentItemValue;
import com.neusoft.hs.domain.order.Order;
import com.neusoft.hs.domain.order.OrderCreateCommand;
import com.neusoft.hs.domain.order.OrderException;
import com.neusoft.hs.domain.order.OrderExecuteException;
import com.neusoft.hs.domain.organization.Doctor;
import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.platform.entity.IdEntity;

/**
 * 处方 用于说明一组药品医嘱条目对应的疾病诊断
 * 
 * @author kingbox
 *
 */
@Entity
@Table(name = "app_pharmacy_prescription")
public class Prescription extends IdEntity implements OrderCreateCommand {

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "app_pharmacy_prescription_order", joinColumns = { @JoinColumn(name = "prescription_id", referencedColumnName = "id") }, inverseJoinColumns = { @JoinColumn(name = "order_id", referencedColumnName = "id") })
	private List<Order> orders;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "app_pharmacy_prescription_diagnosis_value", joinColumns = { @JoinColumn(name = "prescription_id", referencedColumnName = "id") }, inverseJoinColumns = { @JoinColumn(name = "diagnosis_value_id", referencedColumnName = "id") })
	private List<DiagnosisTreatmentItemValue> diagnosisTreatmentItemValues;

	@Column(length = 256)
	private String illustrate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "visit_id")
	private Visit visit;

	@Column(length = 16)
	private String visitName;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "creator_id")
	private Doctor creator;

	@Column(length = 32)
	private String creatorName;

	@Column(name = "create_date")
	private Date createDate;

	@Column(length = 256)
	private String template;

	/**
	 * @roseuid 58D0C70F0247
	 */
	public Prescription() {

	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	public void addOrder(Order order) {
		if (this.orders == null) {
			this.orders = new ArrayList<Order>();
		}
		this.orders.add(order);
	}

	@Override
	public String getPlaceType() {
		return this.orders.get(0).getPlaceType();
	}

	public List<DiagnosisTreatmentItemValue> getDiagnosisTreatmentItemValues() {
		return diagnosisTreatmentItemValues;
	}

	public void setDiagnosisTreatmentItemValues(
			List<DiagnosisTreatmentItemValue> diagnosisTreatmentItemValues) {
		this.diagnosisTreatmentItemValues = diagnosisTreatmentItemValues;
	}

	public void addDiagnosisTreatmentItemValue(DiagnosisTreatmentItemValue value) {
		if (this.diagnosisTreatmentItemValues == null) {
			this.diagnosisTreatmentItemValues = new ArrayList<DiagnosisTreatmentItemValue>();
		}
		this.diagnosisTreatmentItemValues.add(value);
	}

	public String getIllustrate() {
		return illustrate;
	}

	public void setIllustrate(String illustrate) {
		this.illustrate = illustrate;
	}

	public Visit getVisit() {
		return visit;
	}

	public void setVisit(Visit visit) {
		this.visit = visit;
	}

	public String getVisitName() {
		return visitName;
	}

	public void setVisitName(String visitName) {
		this.visitName = visitName;
	}

	public Doctor getCreator() {
		return creator;
	}

	public void setCreator(Doctor creator) {
		this.creator = creator;
		this.creatorName = creator.getName();
	}

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	@Override
	public void check() throws OrderException, OrderExecuteException {
		if (diagnosisTreatmentItemValues == null
				|| diagnosisTreatmentItemValues.size() == 0) {
			throw new OrderException(null, "诊断不能为空");
		}
		for (Order order : this.orders) {
			order.check();
		}
	}

	@Override
	public void save() {

		for (Order order : this.orders) {
			order.save();
		}

		this.getService(PrescriptionRepo.class).save(this);
	}

}
