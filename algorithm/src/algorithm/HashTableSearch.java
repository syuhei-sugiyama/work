package algorithm;

import java.util.Scanner;

public class HashTableSearch {
	// ハッシュ表の実体となる配列(要素数を10個とする)
	public static int[] hashTable =
		{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1};

	/**
	 * 【概要】ハッシュ関数
	 * @param data hashTableに格納する値
	 * @return hashTableにdataを格納する際の添え字
	 */
	public static int hashFunc(int data) {
		/*
		 * 引数dataを10で割った余りを返す
		 * 10で割った余りは、0～9になる為、
		 * 要素数10個で用意したhashTableの添え字にちょうど収まる
		 */
		return data % 10;
	}

	public static void main(String[] args) {
		int data, hashValue;

		// データをキー入力してハッシュ表に格納する
		Scanner scn = new Scanner(System.in);
		do {
			// 格納するデータを入力する
			System.out.printf("\n格納するデータ = ");
			data = scn.nextInt();

			// マイナスの値が入力されたらデータの入力受け付けを終了する
			if (data < 0) {
				break;
			}

			// ハッシュ関数を呼び出し、ハッシュ値を求める
			hashValue = hashFunc(data);
			System.out.printf("ハッシュ値 = %d %% 10 = %d\n", data, hashValue);

			/*
			 * ハッシュ関数によって返された値を添え字にして、
			 * 入力された値(=data)を、ハッシュ表(=hashTable)に格納する
			 */
			hashTable[hashValue] = data;
			System.out.printf("hashTable[%d]に%dを格納する。\n", hashValue, data);

		} while(true);

		// データをキー入力してハッシュ表からデータを探す
		do {
			// サーチするデータをキー入力
			System.out.printf("\nサーチするデータ = ");
			data = scn.nextInt();

			// マイナスの値が入力されたらデータのサーチを終了する
			if (data < 0) {
				break;
			}

			// ハッシュ関数を呼び出し、ハッシュ値を求める
			hashValue = hashFunc(data);
			System.out.printf("ハッシュ値 = %d %% 10 = %d\n", data, hashValue);

			// サーチした結果を表示する
			if (hashTable[hashValue] == data) {
				System.out.printf("hashTable[%d]の値は%dなので、見つかった位置を表示する。\n",
						hashValue, data);
				System.out.printf("%d番目に見つかりました。\n", hashValue);
			} else {
				System.out.printf("hashTable[%d]の値は%dではないので、"
						+ "「見つかりません。」と表示する。\n",
						hashValue, data);
				System.out.printf("見つかりません。\n");
			}
		} while(true);
		scn.close();
	}
}
