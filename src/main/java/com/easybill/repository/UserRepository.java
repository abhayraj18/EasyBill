package com.easybill.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.easybill.model.User;
import com.easybill.model.metadata.EnumConstant.Status;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
	
	Optional<User> findByUsernameAndStatus(String username, Status status);

	Optional<User> findByEmail(String email);

	boolean existsByEmailAndStatus(String email, Status status);

	boolean existsByUsernameAndStatus(String username, Status active);

}
