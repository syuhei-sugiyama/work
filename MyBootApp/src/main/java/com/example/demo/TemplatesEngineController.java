package com.example.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.repositories.MyDataMongoRepository;
import com.example.demo.repositories.MyDataRepository;

// @Controllerアノテーションにより、webページを扱う際のコントローラークラスであることを示す
@Controller
public class TemplatesEngineController {
	@RequestMapping("/tec")
	public ModelAndView ognl1(ModelAndView mav) {
		mav.setViewName("index2");
		return mav;
	}

	/*
	 * @RequestMappingにより、/tec2へアクセスがあった際に、呼ばれるメソッド
	 */
	@RequestMapping("/tec2")
	public ModelAndView tec2(ModelAndView mav) {
		// 表示するテンプレートは「index3.html」を設定
		mav.setViewName("index3");
		// テンプレート内の、msgという名前の変数に、第二引数の値を設定
		mav.addObject("msg", "current data.");
		// DataObjectクラスのインスタンス
		DataObject obj = new DataObject(123, "hanako", "hanako@flower");
		obj.setAge(25);
		obj.setGender("male");
		/*
		 * 作成したDataObjectクラスのインスタンスを、テンプレート内で
		 * objectという名前の変数で使用できるように、設定
		 */
		mav.addObject("object", obj);
		return mav;
	}

	@RequestMapping("/tec3/{id}")
	/*
	 * パス変数として、idという名前を設定
	 * @PathVariableアノテーションで、パス変数idの値を引数として設定
	 */
	public ModelAndView tec3(@PathVariable int id, ModelAndView mav) {
		// 表示するテンプレートは「index4.html」を設定
		mav.setViewName("index4");
		// テンプレート内で使用するため、key-valueの形で値を格納
		mav.addObject("id", id);
		mav.addObject("check", id >= 0);
		mav.addObject("trueVal", "POSITIVE!!");
		mav.addObject("falseVal", "negative.....");
		return mav;
	}

	@RequestMapping("/month/{month}")
	/*
	 * パス変数として、monthという名前を設定
	 * @PathVariableアノテーションで、パス変数monthの値を引数として設定
	 */
	public ModelAndView month(@PathVariable int month, ModelAndView mav) {
		// クエリパラメータの値を月と見立てて、季節を表示する
		mav.setViewName("index5");
		// Mathクラスのabsメソッドにより、引数monthの絶対値を取得し、それを12で割った値を取得
		int m = Math.abs(month) % 12;
		// 上記の処理で、monthが12だった場合のみ、12を代入
		m = m == 0 ? 12 : m;
		mav.addObject("month", m);
		/*
		 * 下記の処理により、1～2なら0、3～5なら1、6～8なら2、9～11は3、12は4を得られる。
		 * この値をテンプレート側で判定する。
		 */
		mav.addObject("check", Math.floor(m / 3));
		return mav;
	}

	@RequestMapping("/collection")
	public ModelAndView collection(ModelAndView mav) {
		mav.setViewName("index6");
		ArrayList<String[]> data = new ArrayList<String[]>();
		data.add(new String[]{"taro", "taro@yamada", "090-999-999"});
		data.add(new String[]{"hanako", "hanako@flower", "080-888-888"});
		data.add(new String[]{"sachiko", "sachiko@happy", "080-888-888"});
		mav.addObject("data", data);
		return mav;
	}

	@RequestMapping("/inline")
	public ModelAndView inline(ModelAndView mav) {
		mav.setViewName("index7");
		ArrayList<DataObject> data = new ArrayList<DataObject>();
		data.add(new DataObject(0, "taro", "taro@yamada"));
		data.add(new DataObject(1, "hanako", "hanako@flower"));
		data.add(new DataObject(2, "sachiko", "sachiko@happy"));
		mav.addObject("data", data);
		return mav;
	}

	@RequestMapping("/jsInline/{tax}")
	public ModelAndView jsInline(ModelAndView mav, @PathVariable int tax) {
		mav.setViewName("index8");
		mav.addObject("tax", tax);
		return mav;
	}

	@RequestMapping("/fragment")
	public ModelAndView fragment(ModelAndView mav) {
		mav.setViewName("index9");
		return mav;
	}

	/*
	 * @Autowired
	 * 自動紐づけ。アプリケーション(今回はMyBootAppというアプリ)に用意されているBeanオブジェクト
	 * (Spring MVCによって自動的にインスタンスが作成され、アプリケーション内で利用可能になったもの)に
	 * 関連付けを行う。これにより、MyDataRepositoryのインスタンスが自動的にrepositoryフィールドに設定される。
	 * MyDataRepositoryはInterfaceですが、Spring MVCにより、Interfaceに必要な処理が組み込まれた無名クラスの
	 * インスタンスが作成され、それがフィールドに設定されている。
	 */
	@Autowired
	MyDataRepository repository;

