package demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import demo.model.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
