package wsp.projekt.wsp.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import wsp.projekt.wsp.logic.CommentLogic;
import wsp.projekt.wsp.model.Comment;
import wsp.projekt.wsp.model.Student;

@RestController
@RequestMapping("/rest/comment")
public class CommentController {
	
	@Autowired
	CommentLogic commentLogic;
	

	/**
	 * Poziva get metodu iz commentLogic sucelja i prosljeduje predane parametre
	 * 
	 * @param commentID ID specifiranog komentara
	 * @param registeredStudentID ID trenutno registriranog studenta
	 * @return Comment objekt specifiranog commentID-a 
	 */
	@GetMapping("/get/{commentID}")
	public Comment get(@PathVariable("commentID") Integer commentID,
			@RequestParam(value = "registeredStudentID", required = false) Integer registeredStudentID) {
		return commentLogic.get(commentID, registeredStudentID);
	}


	/**
	 * Poziva getList metodu iz commentLogic sucelja i prosljeduje predane parametre
	 * 
	 * @param registeredStudentID ID trenutno registriranog studenta
	 * @return lista Comment objekata
	 */
	@GetMapping("/getList")
	public List<Comment> getList(@RequestParam(value = "registeredStudentID", required = false) Integer registeredStudentID){
		return commentLogic.getList(registeredStudentID);
	}
	

	/**
	 * Poziva insert metodu iz commentLogic sucelja i prosljeduje predane parametre
	 * 
	 * @param comment Comment objekt koji se sprema u bazu
	 * @return spremljeni Comment objekt
	 */
	@PostMapping("/insert")
	@ResponseStatus(code = HttpStatus.CREATED)
	public @ResponseBody Comment insert(@RequestBody Comment comment) {
		return commentLogic.insert(comment);
	}
	

	/**
	 * Poziva delete metodu iz commentLogic sucelja i prosljeduje predane parametre
	 * 
	 * @param commentID ID specifiranog komentara
	 */
	@DeleteMapping("/delete")
	public void delete(@RequestParam(value = "commentID", required = true) Integer commentID) {
		commentLogic.delete(commentID);
	}


	/**
	 * Poziva getListByUC metodu iz commentLogic sucelja i prosljeduje predane parametre
	 * 
	 * @param universityID ID specifiranog sveucilista
	 * @param subjectID ID specifiranog predmeta
	 * @param studentID ID specifiranog studenta
	 * @param registeredStudentID ID trenutno registriranog studenta
	 * @return lista Comment objekata sa predanim parametrima
	 */
	@GetMapping("/getListByUC")
	public List<Comment> getListByUC(@RequestParam(value = "universityID", required = false) Integer universityID,
			@RequestParam(value = "subjectID", required = false) Integer subjectID,
			@RequestParam(value = "studentID", required = false) Integer studentID,
			@RequestParam(value = "registeredStudentID", required = false) Integer registeredStudentID) {
		return commentLogic.getListByUC(universityID, subjectID, studentID, registeredStudentID);
	}
	

	/**
	 * Poziva getSingleByUC metodu iz commentLogic sucelja i prosljeduje predane parametre
	 * 
	 * @param universityID ID specifiranog sveucilista
	 * @param subjectID ID specifiranog predmeta
	 * @param studentID ID specifiranog studenta
	 * @param registeredStudentID ID trenutno registriranog studenta
	 * @return Comment objekat sa predanim parametrima
	 */
	@GetMapping("/getSingleByUC")
	public Comment getSingleByUC(@RequestParam(value = "universityID", required = false) Integer universityID,
			@RequestParam(value = "subjectID", required = false) Integer subjectID,
			@RequestParam(value = "studentID", required = false) Integer studentID,
			@RequestParam(value = "registeredStudentID", required = false) Integer registeredStudentID) {
		return commentLogic.getSingleByUC(universityID, subjectID, studentID,registeredStudentID);
	}
	

	/**
	 * Poziva getTopComment metodu iz commentLogic sucelja i prosljeduje predane parametre
	 * 
	 * @param universityID ID specifiranog sveucilista
	 * @param registeredStudentID ID trenutno registriranog studenta
	 * @return Comment objekat sa predanim parametrima
	 */
	@GetMapping("/getTopComment")
	public Comment getTopComment(@RequestParam(value = "universityID", required = false) Integer universityID,
			@RequestParam(value = "registeredStudentID", required = false) Integer registeredStudentID) {
		return commentLogic.getTopComment(universityID,registeredStudentID);
	}
	
	
	

}
