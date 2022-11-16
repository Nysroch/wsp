package wsp.projekt.wsp.repository;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import wsp.projekt.wsp.model.Report;

@Repository
public interface ReportRepository extends CrudRepository<Report, Integer>, QuerydslPredicateExecutor<Report>{

}
