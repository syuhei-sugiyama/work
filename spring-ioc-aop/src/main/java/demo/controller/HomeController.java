package demo.controller;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import demo.repository.CoffeeRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class HomeController {

	private final ApplicationContext appContext;

	@GetMapping("/")
	public String showList(Model model) {
		/*
		 * getBean("bean名")
		 * IoCコンテナからBeanを取得することができる
		 * つまり、アプリケーションにて自動で生成されるBeanを取得できる
		 * IoCコンテナに登録されているBean名は、先頭が小文字に注意
		 */
		CoffeeRepository repository = (CoffeeRepository) appContext.getBean("coffeeRepository");
		model.addAttribute("toString", this.toString());
		model.addAttribute("allCoffee", repository.findAll());
		return "index";
	}
}
