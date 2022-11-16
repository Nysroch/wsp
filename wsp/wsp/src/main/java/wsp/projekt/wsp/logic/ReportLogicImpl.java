package wsp.projekt.wsp.logic;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;

import wsp.projekt.wsp.model.QReport;
import wsp.projekt.wsp.model.Report;
import wsp.projekt.wsp.model.Upvote;
import wsp.projekt.wsp.repository.ReportRepository;

@Service
public class ReportLogicImpl extends BaseLogic<Report> implements ReportLogic{
	
	private static QReport report = QReport.report;
	
	@Autowired
	private ReportRepository reportRepository;

	@Override
	public Report insert(Report report) {
		return reportRepository.save(report);
	}
	
	@Override
	public void delete(Integer commentID) {
		JPAQuery<Report> query = query();
		
		query.select(selectConstructor());
		query.from(report);
		query.where(report.commentID.eq(commentID));
		
		
		List<Report> reportList = query.fetch();
				
		if(!reportList.isEmpty()) {
			reportRepository.deleteAll(reportList);
		}
	}

	private ConstructorExpression<Report> selectConstructor() {
		return Projections.constructor(Report.class, report.reportID, report.commentID, report.studentID, report.reason);
	}
}
