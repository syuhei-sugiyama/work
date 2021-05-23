package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.*;

import javax.transaction.Transactional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.example.demo.model.SiteUser;
import com.example.demo.repository.SiteUserRepository;
import com.example.demo.util.Role;

/*
 * @SpringBootTest
 * SpringBootの機能を有効にするためのアノテーション
 * @Transactional
 * テスト開始から終了までを、トランザクション処理にすることができるアノテーション
 * 付与すると、各テスト後に、データが初期化される
 */
@SpringBootTest
@Transactional
class UserDetailsServiceImplTest {

	@Autowired
	SiteUserRepository repository;

	@Autowired
	UserDetailsServiceImpl service;

	/*
	 * @Test
	 * テストメソッドであることを示すアノテーション
	 * @DisplayName
	 * 表示名(テスト名)を付けることができるアノテーション
	 * Junit画面に、日本語などの分かりやすい表示にできる
	 */
	@Test
	@DisplayName("ユーザ名が存在する時、ユーザ詳細を取得することを期待します")
	void whenUsernameExists_expectToGetUserDetails() {
		/*
		 * テストの基本パターン
		 * AAA(Arrange-Act-Assert)
		 * テストの構造が明確になり、理解しやすい
		 */
		// Arrange(準備する)
		SiteUser user = new SiteUser();
		user.setUsername("原田");
		user.setPassword("password");
		user.setEmail("harada@example.com");
		user.setRole(Role.USER.name());
		repository.save(user);

		// Act(実行する)
		UserDetails actual = service.loadUserByUsername("原田");

		// Assert(検証する)
		/*
		 * 同じ値か検証するために、assertEqualsを使用
		 * 第一引数にexpected(期待値)、第二引数にactual(実際の値)
		 */
		assertEquals(user.getUsername(), actual.getUsername());
	}

	@Test
	@DisplayName("ユーザ名が存在しない時、例外をスローします")
	void whenUsernameDoesNotExist_throwException() {
		// Act & Assert
		assertThrows(UsernameNotFoundException.class, () -> service.loadUserByUsername("竹田"));
	}
}
