package com.akash.blog.service;

import java.util.List;

import com.akash.blog.entity.Role;

public interface RoleService {
	List<Role> getAllRoles();
	Role createRole(Role role);
	Role getById(Integer id);
	void deleteRole(Integer id);
	void initiateRoles();
}
