package wsp.projekt.wsp.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import wsp.projekt.wsp.logic.StudentLogic;
import wsp.projekt.wsp.model.Student;

@RestController
@RequestMapping("/rest/student")
public class StudentController {
	
	@Autowired
	StudentLogic studentLogic;
	

	/**
	 * Poziva get metodu iz studentLogic sucelja i prosljeduje predane parametre
	 * 
	 * @param studentID ID specifiranog studenta
	 * @return Student objekt spcifiranog studentID-a
	 */
	@GetMapping("/get/{studentID}")
	public Student get(@PathVariable("studentID") int studentID){
		return studentLogic.get(studentID);
	}


	/**
	 * Poziva get metodu iz studentLogic sucelja i prosljeduje predane parametre
	 * 
	 * @return lista Student objekata
	 */
	@GetMapping("/getList")
	public List<Student> getList(){
		return studentLogic.getList();
	}
	

	/**
	 * Poziva get metodu iz studentLogic sucelja i prosljeduje predane parametre
	 * 
	 * @param student Student objekt koji se sprema u bazu
	 * @return spremljeni Student objekt
	 */
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public @ResponseBody Student insert(@RequestBody Student student) {
		return studentLogic.insert(student);
	}
	

	/**
	 * Poziva get metodu iz studentLogic sucelja i prosljeduje predane parametre
	 * 
	 * @param student Student objekt koji se sprema u bazu
	 * @return universityID iz spremljenog Student objekta
	 */
	@PostMapping("/register")
	public Integer registerUser(@RequestBody Student student) {
		return studentLogic.registerUser(student);
	}
}
