package com.easybill.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.easybill.model.OrderDetail;

@Repository
public interface OrderDetailRepository extends CrudRepository<OrderDetail, Integer> {

	List<OrderDetail> findByIdIn(List<Integer> idList);

	@Modifying
	@Transactional
	void deleteByIdIn(List<Integer> idList);

}
