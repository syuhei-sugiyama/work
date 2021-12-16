package com.example.demo.util.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Saiban {

	@Id
	private String saibanId;

	private Long currentNumber;

	private String createBy;

	private Date createTime;

	private String updateBy;

	private Date updateTime;
}
