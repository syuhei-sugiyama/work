package algorithm;

/**
 *
 * 【概要】二分探索木の要素用クラス
 *
 */
class BST {
	// 要素の値
	public int data;
	// 左側の要素の添え字
	public int left;
	// 右側の要素の添え字
	public int right;
}

/**
 * 【概要】二分探索木操作用クラス
 *
 *
 */
public class BinarySearchTree {
	// 二分探索木の実体となる配列(要素数を最大10個とする)
	public static BST[] tree = new BST[10];

	// ルート(根)の要素の添え字(連結リストの先頭ポインタ)
	public static int rootIdx = 0;

	// その他の要素の添え字
	public static int newIdx = 0;

	/**
	 * 【概要】二分探索木に要素を追加する
	 * @param data
	 */
	public static void addBST(int data) {
		// 現在たどっている要素の添え字
		int currentIdx;
		// 追加が完了したことを示すフラグ
		boolean addFlag;

		// 物理的な位置に追加
		tree[newIdx].data = data;
		tree[newIdx].left= -1;
		tree[newIdx].right= -1;

		// ルートのデータではない場合、論理的な位置にポインタを設定する
		if(newIdx != rootIdx) {
			// ルートから二分探索木をたどる
			currentIdx = rootIdx;
			// 追加未完了の為、false代入
			addFlag = false;

			do {
				// より小さい場合は、左側にたどる
				if(data < tree[currentIdx].data) {
					/*
					 * 左側が末端なら、そこに追加する
					 * 次のポインタを示す値が「-1」つまり、次の要素が無い
					 * すなわち、末端を意味する
					 */
					if(tree[currentIdx].left == -1) {
						tree[currentIdx].left = newIdx;
						addFlag = true;
					} else {
						// 左側が末端でなければ、さらに左側の要素をたどる
						currentIdx = tree[currentIdx].left;
					}
				} else {
					// より大きい場合は、右側にたどる(同じ値はないとする)
					// 右側が末端なら、そこに追加する
					if(tree[currentIdx].right == -1) {
						tree[currentIdx].right = newIdx;
						addFlag = true;
					} else {
						// 右側が末端でないなら、さらに右側の要素をたどる
						currentIdx = tree[currentIdx].right;
					}
				}
			} while(addFlag == false);
		}

		// 次に格納する要素の為に添え字を1増やしておく
		newIdx++;
	}

	/**
	 * 【概要】二分探索木の実体の配列を、物理的な順序で表示する
	 */
	public static void printPhysicalBST() {
		int i = 0;

		for(i = 0; i < newIdx; i++) {
			System.out.printf(
					"tree[%d] : data = %d, left = %d, right = %d\n",
					i, tree[i].data, tree[i].left, tree[i].right
					);
		}
	}

	/**
	 * 【概要】二分探索木を論理的な順序で表示するメソッド
	 */
	public static void printLogicalBST(int currentIdx) {
		if(currentIdx != -1) {
			System.out.printf(
					"tree[%d] : data = %d, left = %d, right = %d\n",
					currentIdx, tree[currentIdx].data, tree[currentIdx].left,tree[currentIdx].right
					);
			// この部分が再帰呼び出し
			printLogicalBST(tree[currentIdx].left);
			printLogicalBST(tree[currentIdx].right);
		}
	}

	/**
	 * 【概要】二分探索木を探索するメソッド
	 * @param x 検索値
	 * @return
	 */
	public static int searchBST(int x) {
		// 見つかった要素の添え字
		int idx = -1;
		// 現在たどっている要素の添え字
		int currentIdx = rootIdx;

		while(currentIdx != -1) {
			if(tree[currentIdx].data == x) {
				idx = currentIdx;
				break;
			} else if (tree[currentIdx].data > x) {
				currentIdx = tree[currentIdx].left;
			} else {
				currentIdx = tree[currentIdx].right;
			}
		}

		return idx;
	}

	/**
	 * 【概要】再帰呼び出しで二分探索木を探索するメソッド
	 * @param x 検索値
	 * @param currentIdx 要素の添え字
	 * @return
	 */
	public static int searchRecBST(int x, int currentIdx) {
		if(currentIdx == -1) {
			return -1;
		} else {
			if(tree[currentIdx].data == x) {
				return currentIdx;
			} else if(tree[currentIdx].data > x) {
				// 再帰呼び出し
				return searchRecBST(x, tree[currentIdx].left);
			} else {
				// 再帰呼び出し
				return searchRecBST(x, tree[currentIdx].right);
			}
		}
	}

	/**
	 * 【概要】プログラム実行開始mainメソッド
	 */
	public static void main(String[] args) {
		for(int i = 0; i < tree.length; i++) {
			/*
			 * 配列treeは、格納できる要素としてBSTクラス型の値を指定してる為
			 */
			tree[i] = new BST();
		}

		// 二分探索木を構築して、物理的な順序で表示する
		addBST(4);
		addBST(6);
		addBST(5);
		addBST(2);
		addBST(3);
		addBST(7);
		addBST(1);
//		printPhysicalBST();

		// 二分探索木を論理的な順序で表示する
//		System.out.printf("-----------------------------------------------\n");
//		printLogicalBST(rootIdx);

		// 二分探索木を探索する
		System.out.printf("「5」の探索結果 = %d\n", searchBST(5));
		System.out.printf("「8」の探索結果 = %d\n", searchBST(8));

		// 再帰呼び出しで二分探索木を探索する
		System.out.printf("-----------------------------------------------\n");
		System.out.printf("「5」の探索結果 = %d\n", searchRecBST(5, rootIdx));
		System.out.printf("「8」の探索結果 = %d\n", searchRecBST(8, rootIdx));
	}
}
