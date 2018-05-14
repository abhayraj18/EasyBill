package com.easybill.service;

import com.easybill.exception.EntityNotFoundException;
import com.easybill.exception.ValidationException;
import com.easybill.model.User;
import com.easybill.pojo.UserVO;

public interface UserService {

	User saveUser(UserVO userVO) throws ValidationException;

	UserVO getUserById(Integer userId) throws EntityNotFoundException;

	UserVO findByEmail(String email) throws EntityNotFoundException;

	Boolean isEmailAvailable(String email);

}
