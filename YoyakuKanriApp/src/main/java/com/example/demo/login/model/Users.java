package com.example.demo.login.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.example.demo.util.entity.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Users extends BaseEntity{

	public interface AddGroup {}

	public interface UpdateGroup {}

	public interface DeleteGroup {}

	@Id
	@Size(max = 6)
	@NotEmpty(groups = {UpdateGroup.class, DeleteGroup.class})
	private String userId;

	@NotEmpty(groups = {AddGroup.class, UpdateGroup.class})
	private String password;

	private boolean admin;

	private String role;
}