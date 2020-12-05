package algorithm;

import java.util.Scanner;

public class BinarySearch {
	public static void main(String[] args) {
		Scanner scn = new Scanner(System.in);
		int[] a = { 39, 41, 53, 55, 68, 72, 84, 88, 92, 97};
		int x, pos, left, right, middle;

		System.out.printf("x = ");
		x = scn.nextInt();
		// 検索対象値が格納されている添え字
		// -1で初期化
		pos = -1;
		// 配列の最小添え字
		left = 0;
		// 配列の最大添え字
		right = a.length - 1;
		System.out.printf("ループの前 : x = %d\n", x);
		System.out.printf("ループの前 : pos = %d, \tleft = %d, \tmiddle = ?, "
				+ "\tright = %d\n", pos, left, right);

		//
		while (pos == -1 && left <= right) {
			middle = (left + right) / 2;
			if(a[middle] == x) {
				pos = middle;
			} else if(a[middle] > x) {
				right = middle -1;
			} else {
				left = middle + 1;
			}
			System.out.printf("ループの中 : pos = %d, \tleft = %d, \tmiddle = %d, "
					+ "\tright = %d\n", pos, left, middle, right);
		}
		scn.close();
		System.out.printf("pos = %d\n", pos);
	}
}
