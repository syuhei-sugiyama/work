package com.example.demo.hairdresser.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Hairdresser {

	public interface AddGroup{}

	public interface UpdateGroup{}

	public interface DeleteGroup{}

	@Id
	@NotEmpty(groups = {UpdateGroup.class, DeleteGroup.class})
	private String hairdresserId;

	@Size(max = 30, min = 2, groups = {AddGroup.class, UpdateGroup.class})
	@NotEmpty(groups = {AddGroup.class, UpdateGroup.class}, message = "美容師名を入力してください")
	private String hairdresserName;

	@Size(max = 6)
	private String createBy;

	private Date createTime;

	@Size(max = 6)
	private String updateBy;

	private Date updateTime;
}
