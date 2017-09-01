//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\outpatientoffice\\OutPatientRoom.java

package com.neusoft.hs.domain.outpatientoffice;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.neusoft.hs.domain.organization.Dept;

@Entity
@DiscriminatorValue("OutPatientRoom")
public class OutPatientRoom extends Dept {



	@Override
	public String toString() {
		return getName();
	}
}
