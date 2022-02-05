package com.example.demo.waitingList.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.example.demo.util.entity.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class WaitingListHistory extends BaseEntity {

	@Id
	private String waitingListHistoryId;

	private String BiyoshiId;

	private String customer;

	private Date startDate;

	private Date endDate;

	private String menus;
}
