package com.easybill.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.easybill.model.User;
import com.easybill.model.metadata.EnumConstant.Status;
import com.easybill.repository.UserRepository;

@Service
public class AuthenticationManagerImpl implements AuthenticationManager {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		User user = userRepository.findByUsernameAndStatus(authentication.getPrincipal().toString(), Status.ACTIVE)
				.orElseThrow(() -> new UsernameNotFoundException(
						"User could not be found with username: " + authentication.getPrincipal().toString()));
		if (passwordEncoder.matches(authentication.getCredentials().toString(), user.getPassword())) {
			UserDetails userDetails = UserPrincipal.create(user);
			UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
					authentication.getPrincipal(), authentication.getCredentials(), userDetails.getAuthorities());
			usernamePasswordAuthenticationToken.setDetails(userDetails);
			return usernamePasswordAuthenticationToken;
		} else {
			throw new BadCredentialsException("User could not be authenticated");
		}
	}

}
