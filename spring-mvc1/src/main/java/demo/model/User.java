package demo.model;

import lombok.Data;

/*
 * Lombok(ロンボック)
 * 定型コードの削除に役立つJavaアノテーションライブラリ
 * @Data
 * Lombokのアノテーションの1つ
 * GetterやSetter、toStringなどの定型コードを自動で作成してくれる
 */
@Data
public class User {
	private String name;
	private String email;
	private Integer age;
}
