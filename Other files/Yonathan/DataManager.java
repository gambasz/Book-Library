package test;

import java.sql.*;
import java.util.ArrayList;

public class DataManager {
	
	private ArrayList<Courses> courses = new ArrayList<>();
	private final String DB_URL = "jdbc:mysql://localhost:3306/mcbook";
	
	public void readSqlFile() {
		
		
		try {
			
			//Class.forName("com.mysql.jdbc.Driver");
			
			Connection conn = DriverManager.getConnection(DB_URL,"root","");
			
			Statement stm = conn.createStatement();
			String sqlStatement = "SELECT CRN, Name, class, department, book, latest FROM MCbook";
			ResultSet result= stm.executeQuery(sqlStatement);
			
			while(result.next()) {
				courses.add(new Courses(result.getInt("CRN"),
											result.getString("Name"),
											result.getString("class"),
											result.getString("department"),
											result.getString("book"),
											result.getString("latest")));
			}
			
			
			
			conn.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.print("Error Occured");
		}
		
		
	}
	
	public void showContents() {
		
		for(Courses s: courses) {
			System.out.println(s.getName());
		}
		
		
	}
	
	
}
