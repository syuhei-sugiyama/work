package demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/*
 * WebSecurityConfigurerAdapterクラス
 * セキュリティを設定する場合、WebSecurityConfigurerAdapterを継承して、クラスを作成
 * クラスには、@Configurationと@EnableWebSecurityアノテーションを付ける
 */
/*
 * @EnableWebSecurity
 * Spring Securityの機能を有効にするためのアノテーション
 */
/*
 * @Configuration
 * 付与されたクラスが、構成クラスであることを示すアノテーション
 * アプリ起動時に、付与されたクラスは構成クラスとしてインスタンス化され、
 * クラス内に記述されているBeanなどをアプリに登録する
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		/*
		 * パスワードの暗号化用に、bcrypt(ビークリプト)を使用
		 */
		return new BCryptPasswordEncoder();
	}

	/*
	 * configure(HttpSecurity http)
	 * こちらではhttpリクエストの設定を実施
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		// 認証リクエストの設定
		.authorizeRequests()
		// 認証の必要があるように設定
		.anyRequest().authenticated()
		.and()
		// フォームベース認証の設定
		.formLogin();
	}

	/*
	 * configure(AuthenticationManagerBuilder auth)
	 * こちらではユーザの設定を実施
	 */
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
