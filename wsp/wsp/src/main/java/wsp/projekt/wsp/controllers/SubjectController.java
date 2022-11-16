package wsp.projekt.wsp.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import wsp.projekt.wsp.logic.SubjectLogic;
import wsp.projekt.wsp.model.Subject;

@RestController
@RequestMapping("/rest/subject")
public class SubjectController {

	
	@Autowired
	SubjectLogic subjectLogic;
	

	/**
	 * Poziva get metodu iz subjectLogic sucelja i prosljeduje predane parametre
	 * 
	 * @param subjectID ID specifiranog predmeta
	 * @return Subject objekt specifiranog subjectID-a
	 */
	@GetMapping("/get/{subjectID}")
	public Subject get(@PathVariable("subjectID") int subjectID) {
		return subjectLogic.get(subjectID);
	}
	

	/**
	 * Poziva getList metodu iz subjectLogic sucelja i prosljeduje predane parametre
	 * 
	 * @return lista Subject objekata
	 */
	@GetMapping("/getList")
	public List<Subject> getList() {
		return subjectLogic.getList();
	}
	

	/**
	 * Poziva getTopSubjects metodu iz subjectLogic sucelja i prosljeduje predane parametre
	 * 
	 * @param universityID ID specifiranog sveucilista 
	 * @return lista top 3 Subject objekata specifiranog sveucilista ili svih sveucilista
	 */
	@GetMapping("/getTopSubjects")
	public List<Subject> getTopSubjects(@RequestParam(value = "universityID", required = false) Integer universityID) {
		return subjectLogic.getTopSubjects(universityID);
	}
	

	/**
	 * Poziva getListByUC metodu iz subjectLogic sucelja i prosljeduje predane parametre
	 * 
	 * @param universityID ID specifiranog sveucilista
	 * @return lista Subject objekata specifiranog sveucilista ili svih sveucilista
	 */
	@GetMapping("/getListByUC")
	public List<Subject> getListByUC(@RequestParam(value = "universityID", required = true) Integer universityID) {
		return subjectLogic.getListByUC(universityID);
	}
	
}
