package com.akash.blog.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.akash.blog.entity.Role;
import com.akash.blog.repository.RoleRepository;
import com.akash.blog.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	RoleRepository roleRepository;
	
	@Override
	public Role createRole(Role role) {
		return roleRepository.save(role);
	}

	@Override
	public Role getById(Integer id) {
		return roleRepository.findById(id).get();
	}

	@Override
	public void deleteRole(Integer id) {
		roleRepository.deleteById(id);
	}

	@Override
	public List<Role> getAllRoles() {
		return roleRepository.findAll();
	}

	@Override
	public void initiateRoles() {
		Role role1=new Role();
		role1.setName("ROLE_ADMIN");
		roleRepository.save(role1);
		Role role2=new Role();
		role2.setName("ROLE_USER");
		roleRepository.save(role2);	
	}

}
