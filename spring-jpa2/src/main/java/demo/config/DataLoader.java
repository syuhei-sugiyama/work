package demo.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import demo.model.Comment;
import demo.repository.CommentRepository;
import lombok.RequiredArgsConstructor;

/*
 * @RequiredArgsConstructor
 * コンストラクタを自動生成するアノテーション
 * 対象はfinalが付与されたフィールド
 * @Component
 * 付与されたクラスがコンポーネントクラスであることを示すアノテーション
 */
@RequiredArgsConstructor
@Component
public class DataLoader implements CommandLineRunner {

	private final CommentRepository repository;

	@Override
	public void run(String... args) throws Exception {
		Comment comment = new Comment();
		comment.setContent("こんにちは");
		repository.save(comment);
		comment = new Comment();
		comment.setContent("テストコメント");
		repository.save(comment);
	}

}
