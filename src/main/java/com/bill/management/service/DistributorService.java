package com.bill.management.service;

import com.bill.management.pojo.DistributorVO;
import com.bill.management.schema.Distributor;

public interface DistributorService {

	Distributor saveDistributor(Distributor distributor);

	DistributorVO getDistributor(Integer distributorId);

}
