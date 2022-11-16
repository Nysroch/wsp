package wsp.projekt.wsp.logic;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;

import com.querydsl.jpa.impl.JPAQuery;

public abstract class BaseLogic<T> {

	@Autowired
	private EntityManager entityManager;
	
	protected EntityManager getEm() {
		return entityManager;
	}
	
	/**
	 * Generira novi JPAQuery objekt za kreiranje upita za bazu podataka
	 * 
	 * @return novi JPAQuery objekt 
	 */
	protected JPAQuery<T> query(){
		return new JPAQuery<>(getEm());
	}
	
//	protected T insert(T data) {
//		getEm().persist(data);
//	}
	
	
}
