package demo.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

/*
 * @Configuration
 * 付与されたクラスが、設定(構成)クラスであることを示す
 */
@Configuration
public class MessageConfig {

	/*
	 * @Bean
	 * Springに管理されるBeanの生成を示す。設定処理の本体
	 * アプリ起動時に、自動で処理が実行される
	 */
	@Bean
	public MessageSource messageSource() {
		var source = new ReloadableResourceBundleMessageSource();
		// 読み込むメッセージファイルを指定
		source.setBasenames("classpath:i18n/messages", "classpath:i18n/ValidationMessages");
		// メッセージの文字コードをUTF-8にする
		source.setDefaultEncoding("UTF-8");
		// システムの言語に関係なく、デフォルトのメッセージを返せるようにする
		source.setFallbackToSystemLocale(false);
		return source;
	}

	@Bean
	public LocalValidatorFactoryBean getValidator() {
		var bean = new LocalValidatorFactoryBean();
		// 上記の、messageSourceメソッドをセット
		bean.setValidationMessageSource(messageSource());
		return bean;
	}
}
