package com.example.demo.user.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.login.model.Users;
import com.example.demo.login.util.Role;
import com.example.demo.user.UserConst;
import com.example.demo.user.repository.UserRepository;
import com.example.demo.user.serviceif.UserService;
import com.example.demo.util.model.UtilColumn;
import com.example.demo.util.service.SaibanServiceImpl;
import com.example.demo.util.service.UtilColumnServiceImpl;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;

	private final SaibanServiceImpl saibanServiceImpl;

	private final UtilColumnServiceImpl utilColumnServiceImpl;

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
	public void addUser(Users userInfo, String loginUser) {
		// 採番テーブルを使った、ユーザIDの生成
		userInfo.setUserId(saibanServiceImpl.createId(UserConst.USER_ID, loginUser));

		// 共通カラムの値生成
		UtilColumn utilColumnVal = utilColumnServiceImpl.createUtilColumnValue(loginUser);
		userInfo.setCreateBy(utilColumnVal.getCreateBy());
		userInfo.setCreateTime(utilColumnVal.getCreateTime());
		userInfo.setUpdateBy(utilColumnVal.getUpdateBy());
		userInfo.setUpdateTime(utilColumnVal.getUpdateTime());

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
	public void updateUser(Users userInfo, String loginUser) {
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
		// 更新者、更新日時の更新
		UtilColumn utilColumnVal = utilColumnServiceImpl.createUtilColumnValue(loginUser);
		user.setUpdateBy(utilColumnVal.getUpdateBy());
		user.setUpdateTime(utilColumnVal.getUpdateTime());

		// ユーザテーブルを更新
		userRepository.save(user);
	}

	@Override
	public void deleteUser(String userId) {
		userRepository.deleteById(userId);
	}

}
