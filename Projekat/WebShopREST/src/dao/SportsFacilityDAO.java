package dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import beans.DateHelper;
import beans.Location;
import beans.SportsFacility;
import beans.Status;
import beans.TimeHelper;
import beans.Training;
import beans.TrainingHistory;
import beans.TypeFacility;

public class SportsFacilityDAO {
	private static SportsFacilityDAO Instance = null;

	private Map<Integer, SportsFacility> facilitys = new HashMap<>();

	private SportsFacilityDAO() {

	}

	private SportsFacilityDAO(String contextPath) {
		loadFacility(contextPath);
	}

	public static SportsFacilityDAO getInstance() {
		if (Instance == null) {
			Instance = new SportsFacilityDAO();
		}
		return Instance;
	}

	public SportsFacility find(int id) {
		return facilitys.get(id);
	}

	public Collection<SportsFacility> findAll() {
		return facilitys.values();
	}

	public ArrayList<SportsFacility> search(String searchValue, String criterion){
		ArrayList<SportsFacility> find = new ArrayList<SportsFacility>();
		if(criterion.equals("name")) {
			for(SportsFacility sp : facilitys.values()) {
				if(sp.getName().contains(searchValue)) {
					find.add(sp);
				}
			}
		} else if(criterion.equals("type")) {
			for(SportsFacility sp : facilitys.values()) {
				if(sp.getType().toString().contains(searchValue)) {
					find.add(sp);
				}
			}
		} else if(criterion.equals("location")){
			for(SportsFacility sp : facilitys.values()) {
				if(sp.getLocation().getCity().contains(searchValue) || sp.getLocation().getStreet().contains(searchValue)) {
					find.add(sp);
				}
			}
		} else {
			for(SportsFacility sp : facilitys.values()) {
				if(sp.getAverage() == Double.parseDouble(searchValue)) {		//crazy opasno
					find.add(sp);
				}
			}
		}
		return find;
	}
	
	public SportsFacility save(SportsFacility facility) {
		Integer maxId = -1;
		for (int id : facilitys.keySet()) {
			int idNum = id;
			if (idNum > maxId) {
				maxId = idNum;
			}
		}
		maxId++;
		facility.setId(maxId);
		facilitys.put(facility.getId(), facility);
		return facility;
	}

	public void loadFacility(String contextPath) {
		BufferedReader in = null;
		try {
			File file = new File(contextPath + "/Baza/facilitys.txt");
			in = new BufferedReader(new FileReader(file));
			String line;
			StringTokenizer st;
			while ((line = in.readLine()) != null) {
				line = line.trim();
				if (line.equals("") || line.indexOf('#') == 0)
					continue;
				st = new StringTokenizer(line, ";");
				while (st.hasMoreTokens()) {

					int id = Integer.parseInt(st.nextToken().trim());
					String name = st.nextToken().trim();

					int facility_type = Integer.parseInt(st.nextToken().trim()); // Za enum jel dobro?
					TypeFacility[] facility_types = TypeFacility.values();
					TypeFacility facility_typeFromFile = facility_types[facility_type];

					int status_type = Integer.parseInt(st.nextToken().trim()); // Za enum jel dobro?
					Status[] status_types = Status.values();
					Status status_typeFromFile = status_types[status_type];

					int locationId = Integer.parseInt(st.nextToken().trim());
					Location location = new Location(locationId);

					String image = st.nextToken().trim();

					double average = Double.parseDouble(st.nextToken().trim());

					LocalTime startTime = TimeHelper.stringToTime(st.nextToken().trim());
					LocalTime endTime = TimeHelper.stringToTime(st.nextToken().trim());

					// facilitys.put(id,comment,facility_type,statusId,image,average,startTime,endTime);
					facilitys.put(id, new SportsFacility(id, name, facility_typeFromFile, new ArrayList<Training>(),
							status_typeFromFile, location, image, average, startTime, endTime));

				}

			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (Exception e) {
				}
			}
		}
	}

	public SportsFacility change(SportsFacility facility) {
		facilitys.put(facility.getId(), facility);
		return facility;
	}

	public SportsFacility delete(int id) {
		return facilitys.remove(id);
	}

	public void connectFacilityLocation() {

		ArrayList<Location> locations = new ArrayList<Location>(LocationDAO.getInstance().findAll());
		for (SportsFacility facility : facilitys.values()) {
			int id = facility.getLocation().getId();

			for (Location location : locations) {
				if (location.getId() == id) {
					facility.setLocation(location);
					break;
				}
			}
		}
	}
}
