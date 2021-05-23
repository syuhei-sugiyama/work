package mrs.domain.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Proxy;

/*
 * @Entity→付与されたクラスがエンティティクラスであることを示すアノテーション
 * @Table→nameプロパティを指定することで、付与されたクラスに割り当てるテーブルを指定できる
 * 省略可能で、その場合は、クラス名がそのままテーブル名として使われる
 * @Proxy→lazyプロパティにてfalseを設定する。(デフォルトはtrue)
 * これは、org.hibernate.LazyInitializationExceptionが発生しないようにするため付与している
 * 遅延読み込みをデフォルトだと実行しようするが、usrテーブルとのセッションが無いため、例外が発生してしまっていた。
 */
@Entity
@Table(name = "usr")
@Proxy(lazy = false)
public class User implements Serializable {
	/*
	 * @Id→プライマリーキーを指定する。エンティティクラス定義時は必ず用意する。レコード毎の識別子。
	 */
	@Id
	private String userId;

	private String password;

	private String firstName;

	private String lastName;

	/*
	 * @Enumerated→列挙型の値をDBに格納することを宣言するアノテーション
	 * カラムの値に文字列を格納するため、EnumType.STRINGを指定
	 */
	@Enumerated(EnumType.STRING)
	private RoleName roleName;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public RoleName getRoleName() {
		return roleName;
	}

	public void setRoleName(RoleName roleName) {
		this.roleName = roleName;
	}
}
