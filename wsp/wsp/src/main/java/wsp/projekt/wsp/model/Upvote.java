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
public class Upvote {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(insertable=false, updatable=false, name = "upvoteID")
	private Integer upvoteID;
	
	@Column(name = "commentID")
	private Integer commentID;

	@Column(name = "studentID")
	private Integer studentID;

	public Upvote(Integer upvoteID, Integer commentID, Integer studentID) {
		this.upvoteID = upvoteID;
		this.commentID = commentID;
		this.studentID = studentID;
	}
	
	
	

}
