package com.easybill.service.impl;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.easybill.exception.EntityNotFoundException;
import com.easybill.exception.ValidationException;
import com.easybill.model.Distributor;
import com.easybill.model.Role;
import com.easybill.model.User;
import com.easybill.model.Wholesaler;
import com.easybill.model.metadata.EnumConstant.RoleName;
import com.easybill.model.metadata.EnumConstant.UserType;
import com.easybill.pojo.UserVO;
import com.easybill.repository.UserRepository;
import com.easybill.service.RoleService;
import com.easybill.service.UserService;
import com.easybill.util.ExceptionMessage;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private RoleService roleService;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public Boolean isEmailAvailable(String email) {
		return !userRepository.existsByEmail(email);
	}

	@Override
	public User saveUser(UserVO userVO) throws ValidationException {
		User user = null;
		switch (UserType.valueOf(userVO.getUserType().toUpperCase())) {
		case DISTRIBUTOR:
			user = getDistributor(userVO);
			break;
		case WHOLESALER:
			user = getWholesaler(userVO);
			break;
		default:
			throw new ValidationException(ExceptionMessage.USER_TYPE_EXCEPTION_MESSAGE);
		}
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userRepository.save(user);
		return user;
	}

	private User getWholesaler(UserVO userVO) {
		User user = new Wholesaler(userVO.getName(), userVO.getEmail(), userVO.getEmail(), userVO.getPassword(),
				userVO.getAddress(), userVO.getPhoneNumber());
		Set<Role> roles = new HashSet<>();
		Role role = roleService.findByName(RoleName.ROLE_WHOLESALER.toString());
		roles.add(role);
		((Wholesaler) user).setRoles(roles);
		return user;
	}

	private User getDistributor(UserVO userVO) {
		User user = new Distributor(userVO.getName(), userVO.getEmail(), userVO.getEmail(), userVO.getPassword(),
				userVO.getAddress(), userVO.getPhoneNumber());
		Set<Role> roles = new HashSet<>();
		Role role = roleService.findByName(RoleName.ROLE_DISTRIBUTOR.toString());
		roles.add(role);
		((Distributor) user).setRoles(roles);
		return user;
	}

	@Override
	public UserVO getUserById(Integer userId) throws EntityNotFoundException {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new EntityNotFoundException("User could not be found with id: " + userId));
		return mapToUserVO(user);
	}

	@Override
	public UserVO findByEmail(String email) throws EntityNotFoundException {
		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new EntityNotFoundException("User cound not found with email: " + email));
		return mapToUserVO(user);
	}

	private UserVO mapToUserVO(User user) {
		UserVO userVO = new UserVO();
		userVO.setId(user.getId());
		userVO.setName(user.getName());
		userVO.setPhoneNumber(user.getPhoneNumber());
		userVO.setAddress(user.getAddress());
		userVO.setEmail(user.getEmail());
		if (user.isDistributor()) {
			userVO.setUserType(UserType.DISTRIBUTOR.toString());
		} else if (user.isWholesaler()) {
			userVO.setUserType(UserType.WHOLESALER.toString());
		}
		return userVO;
	}

}
