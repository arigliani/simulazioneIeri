package it.polito.tdp.formula1.model;


import java.util.*;

import it.polito.tdp.formula1.db.F1DAO;

public class Model {
	private F1DAO dao;
	private List<Season> stagioni;
	
	public Model(){
		 dao= new F1DAO();
		 stagioni= new LinkedList<>();
	}
	public List<Season> getAllStagioni(){
		stagioni=dao.getAllSeasons();
		return this.stagioni;
		
		
	}
	public List<Circuit> getAllCircuiti(Season s){
		return dao.getCirciti(s);
	}
	
	public List<Driver> getAllPilotaCircuiti(Circuit c, Season s){
		return dao.getPiloti(c, s);
	}
	public Race getGara(Season s, Circuit c) {
		return dao.getGara(s,c);
	}


}
