package com.example.demo.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.repository.SiteUserRepository;


/*
 * 自作バリデーションを作成する場合、
 * ConstraintValidatorインタフェースを実装する
 * この時、オーバーライドするisValidメソッドでは、チェックエラーの場合return falseを返すようにする
 */
public class UniqueLoginValidator implements ConstraintValidator<UniqueLogin, String> {

	private final SiteUserRepository userRepository;

	public UniqueLoginValidator() {
		this.userRepository = null;
	}

	@Autowired
	public UniqueLoginValidator(SiteUserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return userRepository == null || userRepository.findByUsername(value) == null;
	}
}
