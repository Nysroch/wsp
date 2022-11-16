package wsp.projekt.wsp.logic;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.impl.JPAQuery;

import wsp.projekt.wsp.model.QUpvote;
import wsp.projekt.wsp.model.Comment;
import wsp.projekt.wsp.model.Upvote;
import wsp.projekt.wsp.repository.UpvoteRepository;

@Service
public class UpvoteLogicImpl extends BaseLogic<Upvote> implements UpvoteLogic {

	private static QUpvote upvote = QUpvote.upvote;
	
	@Autowired
	private UpvoteRepository upvoteRepository;
	
	
	@SuppressWarnings("deprecation")
	@Override
	public Long getByCommentID(int commentID) {
		return query().from(upvote).where(upvote.commentID.eq(commentID)).fetchCount();
	}
	
	@Override
	public Upvote insert(Upvote upvote) {
		return upvoteRepository.save(upvote);
	}
	
	@Override
	public void delete(Integer commentID, Integer studentID) {
		JPAQuery<Upvote> query = query();
		
		query.select(selectConstructor());
		query.from(upvote);
		query.where(upvote.commentID.eq(commentID));
		
		if(studentID != null) {
			query.where(upvote.studentID.eq(studentID));
		}
		
		List<Upvote> upvoteList = query.fetch();
				
		if(!upvoteList.isEmpty()) {
			upvoteRepository.deleteAll(upvoteList);
		}
	}

	private ConstructorExpression<Upvote> selectConstructor() {
		return Projections.constructor(Upvote.class, upvote.upvoteID, upvote.commentID, upvote.studentID);
	}
	
	
	

}
