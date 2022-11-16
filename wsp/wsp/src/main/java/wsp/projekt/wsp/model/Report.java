package wsp.projekt.wsp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
@Table
public class Report {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(insertable=false, updatable=false, name = "reportID")
	private Integer reportID;
	
	@Column(name = "commentID")
	private Integer commentID;
	
	@Column(name = "studentID")
	private Integer studentID;

	@Column(name = "reason")
	private String reason;

	public Report(Integer reportID, Integer commentID, Integer studentID, String reason) {
		this.reportID = reportID;
		this.commentID = commentID;
		this.studentID = studentID;
		this.reason = reason;
	}
	
	public Report(Integer commentID, Integer studentID, String reason) {
		this.commentID = commentID;
		this.studentID = studentID;
		this.reason = reason;
	}

}
