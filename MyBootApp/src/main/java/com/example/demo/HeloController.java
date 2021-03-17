package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/*
 * RestControllerとは、RESTのために用意されている専用コントローラー
 * REST→「Representational State Transfer」
 * RESTはネットワーク経由で外部のサーバーにアクセスし、必要な情報を取得するような
 * 仕組みを作るのに利用される考え方。
 * また、RESTにしたがって設計されるシステムを「RESTful」と呼んだりする
 * 基本的に、シンプルなテキストの形でデータを送信する形。
 */
/*
 * @Controller
 * →通常のWebページを利用する場合に、コントローラークラスに付けるアノテーション
 */
@Controller
public class HeloController {
	String[] names = {"tuyano", "hanako", "taro", "sachiko", "ichiro"};
	String[] mails = {
			"syoda@tuyano.com",
			"hanako@flower",
			"taro@yamada",
			"sachiko@happy",
			"ichiro@baseball"
	};

	/*
	 * リクエストマッピング
	 * →サーバーが受け付けるURLと処理を関連付ける(=マッピングする)仕組み
	 * 引数に指定したアドレスにアクセスがあったら、割り当てられているメソッドが
	 * 呼び出される仕組み。
	 * 以下でいうと、「/」つまり、ルートのページへアクセスがあった際に、
	 * indexメソッドが呼び出される。
	 * このリクエストマッピングの設定を行うのが、@RequestMappingアノテーション
	 */
	@RequestMapping("/aaa")
	public String index() {
		/*
		 * リクエストにマッピングされるメソッド
		 * (@RequestMappingアノテーションが付与されたメソッド。リクエストハンドラ)の
		 * 書き方は決まっている。
		 * Stringを戻り値とする。
		 */
		return "Hello Spring-Boot World!!";
	}

	/*
	 * パス変数
	 * @RequestMappingの引数に指定した"/{num}"について
	 * →これは、"/"というアドレスの後にあるパスの値をnumという変数として受け取ることを示している
	 *
	 * メソッドの引数に使用されている「@PathVariable」
	 * このアノテーションは、パス変数によって受け取った値を、メソッドの引数として受け取ることを意味している。
	 * なので、例えば、"/100"というアドレスでアクセスがあった際、
	 * まず、「@RequestMapping("/{num}")」により、num=100という値が取り出され、
	 * その取り出した値が、「@PathVariable」により、メソッドの引数numに渡される。
	 * ちなみに、@RequestMappingの引数のnumを変更してアクセスしようとするとエラーになった
	 * →@RequestMappingの引数に指定した変数名と、メソッドの引数名は一致させる必要がある。
	 */
	@RequestMapping("/rest/{restnum}")
	public String restindex(@PathVariable int restnum) {
		int res = 0;
		for (int i = 1; i <= restnum; i++) {
			res += i;
		}
		return "total: " + res;
	}

	@RequestMapping("/resJsonObj/{id}")
	public DataObject resJsonObj(@PathVariable int id) {
		/*
		 * @RequestMapping、@PathVariableについては、他のメソッドと同じ
		 * ここでは、オブジェクトをreturnしている。
		 * Spring Boot側で自動で、returnされたインスタンスの内容をJSON形式に変換したテキストを
		 * ブラウザに出力してくれる。
		 * returnするインスタンスのクラス変数と、それにセットされた値が、JSON形式で出力される。
		 * なので、例えば、「/resJsonObj/0」というアドレスにアクセスがあった時、
		 * 変数idとして「0」を受け取り、メソッドの引数idへ渡す
		 * returnするDataObjectクラスのインスタンスを生成する際に、
		 * コンストラクタを実行しており、その引数として、このメソッドの引数idを使用
		 * DataObjectクラスの3つのクラス変数へそれぞれ値が代入された
		 * DataObjectクラス型のオブジェクトをreturnする為、
		 * 「{"id":0,"name":"tuyano","value":"syoda@tuyano.com"}」という内容が出力される。
		 */
		return new DataObject(id, names[id], mails[id]);
	}

	@RequestMapping("/webpage")
	public String webpage() {
		/*
		 * @Controllerアノテーションを付与した、Controllerクラスのリクエストハンドラ(アドレスに紐づけたメソッド)では
		 * テンプレート名(src/main/resources/templatesの配下に用意したHTMLファイルがテンプレートとなる。テンプレート名は
		 * ファイルの「.html」を除いた名前。今回なら、index.htmlの「index」がテンプレート名)をreturnすると
		 * この名前で、テンプレートを検索し、該当するHTMLファイルを読み込んでレンダリング(=整形)し、
		 * クライアント側(=リクエスト投げてきたブラウザ)に、送信する。
		 * ※ただし、これらは、pom.xmlにて、dependencyタグ(依存関係)にspring-boot-starter-thymeleafを設定しているからこそ。
		 * テンプレートエンジンのためのSpring Bootのライブラリを組み込むことで、「名前でテンプレートが自動的にロードされる」
		 * という機能が使えるようになっている。
		 */
		return "index";
	}

