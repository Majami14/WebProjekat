package beans;

import java.time.LocalTime;
import java.util.ArrayList;

public class SportsFacility {
	private int id;
	private String name;
	private TypeFacility type;
	private ArrayList<Training> content;
	private Status status;
	private Location location;
	private String image;
	private double average;
	private LocalTime startTime;
	private LocalTime endTime;

	public SportsFacility() {
		this.content = new ArrayList<Training>();
	}

	public SportsFacility(int id) {
		this.id = id;
	}

	public SportsFacility(int id, String name, TypeFacility type, ArrayList<Training> content, Status status,
			Location location, String image, double average, LocalTime startTime, LocalTime endTime) {
		super();
		this.id = id;
		this.name = name;
		this.type = type;
		this.status = status;
		this.content = content;
		this.location = location;
		this.image = image;
		this.average = average;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public TypeFacility getType() {
		return type;
	}

	public void setType(TypeFacility type) {
		this.type = type;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public double getAverage() {
		return average;
	}

	public void setAverage(double average) {
		this.average = average;
	}

	public String getStartTime() {
		return TimeHelper.timeToString(startTime);
	}

	public void setStartTime(String startTime) {
		this.startTime = TimeHelper.stringToTime(startTime);
	}

	public String getEndTime() {
		return TimeHelper.timeToString(endTime);
	}

	public void setEndTime(String endTime) {
		this.endTime = TimeHelper.stringToTime(endTime);
	}

	public ArrayList<Training> getContent() {
		return content;
	}

	public void setContent(ArrayList<Training> content) {
		this.content = content;
	}

	public String fileLine() {
		return id + ";" + name + ";" + type.ordinal() + ";" + status.ordinal() + ";" + location.getId() + ";" + image + ";" + average + ";"
				+ TimeHelper.timeToString(startTime) + ";" + TimeHelper.timeToString(endTime);
	}
}
