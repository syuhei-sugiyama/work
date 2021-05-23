package dev.itboot.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

/*
 * @OpenAPIDefinition
 * Open APIの定義ができる
 * 引数(info)を使うことで、APIの説明を記述できる
 * @Info
 * 説明(メタデータ)を記述できる
 */
@OpenAPIDefinition(info = @Info(title = "Task API", version = "1.0.0", description = "これは、Taskアプリケーションに関するAPIです"))
@SpringBootApplication
public class SpringRest3Application {

	public static void main(String[] args) {
		SpringApplication.run(SpringRest3Application.class, args);
	}

}
