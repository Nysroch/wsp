package wsp.projekt.wsp.logic;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.impl.JPAQuery;

import wsp.projekt.wsp.model.QComment;
import wsp.projekt.wsp.model.QReport;
import wsp.projekt.wsp.model.QSubject;
import wsp.projekt.wsp.model.QUniversity;
import wsp.projekt.wsp.model.QUpvote;
import wsp.projekt.wsp.model.Comment;
import wsp.projekt.wsp.repository.CommentRepository;

@Service
public class CommentLogicImpl extends BaseLogic<Comment> implements CommentLogic {

	private static QComment comment = QComment.comment;
	
	private static QSubject subject = QSubject.subject;
	
	private static QUniversity university = QUniversity.university;
	
	private static QUpvote upvote = QUpvote.upvote;
	
	private static QReport report = QReport.report;
	
	@Autowired
	private CommentRepository commentRepository;
	
	@Autowired
	ReportLogic reportLogic;
	
	@Autowired
	UpvoteLogic upvoteLogic;
	
	
	@Override
	public Comment get(Integer commentID, Integer registeredStudentID) {
		
		return buildQuery(null, null, null, commentID, registeredStudentID).fetchOne();
	}

	@Override
	public List<Comment> getList(Integer registeredStudentID) {
						
		return buildQuery(null, null, null, null, registeredStudentID).fetch();
	}
	
	@Override 
	public List<Comment> getListByUC(Integer universityID, Integer subjectID, Integer studentID, Integer registeredStudentID){
						
		return buildQuery(universityID, subjectID, studentID, null, registeredStudentID).fetch();
	}
	
	@Override 
	public Comment getSingleByUC(Integer universityID, Integer subjectID, Integer studentID, Integer registeredStudentID){
		
		return buildQuery(universityID, subjectID, studentID, null, registeredStudentID).fetchOne();
	}
	
	@Override
	public Comment getTopComment(Integer universityID, Integer registeredStudentID) {
		
	    Date date = new Date(); 
	    date.setTime(0);
		JPAQuery<Comment> query = buildQuery(null, null, null, null, registeredStudentID);
		
		if(universityID != null) {
			query.where(subject.universityID.eq(universityID).or(comment.universityID.eq(universityID)));
		}
		query.where(comment.dateEdited.after(date)).orderBy(upvote.upvoteID.count().desc()).limit(1);
		
		return query.fetchOne();
	}
	
	
	@Override
	public Comment insert(Comment comment) {
		
		Comment temp = comment;
		temp.setDateCreated(new Date());
		temp.setDateEdited(new Date());
		
		if(comment.getCommentID() != null) {
			
			temp = commentRepository.findById(comment.getCommentID()).get();
			
			temp.setDescription(comment.getDescription());
			temp.setRating(comment.getRating());
			temp.setDateEdited(new Date());
			
			upvoteLogic.delete(comment.getCommentID(), null);
		}
		
		return commentRepository.save(temp);
	}
	
	@Override
	public void delete(Integer commentID) {
		
		reportLogic.delete(commentID);
		
		upvoteLogic.delete(commentID, null);
		
		commentRepository.deleteById(commentID);
	}
	
	private JPAQuery<Comment> buildQuery(Integer universityID, Integer subjectID, Integer studentID, Integer commentID, Integer registeredStudentID){
		
		JPAQuery<Comment> query = query();
		
		BooleanBuilder filter = new BooleanBuilder();
		
		if(universityID != null) {
			filter.and(comment.universityID.eq(universityID));
		}
		if(subjectID != null) {
			filter.and(comment.subjectID.eq(subjectID));
		}
		if(studentID != null) {
			filter.and(comment.studentID.eq(studentID));
		}
		if(commentID != null) {
			filter.and(comment.commentID.eq(commentID));
		}
		
		query.select(selectConstructor(registeredStudentID != null ? registeredStudentID : 0));
		query.from(comment);
		query.leftJoin(upvote).on(upvote.commentID.eq(comment.commentID));
		query.leftJoin(subject).on(subject.subjectID.eq(comment.subjectID));
		query.leftJoin(university).on(university.universityID.eq(comment.universityID).or(university.universityID.eq(subject.universityID)));
		query.where(filter).orderBy(comment.dateEdited.desc());
		query.groupBy(comment.commentID);
		
		
		return query;
		
	}

	
	private ConstructorExpression<Comment> selectConstructor(Integer registeredStudentID) {
		return Projections.constructor(Comment.class, comment.commentID, comment.studentID, comment.universityID, comment.subjectID,
				comment.rating, comment.description, comment.dateCreated, comment.dateEdited, upvote.upvoteID.count(),
				new CaseBuilder().when(query().from(upvote).where(comment.commentID.eq(upvote.commentID).and(upvote.studentID.eq(registeredStudentID))).exists()).then(true).otherwise(false),
				new CaseBuilder().when(query().from(report).where(comment.commentID.eq(report.commentID).and(report.studentID.eq(registeredStudentID))).exists()).then(true).otherwise(false),
				university.name, subject.name.coalesce(""));
	}


}
