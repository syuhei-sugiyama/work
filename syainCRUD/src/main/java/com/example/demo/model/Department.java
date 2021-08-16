package com.example.demo.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Department {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Size(max = 40)
	private String name;

	/*
	 * 一対多のテーブルを指定する
	 * マップする対象フィールドをmappedByで指定する
	 * 今回、関連付ける対象のエンティティがEmployeeクラスで、
	 * かつその中に、Departmentクラスのフィールドをdepartmentという名前のフィールドで
	 * 保持しているため、下記のような指定になる
	 * 1つの部署名に対して、多数の社員がぶら下がっている形になるため、
	 * @OneToManyを使用している
	 */
	@OneToMany(mappedBy = "department")
	private List<Employee> employees;
}
