package com.akash.blog.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.akash.blog.entity.User;


public interface UserService extends UserDetailsService{
	User saveUser(User user,boolean firstTime);
	User findUserByEmail(String email);
	User getUserById(Integer id);
	List<User> getAllUsers();
	void deleteUser(Integer id);
}
