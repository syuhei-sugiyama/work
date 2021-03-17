package com.example.demo.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.MyData;

/*
 * @Repository
 * このクラスがデータアクセスのクラスであることを示すアノテーション
 * リポジトリのようにデータベースにアクセスするためのクラスには、この@Repositoryアノテーションを付ける
 */
@Repository
public interface MyDataRepository extends JpaRepository<MyData, Long> {
	/*
	 * JpaRepository
	 * 新たにリポジトリを作成する際の土台となるもの
	 * すべてのリポジトリは、このJpaRepositoryを継承して作成される
	 */

	/*
	 * findByIdメソッド
	 * IDをキーにしてエンティティを検索して取り出す
	 * 戻り値のOptionalについて
	 * nullかもしれないオブジェクトをラップするためのクラス
	 * 指定したIDが存在しない場合、nullが返される
	 * Optionalを使うことで、結果は必ずOptionalインスタンスを得られる
	 * なので、nullにはならない。かつ、nullチェックをしなくて済む
	 * Optionalインスタンスに対して、getメソッドを使用することで、検索結果が格納されている、
	 * ラップしたインスタンス(今回は、エンティティクラスであるMyDataクラスのインスタンス)を取り出せる。
	 */
	public Optional<MyData> findById(long id);

	/*
	 * リポジトリのメソッド自動生成について
	 * JpaRepositoryでは、メソッド名を元に、そのメソッドの処理を自動生成する機能を持っている
	 * アプリケーション実行時に、定義されたリポジトリのインターフェースを元に、内部的に実装クラスが生成されている
	 * なぜこのようなことが可能なのか
	 * JpaRepositoryに、辞書によるコードの自動生成機能が組み込まれている為
	 * 例えば、上述したfindByIdメソッドについても、idという値によってエンティティを検索するという処理をしてくれる
	 * クエリを自動で生成している。
	 */

	/*
	 * findByNameLikeメソッド
	 * nameというカラムの値に対して、あいまい検索をする
	 */
	public List<MyData> findByNameLike(String name);
	/*
	 * findByIdIsNotNullにより、idというカラムの値がnullでないものを指定
	 * OrderByIdDescで、idというカラムの値を降順で並び替える
	 * この上記2つの意味が込められているメソッド
	 */
	public List<MyData> findByIdIsNotNullOrderByIdDesc();
	/*
	 * findByAgeGreaterThanメソッド
	 * GreaterThanにより、ageというカラムの値が、引数ageの値より大きいものを探し出す
	 */
	public List<MyData> findByAgeGreaterThan(Integer age);
	/*
	 * findByAgeBetweenメソッド
	 * ageというカラムの値が、引数age1とage2の間にある値を探し出す
	 */
	public List<MyData> findByAgeBetween(Integer age1, Integer age2);
	/*
	 * メソッド自動生成のポイント
	 * ・メソッド名はキャメル記法が基本
	 *   「各単語の1文字目だけを大文字にする」というもの
	 *   findByName→○
	 *   findbyName→×。例外が発生し、メソッドが生成されない。
	 * ・メソッド名の単語の並び
	 *   大まかなルール
	 *   「find[By○○][そのほかの検索条件][OrderByなど]」
	 *   findByIdIsNotNullOrderByIdDesc→○
	 *   findOrderByIdDescByIdIsNotNull→×。例外が発生
	 * ・引数の型に注意
	 *   エンティティに用意したプロパティの型と、メソッドの引数の型が一致していないと例外が発生する
	 *   Javaの一般的なコードのような、オートボクシングはなされない
	 *   例えば、MyDataクラスのageというプロパティはInteger型で宣言しているが、
	 *   検索メソッドとして、findByAgeメソッドを作成時、引数にint型を指定してしまうと、例外が発生する
	 */
	@Query("select d from MyData d order by d.name")
	List<MyData> findAllOrderByName();
	/*
	 * @Queryアノテーション
	 * このアノテーションを付与したメソッドを呼び出すと、
	 * @Queryアノテーションの引数に指定したクエリが実行される
	 */
	@Query("from MyData where age > :min and age < :max")
	public List<MyData> findByAge(@Param("min") int min, @Param("max") int max);
	/*
	 * @Queryアノテーションにて、埋め込みパラメータ有りのクエリを用意した時
	 * 上記のように、宣言したメソッドの引数がポイント
	 * @Paramアノテーションを使用し、引数に埋め込みパラメータの名前を指定することで、
	 * 宣言したメソッドの引数の値が、クエリにはめ込まれ、クエリが実行される
	 */

	public Page<MyData> findAll(Pageable pageable);
}
