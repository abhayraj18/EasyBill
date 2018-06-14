package com.easybill.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.easybill.model.BillInformation;

@Repository
public interface BillInformationRepository extends CrudRepository<BillInformation, Integer>, JpaSpecificationExecutor<BillInformation> {

	@Query(value = "SELECT SUM(EXCESS_AMOUNT) FROM BILL_INFORMATION WHERE ID IN (SELECT ID FROM ORDER_INFO WHERE ORDERED_BY = :userId)", nativeQuery = true)
	Float getExcessAmount(@Param("userId") Integer userId);

	@Modifying
	@Transactional
	@Query(value = "UPDATE BILL_INFORMATION SET EXCESS_AMOUNT = NULL WHERE ID IN (SELECT ID FROM ORDER_INFO WHERE ORDERED_BY = :userId)", nativeQuery = true)
	void resetExcessAmount(Integer userId);

}
