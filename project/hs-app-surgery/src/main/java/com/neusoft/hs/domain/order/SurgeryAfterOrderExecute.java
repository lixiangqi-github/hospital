package com.neusoft.hs.domain.order;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("SurgeryAfter")
public class SurgeryAfterOrderExecute extends OrderExecute{

}
