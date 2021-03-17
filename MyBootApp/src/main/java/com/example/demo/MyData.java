package com.example.demo;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

// @Entity→このクラスがエンティティクラスであることを示すアノテーション。エンティティクラスでは必ず必要
/*
 * @Table(name="mydata")
 * このエンティティクラスに紐づいているテーブルを指定する。省略可。その場合はクラス名がそのままテーブル名として使われる。
 */
/*
 * @NamedQueryアノテーション
 * 名前付きクエリを作成する際に使用するアノテーション
 * 引数について
 * queryは、実行したいクエリ文を設定
 * nameは、このクエリ文に名前を付ける。これによって、この名前を指定することで、設定したクエリを指定したことになる
 * さらに、複数のクエリ文を用意したい場合は、@NamedQueriesアノテーションを使用できる
 * 下記のように、配列の初期化時に、値を設定する時と同様に、{}を使って、複数の@NamedQueryアノテーションを用意する
 */
@Entity
@Table(name="mydata")
@NamedQueries({
		@NamedQuery(name = "findWithName", query = "from MyData where name like :fname"),
		@NamedQuery(name = "findByAge", query = "from MyData where age > :min and age < :max")
})
@XmlRootElement
public class MyData {
	// @Id→プライマリーキーを指定する。エンティティクラス定義時は必ず用意する。レコード毎の識別子。
	@Id
	/*
	 * @GeneratedValue
	 * プライマリーキーのフィールドに対して値を自動生成する
	 * 引数のstrategyで生成方法を指定。GenerationTypeという列挙型の値を指定する。
	 * AUTOを指定することで、自動的に値を割り振る
	 */
	@GeneratedValue(strategy = GenerationType.AUTO)
	/*
	 * @Column
	 * フィールドに割り当てられるカラム名を指定。省略可能。その場合は、フィールド名がそのままカラム名として使われる
	 * 引数にはいくつか用意されている
	 * length→最大の長さ(Stringでは文字数)を指定
	 * nullable→null(未入力)を許可するかどうかを指定
	 */
	@Column
	@NotNull // nullを許可しない
	private long id;

	@Column(length = 50, nullable = false)
	@NotEmpty
	/*
	 * @NotEmpty
	 * 項目がString値の場合に使用
	 * フォームの入力フィールドに何も書かずに送信すると、値は空文字が送られてきて、nullとはならない
	 * そのため、@NotEmptyを使用し、空かどうかを見ている
	 * message="○○"という形で、画面に表示するためのエラーメッセージを編集できる
	 */
	private String name;

	@Column(length = 200, nullable = true)
	@Email // 電子メールアドレスとして有効かどうかをチェックする
	private String mail;

	@Column(nullable = true)
	@Min(value = 0) // 入力可能な最小値を設定
	@Max(value = 200) // 入力可能な最大値を設定
	private Integer age;

	@Column(nullable = true)
	@Phone(onlyNumber = true, message = "バリデーションチェックでエラー(メモ)")
	/*
	 * 自作のアノテーションクラスPhoneを設定している
	 * Phoneクラスに宣言したメソッドを、引数として、指定できる
	 */
	private String memo;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	@OneToMany(cascade = CascadeType.ALL)
	@Column(nullable = true)
	private List<MsgData> msgdatas;

	public List<MsgData> getMsgdatas() {
		return msgdatas;
	}

	public void setMsgdatas(List<MsgData> msgdatas) {
		this.msgdatas = msgdatas;
	}
}

