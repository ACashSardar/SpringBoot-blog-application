package com.akash.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.akash.blog.entity.Role;

public interface RoleRepository extends JpaRepository<Role,Integer> {

}
