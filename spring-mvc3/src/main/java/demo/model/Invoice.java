package demo.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.format.annotation.NumberFormat;

import lombok.Getter;
import lombok.Setter;

/*
 * @Getter、@Setter
 * Lombokのアノテーション
 * これらのアノテーションを付与することで、実際にクラス内に記述しなくても、
 * 宣言したフィールドに対するgetter/setterが作成される。
 * アウトラインビューを確認すると、getter/setterが作成されていることが分かる。
 */
@Getter
@Setter
public class Invoice {

	// 必須入力、文字列が60文字まで
	@NotBlank
	@Size(max = 60)
	private String name;

	// 必須入力、文字列が254文字まで
	@NotBlank
	@Size(max = 254)
	private String address;

	// 必須入力、半角数字かハイフン
	@NotBlank
	@Pattern(regexp = "[0-9-]*")
	private String phoneNumber;

	// 必須入力、1000円以上、数値へ変換
	@NotNull
	@Min(1000)
	/*
	 * @NumberFormat
	 * アノテーションの1つ。指定パターンの文字列を、数値へ型変換する
	 */
	@NumberFormat(pattern = "#,###")
	private BigDecimal price;

	// 必須入力、日付へ変換
	@NotNull
	/*
	 * @DateTimeFormat
	 * アノテーションの1つ。yyyy-MM-ddの文字列を、日付に型変換する。
	 * 引数に、pattern = "yyyy-MM-dd"と記述しても同じ動作になる
	 */
	@DateTimeFormat(iso = ISO.DATE)
	private LocalDate paymentDeadline;
}
