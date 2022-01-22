package com.example.demo.waitingList.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class WaitingListHistory {

	@Id
	private String waitingListHistoryId;

	private String BiyoshiId;

	private String customer;

	private Date startDate;

	private Date endDate;

	private String menus;

	@Size(max = 6)
	private String createBy;

	private Date createTime;

	@Size(max = 6)
	private String updateBy;

	private Date updateTime;
}
