package com.easybill.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.easybill.model.Distributor;

@Repository
public interface DistributorRepository extends CrudRepository<Distributor, Integer> {

}
