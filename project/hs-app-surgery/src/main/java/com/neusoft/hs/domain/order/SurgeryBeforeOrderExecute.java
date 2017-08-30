package com.neusoft.hs.domain.order;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("SurgeryBefore")
public class SurgeryBeforeOrderExecute extends OrderExecute {

}
