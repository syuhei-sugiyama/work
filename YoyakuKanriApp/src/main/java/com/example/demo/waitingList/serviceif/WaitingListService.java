package com.example.demo.waitingList.serviceif;

import com.example.demo.waitingList.model.WListScreenSelectionItems;
import com.example.demo.waitingList.model.WaitingListForm;

public interface WaitingListService {

	String getAllWaitingList();

	void register(WaitingListForm waitingListForm);

	/**
	 * 【機能】キャンセル待ち情報をフォームにセットする
	 * @param waitingListForm フォーム
	 * @param waitingListHistoryId キャンセル待ち履歴ID
	 */
	void setWaitingListInfoToForm(WaitingListForm waitingListForm, String waitingListHistoryId);

	/**
	 * 【機能】キャンセル待ちを更新する
	 * @param waitingListForm フォーム
	 */
	void updateWaitingList(WaitingListForm waitingListForm);

	/**
	 * 【機能】キャンセル待ちを削除する
	 * @param waitingListHistoryId キャンセル待ち履歴ID
	 */
	void deleteWaitingList(String waitingListHistoryId);

	/**
	 * 【機能】キャンセル待ち画面の選択項目を取得する
	 * @return
	 */
	WListScreenSelectionItems getScreenItems();
}
