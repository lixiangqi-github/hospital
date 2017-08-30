package com.neusoft.hs.domain.order;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("SurgeryArrange")
public class SurgeryArrangeOrderExecute extends OrderExecute {

}
