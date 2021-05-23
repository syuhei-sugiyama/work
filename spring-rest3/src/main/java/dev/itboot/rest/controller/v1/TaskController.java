package dev.itboot.rest.controller.v1;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.itboot.rest.model.Task;
import dev.itboot.rest.repository.TaskRepository;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

/*
 * @RequestMapping
 * 引数に指定したパスで、HTTPリクエストを受け付ける
 * @RequiredArgsConstructor
 * finalなフィールドを対象に、コンストラクタを自動生成する
 * @RestController
 * 付与されたクラスが、REST用のコントローラーであることを示す
 */
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
@RestController
public class TaskController {

	private final TaskRepository repository;

	/*
	 * @Operation
	 * HTTPメソッドの操作説明を記述する。ブラウザで見た時に表示される
	 */
	@Operation(summary = "タスクを全件取得します")
	@GetMapping("/")
	List<Task> findAll() {
		return repository.findAll();
	}

	/*
	 * @RequestBody
	 * HTTPリクエストのボディ部分を付与した引数に格納する
	 * 下記の場合だと、ボディ部分をTask型の変数taskに格納している
	 */
	@Operation(summary = "タスクを登録します")
	@PostMapping("/")
	Task save(@RequestBody Task task) {
		// taskに値が格納してあるので、そのまま保存できる
		return repository.save(task);
	}

	/*
	 * @PathVariable
	 * パスの値({}で囲った部分)を変数に格納する
	 * 下記の場合だと、例えば、「/1」というパスでアクセスした際に、
	 * 付与した引数idに、「1」が格納される
	 */
	@Operation(summary = "タスクを1件取得します")
	@GetMapping("/{id}")
	Task findById(@PathVariable Long id) {
		// idに値が格納してあるので、そのまま検索に利用できる
		return repository.findById(id).get();
	}

	@Operation(summary = "タスクを更新します")
	@PutMapping("/{id}")
	Task save(@RequestBody Task newTask, @PathVariable Long id) {
		return repository.findById(id).map(
				task -> {
					// 該当IDが存在する場合、更新する
					task.setName(newTask.getName());
					task.setCompleted(newTask.getCompleted());
					return repository.save(task);
				}).orElseGet(() -> {
					// 該当IDが存在しない場合、登録する
					newTask.setId(id);
					return repository.save(newTask);
				});
	}

	@Operation(summary = "タスクを削除します")
	@DeleteMapping("/{id}")
	void deleteById(@PathVariable Long id) {
		repository.deleteById(id);
	}
}
