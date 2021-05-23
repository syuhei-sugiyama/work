package demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import demo.model.User;

@Controller
public class HomeController {

	@GetMapping("/form")
	private String readFrom(@ModelAttribute User user) {
		return "form";
	}

	@PostMapping("/form")
	private String confirm(@ModelAttribute User user) {
		/*
		 * @ModelAttribute
		 * モデル属性にバインドする。
		 * バインド・・・「結びつける」、「関連付ける」などの意味
		 * 引数を指定した場合は、その名前でバインドされる
		 * 下記の2つは同じ動作になる
		 * @ModelAttribute User user
		 * @ModelAttribute("user") User user
		 */
		/*
		 * @ModelAttributeを使うと、リクエストと一致するものが、モデルへ格納される。
		 * 本来なら、ModelクラスのインスタンスにaddAttributeメソッドを使って、
		 * Userクラスのインスタンスを登録するところ、それを行う必要がなくなる
		 * model.addAttribute("user", user)が不要
		 */
		return "form";
	}
}
