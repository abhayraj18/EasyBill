package com.easybill.service;

import com.easybill.pojo.DistributorVO;
import com.easybill.schema.Distributor;

public interface DistributorService {

	Distributor saveDistributor(Distributor distributor);

	DistributorVO getDistributor(Integer distributorId);

}
