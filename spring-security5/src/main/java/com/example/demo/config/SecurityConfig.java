package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.example.demo.util.Role;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private final UserDetailsService userDetailsService;

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		// セキュリティ設定を無視(ignoring)するパスを指定する
		// 通常、cssやjs、imgなどの静的リソースを指定する
		// 「/js/**」を追加
		web.ignoring().antMatchers("/js/**","/css/**", "/img/**", "/webjars/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		// 「/login」をアクセス可能にする
		// 「/register」を追加
		.antMatchers("/login", "/register").permitAll()
		// 「/admin」は、ADMINユーザのみアクセス可能にする
		.antMatchers("/admin/**").hasRole(Role.ADMIN.name())
		.anyRequest().authenticated()
		.and()
		.formLogin()
		// ログイン時のURLを指定
		.loginPage("/login")
		// 認証後にリダイレクトする場所を指定
		.defaultSuccessUrl("/")
		.and()
		// ログアウトの設定
		.logout()
		// ログアウト時のURLを指定
		.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
		.and()
		// Remember-Meの認証を許可する
		// これを設定すると、ブラウザ閉じて
		// 再度開いた場合でも「ログインしたまま」にできる
		.rememberMe();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// userDetailsServiceを使用して、DBからユーザを参照できるようにする
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}
}
