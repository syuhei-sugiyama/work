package com.example.demo.waitingList.model;

import java.util.Map;

import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class WaitingListForm {

	public interface AddGroup {
	}

	public interface UpdateGroup {
	}

	public interface DeleteGroup {
	}

	public interface ConvertToYoyakuGroup {
	}

	@NotEmpty(groups = { AddGroup.class, UpdateGroup.class, ConvertToYoyakuGroup.class })
	private String date;

	@NotEmpty(groups = { AddGroup.class, UpdateGroup.class, ConvertToYoyakuGroup.class })
	private String startTime;

	@NotEmpty(groups = { AddGroup.class, UpdateGroup.class, ConvertToYoyakuGroup.class })
	private String endTime;

	@NotEmpty(groups = { AddGroup.class, UpdateGroup.class, ConvertToYoyakuGroup.class })
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
	@NotEmpty(groups = { AddGroup.class, UpdateGroup.class, ConvertToYoyakuGroup.class })
	private String selectHairdresserId;

	/**
	 * 画面から選択されたメニューのID保存用
	 * (checkboxの為、複数選択された時用にString配列で受け取る)
	 */
	@NotEmpty(groups = { AddGroup.class, UpdateGroup.class, ConvertToYoyakuGroup.class })
	private String[] selectMenuId;

	/**
	 * 更新・削除用にキャンセル待ち履歴IDの保持
	 */
	@NotEmpty(groups = { UpdateGroup.class, DeleteGroup.class, ConvertToYoyakuGroup.class })
	private String waitingListHistoryId;
}
