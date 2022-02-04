package com.example.demo.util.entity;

import java.util.Date;

import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.validation.constraints.Size;

import org.springframework.security.core.context.SecurityContextHolder;

import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public abstract class BaseEntity {

	@Size(max = 6)
	private String createBy;

	private Date createTime;

	@Size(max = 6)
	private String updateBy;

	private Date updateTime;

	@PrePersist
	public void onPrePersist() {
		// SecurityContextHolderから認証済みユーザ名取得
		setCreateBy(SecurityContextHolder.getContext().getAuthentication().getName());
		setCreateTime(new Date());
		setUpdateBy(SecurityContextHolder.getContext().getAuthentication().getName());
		setUpdateTime(new Date());
	}

	@PreUpdate
	public void onPreUpdate() {
		setUpdateBy(SecurityContextHolder.getContext().getAuthentication().getName());
		setUpdateTime(new Date());
	}
}
