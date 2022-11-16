package wsp.projekt.wsp.logic;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;

import wsp.projekt.wsp.model.QComment;
import wsp.projekt.wsp.model.QSubject;
import wsp.projekt.wsp.model.Comment;
import wsp.projekt.wsp.model.Subject;
import wsp.projekt.wsp.model.University;
import wsp.projekt.wsp.repository.SubjectRepository;

@Service
public class SubjectLogicImpl extends BaseLogic<Subject> implements SubjectLogic {

	private static QSubject subject = QSubject.subject;
	private static QComment comment = QComment.comment;
	
	@Override
	public Subject get(Integer subjectID) {
		return buildQuery(subjectID, null).fetchOne();
	}

	@Override
	public List<Subject> getList() {
		return buildQuery(null, null).fetch();
	}
	
	@Override
	public List<Subject> getTopSubjects(Integer universityID){
		return buildQuery(null, universityID).orderBy(comment.rating.avg().desc()).limit(3).fetch();
	}
	
	@Override
	public List<Subject> getListByUC(Integer universityID) {
		return buildQuery(null, universityID).fetch();	
	}
	
	
	private JPAQuery<Subject> buildQuery(Integer subjectID, Integer universityID){
		
		BooleanBuilder filter = new BooleanBuilder();
		
		if(subjectID != null) {
			filter.and(subject.subjectID.eq(subjectID));
		}
		if(universityID != null) {
			filter.and(subject.universityID.eq(universityID));
		}

		
		JPAQuery<Subject> query = query();
		
		query.select(selectConstructor());
		query.from(subject);
		query.leftJoin(comment).on(comment.subjectID.eq(subject.subjectID));
		query.where(filter);
		query.groupBy(subject.subjectID);
		
		return query;
	}
	
	private ConstructorExpression<Subject> selectConstructor() {
		return Projections.constructor(Subject.class, subject.subjectID, subject.universityID, subject.name, subject.description, 
				subject.semester, subject.major, comment.rating.avg().coalesce((double) 0), comment.commentID.count().coalesce((long) 0));
	}


 

}
