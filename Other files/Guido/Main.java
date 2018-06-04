/* Hey Guys. So here is the code to connect to the database. A couple of notes to take into account:

* You are going to need to use the JDBC library (latest one). You simply add it to the project and you'll be able to use the driver  in order to connect to the database. 

* I downloaded that library from here: http://www.oracle.com/technetwork/database/features/jdbc/jdbc-drivers-12c-download-1958347.html - I downloaded the tar.gz file (in my case because im using linux, but you should download what will probably be a zip file (not just a .jar file) - Inside that .zip file you'll find many jar files, the one you need is "ojdbc8.jar". If for some reason is not working just try adding other jars and hope for the best...

Here's a link on how to do that with the IntelliJ IDE: https://stackoverflow.com/questions/1051640/correct-way-to-add-external-jars-lib-jar-to-an-intellij-idea-project

If you are using another IDE a quick google search will probably tell you how to add that libarary if you don't know how to already.

*/

package com.company;

import java.sql.*;

public class Main {

public static void main(String[] args) {
  
  try {
  Class.forName("oracle.jdbc.driver.OracleDriver");
      } catch (ClassNotFoundException e) {
            e.printStackTrace();
      }

   try{
            // Currently using 'old' format of URL for connecting to the database.
            // We should consider changing the format (we need to request a way to coneect without the use of SID.
            /* How I figured it out: https://stackoverflow.com/questions/4832056/java-jdbc-how-to-connect-to-oracle-using-service-name-instead-of-sid */

Connection conn = DriverManager.getConnection("jdbc:oracle:thin:USERNAME/PASSWORD@HOST:PORT:SID");

System.out.println("Successfully connected to the database! (I guess...)");
            
// The statement object is used to pass a SQL query to the database later.

Statement stmt=conn.createStatement();

//Executing the query (fire in the hole)

        ResultSet rs=stmt.executeQuery("SELECT * FROM PERSON");
        while(rs.next()) {
            System.out.println(rs.getInt(1) + "  " + rs.getString(2) + "  " + rs.getString(3) + " " + rs.getString(4));
        }

        // Always close the connection after...
        conn.close();

}catch (SQLException e) {
            e.printStackTrace();
        }
}
  
  /* String insertTableSQL = "INSERT INTO PERSON"
                    + "(ID, FIRSTNAME, LASTNAME, TYPE) " + "VALUES"
                    + "(1,'Guido-was-here','Guido-was-here','Guido-was-here'))";

            String testSQL = "INSERT INTO PERSON VALUES (1, 'Guido-was-here', 'Guido-was-here', 'Guido-was-here')";

            Statement test = conn.createStatement();

            System.out.println(testSQL);

            test.executeQuery(testSQL);
       
*/
