package com.example.demo.yoyaku.model;

import javax.validation.constraints.NotEmpty;

import lombok.Data;

/**
 * 【機能】予約登録画面で使用するフォームクラス
 */
@Data
public class YoyakuRirekiForm {

	@NotEmpty
	private String date;

	@NotEmpty
	private String startTime;

	@NotEmpty
	private String endTime;

	@NotEmpty
	private String customer;

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
	 * 更新・削除用に予約履歴IDの保持
	 */
	private String yoyakuRirekiId;

	/**
	 * キャンセル待ち→予約へ移動した時の為にキャンセル待ち履歴IDを保持
	 */
	private String waitingListHistoryId;
}
