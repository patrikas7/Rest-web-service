package objects;

public class Player {
	
	private String name;
	private String surname;
	private int age;
	private String nationality;
	private String position;
	private int jerseyNumber;
	private String team;
	private int pointsAvarage;
	private int id;
	
	public Player() {
		this(null,null,0,null,null,0,null,0,0);
	}
	
	
	public Player(String name, String surname, int age, String nationality, String position, int jerseyNumber,
			String team, int pointsAvarage, int id) {
		this.name = name;
		this.surname = surname;
		this.age = age;
		this.nationality = nationality;
		this.position = position;
		this.jerseyNumber = jerseyNumber;
		this.team = team;
		this.pointsAvarage = pointsAvarage;
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


	public int getAge() {
		return age;
	}


	public void setAge(int age) {
		this.age = age;
	}


	public String getNationality() {
		return nationality;
	}


	public void setNationality(String nationality) {
		this.nationality = nationality;
	}


	public String getPosition() {
		return position;
	}


	public void setPosition(String position) {
		this.position = position;
	}


	public int getJerseyNumber() {
		return jerseyNumber;
	}


	public void setJerseyNumber(int jerseyNumber) {
		this.jerseyNumber = jerseyNumber;
	}


	public String getTeam() {
		return team;
	}


	public void setTeam(String team) {
		this.team = team;
	}


	public int getPointsAvarage() {
		return pointsAvarage;
	}


	public void setPointsAvarage(int pointsAvarage) {
		this.pointsAvarage = pointsAvarage;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}
	
	
	
	
}
