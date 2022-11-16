package wsp.projekt.wsp.repository;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import wsp.projekt.wsp.model.Subject;

@Repository
public interface SubjectRepository extends CrudRepository<Subject, Integer>, QuerydslPredicateExecutor<Subject>{ 

}
