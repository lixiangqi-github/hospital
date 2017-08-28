package com.neusoft.hs.domain.organization;

import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Entity
@DiscriminatorValue("Admin")
public class Admin extends AbstractUser {

	public Admin() {
	}

	public Admin(String id) {
		this.setId(id);
	}

	@Override
	public List<Dept> getOperationDepts() {
		Pageable pageable = new PageRequest(0, Integer.MAX_VALUE);
		return this.getService(UnitRepo.class).findDepts(this.getOrg(), pageable);
	}

	@Override
	public String getDeptId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setDeptId(String deptId) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getOrgId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOrgId(String orgId) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<String> getRoleIds() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setRoleIds(List<String> roleIds) {
		// TODO Auto-generated method stub

	}

	@Override
	public Dept getDept() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setDept(Dept dept) {
		// TODO Auto-generated method stub

	}
}
