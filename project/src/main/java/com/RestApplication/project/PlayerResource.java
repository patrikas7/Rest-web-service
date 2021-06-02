package com.RestApplication.project;

import java.util.List;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import objects.Player;
import repository.PlayerRepository;

@Path("players")
public class PlayerResource {
	
	PlayerRepository playerRepository = new PlayerRepository();
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Player> getPlayers(){
		return playerRepository.getPlayers();
	}
	
	@GET
	@Path("/{name}/{surname}")
	@Produces({MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON})
	public Player getPlayer(@PathParam("name") String playerName, @PathParam("surname") String playerSurname) {
		return playerRepository.getPlayer(playerName, playerSurname);
	}
	
	@POST
	@Path("/createPlayer")
	@Consumes(MediaType.APPLICATION_JSON)
	public Player createPlayer(Player player) {
		if(playerRepository.createPlayer(player)) {
			return playerRepository.getPlayer(player.getName(), player.getSurname());
		}
		
		return new Player();
	}
	
	@PUT
	@Path("/updatePlayer/{name}/{surname}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Player updatePlayer(Player player, @PathParam("name") String playerName, @PathParam("surname") String playerSurname) {
		if(playerRepository.updatePlayer(player, playerName, playerSurname)) {
			return playerRepository.getPlayer(player.getName(), player.getSurname());
		}
		
		return new Player();
	}
	
	@DELETE
	@Path("/deletePlayer/{name}/{surname}")
	public Player deletePlayer(@PathParam("name") String playerName, @PathParam("surname") String playerSurname) {
		Player player = playerRepository.getPlayer(playerName, playerSurname);
		
		if(playerRepository.deletePlayer(playerName, playerSurname)) {
			return player;
		}
		
		return new Player();
	}
	
}
