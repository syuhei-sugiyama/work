package com.example.demo;

import java.util.Date;

import org.springframework.data.annotation.Id;

/*
 * MongoDBを利用するためのエンティティクラス
 * @Entityアノテーションは付与しない
 * →MongoDBは、一般的なRDBMSとは違う仕組みで動いており、テーブルと関連付けられる@Entityは使わず、一般的なクラスとして作成
 */
public class MyDataMongo {
	/*
	 * MongoDBでは、プライマリーキーに相当する値に対して、Stringを利用できる
	 * ランダムに生成されたキーがデータの識別用に保存されるようになる
	 */
	@Id
	private String id;

	private String name;
	private String memo;
	private Date date;

	public MyDataMongo(String name, String memo) {
		super();
		this.name = name;
		this.memo = memo;
		this.date = new Date();
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getMemo() {
		return memo;
	}

	public Date getDate() {
		return date;
	}
}
