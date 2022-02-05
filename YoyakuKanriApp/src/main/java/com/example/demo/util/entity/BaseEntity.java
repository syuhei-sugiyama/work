package com.example.demo.util.entity;

import java.util.Date;

import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.validation.constraints.Size;

import org.springframework.security.core.context.SecurityContextHolder;

import lombok.Getter;
import lombok.Setter;

/**
 * Entityの基本クラス
 */
@MappedSuperclass
@Getter
@Setter
public abstract class BaseEntity {

	/**
	 * 作成者
	 */
	@Size(max = 6)
	private String createBy;

	/**
	 * 作成日時
	 */
	private Date createTime;

	/**
	 * 更新者
	 */
	@Size(max = 6)
	private String updateBy;

	/**
	 * 更新日時
	 */
	private Date updateTime;

	/**
	 * テーブルへ登録する前に実行する処理
	 * 共通カラムの値を設定
	 */
	@PrePersist
	public void onPrePersist() {
		/*
		 * @PrePersistを付与されたメソッドはテーブルへ登録する前に実行されるメソッドとなる
		 * ここで共通カラムの値を設定することで、個別でわざわざSETする必要が無くなる
		 */
		// システムユーザ登録時のみ事前に作成者、更新者を設定するため下記の判定用意
		if (getCreateBy() == null) {
			// SecurityContextHolderから認証済みユーザ名取得
			setCreateBy(SecurityContextHolder.getContext().getAuthentication().getName());
		}
		setCreateTime(new Date());

		if (getUpdateBy() == null) {
			setUpdateBy(SecurityContextHolder.getContext().getAuthentication().getName());
		}
		setUpdateTime(new Date());
	}

	/**
	 * テーブルを更新する前に実行する処理
	 * 共通カラムのうち、更新すべき値を設定
	 */
	@PreUpdate
	public void onPreUpdate() {
		/*
		 * @PreUpdateを付与されたメソッドはテーブルを更新する前に実行されるメソッドとなる
		 */
		setUpdateBy(SecurityContextHolder.getContext().getAuthentication().getName());
		setUpdateTime(new Date());
	}
}
