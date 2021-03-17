package com.example.demo;

import org.thymeleaf.IEngineConfiguration;
import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.engine.AttributeName;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.processor.element.AbstractAttributeTagProcessor;
import org.thymeleaf.processor.element.IElementTagStructureHandler;
import org.thymeleaf.standard.expression.IStandardExpression;
import org.thymeleaf.standard.expression.IStandardExpressionParser;
import org.thymeleaf.standard.expression.StandardExpressions;
import org.thymeleaf.templatemode.TemplateMode;

/*
 * Thymeleafに用意されている「ユーティリティオブジェクト」の利用
 * Thymeleafの中に埋め込んで利用できる、特別なオブジェクト
 * Javaのクラスとして定義し、Thymeleaf内から、呼び出して結果を出力させる
 * 「ユーティリティオブジェクト」を使うには、
 * Thymeleafの属性を処理する「AttributeTagProcessor」クラスと、
 * AttributeTagProcessorをまとめるための「Dialect」クラスを用意する必要がある
 */
/*
 * まずは、AbstractAttributeTagProcessorを継承したクラスを用意
 * 独自タグの具体的な処理を用意する
 */
public class MyPageAttributeTagProcessor extends AbstractAttributeTagProcessor {

	private static final String ATTR_NAME = "mypage";
	private static final int PRECEDENCE = 10000;
	public static int size = 2;

	public MyPageAttributeTagProcessor(final String dialectPrefix) {
		super(TemplateMode.HTML, dialectPrefix, null, false, ATTR_NAME, true, PRECEDENCE, true);
	}

	protected MyPageAttributeTagProcessor(
			TemplateMode templateMode,
			String dialectPrefix,
			String elementName,
			boolean prefixElementName,
			String attributeName,
			boolean prefixAttributeName,
			int precedence,
			boolean removeAttribute) {
		super(
				templateMode,
				dialectPrefix,
				elementName,
				prefixElementName,
				attributeName,
				prefixAttributeName,
				precedence,
				removeAttribute);
	}

	/*
	 * 引数について
	 * ITemplateContext・・・テンプレートのコンテキストを扱う。ここからテンプレートエンジンや設定情報など、
	 * テンプレートの情報を取り出せる
	 * IProcessableElementTag・・・エレメントタグを扱うクラス。タグに組み込まれている属性などの情報を取り出せる
	 * AttributeName・・・属性名を扱うクラス。属性名やプレフィックス(値の前に付けられるテキスト)などを扱う
	 * String・・・属性の値
	 * IElementTagStructureHandler・・・エレメントの構造をハンドリングする。これを使って、エレメントに属性などを
	 * 組み込んだりする
	 */
	@Override
	protected void doProcess(
			ITemplateContext context,
			IProcessableElementTag tag,
			AttributeName attrName,
			String attrValue,
			IElementTagStructureHandler handler) {
		/*
		 * int value = (int) expression.execute(context);までは、お決まりの手順
		 * 最後の行については、独自で作成する属性に設定する値を、整数値にしたい場合はintにキャストする
		 * テキストを値に設定する時は、Stringにキャストしてあげる
		 */
		final IEngineConfiguration configuration = context.getConfiguration();
		final IStandardExpressionParser parser =
				StandardExpressions.getExpressionParser(configuration);
		final IStandardExpression expression =
				parser.parseExpression(context, attrValue);
		int value = (int) expression.execute(context);
		value = value < 0 ? 0 : value;
		handler.setAttribute("href", "/page?size=" + size + "&page=" + value);
	}

}
