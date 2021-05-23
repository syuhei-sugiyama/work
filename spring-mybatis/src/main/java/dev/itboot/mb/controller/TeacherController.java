package dev.itboot.mb.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import dev.itboot.mb.model.Teacher;
import dev.itboot.mb.service.TeacherService;
import lombok.RequiredArgsConstructor;

/*
 * @Controller
 * 付与されたクラスがコントローラーであることを示す
 */
@RequiredArgsConstructor
@Controller
public class TeacherController {

	private final TeacherService service;

	/*
	 * @GetMapping
	 * HTTPのGETリクエストを受け付ける
	 * 引数に指定したパスにGETでアクセスした際に、下記のメソッドが呼ばれる
	 * @PageableDefault
	 * Pageableのデフォルトを設定できる。
	 * 「size = 5」とした場合、1ページの表示件数が5件になる。
	 */
	@GetMapping("/")
	public String getAllTeachers(Model model, @PageableDefault(size = 5) Pageable pageable) {
		model.addAttribute("page", service.selectAll(pageable));
		// list.htmlを呼び出す
		return "list";
	}

	@GetMapping("/add")
	public String addTeacher(@ModelAttribute Teacher teacher) {
		return "form";
	}

	/*
	 * @PostMapping
	 * HTTPのPOSTリクエストを受け付ける
	 * 引数に指定したパスに、POSTでアクセスした際に、下記のメソッドが呼ばれる
	 * @Validated
	 * 入力値のチェックを行う。Teacherクラスの各フィールドに付与したバリデーションチェックが働く
	 * チェック結果は、BindingResult型の変数resultに格納される
	 * @ModelAttribute
	 * モデル属性にバインドする(=関連付ける)
	 * リクエストとして渡されたデータと、@ModelAttributeを付与されたTeacherクラスとの間で、
	 * リクエストと一致するもの(=Teacherクラスの各フィールド名とリクエストの変数名が合致するもの)の
	 * 値が、モデル(今回はTeacherクラス)へ格納される
	 */
	@PostMapping("/process")
	public String process(@Validated @ModelAttribute Teacher teacher, BindingResult result) {
		// バリデーションチェックの結果、バリデーションに引っかかった場合はtrue
		if (result.hasErrors()) {
			return "form";
		}
		service.save(teacher);
		return "redirect:/";
	}

	/*
	 * @PathVariable
	 * パスの値を変数に格納する
	 * 下記の場合でいうと、例えば「/edit/1」というパスにGETアクセスがあった場合
	 * editTeacherメソッドの引数idには、「1」という値が格納される
	 */
	@GetMapping("/edit/{id}")
	public String editTeacher(@PathVariable Long id, Model model) {
		model.addAttribute("teacher", service.selectByPrimaryKey(id));
		return "form";
	}

	@GetMapping("/delete/{id}")
	public String deleteTeacher(@PathVariable Long id) {
		service.deleteByPrimaryKey(id);
		return "redirect:/";
	}
}
