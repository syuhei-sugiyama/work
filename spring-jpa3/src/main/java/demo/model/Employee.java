package demo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Employee {
	/*
	 * @GeneratedValue
	 * 値を自動生成するためのアノテーション
	 * @GeneratedValueのみだと、@GeneratedValue(strategy = GenerationType.AUTO)と同じになる
	 * この場合は、DBによって適切な採番方法が選択される
	 * 今回は引数にstrategyを設定
	 * データの挿入元が複数ある場合、採番を一致させる必要があり、そのような時は、
	 * 下記の、strategy = GenerationType.IDENTITYを設定する
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Size(max = 40)
	private String name;

	/*
	 * @ManyToOne
	 * 多対一のテーブルを指定
	 * 今回の場合、エンティティクラスのDepartmentクラスを指定
	 * 下記の記述により、複数のEmployeeテーブルのレコードに対して、
	 * 1つのDepartmentテーブルのレコードが対応する
	 */
	@ManyToOne
	private Department department;
}
