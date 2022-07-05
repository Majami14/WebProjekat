package beans;

public class Training {
	private String Name;
	private TrainingType type;
	private SportsFacility sportFacility;
	private int duration;
	private Korisnik coach;
	private String description;
	private String image;
	private int id;

	public Training(int id) {
		this.id = id;
	}
	
	

	public Training() {

	}



	public Training(String name, TrainingType type, SportsFacility sportFacility, int duration, Korisnik coach,
			String description, String image, int id) {
		super();
		Name = name;
		this.type = type;
		this.sportFacility = sportFacility;
		this.duration = duration;
		this.coach = coach;
		this.description = description;
		this.image = image;
		this.id = id;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public TrainingType getType() {
		return type;
	}

	public void setType(TrainingType type) {
		this.type = type;
	}

	public SportsFacility getSportFacility() {
		return sportFacility;
	}

	public void setSportFacility(SportsFacility sportFacility) {
		this.sportFacility = sportFacility;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public Korisnik getCoach() {
		return coach;
	}

	public void setCoach(Korisnik coach) {
		this.coach = coach;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String fileLine() {
		return  id + ";" + Name + ";" + type
				+ ";" + sportFacility + ";" + duration + ";" + ((coach == null) ? -1 : coach.getId()) + ";"
				+ description + ";" + image;
	}
	
}
