package com.example.demo.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/*
 * @Target・・・このアノテーション(つまりUniqueLoginのこと)が、使える場所を指定する
 * ElementType.METHODとElementType.FIELDを指定すると、メソッドとフィールドで使える
 *
 * @Retention・・・このアノテーション(つまりUniqueLoginのこと)の保持期間を指定する
 * RetentionPolicy.RUNTIMEを指定すると、実行時にVMに保存されるため、いつでも使える
 *
 * @Constraint・・・このアノテーション(つまりUniqueLoginのこと)の制約(チェック)対象を指定する
 * 引数のvalidatedByで、チェックロジックを実装したクラスを指定する
 *
 * @interface・・・インタフェースに付けるアノテーション
 * これを付けることで、UniqueLoginという名前をアノテーションとして使える
 * 他のクラスで「@UniqueLogin」という形で使えるってこと
 */
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueLoginValidator.class)
public @interface UniqueLogin {
	String message() default "このユーザ名は既に登録されています";
	Class<?>[] groups() default{};
	Class<? extends Payload>[] payload() default{};
}
