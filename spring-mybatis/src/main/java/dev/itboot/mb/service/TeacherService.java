package dev.itboot.mb.service;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.itboot.mb.mapper.TeacherMapper;
import dev.itboot.mb.model.Teacher;
import lombok.RequiredArgsConstructor;

/*
 * @Transactional
 * 付与したクラス、メソッドをトランザクション処理にする
 * @Service
 * 付与したクラスがサービス層(ビジネスロジック)であることを示す
 */
@RequiredArgsConstructor
@Transactional
@Service
public class TeacherService {

	private final TeacherMapper mapper;

	public Page<Teacher> selectAll(Pageable pageable) {
		/*
		 * RowBounds
		 * MyBatis側のページ情報
		 * 引数として「offset(ページ位置)」と「limit(1ページの表示件数)」を指定する
		 * これらの値は、Spring Dataのpageableから下記のように設定できる
		 * そして、「mapper.selectAll(rowBounds)」のようにして、MyBatisのページング情報を渡す
		 */
		RowBounds rowBounds = new RowBounds(
				(int) pageable.getOffset(),
				pageable.getPageSize());
		List<Teacher> teachers = mapper.selectAll(rowBounds);
		Long total = mapper.count();
		/*
		 * PageImpl
		 * Pageインタフェースの実装クラス
		 * 引数として、「content(内容)」「pageable(ページング情報)」「total(合計)」を指定する
		 */
		return new PageImpl<>(teachers, pageable, total);
	}

	public Teacher selectByPrimaryKey(Long id) {
		return mapper.selectByPrimaryKey(id);
	}

	public void save(Teacher teacher) {
		if (teacher.getId() == null) {
			// IDが存在しない場合は、新規にinsertする
			mapper.insert(teacher);
		} else {
			// 既にIDが存在する場合は、updateをかける
			mapper.updateByPrimaryKey(teacher);
		}
	}

	public void deleteByPrimaryKey(Long id) {
		mapper.deleteByPrimaryKey(id);
	}
}
