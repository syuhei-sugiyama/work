package com.example.demo.yoyaku.model;

import lombok.Data;

@Data
public class YoyakuInfo {

	/**
	 * イベント名
	 */
	private String title;

	/**
	 * 開始日付
	 */
	private String start;

	/**
	 * 終了時間
	 */
	private String end;

	/**
	 * URL
	 */
	private String url;
}
