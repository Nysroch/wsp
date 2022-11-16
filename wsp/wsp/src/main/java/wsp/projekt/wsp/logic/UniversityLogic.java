package wsp.projekt.wsp.logic;

import java.util.List;

import wsp.projekt.wsp.model.University;

public interface UniversityLogic {

	/**
	 * Dohvaca sveuciliste sa predanim universityID-em
	 * 
	 * @param universityID ID specifiranog sveucilista
	 * @return University objekt specifiranog universityID-a
	 */
	public University get(int universityID);
	
	/**
	 * Dohvaca listu svih sveucilista iz baze podataka
	 * 
	 * @return lista University objekata 
	 */
	public List<University> getList();
	
	/**
	 * Dohvaca sveuciliste za predanom domenom
	 * 
	 * @param domain domena po kojoj se traži sveuciliste
	 * @return universityID sveucilista sa predanom domenom
	 */
	public Integer getUniversityIdByDomain(String domain);
}
