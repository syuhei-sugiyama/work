package com.example.demo.util.serviceif;

import java.util.List;

public interface UtilService {

	/**
	 * [機能] String→List<Integer>へ変換する
	 * @param conStr 変換対象文字列
	 * @param sepStr 区切り文字
	 * @return 変換後の値を格納したint配列
	 */
	List<Integer> convertStrToIntArr(String conStr, String serStr);

	/**
	 * [機能] String[]の要素を連結する
	 * @param conStrArr 変換対象文字列配列
	 * @param sepStr 連結用区切り文字
	 * @return 連結文字列
	 */
	String convertStrArrToStr(String[] conStrArr, String sepStr);
}
