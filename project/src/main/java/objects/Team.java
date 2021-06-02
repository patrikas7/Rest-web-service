package objects;

public class Team {
	
	private String name;
	private String city;
	private String coach;
	private int budget;
	private String arena;
	private String leaugesLicences;
	private String wonChampionships;
	private int id;
	
	public Team() {
		this(null, null, null, 0, null, null, null, 0);
	}
	
	public Team(String name, String city, String coach, int budget, String arena, String leaugesLicences,
			String wonChampionships, int id) {
		this.name = name;
		this.city = city;
		this.coach = coach;
		this.budget = budget;
		this.arena = arena;
		this.leaugesLicences = leaugesLicences;
		this.wonChampionships = wonChampionships;
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCoach() {
		return coach;
	}

	public void setCoach(String coach) {
		this.coach = coach;
	}

	public int getBudget() {
		return budget;
	}

	public void setBudget(int budget) {
		this.budget = budget;
	}

	public String getArena() {
		return arena;
	}

	public void setArena(String arena) {
		this.arena = arena;
	}

	public String getLeaugesLicences() {
		return leaugesLicences;
	}

	public void setLeaugesLicences(String leaugesLicences) {
		this.leaugesLicences = leaugesLicences;
	}

	public String getWonChampionships() {
		return wonChampionships;
	}

	public void setWonChampionships(String wonChampionships) {
		this.wonChampionships = wonChampionships;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	
	
}
