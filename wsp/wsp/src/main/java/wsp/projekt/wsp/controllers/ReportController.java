package wsp.projekt.wsp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import wsp.projekt.wsp.logic.ReportLogic;
import wsp.projekt.wsp.model.Report;

@RestController
@RequestMapping("/rest/report")
public class ReportController {

	@Autowired
	ReportLogic reportLogic;
	

	/**
	 * Poziva insert metodu iz reportLogic sucelja i prosljeduje predane parametre
	 * 
	 * @param report Report objekt koji ce se spremiti u bazu
	 * @return spremljeni Report objekt
	 */
	@PostMapping("/insert")
	@ResponseStatus(code = HttpStatus.CREATED)
	public @ResponseBody Report insert(@RequestBody Report report) {
		return reportLogic.insert(report);
	}
	
}
