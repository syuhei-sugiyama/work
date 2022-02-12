package com.example.demo.hairdresser.model;

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
public class Hairdresser extends BaseEntity {

	public interface AddGroup{}

	public interface UpdateGroup{}

	public interface DeleteGroup{}

	@Id
	@NotEmpty(groups = {UpdateGroup.class, DeleteGroup.class})
	private String hairdresserId;

	@Size(max = 30, min = 2, groups = {AddGroup.class, UpdateGroup.class})
	@NotEmpty(groups = {AddGroup.class, UpdateGroup.class}, message = "美容師名を入力してください")
	private String hairdresserName;
}