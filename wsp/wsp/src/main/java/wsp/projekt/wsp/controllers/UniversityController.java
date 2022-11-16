package wsp.projekt.wsp.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import wsp.projekt.wsp.logic.UniversityLogic;
import wsp.projekt.wsp.model.University;

@RestController
@RequestMapping("/rest/university")
public class UniversityController {

	@Autowired
	UniversityLogic universityLogic;
	
	/**
	 * Poziva get metodu iz universityLogic sucelja i prosljeduje predane parametre
	 * 
	 * @param universityID ID specifiranog sveucilista
	 * @return University objekt specifiranog universityID-a
	 */
	@GetMapping("/get/{universityID}")
	public University get(@PathVariable Integer universityID) {
		return universityLogic.get(universityID);
	}

	/**
	 * Poziva getList metodu iz universityLogic sucelja i prosljeduje predane parametre
	 * 
	 * @return lista University objekata 
	 */
	@GetMapping("/getList")
	public List<University> getList() {
		return universityLogic.getList();
		
	}
	
	
	
}
