package com.example.demo.yoyaku.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Size;

import lombok.Data;

@Entity
@Data
public class YoyakuRireki {

	@Id
	private String yoyakuRirekiId;

	private String BiyoshiId;

	private String customer;

	private Date yoyakuDate;

	private Date endDate;

	private String menus;

	@Size(max = 6)
	private String createBy;

	private Date createTime;

	@Size(max = 6)
	private String updateBy;

	private Date updateTime;
}