	/*
	 * @PersistenceContext
	 * EntityManagerのBeanを取得し、フィールドに設定するためのアノテーション
	 * EntityManagerは、Spring Bootの場合、アプリ起動時に、自動的にBeanとしてインスタンスが登録される
	 * これを@PersistenceContextによって、フィールド(変数entityManager)に割り当てている
	 */
//	@PersistenceContext
	EntityManager entityManager;

	MyDataDaoImpl dao;

	/*
	 * Beanが登録されるまでの流れ
	 * まず結論としては、実装者が行うべき作業は、
	 * 「@Repositoryを指定したインターフェースを用意すること」
	 * 「@Autowiredを指定したリポジトリインターフェースのフィールドを用意すること」だけ。
	 * 下記に、Beanが登録されるまでの流れを記述しておく。
	 *   ①アプリケーション起動時に、@Repositoryを付けられたインターフェースを検索し、自動的にその実装クラスが作成され、
	 *     更にそのインスタンスがアプリケーションに、Beanとして登録される。
	 *   ②コントローラーなどのクラスがロードされる際、@Autowiredが指定されているフィールドがあった場合、
	 *     ①で登録済みのBeanから、同じクラスのものを検索し、自動的にそのフィールドにインスタンスを割り当てる
	 */
	@RequestMapping(value = "/repository", method = RequestMethod.GET)
	public ModelAndView repository(ModelAndView mav, @ModelAttribute("formModel") MyData mydata) {
		/*
		 * @ModelAttributeについて
		 * エンティティクラスのインスタンスを自動的に用意するために使用するアノテーション
		 * 引数には、テンプレートにて、formタグのth:object属性にて指定した、オブジェクト名と同じ値を設定する
		 * GETアクセスの際は、新たにnewされたMyDataクラスのインスタンスが渡される
		 * なので、このタイミングで、mydataのフィールド(nameやage)にセッターを使って値をセットすると、
		 * それらの値がセットされた状態で、テンプレートが画面に表示される
		 */
		mav.setViewName("repository");
		mav.addObject("msg", "this is sample content.");
		mav.addObject("formModel", mydata);
		/*
		 * findAllメソッド
		 * エンティティをすべて取り出す
		 * MyDataRepositoryクラスの継承元であるJpaRepositoryインターフェースにあるfindAllメソッドを使用している
		 * MyDataクラスがエンティティとして認識できるのは、
		 * MyDataRepositoryインターフェース作成時に、JpaRepositoryインターフェースを継承し、かつ、
		 * 総称型で、<MyData, Long>と指定することで、対象となるエンティティクラスがMyDataクラス、
		 * プライマリーキーとなるのがLong型の値であることを設定出来ている為
		 */
		Iterable<MyData> list = repository.findAll();
		mav.addObject("datalist", list);
		return mav;
	}

	@RequestMapping(value = "/repository", method = RequestMethod.POST)
	@Transactional(readOnly = false)
	public ModelAndView form(
			@ModelAttribute("formModel") @Validated MyData mydata,
			BindingResult result,
			ModelAndView mav) {
		/*
		 * @Validated
		 * エンティティの各値を自動的に、バリデーションチェックする
		 * →フォームが送信されて、エンティティクラスのインスタンスを、自動で作成する際に、
		 *   バリデーションチェックしてくれる
		 * BindingResult
		 * バリデーションチェックの結果を格納したオブジェクト
		 * @ModelAttributeでフォームの値からMyDataクラスのインスタンスを作成する際の結果がBindingResult型の引数に
		 * 格納されている
		 * エラーが発生しているかどうかは、hasErrorsメソッドで分かる
		 */
		ModelAndView res = null;
		if (!result.hasErrors()) {
			repository.saveAndFlush(mydata);
			res = new ModelAndView("redirect:/repository");
		} else {
			mav.setViewName("repository");
			mav.addObject("msg", "sorry, error is occured.....");
			Iterable<MyData> list = repository.findAll();
			mav.addObject("datalist", list);
			res = mav;
		}
		return res;
		/*
		 * @ModelAttributeについて
		 * エンティティクラスのインスタンスを自動的に用意するために使用するアノテーション
		 * 引数には、テンプレートにて、formタグのth:object属性にて指定した、オブジェクト名と同じ値を設定する
		 * POSTアクセスの場合、テンプレートから送信された、引数と同じ名前のオブジェクトが渡される。
		 * メソッドの引数である、MyDataクラスのインスタンスmydataには、
		 * テンプレートから受け取ったオブジェクト内の、MyDataクラスのフィールド名と一致するvalueが
		 * 自動的に保持された状態となっている。
		 * データを保持した状態のエンティティクラスのインスタンスを、下記の、saveAndFlushメソッド
		 * (JpaRepositoryに用意されているメソッド)を使用して、エンティティを永続化する。
		 *
		 */
		/*
		 * @Transactionalについて
		 * トランザクション機能を実現するためのアノテーション
		 * これをメソッドに付与することで、メソッド内で実行されるデータベースアクセスが1つのトランザクション処理として
		 * 一括して実行されるようになる。
		 * 引数のreadOnlyは、文字通り、読み込むのみとするかどうかを指定するもの
		 * falseにすることで、書き換え可能な、保存などのデータ更新を許可するトランザクションに指定している
		 */
	}

