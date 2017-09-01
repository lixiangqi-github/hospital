package com.neusoft.hs.domain.inspect;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.neusoft.hs.domain.organization.Dept;

@Entity
@DiscriminatorValue("InspectDept")
public class InspectDept extends Dept {
}
