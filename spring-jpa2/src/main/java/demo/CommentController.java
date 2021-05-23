package demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import demo.model.Comment;
import demo.repository.CommentRepository;

@Controller
public class CommentController {

	private final CommentRepository repository;

	/*
	 * コンストラクタが1つの場合、@Autowiredは省略できる
	 */
	public CommentController(CommentRepository repository) {
		this.repository = repository;
	}

	/*
	 * @ModelAttribute
	 * エンティティクラスのインスタンスを自動的に用意する
	 * getAllCommentsメソッドでは、newされたCommentクラスのインスタンスが作成され割り当てられる
	 */
	@GetMapping("/")
	public String getAllComments(@ModelAttribute Comment comment, Model model) {
		model.addAttribute("comments", repository.findAll());
		return "list";
	}

	/*
	 * addCommentメソッドの@ModelAttribute
	 * フォームから送信された値が、自動的にCommentクラスのインスタンスにまとめられて
	 * このメソッドに渡される。その際、@Validatedによって、Commentクラスの各フィールドに
	 * 設定したバリデーションチェックが働く。チェック結果は、BindingResultインタフェース型の
	 * 変数resultに格納される
	 */
	@PostMapping("/add")
	public String addComment(@Validated @ModelAttribute Comment comment,
			BindingResult result, Model model) {
		model.addAttribute("comments", repository.findAll());
		if (result.hasErrors()) {
			/*
			 * バリデーションチェックの結果、エラーが発生していた場合は
			 * フォームから送信された値を保存することなく、list.htmlを表示する
			 */
			return "list";
		}
		/*
		 * フォームから送信された値が自動的にまとめられたCommentクラスのインスタンスを
		 * リポジトリへ保存する
		 */
		repository.save(comment);
		// ルートパス("/")に、リダイレクトする
		return "redirect:/";
	}
}
