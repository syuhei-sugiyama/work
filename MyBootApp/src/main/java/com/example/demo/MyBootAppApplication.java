package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
// このクラスがSpring Bootのアプリケーションクラスであることを示すアノテーション
public class MyBootAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyBootAppApplication.class, args);
	}

}
