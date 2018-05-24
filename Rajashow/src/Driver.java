import java.sql.*;  
class Driver{  
public static void main(String args[]){  
try{  
Class.forName("oracle.jdbc.driver.OracleDriver");  
  
//step2 create  the connection object  
Connection con=DriverManager.getConnection(  
"jdbc:oracle:thin:@localhost:1521:xe","sys","sys");  

BufferedReader reader = new BufferedReader(new FileReader(new File("MCbooks.sql")));
String text = null;


Statement stmt=con.createStatement();  
while ((text = reader.readLine()) != null) {
    stmt.executeUpdate(text);
}
ResultSet rs=stmt.executeQuery("select * from MCBook");  

while(rs.next())  
System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3)+" "+rs.getString(4)+" "+rs.getString(5));  

// CRN search method <<<<
String selectSQL = "select * from MCBook WHERE CRN = ?";


preparedStatement = con.prepareStatement(selectSQL);
// fill the first ? with 1
preparedStatement.setInt(1, 1);
rs = preparedStatement.executeQuery();

while(rs.next())  
System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3)+" "+rs.getString(4)+" "+rs.getString(5));  

con.close();  
  
}catch(Exception e){ System.out.println(e);}  
  
}  
}  