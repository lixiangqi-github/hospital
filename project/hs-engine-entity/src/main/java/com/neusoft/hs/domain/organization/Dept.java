//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\organization\\Dept.java

package com.neusoft.hs.domain.organization;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public abstract class Dept extends Unit {

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "org_id")
	private Org org;

	public Dept() {
	}

	public Dept(String id) {
		this.setId(id);
	}

	public Org getOrg() {
		return org;
	}

	public void setOrg(Org org) {
		this.org = org;
	}

	/**
	 * 部门关联的可操作部门
	 * 
	 * @return
	 */
	@JsonIgnore
	public List<Dept> getOperationDepts() {
		List<Dept> operationDepts = new ArrayList<Dept>();
		operationDepts.add(this);
		return operationDepts;
	}

	public static List<String> getNames(List<Dept> depts) {
		List<String> names = new ArrayList<String>();

		for (Dept dept : depts) {
			names.add(dept.getName());
		}

		return names;
	}
}
