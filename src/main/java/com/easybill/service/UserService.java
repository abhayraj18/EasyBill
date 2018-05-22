package com.easybill.service;

import com.easybill.exception.EntityExistsException;
import com.easybill.exception.EntityNotFoundException;
import com.easybill.exception.RecentPasswordException;
import com.easybill.exception.ValidationException;
import com.easybill.model.User;
import com.easybill.pojo.ChangePasswordForm;
import com.easybill.pojo.EditUserForm;
import com.easybill.pojo.UserVO;

public interface UserService {

	/**
	 * Service to add new user
	 * 
	 * @param userVO
	 * @return created User
	 * @throws ValidationException
	 * @throws EntityExistsException
	 */
	User addUser(UserVO userVO) throws ValidationException, EntityExistsException;

	/**
	 * Service to get user details by id
	 * @param userId
	 * @return user details
	 * @throws EntityNotFoundException
	 */
	UserVO getUserDetailsById(Integer userId) throws EntityNotFoundException;

	/**
	 * Service to get user by email
	 * @param email
	 * @return user having the email
	 * @throws EntityNotFoundException
	 */
	User findByEmail(String email) throws EntityNotFoundException;
	
	/**
	 * Service to check if user name is available
	 * @param username
	 * @return true if user name is available
	 * @throws EntityExistsException
	 */
	Boolean isUsernameAvailable(String username) throws EntityExistsException;

	/**
	 * Service to check if email is available
	 * @param email
	 * @return true if email is available
	 * @throws EntityExistsException
	 */
	Boolean isEmailAvailable(String email) throws EntityExistsException;

	/**
	 * Service to get user by id
	 * @param id
	 * @return user
	 * @throws EntityNotFoundException
	 */
	User getUserById(Integer id) throws EntityNotFoundException;

	/**
	 * Service to edit user details
	 * @param userForm
	 * @throws EntityNotFoundException
	 * @throws EntityExistsException
	 */
	void editUser(EditUserForm userForm) throws EntityNotFoundException, EntityExistsException;

	/**
	 * Service to in-activate the user
	 * @param userId
	 * @throws EntityNotFoundException
	 */
	void inactivateUser(Integer userId) throws EntityNotFoundException;

	void changePassword(ChangePasswordForm changePasswordForm) throws EntityNotFoundException, RecentPasswordException;

}
