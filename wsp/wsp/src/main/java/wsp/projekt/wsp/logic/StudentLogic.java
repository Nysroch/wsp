package wsp.projekt.wsp.logic;

import java.util.List;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;

import wsp.projekt.wsp.model.Student;

public interface StudentLogic {

	/** 
	 * Dohvaca studenta sa predanim studentID-em
	 * 
	 * @param studentID ID specifiranog studenta
	 * @return Student objekt spcifiranog studentID-a
	 */
	public Student get(int studentID);
	
	/**
	 * Dohvaca listu svih studenata iz baze podataka
	 * 
	 * @return lista Student objekata
	 */
	public List<Student> getList();

	/**
	 * Sprema novi red u Student tablicu
	 * 
	 * @param student Student objekt koji se sprema u bazu
	 * @return spremljeni Student objekt
	 */
	Student insert(Student student);
	
	/**
	 * Sprema novi red u Student tablicu, te pri spremanju puni universityID sa
	 *  ID-em sveucilista cija se domena poklapa sa upisanim email-om
	 * 
	 * @param student Student objekt koji se sprema u bazu
	 * @return universityID iz spremljenog Student objekta
	 */
	public Integer registerUser(Student student);
	
	/**
	 * Dohvaca studenta sa predanim email-om
	 *  
	 * @param email specifirani email 
	 * @return Student objekt sa specifiranim email-om
	 * @throws NotFoundException greska se baca ako se ne pronađe student sa specifiranim email-om
	 */
	public Student findStudentByEmail(String email) throws NotFoundException;
}
