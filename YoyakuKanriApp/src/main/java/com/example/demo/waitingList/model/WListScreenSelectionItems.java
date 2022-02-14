package com.example.demo.waitingList.model;

import java.util.Map;

import lombok.Getter;
import lombok.Setter;

/**
 * 【機能】キャンセル待ち画面の選択項目を格納する為のクラス
 */
@Getter
@Setter
public class WListScreenSelectionItems {
	/**
	 * メニュー一覧
	 */
	private Map<String, String> menus;

	/**
	 * 美容師一覧
	 */
	private Map<String, String> hairdressers;
}
