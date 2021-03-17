package com.example.demo;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/*
 * 実際にバリデーションを行うバリデータクラス
 * ConstraintValidatorを実装する
 * 総称型として2つのクラスを指定する。
 * 第一引数には、Annotationクラスを継承したサブクラスを指定
 * 第二引数には、入力されるデータの型を指定
 * 以下の宣言により、このクラスは、「String値を入力し、Phoneアノテーションによって、バリデーションが設定されるもの」
 * という規定がなされたことを示す
 */
public class PhoneValidator implements ConstraintValidator<Phone, String> {

	private boolean onlyNumber = false;

	/*
	 * 下記2つのメソッドは、ConstraintValidatorを実装した為に、
	 * 実装クラスで、実装が必要なメソッド
	 * initializeメソッドでアノテーションクラスからメソッドを呼び出し、必要な値を取得する
	 * isValidでは、取得しておいた値に応じて処理を実行する
	 */

	@Override
	public void initialize(Phone phone) {
		/*
		 * 初期化メソッド
		 * 引数には、ConstraintValidator実装時に、総称型で指定したアノテーションクラスが渡される
		 */
		/*
		 * onlyNumberメソッド
		 * デフォルトではfalseを返すよう設定している。
		 * しかし、モデルのカラムにアノテーションを付与する際に、引数にて、onlyNumber = trueと設定していると、
		 * onlyNumberメソッドを呼び出した際、trueが取得できる
		 */
		onlyNumber = phone.onlyNumber();
	}

	@Override
	public boolean isValid(String input, ConstraintValidatorContext cxt) {
		/*
		 * 実際のバリデーション処理を行うメソッド
		 * returnの際に、入力された値が、指定した正規表現にマッチするかどうかを判定している
		 */
		if (input == null) {
			return false;
		}
		if (onlyNumber) {
			return input.matches("[0-9]*");
		} else {
			return input.matches("[0-9()-]*");
		}
	}
}
