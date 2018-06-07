package com.easybill.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.easybill.model.PaymentInformation;

@Repository
public interface PaymentInformationRepository extends CrudRepository<PaymentInformation, Integer> {

}
