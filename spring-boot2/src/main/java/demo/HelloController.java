package demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/*
 * @Controller→付与されたクラスがコントローラークラスであることを示すアノテーション
 */
@Controller
public class HelloController {
	/*
	 * @GetMapping
	 * HTTPのGETリクエストを受け付けるアノテーション
	 * 引数に指定したパスへアクセスした際に、このアノテーションを付与されたメソッドが呼び出される
	 */
	@GetMapping("/")
	public String greeting() {
		return "hello";
	}

	@GetMapping("/greeting")
	public String greeting(@RequestParam("message") String message, Model model) {
		/*
		 * @RequestParam
		 * リクエストのパラメータを取得できる
		 * URLから、引数に指定したパラメータの値を取得する
		 * ここでいうと、リクエストの中から、messageというパラメータの値を取得し、
		 * メソッドの引数であるmessageに格納している。
		 * URLの、message=○○という部分の、○○が引数に○○が格納される。
		 */
		/*
		 * Thymeleafテンプレートへ値を渡すために、
		 * ModelクラスのaddAttributeメソッドをaddAttribute("名前", 値)という形で使用する
		 * 名前は、Thymeleafテンプレート内で、同じ名前で設定された箇所にあてがわれる
		 */
		model.addAttribute("sample", message);
		return "hello";
	}
}
