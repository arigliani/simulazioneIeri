package it.polito.tdp.formula1.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Year;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import it.polito.tdp.formula1.model.Circuit;
import it.polito.tdp.formula1.model.Driver;
import it.polito.tdp.formula1.model.Race;
import it.polito.tdp.formula1.model.Season;


public class F1DAO {

	public List<Season> getAllSeasons() {
		
		String sql = "SELECT year, url FROM seasons ORDER BY year" ;
		
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			ResultSet rs = st.executeQuery() ;
			
			List<Season> list = new ArrayList<>() ;
			while(rs.next()) {
				list.add(new Season(Year.of(rs.getInt("year")), rs.getString("url"))) ;
			}
			
			conn.close();
			return list ;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}
	
	public List<Circuit> getCirciti(Season s){
		String sql="Select * " + 
				"from circuits,seasons,races " + 
				"where races.circuitId= circuits.circuitId " + 
				"And races.year=seasons.year " + 
				"and seasons.year=? ";
		List<Circuit> result= new LinkedList<>();
		
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setInt(1, s.getYear().getValue());
			ResultSet res = st.executeQuery() ;
			
//			int circuitId, String circuitRef, String name, String location, String country, double lat,
//			double lng, int alt, String url
			
			while(res.next()) {
				Circuit temp= new Circuit( res.getInt("circuitId"),
						res.getString("circuitRef"),
						res.getString("name"),
						res.getString("location"),
						res.getString("country"),
						res.getFloat("lat"),
						res.getFloat("lng"),
						res.getInt("alt"),
						res.getString("url"));
				result.add(temp);
			}
			
			conn.close();
			return result ;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
		
	}
	public List<Driver> getPiloti(Circuit c, Season s) {
		String sql="select * " + 
				"from races,driverstandings,drivers " + 
				"where races.raceId=driverstandings.raceId " + 
				"AND driverstandings.driverId=drivers.driverId " + 
				"And races.circuitId=? " + 
				"and races.year=? ";
    List<Driver> result= new LinkedList<>();
		
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setInt(1,c.getCircuitId());
			st.setInt(2, s.getYear().getValue());
			ResultSet res = st.executeQuery() ;
//			int driverId, String driverRef, int number, String code, String forename, String surname,
//			LocalDate dob, String nationality, String url
			
			while(res.next()) {
				Driver temp= new Driver(res.getInt("driverId"),
						res.getString("driverRef"),
						res.getInt("number"),
						res.getString("code"),
						res.getString("forename"),
						res.getString("surname"),
						res.getDate("dob").toLocalDate(),
						res.getString("nationality"),
						res.getString("url"));
				result.add(temp);
			}
			
			conn.close();
			return result ;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
		
		
	}
	public Race getGara(Season s, Circuit c) {
		String sql="select DIStinct* " + 
				"from races,driverstandings,drivers " + 
				"where races.raceId=driverstandings.raceId " + 
				"AND driverstandings.driverId=drivers.driverId " + 
				"And races.circuitId=? " + 
				"and races.year=? ";
    
		
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setInt(1,c.getCircuitId());
			st.setInt(2, s.getYear().getValue());
			ResultSet res = st.executeQuery() ;
//			int driverId, String driverRef, int number, String code, String forename, String surname,
//			LocalDate dob, String nationality, String url
			Race temp=null;
			if(res.next()) {
				temp= new Race(res.getInt("raceid"),
						Year.of(res.getInt("year")),
						res.getInt("round"),
						res.getInt("circuitid"),
						res.getString("name"),
						res.getDate("date").toLocalDate(),
						null,
						res.getString("url"));
				
				
			}
			
			conn.close();
			return temp ;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}
	
	
	public static void main(String[] args) {
		F1DAO dao = new F1DAO() ;
		
		List<Season> seasons = dao.getAllSeasons() ;
		System.out.println(seasons);
		System.out.println(dao.getCirciti(seasons.get(10)));
	}

	

	
	
}