	@RequestMapping("/{num}")
	public String webIndex(@PathVariable int num, Model model) {
		/*
		 * Modelクラスのインスタンスの引数について
		 * Modelは、Webページで利用するデータを管理するためのクラス。
		 * このModelに、テンプレートで利用する値を設定しておくことで、データを渡すことが出来る
		 */
		int res = 0;
		for(int i = 1; i <= num; i++) {
			res += i;
		}
		/*
		 * addAttributeメソッドで、値を設定
		 * 第一引数で、テンプレート側で使用するための値の名前、第二引数に、保管する値をそれぞれ指定
		 * イメージ的には、key-value
		 * この形で保管しておくことで、テンプレート側は、自らが持っている変数名で、
		 * Modelに保管されている値を探し、ヒットすれば、それに紐づく値を取り出し、HTMLページへ出力する
		 * 今回の場合は、keyに「msg」を指定して、値を保管。呼び出すテンプレート(index.html)にも、
		 * th:text属性を使用して、変数msgの値を表示するように設定しているため、テンプレート側で
		 * ここで設定した値を取り出せる仕組み
		 */
		model.addAttribute("msg", "total: " + res);
		return "index";
	}

	@RequestMapping("/mav/{num}")
	public ModelAndView mav(@PathVariable int num, ModelAndView mav) {
		/*
		 * ModelクラスとModelAndViewクラスについて
		 * どちらもリクエストハンドラで利用可能。利用の仕方が若干異なる
		 * Modelは、テンプレート側で利用するデータ類だけをまとめて管理。
		 * データを管理するだけなので、ビュー関連(利用するテンプレート名など)の情報は持っていない。
		 * なので、Modelを戻り値として使えない(テンプレートの情報を持たない為)
		 * 一方、ModelAndViewは、テンプレートで利用するデータ類と、ビューに関する情報(利用するテンプレート名など)を
		 * すべてまとめて管理。ビュー関連の情報も持っている為、ModelAndViewを戻り値として返すことで、
		 * その戻り値に設定されたテンプレートを利用するようになる。(下記のように、setViewNameメソッドにより、
		 * 引数にテンプレート名を指定)
		 */
		int res = 0;
		for(int i=1; i <= num; i++) {
			res += i;
		}
		mav.addObject("msg", "ModelAndView total: " + res);
		mav.setViewName("index");
		return mav;
	}

	/*
	 * @RequestMappingにより、/formのアドレスへGET通信でアクセスした時に
	 * 呼び出されるメソッド
	 */
	@RequestMapping(value="/form", method = RequestMethod.GET)
	public ModelAndView formindex(ModelAndView mav) {
		/*
		 * setViewNameメソッドにより、呼び出すテンプレートはindex.html
		 */
		mav.setViewName("index");
		/*
		 * addObjectメソッドにより、index.htmlの中の、msgという変数へあてがう値を設定
		 */
		mav.addObject("msg", "お名前を書いて送信してください");
		return mav;
	}

	/*
	 * @RequestMappingにより、/formのアドレスへPOST通信でアクセスした時に
	 * 呼び出されるメソッド
	 */
	@RequestMapping(value="/form", method = RequestMethod.POST)
	public ModelAndView send(@RequestParam("text1")String str, ModelAndView mav) {
		/*
		 * 引数の@RequestParam("text1")について
		 * これは、フォーム送信されてきた値の中で、@RequestParamアノテーションの引数に紐づく値を取得する
		 * ここでは、フォーム送信されてきたものの中で、name="text1"というコントロールに入力されている値を
		 * 取得する。
		 * index.htmlでも、fromタグの中のinputタグのtypeでtextに指定したコントロールのnameをtext1にしている。
		 * formタグのmethod属性にはpostを指定し、action属性には、"/form"を指定することで、
		 * このメソッドが呼び出される条件を満たしている状況となる
		 * あとのaddObject、setViewNameメソッドの内容、流れは同じ。
		 */
		mav.addObject("msg", "こんにちは、" + str + "さん！！");
//		mav.addObject("value", str);
		mav.setViewName("index");
		return mav;
	}

	@RequestMapping(value="/otherForm", method=RequestMethod.GET)
	public ModelAndView otherForm(ModelAndView mav) {
		// 読み込むテンプレート名を設定
		mav.setViewName("index");
		/*
		 * 読み込むテンプレートの中から、
		 * th:text属性で、"msg"と設定されている箇所に対して指定した文字列を表示する
		 */
		mav.addObject("msg", "フォームを送信してください");
		// 上記にて、文字列を設定されたテンプレートをブラウザへ返す
		return mav;
	}

	@RequestMapping(value="/otherForm", method=RequestMethod.POST)
	public ModelAndView send(
			@RequestParam(value = "check1", required = false)boolean check1,
			@RequestParam(value = "radio1", required = false)String radio1,
			@RequestParam(value = "select1", required = false)String select1,
			@RequestParam(value = "select2", required = false)String[] select2,
			ModelAndView mav) {
		/*
		 * 引数について。
		 * required = falseを指定することで、valueに指定したパラメータがなくても
		 * エラーにならず処理が進められるようになる。
		 * 値が渡されない場合は、nullの値として処理される。
		 */

		String res = "";
		try {
			res = "check : " + check1 +
					" radio : " + radio1 +
					" select : " + select1 +
					"\nselect2 : ";
		} catch (NullPointerException e) {}
		try {
			res += select2[0];
			for(int i = 1; i < select2.length; i++) {
				res += ", " + select2[i];
			}
		} catch (NullPointerException e) {
			res += "null";
		}
		mav.addObject("msg", res);
		mav.setViewName("index");
		return mav;
	}


}

class DataObject {
	private int id;
	private String name;
	private String value;
	private int age;
	private String gender;

	public DataObject(int id, String name, String value) {
		super();
		this.id = id;
		this.name = name;
		this.value = value;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}
}
