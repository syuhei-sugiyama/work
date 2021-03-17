package com.example.demo;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class MyDataDaoImpl implements MyDataDao<MyData> {

	private static final long serialVersionUID = 1L;

	private EntityManager entityManager;

	public MyDataDaoImpl() {
		super();
	}

	public MyDataDaoImpl(EntityManager manager) {
		this();
		entityManager = manager;
	}

	@Override
	public List<MyData> getAll() {
		/*
		 * Queryクラス
		 * SQLでデータを問い合わせるためのクエリの為のオブジェクト
		 * createQuery("from MyData")により、「select * from mydata」というクエリが作成される
		 */
		Query query = entityManager.createQuery("from MyData");
		@SuppressWarnings("unchecked")
		/*
		 * getResultListメソッド
		 * Queryオブジェクトの、クエリ実行結果をListインスタンスとして取得できる
		 * リストの要素1つひとつに、MyDataインスタンス、つまり、エンティティが格納されている。
		 */
		List<MyData> list = query.getResultList();
		entityManager.close();
		return list;
	}

	@Override
	public MyData findById(long id) {
		return (MyData) entityManager.createQuery("from MyData where id = " + id).getSingleResult();
		/*
		 * getSingleResultメソッドの利用
		 * クエリの取得結果から、1つだけエンティティを取り出すメソッド
		 * IDのような、一意な値の検索時に利用できる(必ず結果が1つだと事前に把握できるものだから)
		 */
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MyData> findByName(String name) {
		return (List<MyData>) entityManager.createQuery("from MyData where name = " + name).getResultList();
		/*
		 * getResultListメソッドの利用
		 * 今回の検索対象となるnameのように、同じカラムに、同じ値を持つエンティティが複数存在する可能性があるときに使用
		 */
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MyData> find(String fstr) {
		List<MyData> list = null;
		String qstr = "from MyData where id = :fstr";
		/*
		 * 変数qstrのクエリ文について
		 * 「:fstr」という部分がポイント
		 * JPQLのクエリ文にて、「:○○」という形式で記述されたものは、パラメータ用の変数として扱われる
		 * つまり、この後で、fstrという変数に値を設定する作業が発生する
		 */
		Query query = entityManager.createQuery(qstr).setParameter("fstr", Long.parseLong(fstr));
		/*
		 * setParameterメソッドについて
		 * 用意したクエリ文に埋め込まれた、パラメータ用の変数に対して、値を設定する
		 * 第一引数に、パラメータ用の変数名を、第二引数に、設定する値を指定している。
		 * ここのポイントは、第二引数をLong型に変換していること
		 * これは、今回検索するidというカラムの値が、long型として、モデルクラスのMyDataクラスにて
		 * 定義されているので、それに合わせる必要がある
		 */
		list = query.getResultList();
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MyData> find2(String fstr) {
		List<MyData> list = null;
		/*
		 * JPQLのクエリに、「:fid」「:fname」「:fmail」という3つの名前付きパラメータを用意
		 */
		String qstr = "from MyData where id = :fid or name like :fname or mail like :fmail";
		Long fid = 0L;
		try {
			fid = Long.parseLong(fstr);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		/*
		 * setParameterメソッドを3つ連続しての記述
		 * メソッドチェーンを利用した記述(=メソッドの呼び出しを連続して記述する手法)
		 * setParameterメソッドは、返り値として、パラメータを設定済みのQueryインスタンスを返すため
		 * 下記のように、連続して記述することが出来る
		 */
		Query query = entityManager.createQuery(qstr).setParameter("fid", fid)
				.setParameter("fname", "%" + fstr + "%")
				.setParameter("fmail", fstr + "@%");
		list = query.getResultList();
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MyData> findByBindVariable(String fstr) {
		List<MyData> list = null;
		/*
		 * バインド変数によるクエリの生成
		 * 下記のように、「?」の後に数字を指定する形でパラメータの埋め込みが可能
		 */
		String qstr = "from MyData where id = ?1 or name like ?2 or mail like ?3";
		Long fid = 0L;
		try {
			fid = Long.parseLong(fstr);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		/*
		 * バインド変数を使用してクエリを準備した場合、
		 * 下記のように、setParameterメソッドにて、第一引数にはクエリに埋め込んだ「?」の後に指定した数字を設定し、
		 * 第二引数には、あてがう値を設定する
		 */
		Query query = entityManager.createQuery(qstr).setParameter(1, fid)
				.setParameter(2, "%" + fstr + "%")
				.setParameter(3, fstr + "@%");
		list = query.getResultList();
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MyData> findByNamedQuery(String fstr) {
		List<MyData> list = null;
		/*
		 * 名前付きクエリの使い方について
		 * 名前付きクエリを使う場合は、createNamedQueryメソッドを使用して、
		 * 引数に、エンティティクラスに用意した、@NamedQueryアノテーションのnameの値を指定することで、
		 * その@NamedQueryアノテーションに設定したクエリを保持したQueryクラスのインスタンスを獲得できる
		 */
		Query query = entityManager
				.createNamedQuery("findWithName")
				.setParameter("fname", "%" + fstr + "%");
		list = query.getResultList();
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MyData> findByAge(int min, int max) {
		/*
		 * @NamedQueryアノテーションでクエリを用意した場合
		 * 「findByAge」という名前で、エンティティクラスに、クエリを用意
		 * クエリの中に、「:min」「:max」という名前で、後から値をセットするための、埋め込みパラメータを用意
		 * 本メソッド呼び出し時の引数を、クエリの埋め込みパラメータにあてがうように、setParameterメソッドで設定
		 */
		return (List<MyData>)entityManager
				.createNamedQuery("findByAge")
				.setParameter("min", min)
				.setParameter("max", max)
				.getResultList();
	}

	@Override
	public List<MyData> getAllByCriteriaAPI() {
		/*
		 * Criteria APIによる検索
		 * JPAに用意されている「Criteria API」という機能を使う
		 * 3つのクラスを組み合わせて利用
		 * CriteriaBuilderクラス・・・Criteria APIによるクエリ生成を管理するクラス
		 * CriteriaQueryクラス・・・Criteria APIによるクエリ実行のためのクラス
		 * Rootクラス・・・検索されるエンティティのルート(大元)となるクラス。ここから必要なエンティティを絞りこんだりするのに用いる
		 */
		/*
		 * Criteria APIを利用する流れ
		 */
		List<MyData> list = null;
		/*
		 * 1,CriteriaBuilderのインスタンス取得
		 *  EntityManagerのgetCriteriaBuilderメソッドを呼び出すことで取得できる
		 */
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		/*
		 * 2,CriteriaQueryの作成
		 *  これは、Criteria API専用のQueryクラス
		 *  Queryクラスと違って、クエリ文を使わないので、引数にクエリ文の指定が不要
		 *  そのかわり、指定のエンティティにアクセスするために、そのエンティティのclassプロパティを引数に指定する
		 */
		CriteriaQuery<MyData> query = builder.createQuery(MyData.class);
		/*
		 * 3,Rootの取得
		 *  Rootクラスのインスタンスを、CriteriaQueryのfromメソッドを用いて取得する
		 *  引数には、検索するエンティティのclassプロパティを指定する
		 *  fromメソッドにより、MyDataクラスから取得される、全データを情報として保持したRootクラスのインスタンスが得られる
		 */
		Root<MyData> root = query.from(MyData.class);
		/*
		 * 4,CriteriaQueryのメソッドを実行
		 *  CriteriaQueryのインスタンスにて、エンティティを絞り込むためのメソッドを呼び出す
		 *  ここでは、selectメソッドを呼び出しており、すべてのMyDataクラスのデータを取得するように
		 *  CriteriaQueryクラスのインスタンスに設定できる
		 *
		 */
		query.select(root);
		/*
		 * 5,createQueryを用いて結果を取得
		 *  最後に、createQueryメソッドで、Queryを生成し、getResultListメソッドで結果を取得する
		 *  通常のQueryと違うのは、createQueryメソッドの引数が、CriteriaQueryクラスのインスタンスであるという点のみ
		 */
		list = (List<MyData>) entityManager.createQuery(query).getResultList();
		return list;
	}

	@Override
	public List<MyData> findByCriteriaAPI(String fstr) {
		List<MyData> list = null;
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<MyData> query = builder.createQuery(MyData.class);
		Root<MyData> root = query.from(MyData.class);
		/*
		 * Criteria APIによる名前検索
		 * Rootの取得までは、getAllByCriteriaAPIメソッドと同じ処理
		 * CriteriaQueryにて実行するメソッドが多少違ってくる
		 * selectメソッドを使用するところまでは同じだが、その後に、メソッドチェーンを利用して、
		 * 追加でメソッドを呼び出している。
		 * whereメソッド
		 * 引数の条件で絞り込みを行うメソッド
		 * builder.equalメソッド
		 * 第一引数と第二引数が等しいかどうかを判定するメソッド
		 * root.getメソッド
		 * エンティティから指定のプロパティの値に関するPathインスタンスを返す
		 * 下記の処理を整理すると、
		 * MyDataクラスのエンティティから、root.getメソッドにて、nameプロパティ(nameカラム)の値と、
		 * 検索値にあたる変数fstrの値を、builder.equalメソッドによって、等しいかどうか判定して、
		 * whereメソッドによって、判定結果がtrue(=等しい値を持つ)エンティティのみを絞り込んで取得するように
		 * CriteriaQueryクラスのインスタンスに設定している
		 * 最後に、1つ下の行にて、
		 * 絞り込んで検索するように設定されたCriteriaQueryインスタンスをcreateQueryメソッドの引数にあてがい
		 * getResultListメソッドによって、設定した検索の実行結果を取得している
		 */
//		query.select(root).where(builder.equal(root.get("name"), fstr));
		query.select(root).where(builder.notEqual(root.get("name"), fstr));
		list = (List<MyData>) entityManager.createQuery(query).getResultList();
		return list;
	}

	@Override
	public List<MyData> getAllSortByCriteriaAPI() {
		List<MyData> list = null;
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<MyData> query = builder.createQuery(MyData.class);
		Root<MyData> root = query.from(MyData.class);
		/*
		 * Criteria APIによるソート
		 * Rootの取得までは、getAllByCriteriaAPIメソッドと同じ処理
		 * selectメソッドの後に、メソッドチェーンを利用して、orderByメソッドを使用
		 * 引数にて、builder.ascメソッドを使用
		 * 引数に指定された要素を昇順に並び替える
		 * root.get("name")によって、エンティティのnameプロパティを指定しており、
		 * nameを昇順で並び替える
		 */
		query.select(root).orderBy(builder.asc(root.get("name")));
		list = (List<MyData>) entityManager.createQuery(query).getResultList();
		return list;
	}

	@Override
	public List<MyData> getAllPaging() {
		List<MyData> list = null;
		int offset = 1;
		int limit = 2;
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<MyData> query = builder.createQuery(MyData.class);
		Root<MyData> root = query.from(MyData.class);
		query.select(root);
		/*
		 * Criteria APIによるページング
		 * 「5番目から10個のデータだけ取り出す」といったような、ページングをおこなう際のデータ取得
		 * query.select(root);までは変わらず。
		 * 最後の、createQueryメソッドで、Queryクラスのインスタンスを取得した後の処理が変わってくる
		 * setFirstResultメソッドで、取得開始位置を指定する
		 * 一番最初のエンティティから取得する場合は「0」を指定
		 * setMaxResultsメソッドで、取得する個数を指定する
		 */
		list = (List<MyData>) entityManager
				.createQuery(query)
				.setFirstResult(offset)
				.setMaxResults(limit)
				.getResultList();
		return list;
	}
}
