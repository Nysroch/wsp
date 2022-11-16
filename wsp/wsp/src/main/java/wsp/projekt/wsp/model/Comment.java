package wsp.projekt.wsp.model;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.Transient;

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
public class Comment {

	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(insertable=false, updatable=false, name = "commentID")
	private Integer commentID;
	
	@Column(name = "studentID")
	private Integer studentID;
	
	@Column(name = "universityID")
	private Integer universityID;
	
	@Column(name = "subjectID")
	private Integer subjectID;
	
	@Column(name = "rating")
	private Integer rating;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "datecreated")
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	private Date dateCreated;
	
	@Column(name = "dateedited")
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	private Date dateEdited;
	
	@Transient
	private Long upvote;
	
	@Transient
	private Boolean upvoted;
	
	@Transient
	private Boolean reported;
	
	@Transient
	private String universityName;
	
	@Transient
	private String subjectName;

	public Comment(Integer commentID, Integer studentID, Integer universityID, Integer subjectID, Integer rating,
			String description, Date dateCreated, Date dateEdited, Long upvote, Boolean upvoted, Boolean reported,
			String universityName, String subjectName) {
		super();
		this.commentID = commentID;
		this.studentID = studentID;
		this.universityID = universityID;
		this.subjectID = subjectID;
		this.rating = rating;
		
		this.description = description;
		this.dateCreated = dateCreated;
		this.dateEdited = dateEdited;
		this.upvote = upvote;
		this.upvoted = upvoted;
		
		this.reported = reported;
		this.universityName = universityName;
		this.subjectName = subjectName;
	}
	


	
	




	
	
}
