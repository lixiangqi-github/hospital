package com.neusoft.hs.domain.organization;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.neusoft.hs.platform.entity.SuperEntity;
import com.neusoft.hs.platform.user.User;

@Entity
@Table(name = "domain_organization_user")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@JsonTypeInfo(use = com.fasterxml.jackson.annotation.JsonTypeInfo.Id.CLASS, include = As.PROPERTY, property = "@class")
public abstract class AbstractUser extends SuperEntity implements User {

	@Id
	@Column(name = "id", unique = true, nullable = false, length = 36)
	private String id;

	@NotEmpty(message = "名称不能为空")
	@Column(length = 32)
	private String name;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "org_id")
	private Org org;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public abstract Dept getDept();

	public abstract void setDept(Dept dept);

	public Org getOrg() {
		return org;
	}

	public void setOrg(Org org) {
		this.org = org;
	}

	/**
	 * 用户可操作的部门集合
	 * 
	 * @return
	 */
	public List<Dept> getOperationDepts() {
		return this.getDept().getOperationDepts();
	}

	public void delete() {
		this.getService(AbstractUserRepo.class).delete(this);
	}

	@Override
	public String toString() {
		return name;
	}

	public void doLoad() {
		if (this.getDept() != null) {
			this.getDept().doLoad();
		}
	}
}
