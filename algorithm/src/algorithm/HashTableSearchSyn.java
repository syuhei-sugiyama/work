package algorithm;

import java.util.Scanner;

public class HashTableSearchSyn {
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
		// 格納位置、探索位置
		int pos;

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

			// データの格納位置を決める
			pos = hashValue;
			System.out.printf("格納位置pos = %d\n", pos);
			while (hashTable[pos] != -1) {
				// 格納位置を1つ先に進める
				pos++;

				// 末尾を超えたら先頭に戻す
				if(pos >= hashTable.length) {
					pos = 0;
				}
				System.out.printf("格納位置pos = %d\n", pos);

				/*
				 * ハッシュ値の位置まで戻ったら、
				 * ハッシュ表が一杯なので、繰り返しを終了する
				 */
				if(pos == hashValue) {
					break;
				}
			}

			if(hashTable[pos] == -1) {
				// ハッシュ表が一杯でなければ、データを格納する
				hashTable[pos] = data;
				System.out.printf("hashTable[%d]に%dを格納する。\n", pos, data);
			} else {
				// 「ハッシュ表が一杯です。」と表示する
				System.out.printf("ハッシュ表が一杯です。\n");
			}
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

			// データをサーチする
			pos = hashValue;
			System.out.printf("探索位置pos = %d\n", pos);
			while(hashTable[pos] != -1 && hashTable[pos] != data) {
				// 探索位置を1つ先に進める
				pos++;

				// 末尾を超えたら先頭に戻す
				if(pos >= hashTable.length) {
					pos = 0;
				}
				System.out.printf("探索位置pos = %d\n", pos);

				/*
				 * -1を見つけるか、またはハッシュ値の位置まで戻ったら、
				 * データが見つからないことが確定なので、繰り返しを終了する
				 */
				if(hashTable[pos] == -1 || pos == hashValue) {
					break;
				}
			}
			// サーチした結果を表示する
			if(hashTable[pos] == data) {
				System.out.printf(
						"hashTable[%d]の値は%dなので、見つかった位置を表示する。\n",
						pos, data);
				System.out.printf("%d番目に見つかりました。\n", pos);
			} else {
				System.out.printf(
						"hashTable[%d]の値は%dなので、「見つかりません。」と表示する。\n",
						pos, hashTable[pos]);
				System.out.printf("見つかりません。\n");
			}
		} while(true);
		scn.close();
	}
}
