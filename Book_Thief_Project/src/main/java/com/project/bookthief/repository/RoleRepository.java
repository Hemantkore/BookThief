package com.project.bookthief.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.bookthief.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {

}
