package com.example.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyDataRestController {

	@Autowired
	private MyDataService service;

	@RequestMapping("/mydatarest")
	public List<MyData> restAll() {
		return service.getAll();
	}

	@RequestMapping("/mydatarest/{num}")
	public MyData restBy(@PathVariable int num) {
		return service.get(num);
	}

	/*
	 * MySampleBeanクラスのインスタンスに対して、@Autowiredアノテーションを付与
	 * こうすることで、アプリケーション実行時に、自動的に
	 * MySampleBeanクラスのインスタンスを代入できる
	 * なので、countメソッド内で、いきなり、bean.count()を呼び出せている
	 */
	@Autowired
	MySampleBean bean;

	@RequestMapping("/count")
	public int count() {
		return bean.count();
	}
}
