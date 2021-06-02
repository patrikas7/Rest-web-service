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
import objects.Coach;
import repository.CoachRepository;

@Path("coaches")
public class CoachResource {
	
	CoachRepository coachRepository = new CoachRepository();
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Coach> getCoachs(){
		return coachRepository.getCoaches();
	}
	
	@GET
	@Path("/{name}/{surname}")
	@Produces(MediaType.APPLICATION_JSON)
	public Coach getCoach(@PathParam("name") String coachName, @PathParam("surname") String coachSurname) {
		return coachRepository.getCoach(coachName, coachSurname);
	}
	
	@POST
	@Path("/createCoach")
	@Consumes(MediaType.APPLICATION_JSON)
	public Coach createCoach(Coach coach) {
		if(coachRepository.createCoach(coach)) {
			return coachRepository.getCoach(coach.getName(), coach.getSurname());
		}
		
		return new Coach();
	}
	
	@PUT
	@Path("/updateCoach/{name}/{surname}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Coach updateCoach(Coach coach, @PathParam("name") String coachName, @PathParam("surname") String coachSurname) {
		if(coachRepository.updateCoach(coach, coachName, coachSurname)) {
			return coachRepository.getCoach(coach.getName(), coach.getSurname());
		}

		return new Coach();
	}
	
	@DELETE
	@Path("/deleteCoach/{name}/{surname}")
	public Coach deleteCoach(@PathParam("name") String coachName, @PathParam("surname") String coachSurname) {
		Coach coach = coachRepository.getCoach(coachName, coachSurname);
		
		if(coachRepository.deleteCoach(coachName, coachSurname)) {
			return coach;
		}
		
		return new Coach();
	}
}
