package com.example.demo;

import java.util.HashSet;
import java.util.Set;

import org.thymeleaf.dialect.AbstractProcessorDialect;
import org.thymeleaf.processor.IProcessor;
import org.thymeleaf.standard.StandardDialect;

/*
 * ユーティリティオブジェクトを利用するための「Dialect」クラスについて
 * AbstractProcessorDialectクラスを継承したクラスを用意
 */
public class MyDialect extends AbstractProcessorDialect {

	private static final String DIALECT_NAME = "My Dialect";

	public MyDialect() {
		super(DIALECT_NAME, "my", StandardDialect.PROCESSOR_PRECEDENCE);
	}

	protected MyDialect(String name, String prefix, int processorPrecedence) {
		super(name, prefix, processorPrecedence);
	}

	@Override
	public Set<IProcessor> getProcessors(String dialectPrefix) {
		/*
		 * IProcessorは、Thymeleafの属性などの処理を行うクラス全般を表すインターフェース
		 */
		final Set<IProcessor> processors = new HashSet<IProcessor>();
		processors.add(new MyPageAttributeTagProcessor(dialectPrefix));
		return processors;
	}
}