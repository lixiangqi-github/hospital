package com.neusoft.hs.engine.cost;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class CostFacadeImpl implements CostFacade {

}
