package com.example.demo.user.serviceif;

import java.util.List;

import com.example.demo.login.model.Users;

public interface UserService {

	List<Users> findByUserId(String userId);

	void addUser(Users userInfo, String loginUser);

	void updateUser(Users userInfo, String loginUser);

	void deleteUser(String userId);
}
