package com.easybill.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.easybill.exception.EntityExistsException;
import com.easybill.exception.EntityNotFoundException;
import com.easybill.exception.RecentPasswordException;
import com.easybill.exception.ValidationException;
import com.easybill.model.Distributor;
import com.easybill.model.Email.EmailType;
import com.easybill.model.Role;
import com.easybill.model.User;
import com.easybill.model.UserPassword;
import com.easybill.model.Wholesaler;
import com.easybill.model.metadata.EnumConstant.RoleName;
import com.easybill.model.metadata.EnumConstant.Status;
import com.easybill.model.metadata.EnumConstant.UserType;
import com.easybill.pojo.ChangePasswordForm;
import com.easybill.pojo.EditUserForm;
import com.easybill.pojo.ResetPasswordForm;
import com.easybill.pojo.UserVO;
import com.easybill.pojo.OTPForm;
import com.easybill.repository.UserPasswordRepository;
import com.easybill.repository.UserRepository;
import com.easybill.service.EmailService;
import com.easybill.service.RoleService;
import com.easybill.service.UserService;
import com.easybill.util.CommonUtil;
import com.easybill.util.Constants;
import com.easybill.util.DateUtil;
import com.easybill.util.ExceptionMessage;
import com.easybill.util.RestUrlConstants;
import com.easybill.util.otp.OTPUtil;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private RoleService roleService;

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserPasswordRepository userPasswordRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
    private ModelMapper modelMapper;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private OTPUtil otpUtil;
	
	@Autowired
	private CommonUtil commonUtil;
	
	@Override
	public Boolean isEmailAvailable(String email) throws EntityExistsException {
		if (userRepository.existsByEmailAndStatus(email, Status.ACTIVE)) {
			throw new EntityExistsException("User already exists with email: " + email);
		}
		return Boolean.TRUE;
	}
	
	@Override
	public Boolean isUsernameAvailable(String username) throws EntityExistsException {
		if (userRepository.existsByUsernameAndStatus(username, Status.ACTIVE)) {
			throw new EntityExistsException("User already exists with username: " + username);
		}
		return Boolean.TRUE;
	}
	
	@Override
	public Boolean doesUserExistWithEmail(String email) throws EntityNotFoundException {
		if (!userRepository.existsByEmailAndStatus(email, Status.ACTIVE)) {
			throw new EntityNotFoundException("User could not be found with email: "+email);
		}
		return Boolean.TRUE;
	}

	@Override
	public User addUser(UserVO userVO) throws EntityExistsException, EntityNotFoundException, ValidationException {
		// Check if user name is available
		isUsernameAvailable(userVO.getEmail());
		
		User user = null;
		switch (UserType.valueOf(userVO.getUserType().toUpperCase())) {
		case DISTRIBUTOR:
			user = getDistributor(userVO);
			break;
		case WHOLESALER:
			user = getWholesaler(userVO);
			break;
		default:
			throw new ValidationException(ExceptionMessage.INVALID_USER_TYPE_EXCEPTION_MESSAGE);
		}
		user.setPassword(passwordEncoder.encode(userVO.getPassword()));
		userRepository.save(user);
		sendVerificationEmail(user, true);
		return user;
	}

	/**
	 * Get wholesaler object
	 * 
	 * @param userVO
	 * @return
	 * @throws EntityNotFoundException 
	 */
	private User getWholesaler(UserVO userVO) throws EntityNotFoundException {
		User user = new Wholesaler(userVO.getName(), userVO.getEmail(), userVO.getEmail(),
				userVO.getAddress(), userVO.getPhoneNumber());
		Set<Role> roles = new HashSet<>();
		Role role = roleService.findByName(RoleName.ROLE_WHOLESALER.toString(), userVO.getUserType().toUpperCase());
		roles.add(role);
		user.setRoles(roles);
		return user;
	}

	/**
	 * Get Distributor object
	 * @param userVO
	 * @return
	 * @throws EntityNotFoundException 
	 */
	private User getDistributor(UserVO userVO) throws EntityNotFoundException {
		User user = new Distributor(userVO.getName(), userVO.getEmail(), userVO.getEmail(),
				userVO.getAddress(), userVO.getPhoneNumber());
		Set<Role> roles = new HashSet<>();
		Role role = roleService.findByName(RoleName.ROLE_DISTRIBUTOR.toString(), userVO.getUserType().toUpperCase());
		roles.add(role);
		user.setRoles(roles);
		return user;
	}
	
	@Override
	public User getUserById(Integer userId) throws EntityNotFoundException {
		return userRepository.findById(userId)
				.orElseThrow(() -> new EntityNotFoundException("User could not be found with id: " + userId));
	}

	@Override
	public UserVO getUserDetailsById(Integer userId) throws EntityNotFoundException {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new EntityNotFoundException("User could not be found with id: " + userId));
		return mapToUserVO(user);
	}

	@Override
	public User findByEmail(String email) throws EntityNotFoundException {
		return userRepository.findByEmailAndStatus(email, Status.ACTIVE)
				.orElseThrow(() -> new EntityNotFoundException("User cound not be found with email: " + email));
	}

	/**
	 * Map user to userVO
	 * 
	 * @param user
	 * @return
	 */
	private UserVO mapToUserVO(User user) {
		UserVO userVO = modelMapper.map(user, UserVO.class);
		userVO.setPassword(null);
		// Set user type
		if (user.isDistributor()) {
			userVO.setUserType(UserType.DISTRIBUTOR.toString());
		} else if (user.isWholesaler()) {
			userVO.setUserType(UserType.WHOLESALER.toString());
		}
		return userVO;
	}

	@Override
	public void editUser(EditUserForm userForm) throws EntityNotFoundException, EntityExistsException {
		User user = getUserById(userForm.getId());
		
		// If user is editing email, check if its available
		if (!StringUtils.equalsIgnoreCase(user.getEmail(), userForm.getEmail())) { 
			isEmailAvailable(userForm.getEmail());
			sendVerificationEmail(user, false);
		}
		
		user.setName(userForm.getName());
		user.setEmail(userForm.getEmail());
		user.setAddress(userForm.getAddress());
		user.setPhoneNumber(userForm.getPhoneNumber());
		user.setLastModifiedAt(DateUtil.getCurrentTime());
		userRepository.save(user);
	}
	
	@Override
	public void sendVerificationEmail(User user, boolean isNewUser) {
		String emailVerificationToken = commonUtil.getRandomAlphaNumericToken();
		user.setEmailVerificationToken(passwordEncoder.encode(emailVerificationToken));
		user.setEmailVerified(Boolean.FALSE);
		user.setEmailVerifiedAt(null);
		
		Map<String, Object> data = new HashMap<>();
		data.put("id", user.getId());
		data.put("name", user.getName());
		data.put("token", emailVerificationToken);
		data.put("isNewUser", isNewUser);
		
		String url = commonUtil.getServerUrl() + RestUrlConstants.VERIFY_EMAIL_URL;
		data.put("url", url);
		emailService.createAndSendEmail(user, data, EmailType.VERIFY_EMAIL);
	}

	@Override
	public Boolean verifyEmail(String userId, String token) throws NumberFormatException, EntityNotFoundException {
		User user = getUserById(Integer.parseInt(userId.trim()));
		
		if (!passwordEncoder.matches(token, user.getEmailVerificationToken())) {
			return Boolean.FALSE;
		}
		user.setEmailVerified(Boolean.TRUE);
		user.setEmailVerificationToken(null);
		user.setEmailVerifiedAt(DateUtil.getCurrentTime());
		userRepository.save(user);
		return Boolean.TRUE;
	}

	@Override
	public void inactivateUser(Integer userId) throws EntityNotFoundException {
		User user = getUserById(userId);
		user.setStatus(Status.INACTIVE);
		// Append |userId to user name so that its available for new user 
		user.setUsername(user.getUsername() + Constants.PIPE + userId);
		userRepository.save(user);
	}

	@Override
	public void changePassword(ChangePasswordForm changePasswordForm)
			throws EntityNotFoundException, RecentPasswordException {
		User user = getUserById(changePasswordForm.getId());
		if (!passwordEncoder.matches(changePasswordForm.getCurrentPassword(), user.getPassword())) {
			throw new BadCredentialsException("Current password is wrong");
		}

		updatePassword(changePasswordForm.getNewPassword(), user);
	}

	@Override
	public void sendResetPasswordEmail(String emailId) throws EntityNotFoundException {
		User user = findByEmail(emailId);
		Map<String, Object> data = new HashMap<>();
		Map<Integer, Date> otpMap = otpUtil.getOTP(emailId);
		Entry<Integer, Date> entry = otpMap.entrySet().iterator().next();
		data.put("otp", entry.getKey());
		data.put("validUpto", entry.getValue());
		data.put("name", user.getName());
		emailService.createAndSendEmail(user, data, EmailType.RESET_PASSWORD);
	}

	@Override
	public boolean validateOTP(OTPForm otpForm) {
		return otpUtil.validateOTP(otpForm.getEmail(), otpForm.getOtp());
	}

	@Override
	public void resetPassword(ResetPasswordForm resetPasswordForm)
			throws EntityNotFoundException, RecentPasswordException {
		User user = findByEmail(resetPasswordForm.getEmail());
		updatePassword(resetPasswordForm.getPassword(), user);
	}
	
	private void updatePassword(String password, User user) throws RecentPasswordException {
		List<UserPassword> recentPasswords = userPasswordRepository.getRecentPasswords(user.getId(), 3);
		for (UserPassword recentPassword : recentPasswords) {
			if (passwordEncoder.matches(password, recentPassword.getPassword())) {
				throw new RecentPasswordException("Please use a new password, should not be recently used");
			}
		}
		user.setPassword(passwordEncoder.encode(password));
		userRepository.save(user);		
	}

}
