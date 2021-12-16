package com.example.demo.login.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Data;

@Entity
@Data
public class Users {

	public interface AddGroup {}

	public interface UpdateGroup {}

	public interface DeleteGroup {}

	@Id
	@Size(max = 5)
	@NotEmpty(groups = {UpdateGroup.class, DeleteGroup.class})
	private String userId;

	@NotEmpty(groups = {AddGroup.class, UpdateGroup.class})
	private String password;

	private boolean admin;

	private String role;

	@Size(max = 6)
	private String createBy;

	private Date createTime;

	@Size(max = 6)
	private String updateBy;

	private Date updateTime;
}