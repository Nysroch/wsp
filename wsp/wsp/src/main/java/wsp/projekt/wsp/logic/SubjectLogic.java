package wsp.projekt.wsp.logic;

import java.util.List;

import wsp.projekt.wsp.model.Subject;

public interface SubjectLogic {
	
	/**
	 * Dohvaca jedan Subjekt objekt sa specifiranim subjectID-em 
	 * 
	 * @param subjectID ID specifiranog predmeta
	 * @return Subject objekt specifiranog subjectID-a
	 */
	public Subject get(Integer subjectID);
	
	/**
	 * Dohvaca listu svih predemeta
	 * 
	 * @return lista Subject objekata
	 */
	public List<Subject> getList();

	/**
	 * Dohvaca listu top 3 predmeta sa specifiranim universityID-em
	 * 
	 * universityID je opcionalni parametar, ako nije predan metoda vraca predmete sa svih sveucilista
	 * 
	 * @param universityID ID specifiranog sveucilista 
	 * @return lista top 3 Subject objekata specifiranog sveucilista ili svih sveucilista
	 */
	List<Subject> getTopSubjects(Integer universityID);

	/**
	 * Dohvaca listu svih predmeta sa specifiranim universityID-em
	 * 
	 * @param universityID ID specifiranog sveucilista
	 * @return lista Subject objekata specifiranog sveucilista ili svih sveucilista
	 */
	public List<Subject> getListByUC(Integer universityID);

}
