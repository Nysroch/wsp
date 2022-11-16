package wsp.projekt.wsp.repository;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import wsp.projekt.wsp.model.Comment;

@Repository
public interface CommentRepository extends CrudRepository<Comment, Integer>, QuerydslPredicateExecutor<Comment>{

}
