package wsp.projekt.wsp.logic;

import java.util.List;

import wsp.projekt.wsp.model.Comment;

public interface CommentLogic {

	/**
	 * Dohvaca jedan komentar sa specifiranim commentID-em
	 * registeredStudentID je opcionalni parametar i koristi se za provjeru da li registrirani student vec ima upvote ili report na komentaru
	 * 
	 * @param commentID ID specifiranog komentara
	 * @param registeredStudentID ID trenutno registriranog studenta
	 * @return Comment objekt specifiranog commentID-a 
	 */
	public Comment get(Integer commentID, Integer registeredStudentID);
	
	/**
	 * Dohvaca listu svih komentara
	 * registeredStudentID je opcionalni parametar i koristi se za provjeru da li registrirani student vec ima upvote ili report na komentaru
	 * 
	 * @param registeredStudentID ID trenutno registriranog studenta
	 * @return lista Comment objekata
	 */
	public List<Comment> getList(Integer registeredStudentID);

	/**
	 * Sprema ili ažurira red u Comment tablici 
	 * Spremanje ili ažuriranje se razlikuje time da li je commentID u objektu upisan u tablici ili ne
	 * 
	 * @param comment Comment objekt koji se sprema ili ažurira u bazu
	 * @return spremljeni Comment objekt
	 */
	Comment insert(Comment comment);

	/**
	 * Dohvaca jedan komentar iz Comment tablice 
	 * universityID je opcionalni parametar i koristi se za filtiranje komentara sa specifiranim universityID-em
	 * subjectID je opcionalni parametar i koristi se za filtiranje komentara sa specifiranim subjectID-em
	 * studentID je opcionalni parametar i koristi se za filtiranje komentara sa specifiranim studentID-em
	 * registeredStudentID je opcionalni parametar i koristi se za provjeru da li registrirani student vec ima upvote ili report na komentaru
	 * 
	 * @param universityID ID specifiranog sveucilista
	 * @param subjectID ID specifiranog predmeta
	 * @param studentID ID specifiranog studenta
	 * @param registeredStudentID ID trenutno registriranog studenta
	 * @return Comment objekat sa predanim parametrima
	 */
	Comment getSingleByUC(Integer universityID, Integer subjectID, Integer studentID, Integer registeredStudentID);

	/**
	 * Dohvaca listu komentara iz Comment tablice
	 * universityID je opcionalni parametar i koristi se za filtiranje komentara sa specifiranim universityID-em
	 * subjectID je opcionalni parametar i koristi se za filtiranje komentara sa specifiranim subjectID-em
	 * studentID je opcionalni parametar i koristi se za filtiranje komentara sa specifiranim studentID-em
	 * registeredStudentID je opcionalni parametar i koristi se za provjeru da li registrirani student vec ima upvote ili report na komentaru
	 * 
	 * @param universityID ID specifiranog sveucilista
	 * @param subjectID ID specifiranog predmeta
	 * @param studentID ID specifiranog studenta
	 * @param registeredStudentID ID trenutno registriranog studenta
	 * @return lista Comment objekata sa predanim parametrima
	 */
	List<Comment> getListByUC(Integer universityID, Integer subjectID, Integer studentID, Integer registeredStudentID);

	/**
	 * Dohvaca jedan komentar sa najvecim brojem upvote-ova
	 * universityID je opcionalni parametar i koristi se za filtiranje komentara sa specifiranim universityID-em
	 * registeredStudentID je opcionalni parametar i koristi se za provjeru da li registrirani student vec ima upvote ili report na komentaru
	 * 
	 * @param universityID ID specifiranog sveucilista
	 * @param registeredStudentID ID trenutno registriranog studenta
	 * @return Comment objekat sa predanim parametrima
	 */
	public Comment getTopComment(Integer universityID, Integer registeredStudentID);

	/**
	 * Brise redove iz Comment, Upvote i Report tablice sa specifiranim commentID-em
	 * 
	 * @param commentID ID specifiranog komentara
	 */
	public void delete(Integer commentID);
}
