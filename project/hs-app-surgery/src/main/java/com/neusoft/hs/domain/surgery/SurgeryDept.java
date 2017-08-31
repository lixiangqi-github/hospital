package com.neusoft.hs.domain.surgery;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.neusoft.hs.domain.organization.Dept;

@Entity
@DiscriminatorValue("SurgeryDept")
public class SurgeryDept extends Dept {

}
