package wsp.projekt.wsp.repository;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import wsp.projekt.wsp.model.Upvote;

@Repository
public interface UpvoteRepository extends CrudRepository<Upvote, Integer>, QuerydslPredicateExecutor<Upvote>{

}
