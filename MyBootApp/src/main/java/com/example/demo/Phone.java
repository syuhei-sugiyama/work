package com.example.demo;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;

/*
 * いかに記述したアノテーションはすべて必ず必要なもの
 */
@Documented
@Constraint(validatedBy = PhoneValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@ReportAsSingleViolation
public @interface Phone {

	/*
	 * アノテーションクラスでは、メソッドを追加するだけで、
	 * そのメソッドが、アノテーションの引数に使える設定として追加できる
	 * ※defaultを設定しないと、その引数は、アノテーションを設定する際に、必須項目となり、
	 *   その項目がないと、アノテーションが働かない
	 */
	String message() default "please input a phone number.";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	boolean onlyNumber() default false;

}
