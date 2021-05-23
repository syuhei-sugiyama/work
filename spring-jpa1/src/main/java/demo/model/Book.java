package demo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

/*
 * @Entity
 * データの入れ物である「エンティティクラス」であることを示すアノテーション
 */
@Getter
@Setter
@Entity
public class Book {
	/*
	 * @Id
	 * エンティティの主キーを設定するためのアノテーション
	 * @GeneratedValue
	 * 主キーの値を自動採番するためのアノテーション
	 * @Idと一緒に使用する
	 */
	@Id
	@GeneratedValue
	private Long id;

	private String title;
	private String detail;
}
