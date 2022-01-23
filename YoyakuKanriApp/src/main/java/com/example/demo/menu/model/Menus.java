package com.example.demo.menu.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Range;

import lombok.Data;

@Entity
@Data
public class Menus {

	public interface SearchGroup{}

	public interface AddGroup{}

	public interface UpdateGroup{}

	@Id
	@Size(max = 5)
	@NotEmpty(groups = {UpdateGroup.class})
	private String menuId;

	@Size(max = 30)
	@NotEmpty(groups = {AddGroup.class, UpdateGroup.class}, message = "メニュー名を入力してください")
	private String menuName;

	@Range(min = 0, max = 20000, groups = {AddGroup.class, UpdateGroup.class}, message = "金額は0～20000で入力してください")
	@NotNull(groups = {AddGroup.class, UpdateGroup.class}, message = "金額を入力してください")
	private Integer price;

	@Range(min = 10, max = 60, groups = {AddGroup.class, UpdateGroup.class}, message = "所要時間は10分以上60分以下で入力してください")
	@NotNull(groups = {AddGroup.class, UpdateGroup.class}, message = "所要時間を入力してください")
	private Integer requiredTime;

	@Size(max = 6)
	private String createBy;

	private Date createTime;

	@Size(max = 6)
	private String updateBy;

	private Date updateTime;
}
