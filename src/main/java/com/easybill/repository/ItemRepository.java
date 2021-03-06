package com.easybill.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.easybill.model.Item;

@Repository
public interface ItemRepository extends CrudRepository<Item, Integer>, JpaSpecificationExecutor<Item> {

}
