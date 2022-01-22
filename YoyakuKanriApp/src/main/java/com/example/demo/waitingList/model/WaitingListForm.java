package com.example.demo.waitingList.model;

import java.util.Map;

import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class WaitingListForm {

	@NotEmpty
	private String date;

	@NotEmpty
	private String startTime;

	@NotEmpty
	private String endTime;

	@NotEmpty
	private String customer;

	/**
	 * 画面へ表示するための、メニュー一覧保存用
	 */
	private Map<String, String> menus;

	/**
	 * 画面へ表示するための、美容師一覧保存用
	 */
	private Map<String, String> hairdressers;

	/**
	 * 画面から選択された美容師のID保存用
	 */
	private String selectHairdresserId;

	/**
	 * 画面から選択されたメニューのID保存用
	 * (checkboxの為、複数選択された時用にString配列で受け取る)
	 */
	private String[] selectMenuId;

	/**
	 * 更新・削除用にキャンセル待ち履歴IDの保持
	 */
	private String waitingListHistoryId;
}