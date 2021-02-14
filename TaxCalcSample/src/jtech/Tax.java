package jtech;

public class Tax {

	public int taxIn(int price) {
		double taxRate = 0.08;

		// Tax 税込み金額を計算する
		int postTax = (int) (price * (1 + taxRate));
		// Tax 税込み金額を返す
		return postTax;
	}

}
