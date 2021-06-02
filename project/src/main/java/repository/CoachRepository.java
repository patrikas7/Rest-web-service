package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.RestApplication.project.EstablishConnection;

import objects.Coach;

public class CoachRepository {
	
	private Connection connection = null;
	
	public CoachRepository() {
		EstablishConnection establishConnection = new EstablishConnection();
		connection = establishConnection.connection;
	}
	
	/**
	 * Gets all coaches from database
	 * 
	 * @return List of all coaches
	 */
	public List<Coach> getCoaches(){
		String sqlQuery = "select * from treneris";
		ResultSet resultSet = fillResultSet(sqlQuery);
		List<Coach> coaches = new ArrayList<Coach>();
		try {
			while (resultSet.next()) {
				coaches.add(fillCoach(resultSet));
			}
		} catch (Exception e) {
			System.out.println("Error geting coach list: " + e.getMessage());
		}
		
		return coaches;
	}
	
	/**
	 * Gets coach object from database which has given name and surname
	 * 
	 * @param name
	 * @param surname
	 * @return Coach object
	 */
	public Coach getCoach(String name, String surname) {
		String sqlQuery = "select * from treneris where Vardas = '" + name + "' and Pavarde = '" + surname + "'";
		ResultSet resultSet = fillResultSet(sqlQuery);
		Coach coach = new Coach();
		try {
			if (resultSet.next()) {
				coach = fillCoach(resultSet);
			}
		} catch (Exception e) {
			System.out.println("Error getting coach: " + e.getMessage());
		}
		
		return coach;
	}
	
	/**
	 * Posts new coach to database if no duplicates exists
	 * 
	 * @param coach
	 * @return true if coach was created, false if duplicate was found
	 */
	public boolean createCoach(Coach coach){
		if(coachExists(coach.getName(), coach.getSurname())) {
			return false;
		}
		
		String sqlquery = "insert into treneris values (?,?,?,?)";
		PreparedStatement statement = prepareStatement(coach, sqlquery, "create");
		try {
			statement.executeUpdate();
		} catch (Exception e) {
			System.out.println("Error executing statment for create: " + e.getMessage());
		}
		
		return true;
	}
	
	/**
	 * Updates coach in database
	 * 
	 * @param coach
	 * @param coachName
	 * @param coachSurname
	 * @return True if coach was updated, false if it does not exists
	 */ 
	public boolean updateCoach(Coach coach, String coachName, String coachSurname){
		if(!coachExists(coachName, coachSurname)) {
			return false;
		}
		
		String sqlQuery = "update treneris set Vardas=?, Pavarde=?, Tautybe=? where Vardas = '" + coachName + "' " + "and Pavarde = '" +
				coachSurname + "'";
		PreparedStatement statement = prepareStatement(coach, sqlQuery, "update");
		try {
			statement.executeUpdate();
		} catch (Exception e) {
			System.out.println("Error executing statment for update: " + e.getMessage());
		}
		
		return true;
	}
	
	/**
	 * Deletes coach from database by given name and surname
	 * 
	 * @param coachName
	 * @param coachSurname
	 * @return True if coach was deleted, false if coach does not exists
	 */
	public boolean deleteCoach(String coachName, String coachSurname) {
		if(!coachExists(coachName, coachSurname)) {
			return false;
		}
		
		String sqlQuery = "delete from treneris where Vardas = ? and Pavarde = ?";
		try {
			PreparedStatement statement = connection.prepareStatement(sqlQuery);
			statement.setString(1, coachName);
			statement.setString(2, coachSurname);
			statement.executeUpdate();
		} catch (Exception e) {
			System.out.println("Error executing statment for delete: " + e.getMessage());
		}
		
		return true;
		
	}
	
	/**
	 * Fills result set from data given by database after executing SQL query
	 * 
	 * @param sqlQuery
	 * @return
	 */
	private ResultSet fillResultSet(String sqlQuery) {
		ResultSet resultSet = null;
		
		try {
			Statement statement = connection.createStatement();
			resultSet = statement.executeQuery(sqlQuery);
		} catch (Exception e) {
			System.out.println("Error filling result set: " + e.getMessage());
		}
		
		return resultSet;
	}
	
	/**
	 * Fills coach object all parameters from given result set
	 * 
	 * @param resultSet
	 * @return
	 */
	private Coach fillCoach(ResultSet resultSet) {
		Coach coach = new Coach();
		
		try {
			coach.setName(resultSet.getString(1));
			coach.setSurname(resultSet.getString(2));
			coach.setNationality(resultSet.getString(3));
			coach.setId(resultSet.getInt(4));
			coach.setTeam(getCoachTeam(resultSet.getInt(4)));
		} catch (Exception e) {
			System.out.println("Error filling coach object: " + e.getMessage());
		}
		
		return coach;
	}
	
	/**
	 * Prepares statement for sending coach object to database
	 * 
	 * @param coach
	 * @param sqlQuery
	 * @param function
	 * @return Filled prepared statement
	 */
	private PreparedStatement prepareStatement(Coach coach, String sqlQuery, String function) {
		PreparedStatement statement = null;
		
		try {
			statement = connection.prepareStatement(sqlQuery);
			statement.setString(1, coach.getName());
			statement.setString(2, coach.getSurname());
			statement.setString(3, coach.getNationality());
			if(function.equals("create")) {
				statement.setInt(4, getLastId());
			}
			
		} catch (Exception e) {
			System.out.println("Error preparing statement: " + e.getMessage());
		}
		
		return statement;
	}
	
	/**
	 * Gets coach team name by his id
	 * 
	 * @param coachId
	 * @return Coach team name
	 */
	private String getCoachTeam(int coachId) {
		String sqlQuery = "SELECT a.Pavadinimas from krepšinio_komanda a, trenerio_kontraktas b WHERE b.fk__TRENERIS = " + coachId + " and " +
						"b.fk_KREPŠINIO_KOMANDA = a.id_KREPŠINIO_KOMANDA";
		ResultSet resultSet = fillResultSet(sqlQuery);
		String coachTeam = "";
		try {
			if(resultSet.next()) {
				coachTeam = resultSet.getString(1);
			}
		} catch (Exception e) {
			System.out.println("Error getting coach team: " + e.getMessage());
		}
		
		return coachTeam;
	}
	
	/**
	 * Gets id of last element from database and increases it by one,
	 * because id column is not auto increment
	 * 
	 * @return
	 */
	private int getLastId() {
		int id = 0;
		String sqlQuery = "select MAX(id__TRENERIS) from treneris";
		ResultSet resultSet = fillResultSet(sqlQuery);
		
		try {
			if(resultSet.next()) {
				id  = resultSet.getInt(1);
			}
		} catch (Exception e) {
			System.out.println("Error getting last id: " + e.getMessage());
		}
		
		return id + 1;
	}
	
	/**
	 * Finds if coach exists in database by given name and surname
	 * s
	 * @param coachName
	 * @param coachSurname
	 * @return True if coach exists, false otherwise
	 */
	private boolean coachExists(String coachName, String coachSurname) {
		Coach dublicateCoach = getCoach(coachName, coachSurname);
		return dublicateCoach.getName() == null ? false : true;
	}
	

}
