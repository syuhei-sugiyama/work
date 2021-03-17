package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
// このクラスがSpring Bootのアプリケーションクラスであることを示すアノテーション
public class MyBootAppApplication {

	public static void main(String[] args) {
//		SpringApplication.run(MyBootAppApplication.class, args);
		/*
		 * 第二引数は、可変長の引数になっており、値を渡せる
		 * 取り出す側では、ApplicationArgumentsクラスのインスタンスを利用する
		 */
		SpringApplication.run(MyBootAppApplication.class, new String[]{"100"});
	}

}
