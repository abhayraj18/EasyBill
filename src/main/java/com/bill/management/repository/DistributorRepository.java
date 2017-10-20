package com.bill.management.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.bill.management.schema.Distributor;

@Repository
public interface DistributorRepository extends CrudRepository<Distributor, Integer>{

}
