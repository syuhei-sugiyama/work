package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				// 認証リクエストの設定
				.authorizeRequests()
				// 認証の必要があるように設定
				.anyRequest().authenticated()
				.and()
				// フォームペース認証の設定
				.formLogin();
	}


	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth
				// メモリ内認証を設定
				.inMemoryAuthentication()
				// "user"を追加
				.withUser("user")
				// "password"をBCryptで暗号化
				.password(passwordEncoder().encode("password"))
				// 権限(ロール)を設定
				.authorities("ROLE_USER");
	}
}
