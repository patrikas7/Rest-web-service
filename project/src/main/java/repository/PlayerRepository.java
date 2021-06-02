package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.RestApplication.project.EstablishConnection;

import objects.Player;

public class PlayerRepository {
	
	private Connection connection = null;
	
	public PlayerRepository() {
		EstablishConnection establishConnection = new EstablishConnection();
		connection = establishConnection.connection;
	}
	
	/**
	 * Gets all players from database
	 * 
	 * @return List of all players
	 */
	public List<Player> getPlayers(){
		String sqlQuery = "select * from žaidejas";
		ResultSet resultSet = fillResultSet(sqlQuery);
		List<Player> players = new ArrayList<Player>();
		
		try {
			while (resultSet.next()) {
				players.add(fillPlayer(resultSet));
			}
		} catch (Exception e) {
			System.out.println("Error getting players list: " + e.getMessage());
		}
		
		return players;
	}
	
	/**
	 * Gets player object from database which has given name and surname
	 * 
	 * @param name
	 * @param surname
	 * @return Player object
	 */
	public Player getPlayer(String name, String surname) {
		String sqlQuery = "select * from žaidejas where Vardas = '" + name + "' and Pavarde = '" + surname + "'";
		ResultSet resultSet = fillResultSet(sqlQuery);
		Player player = new Player();
		try {
			if(resultSet.next()) {
				player = fillPlayer(resultSet);
			}
		} catch (Exception e) {
			System.out.println("Error getting player: " + e.getMessage());
		}

		return player;
	}
	
	/**
	 * Posts new player to database if no duplicates exists
	 * 
	 * @param player
	 * @return true if player was created, false if duplicate was found
	 */
	public boolean createPlayer(Player player) {
		if(playerExists(player.getName(), player.getSurname())) {
			return false;
		}
		
		String sqlquery = "insert into žaidejas values (?,?,?,?,?,?,?,?,?)";
		PreparedStatement statement = prepareStatement(player, sqlquery, "create");
		try {
			statement.executeUpdate();
		} catch (Exception e) {
			System.out.println("Error executing statment for create: " + e.getMessage());
		}
		
		return true;
	}
	
	/**
	 * Updates player in database
	 * 
	 * @param player
	 * @param playerName
	 * @param playerSurname
	 * @return True if player was updated, false if it does not exists
	 */
	public boolean updatePlayer(Player player, String playerName, String playerSurname) {
		if(!playerExists(playerName, playerSurname)){
			return false;
		}
		
		String sqlQuery = "update žaidejas set Vardas=?, Pavarde=?, Amžius=?, Tautybe=?, Pozicija=?, Numeris=?, " +
						"Komanda=?, Tašku_vidurkis=? where Vardas =  '" + playerName +"' " + "and Pavarde = '" +
						playerSurname + "'";
		PreparedStatement statement = prepareStatement(player, sqlQuery, "update");
		try {
			statement.executeUpdate();
		} catch (Exception e) {
			System.out.println("Error executing statment for update: " + e.getMessage());
		}
		
		return true;
	}
	
	/**
	 * Deletes player from database by given name and surname
	 * 
	 * @param playerName
	 * @param playerSurname
	 * @return True if team was deleted, false if team does not exists
	 */
	public boolean deletePlayer(String playerName, String playerSurname) {
		if(!playerExists(playerName, playerSurname)) {
			return false;
		}
		
		String sqlQuery = "delete from žaidejas where Vardas = ? and Pavarde = ?";
		try {
			PreparedStatement statement = connection.prepareStatement(sqlQuery);
			statement.setString(1, playerName);
			statement.setString(2, playerSurname);
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
	 * Fills player object all parameters from given result set
	 * 
	 * @param resultSet
	 * @return Player object with filled parameters
	 */
	private Player fillPlayer(ResultSet resultSet) {
		Player player = new Player();
		
		try {
			player.setName(resultSet.getString(1));
			player.setSurname(resultSet.getString(2));
			player.setAge(resultSet.getInt(3));
			player.setNationality(resultSet.getString(4));
			player.setPosition(resultSet.getString(5));
			player.setJerseyNumber(resultSet.getInt(6));
			player.setTeam(resultSet.getString(7));
			player.setPointsAvarage(resultSet.getInt(8));
			player.setId(resultSet.getInt(9));
		} catch (Exception e) {
			System.out.println("Error filling player object: " + e.getMessage());
		}
		
		return player;
	}
	
	/**
	 * Prepares statement for sending player object to database
	 * 
	 * @param player
	 * @param sqlQuery
	 * @param function tells for which function statement is being filled
	 * @return Filled prepared statement
	 */
	private PreparedStatement prepareStatement(Player player, String sqlQuery, String function) {
		PreparedStatement statement = null;
		
		try {
			statement = connection.prepareStatement(sqlQuery);
			statement.setString(1, player.getName());
			statement.setString(2, player.getSurname());
			statement.setInt(3, player.getAge());
			statement.setString(4, player.getNationality());
			statement.setString(5, player.getPosition());
			statement.setInt(6, player.getJerseyNumber());
			statement.setString(7, player.getTeam());
			statement.setInt(8, player.getPointsAvarage());
			if(function.equals("create")) {
				statement.setInt(9, getLastId());
			}
			
		} catch (Exception e) {
			System.out.println("Error preparing statement: " + e.getMessage());
		}
		
		return statement;
	}
	
	/**
	 * Gets id of last element from database and increases it by one,
	 * because id column is not auto increment
	 * 
	 * @return
	 */
	private int getLastId() {
		int id = 0;
		String sqlQuery = "select MAX(id_ŽAIDEJAS) from žaidejas";
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
	 * Finds if team exists in database by given name and surname
	 * 
	 * @param playerName
	 * @param playerSurname
	 * @return True if player exists, false otherwise
	 */
	private boolean playerExists(String playerName, String playerSurname) {
		Player dublicatePlayer = getPlayer(playerName, playerSurname);
		return dublicatePlayer.getName() == null ? false : true;
	}
}
