package com.example.demo.util.model;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.example.demo.util.entity.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Saiban extends BaseEntity {

	/**
	 * 機能の識別用ID
	 */
	@Id
	private String saibanId;

	/**
	 * 最新の番号(番号管理用)
	 */
	private Long currentNumber;
}
