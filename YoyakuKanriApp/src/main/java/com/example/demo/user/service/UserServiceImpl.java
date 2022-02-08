package com.example.demo.user.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.login.model.Users;
import com.example.demo.login.util.Role;
import com.example.demo.user.UserConst;
import com.example.demo.user.repository.UserRepository;
import com.example.demo.user.serviceif.UserService;
import com.example.demo.util.service.SaibanServiceImpl;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;

	private final SaibanServiceImpl saibanServiceImpl;

	private final BCryptPasswordEncoder passwordEncoder;

	@Override
	public List<Users> findByUserId(String userId) {

		List<Users> userList = new ArrayList<Users>();

		// 全検索かどうか
		if (userId != null && !userId.isEmpty()) {
			userList.add(userRepository.findByUserId(userId));
		} else {
			userList = userRepository.findAll();
		}
		return userList;
	}

	@Override
	public void addUser(Users userInfo) {
		// 採番テーブルを使った、ユーザIDの生成
		userInfo.setUserId(saibanServiceImpl.createId(UserConst.USER_ID));

		// パスワードの暗号化
		userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));

		// ロール設定
		if (userInfo.isAdmin()) {
			userInfo.setRole(Role.ADMIN.name());
		} else {
			userInfo.setRole(Role.USER.name());
		}

		// ユーザテーブルへ登録
		userRepository.save(userInfo);
	}

	@Override
	public void updateUser(Users userInfo) {
		// 既存の情報取得
		Users user = userRepository.findByUserId(userInfo.getUserId());

		// 画面から入力された情報で上書き
		user.setPassword(passwordEncoder.encode(userInfo.getPassword()));
		user.setAdmin(userInfo.isAdmin());
		if (userInfo.isAdmin()) {
			user.setRole(Role.ADMIN.name());
		} else {
			user.setRole(Role.USER.name());
		}
		// ユーザテーブルを更新
		userRepository.save(user);
	}

	@Override
	public void deleteUser(String userId) {
		userRepository.deleteById(userId);
	}

	@Transactional
	@Override
	public void registerOfSystemUser() {
		Users userInfo = new Users();
		// システムユーザのID/Passwordは固定
		userInfo.setUserId(UserConst.SYSTEM_USER_ID);
		userInfo.setPassword(UserConst.SYSTEM_USER_PASSWORD);
		// パスワードの暗号化
		userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
		// Admin設定
		userInfo.setAdmin(true);
		userInfo.setRole(Role.ADMIN.name());
		// 作成者、更新者
		userInfo.setCreateBy(UserConst.SYSTEM_USER_ID);
		userInfo.setUpdateBy(UserConst.SYSTEM_USER_ID);
		// ユーザテーブルへ登録
		userRepository.save(userInfo);
	}

}