	/*
	 * @PostConstructについて
	 * コンストラクタにより、本クラスのインスタンスが生成された後に呼び出されるメソッドであることを
	 * 設定するためのアノテーション。メソッド名は任意でOK
	 * コントローラーは、呼ばれた時に、最初に一度だけインスタンスが作成され、
	 * 以後はそのインスタンスを保持する。そのため、@PostConstructを付与したメソッドにて、ダミーデータ作成を
	 * 用意することで、アプリケーション実行時に必ず一度だけ実行され、データが準備出来る
	 */
	@PostConstruct
	public void init() {
		dao = new MyDataDaoImpl(entityManager);
		// ダミーデータ作成
		MyData d1 = new MyData();
		d1.setName("sachiko");
		d1.setAge(11);
		d1.setMail("test1@test1.co.jp");
		d1.setMemo("090999999");
		repository.saveAndFlush(d1);
		MyData d2 = new MyData();
		d2.setName("hanako");
		d2.setAge(22);
		d2.setMail("test2@test2.co.jp");
		d2.setMemo("080888888");
		repository.saveAndFlush(d2);
		MyData d3 = new MyData();
		d3.setName("asas");
		d3.setAge(33);
		d3.setMail("test3@test3.co.jp");
		d3.setMemo("070777777");
		repository.saveAndFlush(d3);
	}

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public ModelAndView edit(@ModelAttribute("formModel") MyData mydata, @PathVariable int id, ModelAndView mav) {
		mav.setViewName("edit");
		mav.addObject("title", "edit mydata.");
		/*
		 * パス変数から受け取った値を使用して、エンティティを検索
		 * 戻り値は、検索結果が格納されたMyDataクラスのインスタンスを格納したOptionalクラスのインスタンス
		 * getメソッドを使用することで、格納されているMyDataクラスのインスタンスを取得できる
		 */
		Optional<MyData> data = repository.findById((long)id);
		/*
		 * リポジトリでは、メソッド名を元にエンティティ検索の処理を自動生成するようになっている
		 * その為、findByIdメソッドの実装をせずに済む
		 * findByIdメソッドの場合は、引数の値をID(エンティティごとの一意な識別子)に持つエンティティを検索する
		 * 処理が自動生成されて使えるようになっている。
		 */
		mav.addObject("formModel", data.get());
		return mav;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	@Transactional(readOnly = false)
	public ModelAndView update(@ModelAttribute("formModel") MyData mydata, ModelAndView mav) {
		/*
		 * edit.htmlから送信されてきたフォームが、@ModelAttributeにより、
		 * MyDataクラスのインスタンスに、値が格納された状態で渡される。
		 * このデータをもとに、エンティティの保存を行う。
		 * データの更新には、保存の時と同じ、saveAndFlushメソッドを使用する。
		 * こちらの場合は、自動で作成されるMyDataクラスのインスタンスに、edit.htmlのhidden属性を設定したIDの値が含まれている
		 * saveAndFlushメソッドにて、エンティティ保存時、既にそのIDのエンティティが存在すると、内容を更新して保存する
		 * 新規作成時、フォームには、IDのフィールドが無かったが、これは、自動で新たにIDを割り当ててエンティティとして
		 * 保存するため、IDのフィールドが無かった
		 */
		repository.saveAndFlush(mydata);
		return new ModelAndView("redirect:/repository");
	}

	/*
	 * @RequestMappingにより、valueに設定したアドレスへ、GETアクセスがあった際に呼ばれるメソッド
	 */
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	/*
	 * @PathVariableにより、パス変数であるidの値が、引数idに渡される
	 */
	public ModelAndView delete(@PathVariable int id, ModelAndView mav) {
		// delete.htmlを表示するよう設定
		mav.setViewName("delete");
		mav.addObject("title", "delete mydata");
		/*
		 * パス変数で渡された値を元に、MyDataクラスのエンティティを検索
		 */
		Optional<MyData> data = repository.findById((long)id);
		/*
		 * formModelという名前で、取得したOptionalクラスのインスタンスから、MyDataクラスのエンティティを取得し格納
		 */
		mav.addObject("formModel", data.get());
		return mav;
	}

	/*
	 * @RequestMappingにより、valueに設定したアドレスへ、POSTアクセスがあった際に呼ばれるメソッド
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	/*
	 * @Transactionalにより、付与されたメソッド内の操作を、1つのトランザクションとして扱う
	 * また、引数のreadOnly = falseにより、データ更新可能なトランザクションとなる
	 */
	@Transactional(readOnly = false)
	/*
	 * @RequestParamにより、引数に指定した名前の値を、フォームから送られてきた値の中から探しだし、引数idに渡せる
	 */
	public ModelAndView remove(@RequestParam("id") long id, ModelAndView mav, @ModelAttribute("formModel") MyData mydata) {
		/*
		 * deleteByIdメソッド
		 * 引数として渡されたID(エンティティを一意に識別する値)を用いて、エンティティを削除する
		 * エンティティを渡しても削除できる。その際は、下記に記述したように、
		 * deleteメソッドを使用する
		 */
		repository.deleteById(id);
//		repository.delete(mydata);
		return new ModelAndView("redirect:/repository");
	}

	@RequestMapping(value = "/enMana", method = RequestMethod.GET)
	public ModelAndView enMana(ModelAndView mav) {
		mav.setViewName("enMana");
		mav.addObject("msg", "MyDataのサンプル");
		/*
		 * 手動で、MyDataDaoImplクラスに実装したメソッドを呼び出して、MyDataクラスのエンティティ一覧を取得
		 */
//		Iterable<MyData> list = dao.getAll();
//		Iterable<MyData> list = dao.getAllByCriteriaAPI();
//		Iterable<MyData> list = dao.getAllSortByCriteriaAPI();
		Iterable<MyData> list = dao.getAllPaging();
		mav.addObject("datalist", list);
		return mav;
	}

	@RequestMapping(value = "/find", method = RequestMethod.GET)
	public ModelAndView find(ModelAndView mav) {
		mav.setViewName("find");
		mav.addObject("title", "Find Page");
		mav.addObject("msg", "MyDataのサンプルです");
		mav.addObject("value", "");
		Iterable<MyData> list = dao.getAll();
		mav.addObject("datalist", list);
		return mav;
	}

	@RequestMapping(value = "/find", method = RequestMethod.POST)
	public ModelAndView search(HttpServletRequest request, ModelAndView mav) {
		/*
		 * HttpServletRequestについて
		 * これまで、フォームから送信されてきた値は、@RequestParamを使っていた。
		 * HttpServletRequestが使えないわけではなく、
		 * @RequestParamを使うことで、HttpServletRequestのgetParameterメソッドを呼び出して、
		 * フォームから送信されてきた値を受け取る操作を自動的に行い、その結果を引数に設定していた
		 */
		mav.setViewName("find");
		String param = request.getParameter("fstr");
		if (param == "") {
			mav = new ModelAndView("redirect:/find");
		} else {
			mav.addObject("title", "Find result");
			mav.addObject("msg", "「" + param + "」の検索結果");
			mav.addObject("value", param);
//			List<MyData> list = dao.find(param);
//			List<MyData> list = dao.find2(param);
//			List<MyData> list = dao.findByBindVariable(param);
//			List<MyData> list = dao.findByNamedQuery(param);
			List<MyData> list = dao.findByCriteriaAPI(param);
			mav.addObject("datalist", list);
		}
		return mav;
	}

	@RequestMapping(value = "/queryAnnotation", method = RequestMethod.GET)
	public ModelAndView queryAnnotation(ModelAndView mav) {
		mav.setViewName("queryAnnotation");
		mav.addObject("title", "Find Page");
		mav.addObject("msg", "MyDataのサンプルです");
		Iterable<MyData> list = repository.findAllOrderByName();
		mav.addObject("datalist", list);
		return mav;
	}

	@RequestMapping(value = "/findByAge", method = RequestMethod.GET)
	public ModelAndView findByAge(ModelAndView mav) {
		mav.setViewName("findByAge");
		mav.addObject("title", "FindByAge Page");
		mav.addObject("msg", "MyDataのサンプルです");
		Iterable<MyData> list = dao.getAll();
		mav.addObject("datalist", list);
		return mav;
	}

	@RequestMapping(value = "/findByAge", method = RequestMethod.POST)
	public ModelAndView findRangeAge(ModelAndView mav, @RequestParam("min") int min, @RequestParam("max") int max) {
		mav.setViewName("findByAge");
		mav.addObject("title", "FindByAge Page");
		mav.addObject("msg", "検索結果");
//		Iterable<MyData> list = dao.findByAge(min, max);
		Iterable<MyData> list = repository.findByAge(min, max);
		mav.addObject("datalist", list);
		return mav;
	}

	/*
	 * @Autowiredにより、変数myDataBeanへ、MyDataBeanクラスのインスタンスを自動設定
	 * これは、構成クラスである、MyBootAppConfigにて、MyDataBeanクラスをBeanとして登録し、
	 * MyDataBeanクラスのインスタンスを返すように記述しているため、この箇所で、@Autowiredが機能する
	 */
	@Autowired
	MyDataBean myDataBean;

	@RequestMapping(value = "/beanSearchById/{id}", method = RequestMethod.GET)
	public ModelAndView beanSearchById(ModelAndView mav, @PathVariable long id) {
		mav.setViewName("pickup");
		mav.addObject("title", "Pickup Page");
		String table = "<table>"
				+ myDataBean.getTableTagById(id)
				+ "</table>";
		mav.addObject("msg", "pickup data id = " + id);
		mav.addObject("data", table);
		return mav;
	}

	/*
	 * ページネーションを利用したメソッド
	 * アクセスする際に、「/page?size=5&page=0」というふうにアドレスを指定することで、ページネーションを実現できる
	 * sizeというパラメータによって、1ページに何件データを表示するのかを指定しており、
	 * pageというパラメータは、sizeパラメータによって区切ったページのうち、何ページ目なのか指定している
	 * 上記の例であれば、「1ページに5件のデータを表示するようにして、それの0ページ目、つまり一番最初のページ」を示している
	 */
	@RequestMapping(value = "/page", method = RequestMethod.GET)
	public ModelAndView pagination(ModelAndView mav, Pageable pageable) {
		mav.setViewName("enMana");
		mav.addObject("title", "Find Page");
		mav.addObject("msg", "MyDataのサンプルです。");
		Page<MyData> list = repository.findAll(pageable);
		mav.addObject("datalist", list);
		return mav;
	}

	@Autowired
	MyDataMongoRepository mongoRepository;

	@RequestMapping(value = "/mongo", method = RequestMethod.GET)
	public ModelAndView mongoIndex(ModelAndView mav) {
		mav.setViewName("mongo");
		mav.addObject("title", "Find Page");
		mav.addObject("msg", "MyDataMongoのサンプルです。");
		Iterable<MyDataMongo> list = mongoRepository.findAll();
		mav.addObject("datalist", list);
		return mav;
	}

	@RequestMapping(value = "/mongo", method = RequestMethod.POST)
	@Transactional(readOnly = false)
	public ModelAndView mongoForm(@RequestParam("name") String name, @RequestParam("memo") String memo,
			ModelAndView mav) {
		MyDataMongo mydata = new MyDataMongo(name, memo);
		mongoRepository.save(mydata);
		return new ModelAndView("redirect:/mongo");
	}

	@RequestMapping(value = "/mongoFind", method = RequestMethod.GET)
	public ModelAndView mongoFind(ModelAndView mav) {
		mav.setViewName("mongoFind");
		mav.addObject("title", "Find Page");
		mav.addObject("msg", "MyDataMongoのサンプルです。");
		mav.addObject("value", "");
		List<MyDataMongo> list = mongoRepository.findAll();
		mav.addObject("datalist", list);
		return mav;
	}

	@RequestMapping(value = "/mongoFind", method = RequestMethod.POST)
	public ModelAndView mongoSearch(@RequestParam("find") String param, ModelAndView mav) {
		mav.setViewName("mongoFind");
		if (param == "") {
			mav = new ModelAndView("redirect:/mongoFind");
		} else {
			mav.addObject("title", "Find result");
			mav.addObject("msg", "「" + param + "」の検索結果");
			mav.addObject("value", param);
			List<MyDataMongo> list = mongoRepository.findByName(param);
			mav.addObject("datalist", list);
		}
		return mav;
	}
}