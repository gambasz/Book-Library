/**
 * @author hamed
 * you need to add external jar.  download from here  https://dev.mysql.com/downloads/connector/j/  . choose platform independent. 
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;

public class Professor {

	public static void main(String[]args) throws Exception {
		
		
		
		
		//getConnection();    to connect to the server then erase it
		
		table();
		post();
	}
	
	

	//this adds all of or data to the table
	public static void post()throws Exception{
		
		final String var1 = "firstname";
		final String var2 = "lastname";
		final int crn = 0;
		final String className="class";
		final String department ="department";
		final String book ="book";
		final String latest="latest";
		
		
		try {
			Connection con = getConnection();
			
			//im not sure how to insert crn b/c its integer this works for string.
			PreparedStatement posted = con.prepareStatement("INSERT INTO coursebooks(first, last, crn, class, department, latest) VALUES ('"+var1+"', '"+ var2+"' ,'"+ className+"','"+ department+"','"+ book+"','"+ latest+"') ");
			posted.executeUpdate();
			
		}catch(Exception e) {System.out.print(e);}
		finally {
			System.out.println("Insert conpleted");
		}
	}
	
	
	
	
	
	// this will creat table to hold all the information
	public static void table( )  throws Exception{
		try {
			Connection con = getConnection();
			PreparedStatement create = con.prepareStatement("CREATE TABLE IF NOT EXISTS coursebooks(id int NOT NULL AUTO_INCREMENT, first varchar(255), last varchar(255), crn int, class varchar(255), department varchar(255), book varchar(255), latest varchar(255), PRIMARY KEY(crn))");
		    create.executeUpdate();
	
		}catch(Exception e) {System.out.print(e);}
		finally {
			System.out.println("Function Complete.");
		}
	}
		
	// connecting to the database
	public static Connection getConnection() throws Exception{
		try {
			String driver = "com.mysql.jdbc.Driver";
			String url = "jdbc:mysql://localhost:3306/test";
			String username = "hamed";
			String password = "hamed_95";
			Class.forName(driver);
			
			Connection conn = DriverManager.getConnection(url,username,password);
			System.out.println("connected");
			return conn;
	
		}catch(Exception e) {System.out.print(e);}
		
		return null;
	}
}
