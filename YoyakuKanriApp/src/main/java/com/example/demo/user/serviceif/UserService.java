package com.example.demo.user.serviceif;

import java.util.List;

import com.example.demo.login.model.Users;

public interface UserService {

	List<Users> findByUserId(String userId);

	void addUser(Users userInfo);

	void updateUser(Users userInfo);

	void deleteUser(String userId);

	void registerOfSystemUser();
}
