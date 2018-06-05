package com.easybill.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.easybill.model.BillInformation;

@Repository
public interface BillInformationRepository extends CrudRepository<BillInformation, Integer>, JpaSpecificationExecutor<BillInformation> {

}
