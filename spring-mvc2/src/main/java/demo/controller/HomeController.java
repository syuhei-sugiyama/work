package demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import demo.model.Inquiry;

@Controller
public class HomeController {

	@GetMapping("/")
	public String index(@ModelAttribute Inquiry inquiry) {
		return "index";
	}

	/*
	 * @Validated
	 * 入力値のチェックを行うアノテーション
	 * チェック結果は、BindingResultに格納される
	 * そのため、メソッド内にて、result.hasErrors()を利用することで、
	 * エラーがあったかどうか確認できる
	 * 今回の場合、@Validatedを付与したのが、モデルクラスのInquiryなので、
	 * Inquiryクラスにて、各フィールドに対して設定していたバリデーションチェックが働くことになる
	 * その結果が、BindingResultに格納される
	 */
	@PostMapping("/")
	public String confirm(@Validated @ModelAttribute Inquiry inquiry,
			BindingResult result) {
		if (result.hasErrors()) {
			// エラーがある場合、index.htmlを表示
			return "index";
		}
		return "confirm";
	}
}
