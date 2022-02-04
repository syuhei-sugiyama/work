package com.example.demo.yoyaku.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.example.demo.util.entity.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class YoyakuRireki extends BaseEntity {

	@Id
	private String yoyakuRirekiId;

	private String BiyoshiId;

	private String customer;

	private Date yoyakuDate;

	private Date endDate;

	private String menus;
}
