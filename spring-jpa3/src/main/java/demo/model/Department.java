package demo.model;

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
	 * @OneToMany
	 * 一対多のテーブルを指定
	 * マップする対象フィールドを、mappedByで指定
	 * 1つのDepartmentテーブルのレコードに対して、
	 * 複数のEmployeeテーブルのレコードが対応する
	 */
	@OneToMany(mappedBy = "department")
	private List<Employee> employees;
}
