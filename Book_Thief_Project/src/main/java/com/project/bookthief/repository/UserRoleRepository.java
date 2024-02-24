package com.project.bookthief.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.bookthief.entity.UserRole;

public interface UserRoleRepository extends JpaRepository<UserRole, Integer> {

}
