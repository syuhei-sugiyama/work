package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

/*
 * Spring Bootでは、設定情報について、クラスとして用意できる
 * @Configurationアノテーションを付与することで、
 * アプリケーション実行時に、このクラスが、構成ファイルならぬ、構成クラスとしてインスタンス化され、
 * そこに記述しているBeanなどをアプリケーションへ登録してくれる
 */
@Configuration
public class MyBootAppConfig {

	/*
	 * @Beanアノテーションについて
	 * 「付与されたメソッドが、Beanとして登録するクラスのインスタンスを返す」ことを示している
	 * つまり、構成クラス(@Configurationアノテーションが付与されたクラス)内に、
	 * @Beanを付与して、任意のクラスのインスタンスを返すメソッドを用意すれば、それがすべてBeanとして登録されるようになる
	 */
	@Bean
	MyDataBean myDataBean() {
		return new MyDataBean();
	}

	@Bean
	public ClassLoaderTemplateResolver templateResolver() {
		ClassLoaderTemplateResolver templateResolver =
				new ClassLoaderTemplateResolver();
		templateResolver.setPrefix("templates/");
		templateResolver.setCacheable(false);
		templateResolver.setSuffix(".html");
		templateResolver.setTemplateMode("HTML5");
		templateResolver.setCharacterEncoding("UTF-8");
		return templateResolver;
	}

	@Bean
	public SpringTemplateEngine templateEngine() {
		SpringTemplateEngine templateEngine = new SpringTemplateEngine();
		templateEngine.setTemplateResolver(templateResolver());
		templateEngine.addDialect(new MyDialect());
		return templateEngine;
	}
}