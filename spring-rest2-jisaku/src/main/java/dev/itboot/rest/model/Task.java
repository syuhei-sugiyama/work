package dev.itboot.rest.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

/*
 * @Getter、@Setter
 * get/set + フィールド名のゲッター/セッターを自動生成するアノテーション
 * @Entity
 * データの入れ物である、エンティティクラスであることを示すアノテーション
 */
@Getter
@Setter
@Entity
public class Task {
	/*
	 * @Id
	 * 付与されたフィールドが、エンティティの主キーであることを示すアノテーション
	 * @GeneratedValue
	 * 主キーの値を自動採番するアノテーション
	 * @Idと一緒に使用する
	 */
	@Id
	@GeneratedValue
	private Long id;

	/*
	 * @NotBlank
	 * 文字列が、nullか空文字、空白(半角スペース)でないか検証するためのアノテーション
	 * @Size
	 * 文字列の長さが、指定の範囲内か検証するためのアノテーション
	 */
	@NotBlank
	@Size(max = 255)
	private String name;

	/*
	 * @NotNull
	 * nullでないかを検証するためのアノテーション
	 */
	@NotNull
	private Boolean completed = false;
}
