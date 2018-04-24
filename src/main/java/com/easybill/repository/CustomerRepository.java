package com.easybill.repository;

import org.springframework.data.repository.CrudRepository;

import com.easybill.schema.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Integer>{

}
