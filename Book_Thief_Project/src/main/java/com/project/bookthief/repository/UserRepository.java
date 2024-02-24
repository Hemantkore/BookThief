package com.project.bookthief.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.bookthief.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	Optional<User> findUserByEmail(String email);
}
