package com.example.demo.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.login.model.Users;

public interface UserRepository extends JpaRepository<Users, String> {

	Users findByUserId(String userId);
}