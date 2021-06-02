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
import objects.Team;
import repository.TeamRepository;

@Path("teams")
public class TeamResource {
	
	TeamRepository teamRepository = new TeamRepository();
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Team> getTeams() {
	   return teamRepository.getTeams();
	}
	
	@GET
	@Path("/{name}")
	@Produces(MediaType.APPLICATION_JSON)
	public Team getTeam(@PathParam("name") String name) {
		return teamRepository.getTeam(name);
	}
	
	//TODO: Display error message if duplicate team is being posted
	@POST
	@Path("/createTeam")
	@Consumes(MediaType.APPLICATION_JSON)
	public Team createTeam(Team team) {
		if(teamRepository.createTeam(team)) {
			return teamRepository.getTeam(team.getName());
		}
		
		return new Team();
	}
	
	@PUT
	@Path("/updateTeam/{name}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Team updateTeam(Team team, @PathParam("name") String name) {
		if (teamRepository.updateTeam(team, name)) {
			return teamRepository.getTeam(team.getName());
		}
		
		return new Team();
	}
	
	@DELETE
	@Path("/deleteTeam/{name}")
	public Team deleteTeam(@PathParam("name") String name) {
		Team team = teamRepository.getTeam(name);
		
		if(teamRepository.deleteTeam(name)) {
			return team;
		}
		
		return new Team();
	}
	
	
}
