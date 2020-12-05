package algorithm;

public class InsertionSort {
	public static void printArray(int[] a) {
		for (int i = 0; i < a.length; i++) {
			System.out.printf("[" + a[i] + "]");
		}
		System.out.printf("\n");
	}
	public static void main(String[] args) {
		int[] a = {90, 34, 78, 12, 56};
		int ins, cmp, temp;

		// ソート前の配列の内容を表示する
		printArray(a);
		System.out.printf("\n");

		// 挿入法で昇順にソートする
		// 整列対象の配列の要素数分だけ繰り返し
		for (ins = 1; ins < a.length; ins++) {
			System.out.printf("外側のループ : temp ← a[%d] = %d\n", ins, a[ins]);
			// 一時的に値格納
			temp = a[ins];
			for (cmp = ins - 1; cmp >= 0; cmp--) {
				System.out.printf("内側のループ : ins = %d, cmp = %d, temp = %d\n", ins, cmp, temp);
				if (a[cmp] > temp) {
					a[cmp + 1] = a[cmp];
				} else {
					System.out.printf("breakで中断\n");
					break;
				}
			}
			System.out.printf("外側のループ : ins = %d, cmp = %d, temp = %d\n", ins, cmp, temp);
			System.out.printf("外側のループ : 確定した挿入位置 = a[%d] ← temp\n\n", cmp + 1);
			a[cmp + 1] = temp;
		}
		// ソート後の配列の内容を表示する
		printArray(a);
	}
}
