package dto;

import beans.Korisnik;
import beans.SportsFacility;
import beans.Training;
import beans.TrainingType;

public class TrainingDTO {
	private String name;
	private TrainingType type;
	private SportsFacility sportFacility;
	private int duration;
	private String description;
	private String image;
	private int id;
	private String trainerName;
	
	
	public TrainingDTO(Training training) {
		this.id = training.getId();
		this.name = training.getName();
		this.type = training.getType();
		this.sportFacility = training.getSportFacility();
		this.duration = training.getDuration();
		this.description = training.getDescription();
		this.image = training.getImage();
		this.trainerName = (training.getCoach()==null)?null:(training.getCoach().getFirstName() + " " + training.getCoach().getLastName());
	}

	public String getTrainerName() {
		return trainerName;
	}

	public void setTrainerName(String trainerName) {
		this.trainerName = trainerName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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


}
