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
public class University {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(insertable=false, updatable=false, name = "universityID")
	private Integer universityID;

	@Column(name = "name")
	private String name;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "iconurl")
	private String iconUrl;
	
	@Column(name = "emaildomain")
	private String emailDomain;
	
	@Transient
	private Double rating;
	
	@Transient
	private Double subjectRating;
	
	

	
	public University(Integer universityID, String name, String description, String emailDomain, String iconUrl, Double rating, Double subjectRating) {
		this.universityID = universityID;
		this.name = name;
		this.description = description;
		this.emailDomain = emailDomain;
		this.iconUrl = iconUrl;
		this.rating = DoubleRounder.round(rating, 1);
		this.subjectRating = subjectRating != null ? DoubleRounder.round(subjectRating, 1) : null;
	}
	

	
	

}
