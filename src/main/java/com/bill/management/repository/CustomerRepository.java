package com.bill.management.repository;

import org.springframework.data.repository.CrudRepository;

import com.bill.management.schema.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Integer>{

}
