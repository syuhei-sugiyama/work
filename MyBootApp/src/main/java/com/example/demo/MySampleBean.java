package com.example.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Component;

/*
 * @Componentアノテーション
 * 付与されたクラスは、コンポーネントとして、アプリケーションに認識される
 * このクラスのインスタンスがBeanとしてアプリケーションに登録される
 * コンポーネントとするクラスには必須
 */
@Component
public class MySampleBean {
	private int counter = 0;
	private int max = 10;

	/*
	 * @Autowiredアノテーション
	 * @Componentが付与されたクラスのインスタンスがBeanとして登録される際は、
	 * この@Autowiredアノテーションが付与されたコンストラクタによって、インスタンスが生成される
	 * @Autowiredアノテーションが付与されたコンストラクタがコンポーネントクラスに用意されていないと
	 * アプリケーションの起動に失敗する
	 */
	/*
	 * 引数のApplicationArgumentsについて
	 * これは、アプリケーションが実行された時(@SpringBootApplicationが指定されたクラスで、
	 * SpringApplication.runメソッドが実行された時)に渡された引数が格納されている。
	 * アプリケーション実行時に渡される引数を利用する場合は、このApplicationArgumentsクラスのインスタンスを利用する。
	 */
	@Autowired
	public MySampleBean(ApplicationArguments args) {
		/*
		 * getNonOptionArgsメソッド
		 * アプリケーション実行時の引数をListとして取り出せる
		 */
		List<String> files = args.getNonOptionArgs();
		try {
			/*
			 * アプリケーション実行時に渡した引数は1つなので、
			 * 取り出したListの最初の要素を取得し、int型へ変換してる
			 */
			max = Integer.parseInt(files.get(0));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
	}

	public int count() {
		counter++;
		counter = counter > max ? 0 : counter;
		return counter;
	}
}
