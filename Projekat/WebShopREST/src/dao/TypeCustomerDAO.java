package dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import beans.Location;
import beans.SportsFacility;
import beans.Status;
import beans.TrainingHistory;
import beans.TypeCustomer;
import beans.TypeName;

public class TypeCustomerDAO {
	private static TypeCustomerDAO Instance = null;
	private static String contextPath = "";

	private Map<Integer, TypeCustomer> customers = new HashMap<>();
	
	
	private TypeCustomerDAO() {
		
	}
	
	/***
	 * @param contextPath Putanja do aplikacije u Tomcatu. Može se pristupiti samo iz servleta.
	 */
	public TypeCustomerDAO(String contextPath) {
		loadCustomer(contextPath);
	}
	
	public static TypeCustomerDAO getInstance() {
		if(Instance == null) {
			Instance = new TypeCustomerDAO();
		}
		return Instance;
	}
	
	public TypeCustomer find(int id) {
		return customers.get(id);
	}
	
	public Collection<TypeCustomer> findAll() {
		return customers.values();
	}
	
	
	public TypeCustomer save(TypeCustomer customer) {
		Integer maxId = -1;
		for (int id : customers.keySet()) {
			int idNum = id;
			if (idNum > maxId) {
				maxId = idNum;
			}
		}
		maxId++;
		customer.setId(maxId);
		customers.put(customer.getId(), customer);
		saveToFile();
		return customer;
	}
	
	public void loadCustomer(String contextPath) {
		BufferedReader in = null;
		this.contextPath = contextPath;
		try {
			File file = new File(contextPath + "/Baza/customer.txt");
			in = new BufferedReader(new FileReader(file));
			String line;
			StringTokenizer st;
			while ((line = in.readLine()) != null) {
				line = line.trim();
				if (line.equals("") || line.indexOf('#') == 0)
					continue;
				st = new StringTokenizer(line, ";");
				while (st.hasMoreTokens()) {
					int id  =Integer.parseInt(st.nextToken().trim());
					
					
					int customer_type = Integer.parseInt(st.nextToken().trim()); 
					TypeName[] customer_types = TypeName.values();
					TypeName customer_typeFromFile = customer_types[customer_type];
					
					double discount  =Double.parseDouble(st.nextToken().trim());
					
					int points  =Integer.parseInt(st.nextToken().trim());
					
					customers.put(id,new TypeCustomer(id,customer_typeFromFile, discount,points));
				}
			
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				}
				catch (Exception e) { }
			}
		}
	}
	public TypeCustomer change(TypeCustomer customer) {
		customers.put(customer.getId(), customer);
		return customer;
	}
	
	public TypeCustomer delete(int id) {
		return customers.remove(id);
	}
	
	public void saveToFile() {
		BufferedWriter out = null;
		try {

			File file = new File(contextPath + "/Baza/customer.txt");
			out = new BufferedWriter(new FileWriter(file));
			String line;
			StringTokenizer st;
			for(TypeCustomer customer : customers.values()) {
				out.write(customer.fileLine() + '\n');
			}
			
			
		} catch (Exception ex) {
			ex.printStackTrace();             
		} finally {
			if (out != null) {
				try {
					out.close();
				}
				catch (Exception e) { }
			}
		}
	}
}
