package com.easybill.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.easybill.model.Email;

@Repository
public interface EmailRepository extends CrudRepository<Email, Integer> {

}
