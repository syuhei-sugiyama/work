package com.example.demo.controller;

import static org.hamcrest.CoreMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import javax.transaction.Transactional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.example.demo.model.SiteUser;
import com.example.demo.util.Role;

/*
 * @AutoConfigureMockMvc
 * MockMvcの自動構成を有効にするためのアノテーション
 * MockMvcを使う場合、@SpringBootTestなどと一緒に使用する
 */
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class SecurityControllerTest {

	/*
	 * MockMvc
	 * Spring MVCのテストができるクラス
	 */
	@Autowired
	MockMvc mockMvc;
	/*
	 * 下記の各メソッドで使用している処理のメモ
	 *
	 * ・performメソッド
	 * リクエストを実行する
	 * get("/path")・・・HTTPのGETリクエストを使用
	 * post("/path")・・・HTTPのPOSTリクエストを使用
	 *
	 * ・flashAttr("user", user)メソッド
	 * 入力の属性を設定する
	 * 例えば、"user"を設定すると、Controller側の@ModelAttributeのuserに対して
	 * 値を設定できる
	 *
	 * ・with(csrf())
	 * CSRFトークンを自動挿入する。通常POSTリクエストには、これが必要
	 *
	 * ・andExpectメソッド
	 * 期待する内容・結果を設定する
	 *
	 * ・model().hasErrors()
	 * エラーがあることを検証する
	 *
	 * ・model().hasNoErrors()
	 * エラーがないことを検証する
	 *
	 * ・view().name("html")
	 * 指定したHTMLを表示しているか検証する
	 *
	 * ・redirectedUrl("/path")
	 * 指定したURLに、リダイレクトすることを検証する
	 *
	 * ・status().isFound()
	 * ステータスコードがFount(302 = リダイレクト)であることを検証する
	 *
	 * ・status().isOk()
	 * ステータスコードがOK(200)であることを検証する
	 *
	 * ・content().string(containsString("文字列"))
	 * HTMLの表示内容に、指定した文字列を含んでいるか検証する
	 *
	 */

	@Test
	@DisplayName("登録エラーがある時、エラー表示することを期待します")
	void whenThereIsRegistrationError_expectToSeeErrors() throws Exception {
		mockMvc
				.perform(
						post("/register")
								.flashAttr("user", new SiteUser())
								.with(csrf()))
				.andExpect(model().hasErrors())
				.andExpect(view().name("register"));
	}

	@Test
	@DisplayName("管理者ユーザとして登録する時、成功することを期待します")
	void whenRegisteringAsAdminUser_expectToSucceed() throws Exception {
		SiteUser user = new SiteUser();
		user.setUsername("管理者ユーザ");
		user.setPassword("password");
		user.setEmail("admin@example.com");
		user.setGender(0);
		user.setAdmin(true);
		user.setRole(Role.ADMIN.name());

		mockMvc.perform(post("/register").flashAttr("user", user).with(csrf()))
				.andExpect(model().hasNoErrors())
				.andExpect(redirectedUrl("/login?register"))
				.andExpect(status().isFound());
	}

	/*
	 * @WithMockUser
	 * モックユーザでログインするためのアノテーション
	 * Spring Securityのログインを最も簡単にテストする方法
	 * 主な引数
	 * username・・・モックユーザの名前を指定
	 * roles・・・モックユーザのロールを指定
	 */
	@Test
	@DisplayName("管理者ユーザでログイン時、ユーザ一覧を表示することを期待します")
	@WithMockUser(username = "admin", roles = "ADMIN")
	void whenLoggedInAsAdminUser_expectToSeeListOfUsers() throws Exception {
		mockMvc.perform(get("/admin/list"))
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("ユーザ一覧")))
				.andExpect(view().name("list"));
	}
}
