package com.easybill.service;

import com.easybill.exception.EntityNotFoundException;
import com.easybill.model.Role;

public interface RoleService {

	Role findByName(String name, String userType) throws EntityNotFoundException;

}
