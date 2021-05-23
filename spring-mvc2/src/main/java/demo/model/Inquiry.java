package demo.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Inquiry {
	// 必須入力、文字列が60文字まで
	/*
	 * @NotBlank
	 * バリデーションチェック用のアノテーションの1つ
	 * 文字列が、nullか空文字、空白(半角スペース)でないかをチェック
	 */
	@NotBlank
	/*
	 * @Size
	 * バリデーションチェック用のアノテーションの1つ
	 * 文字列の長さが、指定の範囲内かをチェック
	 */
	@Size(max = 60)
	private String name;

	// 必須入力、Email形式であること、文字列が254文字まで
	@NotBlank
	/*
	 * @Email
	 * バリデーションチェック用のアノテーションの1つ
	 * 文字列が、有効なメールアドレスの形式となっているかチェック
	 */
	@Email
	@Size
	private String email;

	// 必須入力、文字列が500文字まで
	@NotBlank
	@Size
	private String inquiry;
}
