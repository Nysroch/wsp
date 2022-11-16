package wsp.projekt.wsp.logic;

import wsp.projekt.wsp.model.Report;

public interface ReportLogic {

	/**
	 * Sprema novi red u Report tablicu
	 * 
	 * @param report Report objekt koji se sprema u bazu
	 * @return spremljeni Report objekt
	 */
	Report insert(Report report);
		
	
	/**
	 * Brise sve redove iz Report tablice specifiranog commentID-a
	 * 
	 * @param commentID ID komentara na kojem se brisu report-ovi
	 */
	void delete(Integer commentID);
}
