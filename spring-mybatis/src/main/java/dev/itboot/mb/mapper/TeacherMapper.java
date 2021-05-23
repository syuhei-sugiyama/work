package dev.itboot.mb.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import dev.itboot.mb.model.Teacher;

/*
 * @Mapper
 * 付与されたクラスがマッパーであることを示す
 * MyBatisは、起動時にこのアノテーションを検索し、使えるようにする
 * ※マッパー・・・RDBとJavaのオブジェクト(モデルクラスのインスタンス)との、橋渡し役(機能)
 */
@Mapper
public interface TeacherMapper {

	// 合計を取得するために追加
	Long count();

	/*
	 * @Select
	 * SQLのSELECT文を、引数に記述することで、そのSELECT文が発行される
	 */
	// @Select("select * from teacher")
	// ページング情報を渡せるように、RowBoundsを追加
	List<Teacher> selectAll(RowBounds rowBounds);

	/*
	 * SQLに変数を渡す場合、#{変数名}と記述する
	 * 下記の場合は、メソッドの引数idを、where句の条件値に格納したいので、
	 * #{id}と記述している
	 */
//	@Select({
//		"select * from teacher",
//		"where id = #{id}"
//	})
	Teacher selectByPrimaryKey(Long id);

	/*
	 * インスタンスの中身を渡す場合は、#{フィールド名}と記述する
	 * 下記の場合、Teacherクラスのインスタンスから、「userName」「email」を指定して
	 * SQLに渡している
	 *
	 */
//	@Insert({
//		"insert into teacher(user_name, email)",
//		"values(#{userName}, #{email})"
//	})
	int insert(Teacher record);

//	@Update({
//		"update teacher",
//		"set user_name = #{userName}, email = #{email}",
//		"where id = #{id}"
//	})
	int updateByPrimaryKey(Teacher record);

//	@Delete({
//		"delete from teacher",
//		"where id = #{id}"
//	})
	int deleteByPrimaryKey(Long id);
}
