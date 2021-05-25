package dev.itboot.mb.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
/*
 * @Entity
 * データの入れ物である「エンティティクラス」であることを指定するアノテーション
 */
public class Book {

	/*
	 * @Id
	 * エンティティの主キーを設定する
	 * @GeneratedValue
	 * 主キーの値を自動採番する
	 * @Idアノテーションと一緒に使用する
	 */
	@Id
	@GeneratedValue
	private Long id;

	private String title;
	private String detail;
}
