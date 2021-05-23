package demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import demo.model.Invoice;

@Controller
public class HomeController {

	@GetMapping("/")
	public String index(@ModelAttribute Invoice invoice) {
		return "index";
	}

	/*
	 * @Validated
	 * 入力値のバリデーションチェックを行うアノテーション
	 * Invoiceクラスのインスタンスに付与しているので、
	 * Invoiceクラスの各フィールドに付与したアノテーションのバリデーションチェックが働く
	 * チェックの結果は、BindingResultクラスのインスタンスresultに格納される
	 */
	@PostMapping("/")
	public String confirm(@Validated @ModelAttribute Invoice invoice, BindingResult result) {
		if (result.hasErrors()) {
			return "index";
		}
		return "confirm";
	}
}
