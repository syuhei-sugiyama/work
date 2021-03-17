package com.example.demo;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.repositories.MyDataRepository;

@Controller
public class MyDataServiceController {

	@Autowired
	MyDataRepository repository;

	/*
	 * @Autowiredアノテーションは、Spring Frameworkで用意したBeanを、指定の変数に自動的に設定する働きをする
	 * ここでは、MyDataServiceクラスのprivate変数に、自動的にMyDataServiceクラスのBeanインスタンスを設定している
	 * ちなみに、MyDataServiceクラスにて、@Serviceアノテーションを付与したことにより、
	 * 自動的にMyDataServiceクラスのBeanインスタンスが用意された状態となっている。
	 */
	@Autowired
	private MyDataService service;

	@RequestMapping(value = "/mdserv", method = RequestMethod.GET)
	public ModelAndView index(ModelAndView mav) {
		mav.setViewName("MyDataService");
		mav.addObject("title", "Find Page");
		mav.addObject("msg", "MyDataのサンプルです。");
		/*
		 * 変数serviceは、明示的に新しいインスタンスを代入していないにもかかわらずgetAllメソッドなどを使用できる
		 * これは、変数serviceに対して、@Autowiredアノテーションの機能のおかげ、つまり、MyDataServiceクラスのインスタンスが
		 * 自動的に設定されているから、明示的に新しいインスタンスを代入しなくても使える状態となっている。
		 *
		 */
		List<MyData> list = service.getAll();
		mav.addObject("datalist", list);
		return mav;
	}

	@RequestMapping(value = "/mdserv/find", method = RequestMethod.GET)
	public ModelAndView find(ModelAndView mav) {
		mav.setViewName("find");
		mav.addObject("title", "Find Page");
		mav.addObject("msg", "MyDataのサンプルです。");
		mav.addObject("value", "");
		List<MyData> list = service.getAll();
		mav.addObject("datalist", list);
		return mav;
	}

	@RequestMapping(value = "/mdserv/find", method = RequestMethod.POST)
	public ModelAndView search(HttpServletRequest request, ModelAndView mav) {
		mav.setViewName("find");
		String param = request.getParameter("fstr");
		if (param == "") {
			mav = new ModelAndView("redirect:/mdserv/find");
		} else {
			mav.addObject("title", "Find result");
			mav.addObject("msg", "「" + param + "」の検索結果");
			mav.addObject("value", param);
			List<MyData> list = service.find(param);
			mav.addObject("datalist", list);
		}
		return mav;
	}

	@PostConstruct
	public void init() {
		MyData d1 = new MyData();
		d1.setName("sachiko");
		d1.setAge(11);
		d1.setMail("test1@test1.co.jp");
		d1.setMemo("090999999");
		repository.saveAndFlush(d1);
		MyData d2 = new MyData();
		d2.setName("hanako");
		d2.setAge(22);
		d2.setMail("test2@test2.co.jp");
		d2.setMemo("080888888");
		repository.saveAndFlush(d2);
		MyData d3 = new MyData();
		d3.setName("asas");
		d3.setAge(33);
		d3.setMail("test3@test3.co.jp");
		d3.setMemo("070777777");
		repository.saveAndFlush(d3);
		MyData d4 = new MyData();
		d4.setName("shiro");
		d4.setAge(44);
		d4.setMail("shiro@shiro.com");
		d4.setMemo("04044444444");
		repository.saveAndFlush(d4);
	}
}
