package wsp.projekt.wsp.logic;

import wsp.projekt.wsp.model.Upvote;

public interface UpvoteLogic {

	/**
	 * Dohvaca broj upvote-ova za dani commentID
	 * 
	 * @param commentID ID specifiranog komentara
	 * @return broj upvote-ova za predani commentID
	 */
	public Long getByCommentID(int commentID);

	/**
	 * Sprema novi red u Upvote tablicu
	 * 
	 * @param upvote Upvote objekt koji se sprema u bazu
	 * @return spremljeni Upvote objekt
	 */
	Upvote insert(Upvote upvote);

	/**
	 * Brise red iz Upvote tablice za dani commentID i studentID
	 * studentID je opcionalni parametar, ako nije predan brisu se svi upvote-ovi za dani commentID
	 * 
	 * @param commentID ID komentara na kojem se upvote brise
	 * @param studentID ID studenta ciji se upvote brise
	 */
	void delete(Integer commentID, Integer studentID);
	
}
