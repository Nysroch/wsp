package wsp.projekt.wsp.logic;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;

import wsp.projekt.wsp.model.QComment;
import wsp.projekt.wsp.model.QSubject;
import wsp.projekt.wsp.model.QUniversity;
import wsp.projekt.wsp.model.University;

@Service
public class UniversityLogicImpl extends BaseLogic<University> implements UniversityLogic {
	private final static Logger LOGGER = Logger.getLogger(UniversityLogicImpl.class.getName());
	
	private static QUniversity university = QUniversity.university;
	private static QComment comment = QComment.comment;
	private static QSubject subject = QSubject.subject;
	
	@Override
	public University get(int universityID) {
				
		return buildQuery(universityID, null, null).fetchOne();
	}

	@Override
	public List<University> getList() {

		
		return buildQuery(null, null, null).fetch();
	}

	@Override
	public Integer getUniversityIdByDomain(String domain) {
		try {
			 return query().from(university).where(university.emailDomain.eq(domain)).fetchOne().getUniversityID();
		}catch(Exception e) {
			LOGGER.log(Level.INFO,"University not in database, domain: ",domain);
			return 999;
		}
	}
	
	
	private JPAQuery<University> buildQuery(Integer universityID, String domain, String name){
		
		BooleanBuilder filter = new BooleanBuilder();
		
		if(universityID != null) {
			filter.and(university.universityID.eq(universityID));
		}
		if(domain != null) {
			filter.and(university.emailDomain.eq(domain));
		}
		
		if(name != null) {
			filter.and(university.name.contains(name));
		}
		
		JPAQuery<University> query = query();
		
		query.select(selectConstructor());
		query.from(university);
		query.leftJoin(subject).on(university.universityID.eq(subject.universityID));
		query.leftJoin(comment).on(comment.universityID.eq(university.universityID).or(comment.subjectID.eq(subject.subjectID)));
		query.where(filter);
		query.groupBy(university.universityID);
		query.orderBy(university.name.asc());
		
		return query;
	}
	
	private ConstructorExpression<University> selectConstructor() {
		return Projections.constructor(University.class, university.universityID, university.name, university.description, university.emailDomain, university.iconUrl,
				new CaseBuilder().when(comment.universityID.isNotNull()).then(comment.rating).otherwise(Expressions.nullExpression()).avg(),
				new CaseBuilder().when(comment.subjectID.isNotNull()).then(comment.rating).otherwise(Expressions.nullExpression()).avg());
	}

	
}
