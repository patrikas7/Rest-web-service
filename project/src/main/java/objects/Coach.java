package objects;

public class Coach {
	private String name;
	private String surname;
	private String Nationality;
	private String Team;
	private int id;
	
	public Coach() {
		this(null,null,null,null,0);
	}
	
	
	public Coach(String name, String surname, String nationality, String team, int id) {
		this.name = name;
		this.surname = surname;
		Nationality = nationality;
		Team = team;
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getSurname() {
		return surname;
	}


	public void setSurname(String surname) {
		this.surname = surname;
	}


	public String getNationality() {
		return Nationality;
	}


	public void setNationality(String nationality) {
		Nationality = nationality;
	}


	public String getTeam() {
		return Team;
	}


	public void setTeam(String team) {
		Team = team;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((Nationality == null) ? 0 : Nationality.hashCode());
		result = prime * result + ((Team == null) ? 0 : Team.hashCode());
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((surname == null) ? 0 : surname.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Coach other = (Coach) obj;
		if (Nationality == null) {
			if (other.Nationality != null)
				return false;
		} else if (!Nationality.equals(other.Nationality))
			return false;
		if (Team == null) {
			if (other.Team != null)
				return false;
		} else if (!Team.equals(other.Team))
			return false;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (surname == null) {
			if (other.surname != null)
				return false;
		} else if (!surname.equals(other.surname))
			return false;
		return true;
	}
	
	
	
	
}
