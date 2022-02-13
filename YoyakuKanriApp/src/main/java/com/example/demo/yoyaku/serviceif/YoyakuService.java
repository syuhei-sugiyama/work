package com.example.demo.yoyaku.serviceif;

import com.example.demo.yoyaku.model.YoyakuRirekiForm;
import com.example.demo.yoyaku.model.YoyakuScreenSelectionItems;

public interface YoyakuService {

	void register(YoyakuRirekiForm yoyakuRirekiForm);

	String getAllYoyaku();

	void getYoyakuById(YoyakuRirekiForm yoyakuRirekiForm, String yoyakuRirekiId);

	void update(YoyakuRirekiForm yoyakuRirekiForm);

	void delete(String yoyakuRirekiId);

	/**
	 * 【機能】予約画面の選択項目を取得する
	 * @return YoyakuScreenSelectionItems 選択項目を格納したインスタンス
	 */
	YoyakuScreenSelectionItems getScreenItems();

}