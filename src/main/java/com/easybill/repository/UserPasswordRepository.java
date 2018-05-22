package com.easybill.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.easybill.model.UserPassword;

@Repository
public interface UserPasswordRepository extends PagingAndSortingRepository<UserPassword, Integer> {
	
	@Query(value = "Select * from USER_PASSWORD where USER_ID = :userId order by CREATED_AT DESC LIMIT :limit", nativeQuery = true)
	List<UserPassword> getRecentPasswords(@Param("userId") Integer userId, @Param("limit") Integer limit);

}
