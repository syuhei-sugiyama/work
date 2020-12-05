package algorithm;

class StationList {
	// 駅名
	public String name;
	// 次のポインタを示す値
	public int next;
}

// 連結リストを操作するクラスの定義
public class LinkedList {
	// 連結リストの実体となる配列(要素数を最大10個とする)
	public static StationList[] list = new StationList[10];

	// 先頭ポインタ
	public static int top;

	// 初期状態の連結リストを作成するメソッド
	public static void initStationList() {
		for(int i=0; i < list.length; i++) {
			list[i] = new StationList();
		}

		// 駅名とポインタを設定する
		list[0].name = "新大阪";
		// 次が無い場合の「-1」
		list[0].next = -1;
		list[1].name = "名古屋";
		list[1].next = 3;
		list[2].name = "東京";
		list[2].next = 4;
		list[3].name = "京都";
		list[3].next = 0;
		list[4].name = "新横浜";
		list[4].next = 1;

		// 先頭ポインタを設定する
		top = 2;
	}

	// 連結リストの要素を表示するメソッド
	public static void printStationList() {
		// 先頭要素として宣言した値を代入
		int idx = top;
		// 次の検索対象が無くなるまで繰り返し
		while(idx != -1) {
			System.out.printf("[" + list[idx].name + "]→");
			// 要素を表示するたびに、次に検索する要素番号を
			// 表示したlistのポインタに更新する
			idx = list[idx].next;
		}
		System.out.printf("\n");
	}

	/**
	 * 【概要】連結リストに要素を挿入するメソッド
	 * @param insIdx 挿入する要素の添え字
	 * @param insName 挿入する要素の駅名
	 * @param prevIdx 前の添え字
	 */
	public static void insertStationList(int insIdx, String insName, int prevIdx) {
		// 空のStationListの駅名にinsNameを格納
		list[insIdx].name = insName;
		// 空のStationListのポインタに、前の要素のポインタを格納
		list[insIdx].next = list[prevIdx].next;
		// 前の要素のポインタに、挿入する要素の添え字を格納
		list[prevIdx].next = insIdx;
	}

	/**
	 * 【概要】連携リストから要素を削除するメソッド
	 * @param delIdx 削除対象の要素の添え字
	 * @param prevIdx 削除対象の要素の1つ前の要素の添え字
	 */
	public static void deleteStationList(int delIdx, int prevIdx) {
		/*
		 * 削除対象のStationListクラスのインスタンスの、
		 * 1つ前のStationListクラスのインスタンスの、次のポインタを示す値を
		 * 削除対象のStationListクラスのインスタンスの、次のポインタを示す値に
		 * 更新することによって、論理的に、要素を削除する
		 * なので、物理的には要素として残ったままになる。
		 */
		list[prevIdx].next = list[delIdx].next;
	}

	/**
	 * 【概要】本メソッド実行時のStationListの中身を表示
	 */
	public static void currentList() {
		for(int i=0; i < list.length; i++) {
			System.out.print(list[i].name + " " + list[i].next);
			System.out.println();
		}
	}

	public static void main(String[] args) {
		// 初期状態の連結リストを作成して表示する
		initStationList();
		printStationList();
		currentList();


		// 連結リストに要素を挿入して表示する
		insertStationList(8, "品川", 0);
		printStationList();
		currentList();

		// 連結リストから要素を削除して表示する
		deleteStationList(5, 2);
		printStationList();
		currentList();
	}
}
