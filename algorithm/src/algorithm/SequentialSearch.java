package algorithm;

import java.util.Scanner;

public class SequentialSearch {
	public static void main(String[] argas) {
		// 入力受け付ける為のScannerクラス変数宣言
		Scanner scanner = new Scanner(System.in);
		int[] a = {72, 68, 92, 88, 41, 53, 97, 84, 39, 55};
		int x, pos, i;

		System.out.printf("x = ");
		// 入力された値をint型の値として取得
		x = scanner.nextInt();
		scanner.close();
		// 検索対象の値が格納されている要素番号を示す
		// 初期値「-1」→見つからなかった場合に返す値(添え字としてありえない番号)
		pos = -1;
		i = 0;
		System.out.printf("ループの前 : x = %d\n", x);
		System.out.printf("ループの前 : pos = %d\n", pos);
		for (int val : a) {
			// 検索対象と一致した場合
			if (val == x) {
				// posを更新
				pos = i;
				hitPrint(pos, i);
				// ループ抜ける
				break;
			}
			print(pos, i);
			// 添え字番号更新
			i++;
		}
		// 検索結果表示
		System.out.printf("pos = %d\n", pos);
		System.out.printf("ループの後 : pos = %d, i = %d\n", pos, i);
	}

	public static void print(int pos, int i) {
		System.out.printf("ループの中 : pos = %d, i = %d\n", pos, i);
	}

	public static void hitPrint(int pos, int i) {
		System.out.printf("ループの中(ヒット時) : pos = %d, i = %d\n", pos, i);
	}
}
