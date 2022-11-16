package wsp.projekt.wsp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.decimal4j.util.DoubleRounder;

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
public class Subject {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(insertable=false, updatable=false, name = "subjectID")
	private Integer subjectID;
	
	@Column(name = "universityID")
	private Integer universityID;

	@Column(name = "name")
	private String name;

	@Column(name = "description")
	private String description;

	@Column(name = "semester")
	private Integer semester;

	@Column(name = "major")
	private String major;
	
	@Transient
	private Double rating;
	
	@Transient
	private Long commentNumber;
	
	public Subject(Integer subjectID, Integer universityID, String name, String description, Integer semester, String major, Double rating, Long commentNumber) {
		this.subjectID = subjectID;
		this.universityID = universityID;
		this.name = name;
		this.description = description;
		this.semester = semester;
		this.major = major;
		this.rating = DoubleRounder.round(rating, 1);
		this.commentNumber = commentNumber;
	}
	
	
	
	
	
	
}
