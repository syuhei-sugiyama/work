package com.example.demo.model;

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
	 * @GeneratedValue(strategy = GenerationType.IDENTITY)
	 * DBのidentity列を使用して、キーを自動採番します。
	 * strategyを指定しない場合、@GeneratedValue(strategy = GenerationType.AUTO)となる
	 * その場合、DBによって適切な採番方法が選択される
	 * 今回は、データをSQLとWebで挿入する。この時、採番を一致させる必要があるため、
	 * GenerationType.IDENTITYを指定している
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Size(max = 40)
	private String firstName;

	@NotBlank
	@Size(max = 40)
	private String lastName;

	@NotBlank
	private String sex;

	/*
	 * 多対一のテーブルを指定する
	 * 今回の場合、Employeeテーブルのレコードがいくら増えようとも
	 * departmentの値(=部署名を格納したレコード)が同じであれば、
	 * 対応するDepartmentテーブルは必ず1レコードだけなので、
	 * Employeeが多に対して、Departmentが一になる
	 * そのため、@ManyToOneを使用してる
	 */
	@ManyToOne
	private Department department;
}
