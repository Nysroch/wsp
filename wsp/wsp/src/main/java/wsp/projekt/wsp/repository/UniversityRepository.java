package wsp.projekt.wsp.repository;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import wsp.projekt.wsp.model.University;

@Repository
public interface UniversityRepository extends CrudRepository<University, Integer>, QuerydslPredicateExecutor<University>{

}
