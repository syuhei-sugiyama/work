package com.example.demo.waitingList.model;

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

	/**
	 * 予約→キャンセル待ちへ移動した時の為に予約履歴IDを保持
	 */
	private String yoyakuRirekiId;
}
