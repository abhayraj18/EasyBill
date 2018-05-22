package com.easybill.config.security;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.easybill.model.User;
import com.easybill.model.metadata.EnumConstant.Status;
import com.easybill.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsernameAndStatus(username, Status.ACTIVE)
				.orElseThrow(() -> new UsernameNotFoundException("User could not be found with username: " + username));
		return UserPrincipal.create(user);
	}

	@Transactional
	public UserDetails loadUserById(Integer id) {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new UsernameNotFoundException("User could not be found with id: " + id));
		return UserPrincipal.create(user);
	}

}
