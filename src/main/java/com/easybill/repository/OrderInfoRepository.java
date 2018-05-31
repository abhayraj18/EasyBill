package com.easybill.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.easybill.model.OrderInfo;

@Repository
public interface OrderInfoRepository extends CrudRepository<OrderInfo, Integer>, JpaSpecificationExecutor<OrderInfo> {

}
