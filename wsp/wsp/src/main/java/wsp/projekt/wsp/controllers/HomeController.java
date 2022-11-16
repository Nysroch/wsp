package wsp.projekt.wsp.controllers;

import java.util.logging.Logger;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import wsp.projekt.wsp.model.Student;

@CrossOrigin("*")
@RestController
public class HomeController {
	private final static Logger LOGGER = Logger.getLogger(HomeController.class.getName());
	
//	@GetMapping("/test")
//	public String test() {
//		LOGGER.info("Testiranje");
//		return ("Initial backend call2");
//	}
	

}
