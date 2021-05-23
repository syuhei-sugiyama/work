package mrs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;

/*
 * @Configurationを付与したクラスは、アプリケーション起動時に、
 * 構成クラスとしてインスタンス化され、クラス内に記述されているBeanなどをアプリケーションに登録していく
 */
@Configuration
public class ThymeleafConfig {
	/*
	 * @Beanを付与したメソッドは、そのメソッドが、Beanとして登録するインスタンスを返すことを示す
	 * 構成クラス(=@Configurationを付与したクラス)内に、@Beanを付与して、任意のクラスのインスタンスを返すメソッドを
	 * 用意すれば、それらがすべてBeanとして、アプリケーションに登録されるようになる。
	 */
	@Bean
	public Java8TimeDialect java8TimeDialect() {
		return new Java8TimeDialect();
	}
}
