import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.sql.*;  
class ConnectToDatabase {  
public static void main(String args[]){  
try{  
// Buoc 1: Tai lop Driver  
Class.forName("oracle.jdbc.driver.OracleDriver");  
  
// Buoc 2: Tao doi tuong Connection  
Connection con=DriverManager.getConnection(  
"jdbc:oracle:thin:@acoracle.montgomerycollege.edu:15521:acoradb","cs270227om","om");  
  
// Buoc 3: Tao doi tuong Statement  
Statement stmt=con.createStatement();  
  

// Buoc 5: Dong doi tuong Connection  
con.close();  
  
}catch(Exception e){ System.out.println(e);}  
  
}  
}  