package com.easybill.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.easybill.pojo.DistributorVO;
import com.easybill.repository.DistributorRepository;
import com.easybill.schema.Distributor;
import com.easybill.service.DistributorService;

@Service
public class DistributorServiceImpl implements DistributorService {

	@Autowired
	private DistributorRepository distributorRepository;

	public Distributor saveDistributor(Distributor distributor) {
		return distributorRepository.save(distributor);
	}

	@Override
	public DistributorVO getDistributor(Integer distributorId) {
		Optional<Distributor> distributor = distributorRepository.findById(distributorId);
		if (distributor.isPresent()) {
			Distributor dist = distributor.get();
			DistributorVO distributorVO = new DistributorVO();
			distributorVO.setId(dist.getId());
			distributorVO.setName(dist.getName());
			distributorVO.setPhoneNumber(dist.getPhoneNumber());
			distributorVO.setAddress(dist.getAddress());
			return distributorVO;
		}
		return null;
	}

}
