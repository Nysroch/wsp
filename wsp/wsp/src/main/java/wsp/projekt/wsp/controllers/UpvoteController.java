package wsp.projekt.wsp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import wsp.projekt.wsp.logic.UpvoteLogic;
import wsp.projekt.wsp.model.Upvote;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest/upvote")
public class UpvoteController {
	
	@Autowired
	UpvoteLogic upvoteLogic;
	
	
	/**
	 * Poziva getByCommentID metodu iz upvoteLogic sucelja i prosljeduje predane parametre
	 * 
	 * @param commentID ID specifiranog komentara
	 * @return broj upvote-ova za predani commentID
	 */
	
	@GetMapping("/getByCommentID")
	public Long getByCommentID(int commentID) {
		return upvoteLogic.getByCommentID(commentID);
		
	}
	
	/**
	 * Poziva insert metodu iz upvoteLogic sucelja i prosljeduje predane parametre
	 * 
	 * @param upvote Upvote objekt koji se sprema u bazu
	 * @return spremljeni Upvote objekt
	 */
	@PostMapping("/upvote")
	@ResponseStatus(code = HttpStatus.CREATED)
	public @ResponseBody Upvote insert(@RequestBody Upvote upvote) {
		return upvoteLogic.insert(upvote);
	}
	
	/**
	 * Poziva delete metodu iz upvoteLogic sucelja i prosljeduje predane parametre
	 * 
	 * @param commentID ID komentara na kojem se upvote brise
	 * @param studentID ID studenta ciji se upvote brise
	 */
	@DeleteMapping("/delete")
	@ResponseStatus(code = HttpStatus.OK)
	public void delete(@RequestParam(value = "commentID", required = true) Integer commentID,
			@RequestParam(value = "studentID", required = false) Integer studentID) {
		upvoteLogic.delete(commentID, studentID);
	}
	

}
