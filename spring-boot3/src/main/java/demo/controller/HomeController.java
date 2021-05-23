package demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

	@GetMapping("/")
	public String index(Model model) {
		return "form";
	}

	/*
	 * @PostMapping
	 * HTTPのPOSTリクエストを受け付けるためのアノテーション
	 * 引数に指定したパスにアクセスされた際に、このアノテーションが付与されたメソッドが呼ばれる
	 */
	@PostMapping("/confirm")
	public String confirm(@RequestParam String message, Model model) {
		model.addAttribute("message", message);
		return "confirm";
	}
}
