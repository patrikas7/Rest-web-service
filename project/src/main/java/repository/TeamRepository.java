package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.RestApplication.project.EstablishConnection;

import objects.Team;

public class TeamRepository {
	
	private Connection connection = null;
	
	public TeamRepository() {
		EstablishConnection establishConnection = new EstablishConnection();
		connection = establishConnection.connection;
	}
	
	/**
	 * Gets all teams from database
	 * 
	 * @return List of all teams
	 */
	public List<Team> getTeams() {
		String sqlQuery = "select * from krepšinio_komanda";
		ResultSet resultSet = fillResultSet(sqlQuery);
		List<Team> teams = new ArrayList<Team>();
		
		try {
			while (resultSet.next()) {
				teams.add(fillTeam(resultSet));
			}
		} catch (Exception e) {
			System.out.println("Error geting teams list: " + e.getMessage());
		}
		
		return teams;
	}
	
	/**
	 * Gets team object from database which has given name
	 * 
	 * @param name Team name
	 * @return Team object
	 */
	public Team getTeam(String name) {
		String sqlQuery = "select * from krepšinio_komanda where Pavadinimas= '" + name + "'";
		ResultSet resultSet = fillResultSet(sqlQuery);
		Team team = new Team();
		try {
			if(resultSet.next()) {
				team = fillTeam(resultSet);
			}
		} catch (Exception e) {
			System.out.println("Error getting team: " + e.getMessage());
		}
		
		return team;
	}
	
	/**
	 * Posts new team to database if no duplicates exists
	 * 
	 * @param team
	 * @return true if team was created, false if duplicate was found
	 */
	public boolean createTeam(Team team) {
		if(teamExists(team.getName())) {
			return false;
		}

		String sqlQuery = "insert into krepšinio_komanda values (?,?,?,?,?,?,?,?)";
		PreparedStatement statement = prepareStatment(team, sqlQuery, "create");
		try {
			statement.executeUpdate();
		} catch (Exception e) {
			System.out.println("Error executing statment for create: " + e.getMessage());
		}
		
		return true;
	}
	
	/**
	 * Updates team in database
	 * 
	 * @param team
	 * @return True if team was updated, false if it does not exists
	 */
	public boolean updateTeam(Team team, String name) {
		if(!teamExists(name)) {
			return false;
		}
		
		String sqlQuery = "update krepšinio_komanda set Pavadinimas=?, Miestas=?, Treneris=?, Biudžetas=?, Arena=?, " +
					"Lygu_licenzija=?, Leimejimai=? where Pavadinimas = '" + name + "'";
		PreparedStatement statement = prepareStatment(team, sqlQuery, "update");
		try {
			statement.executeUpdate();
		} catch (Exception e) {
			System.out.println("Error executing statment for update: " + e.getMessage());
		}
		
		return true;
	}
	
	/**
	 * Deletes team from database by given name
	 * 
	 * @param teamName
	 * @return True if team was deleted, false if team does not exists
	 */
	public boolean deleteTeam(String teamName) {
		if(!teamExists(teamName)){
			return false;
		}
		
		String sqlQuery = "delete from krepšinio_komanda where Pavadinimas = ?";
		try {
			PreparedStatement statement = connection.prepareStatement(sqlQuery);
			statement.setString(1, teamName);
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
	 * @return Filled result set
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
	 * Fills team object all parameters from given result set
	 * 
	 * @param resultSet
	 * @return Team object with filled parameters
	 */
	private Team fillTeam(ResultSet resultSet) {
		Team team = new Team();
		
		try {
			team.setName(resultSet.getString(1));
			team.setCity(resultSet.getString(2));
			team.setCoach(resultSet.getString(3));
			team.setBudget(resultSet.getInt(4));
			team.setArena(resultSet.getString(5));
			team.setLeaugesLicences(resultSet.getString(6));
			team.setWonChampionships(resultSet.getString(7));
			team.setId(resultSet.getInt(8));
			
		} catch (Exception e) {
			System.out.println("Error filling team object: " + e.getLocalizedMessage());
		}
		
		return team;
	}	
	
	/**
	 * Prepares statement for sending team object to database
	 * 
	 * @param team
	 * @param sqlQuery
	 * @param function tells for which function statement is being filled
	 * @return Filled prepared statement
	 */
	private PreparedStatement prepareStatment(Team team, String sqlQuery, String function) {
		PreparedStatement statement = null;
		
		try {
			statement = connection.prepareStatement(sqlQuery);
			statement.setString(1, team.getName());
			statement.setString(2, team.getCity());
			statement.setString(3, team.getCoach());
			statement.setInt(4, team.getBudget());
			statement.setString(5, team.getArena());
			statement.setString(6, team.getLeaugesLicences());
			statement.setString(7, team.getWonChampionships());
			if(function.equals("create")){
				statement.setInt(8, getLastId());
			}
		} catch (Exception e) {
			System.out.println("Error preparing statment: " + e.getMessage());
		}
		
		return statement;
	}
	
	/**
	 * Gets id of last element from database and increases it by one,
	 * because id column is not auto increment
	 * 
	 * @return Id for new team object
	 */
	private int getLastId(){
		int id = 0;
		String sqlQuery = "select MAX(id_KREPŠINIO_KOMANDA) from krepšinio_komanda";
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
	 * Finds if team exists in database by given name
	 * 
	 * @param teamName
	 * @return True if team exists, false otherwise
	 */
	private boolean teamExists(String teamName) {
		Team dublicateTeamCheck = getTeam(teamName);
		return dublicateTeamCheck.getName() == null ? false : true;
	}
		
}


