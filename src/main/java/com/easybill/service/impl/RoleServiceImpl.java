package com.easybill.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.easybill.exception.EntityNotFoundException;
import com.easybill.model.Role;
import com.easybill.repository.RoleRepository;
import com.easybill.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleRepository roleRepository;

	@Override
	public Role findByName(String name, String userType) throws EntityNotFoundException {
		return roleRepository.findByName(name).orElseThrow(() -> new EntityNotFoundException("Role could not be found for " + userType));
	}

}
