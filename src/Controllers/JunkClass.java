package Controllers;

import java.sql.ResultSet;
import java.sql.SQLException;
public class JunkClass {


// **********************











    // Relationship Tables
    public static void printTableCoursePerson() {

        try {

            ResultSet rs = st.executeQuery(getTableCoursePersonQuery());
            while (rs.next()) {

                System.out.println(rs.getInt(1) + "|" + rs.getInt(2));
            }

        } catch (SQLException e) {

            e.printStackTrace();
            System.out.println("Exception when executing printTableCoursePerson() method.");
        }

    }

    public static void printTableCourseResource() {

        try {

            ResultSet rs = st.executeQuery(getTableCourseResourceQuery());
            while (rs.next()) {

                System.out.println(rs.getInt(1) + "|" + rs.getInt(2));
            }

        } catch (SQLException e) {

            e.printStackTrace();
            System.out.println("Exception when executing printTableCourseResource() method.");
        }
    }

    public static void printTableCourseSemester() {

        try {

            ResultSet rs = st.executeQuery(getTableCourseSemesterQuery());
            while (rs.next()) {

                System.out.println(rs.getInt(1) + "|" + rs.getInt(2) + "|" + rs.getInt(3));
            }

        } catch (SQLException e) {

            e.printStackTrace();
            System.out.println("Exception when executing printTableCourseSemester() method.");
        }

    }

    public static void printTablePersonResource() {

        try {

            ResultSet rs = st.executeQuery(getTablePersonResourceQuery());
            while (rs.next()) {

                System.out.println(rs.getInt(1) + "|" + rs.getInt(2));
            }

        } catch (SQLException e) {

            e.printStackTrace();
            System.out.println("Exception when executing printTablePersonResource() method.");
        }

    }

    public static void printTablePublisherResource() {

        try {

            ResultSet rs = st.executeQuery(getTablePublisherResourceQuery());
            while (rs.next()) {

                System.out.println(rs.getInt(1) + "|" + rs.getInt(2));
            }

        } catch (SQLException e) {

            e.printStackTrace();
            System.out.println("Exception when executing printTablePublisherResource() method.");
        }

    }

    //===========================================PRINTS MATCHING ID=====================================================

    public static void printPersonInTable(int id) {

        try {

            ResultSet rs = st.executeQuery(getPersonInTableQuery(id));
            while (rs.next()) {
                System.out.println(rs.getInt(1) + "|" + rs.getString(2) +
                        rs.getString(3) + rs.getString(4));
            }

        } catch (SQLException e) {

            e.printStackTrace();
            System.out.println("ERROR: Exception when trying to execute printPersonInTable() method.");
        }


        // return String.format("SELECT * FROM PERSON WHERE ID = %d", id);
    }

    public static void printCourseInTable(int id) {

        try {

            ResultSet rs = st.executeQuery(getCourseInTableQuery(id));

            while (rs.next()) {
                System.out.println(rs.getInt(1) + "|" + rs.getString(2) +
                        rs.getString(3) + rs.getString(4) + "|" + rs.getString(5));
            }

        } catch (SQLException e) {

            e.printStackTrace();
            System.out.println("ERROR: Exception when trying to execute printCourseInTable() method.");

        }
    }

    public static void printResourcesInTable(int id) {

        try {

            ResultSet rs = getResourceInTableQuery(id).executeQuery();

            while (rs.next()) {
                System.out.println(rs.getInt(1) + "|" + rs.getString(2) +
                        rs.getString(3) + rs.getString(4) + rs.getInt(5) +
                        rs.getInt(6) + rs.getString(7));
            }

        } catch (SQLException e) {

            e.printStackTrace();
            System.out.println("ERROR: Exception when trying to execute printResourcesInTable() method.");
        }

    }

    public static void printPublisherInTable(int id) {

        try {

            ResultSet rs = st.executeQuery(getPublisherInTableQuery(id));

            while (rs.next()) {
                System.out.println(rs.getInt(1) + "|" + rs.getString(2) +
                        rs.getString(3) + rs.getString(4));
            }

        } catch (SQLException e) {

            e.printStackTrace();
            System.out.println("ERROR: Exception when trying to execute printPublisherInTable() method.");
        }
    }



    //================================================= QUERIES ========================================================

    //===========================================SELECT METHODS (QUERIES)===============================================

    //===========================================SELECT WHOLE TABLE QUERY===============================================

    //Regular Tables





















    //**









             /* String query1 = "CREATE TABLE SEMESTER (id NUMBER(10) NOT NULL, title VARCHAR2(100) NOT NULL)";

        String tableCreateQuery = "create table JBT(name varchar2(10), course varchar2(10))";

         String query2 = "ALTER TABLE SEMESTER\n" +
                "  ADD (\n" +
                "    CONSTRAINT semester_pk PRIMARY KEY  (id));";

         String query3 = "CREATE SEQUENCE semester_sequence;";

         String query4 = "CREATE OR REPLACE TRIGGER semester_on_insert\n" +
                "  BEFORE INSERT ON SEMESTER\n" +
                "  FOR EACH ROW\n" +
                "BEGIN\n" +
                "  SELECT semester_sequence.nextval\n" +
                "  INTO :new.id\n" +
                "  FROM dual;\n" +
                "END;\n"; */

    // Currently using 'old' format of URL for connecting to the database.
    // We should consider changing the format (we need to request a way to coneect without the use of SID.

    // How I figured it out: https://stackoverflow.com/questions/4832056/java-jdbc-how-to-connect-to-oracle-using-service-name-instead-of-sid


    //String testSQL = "INSERT INTO BOOKS (title) VALUES ('asrasdas')";
    //String testSQL2 = "DELETE FROM BOOKS WHERE ID=2";

    //System.out.println(testSQL);

    //test.executeQuery(query1);

    //step3 create the statement object
       /* Statement stmt=conn.createStatement();

        //step4 execute query

        ResultSet rs=stmt.executeQuery("SELECT * FROM PERSON");
        while(rs.next()) {
            System.out.println(rs.getInt(1) + "  " + rs.getString(2) + "  " + rs.getString(3) + " " + rs.getString(4));
        } */

    //step5 close the connection object

       /* public static void createConnectionToDB(){

        try{

            // Currently using 'old' format of URL for connecting to the database.
            // We should consider changing the format (we need to request a way to coneect without the use of SID.

            Connection conn = DriverManager.getConnection("jdbc:oracle:thin:USERNAME/PASSWORD@HOST:PORT:SID");

            System.out.println("Successfully connected to the database! (I guess...)");

            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    */

  /* public static void testInsert(){

       System.out.println("sup");
   }

   public static String getTableQuery(String table){

       return "SELECT * FROM " + table;

   }

   public static String insertIntoPerson(String firstname, String lastname, String type){



       return "";

       }

       public void createTable(){



       }


   } */

/*

https://stackoverflow.com/questions/4832056/java-jdbc-how-to-connect-to-oracle-using-service-name-instead-of-sid

 */

   /*  String insertTableSQL = "INSERT INTO PERSON"
                    + "(ID, FIRSTNAME, LASTNAME, TYPE) " + "VALUES"
                    + "(1,'Guido-was-here','Guido-was-here','Guido-was-here'))";

            String testSQL = "INSERT INTO PERSON VALUES (1, 'Guido-was-here', 'Guido-was-here', 'Guido-was-here')";

            Statement test = conn.createStatement();

            System.out.println(testSQL);

            test.executeQuery(testSQL);
            */
}

      /* try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
*/
      /*


           //System.out.print(asd.addPersonQuery(guido));
            //System.out.println(asd.getIDInQuery("PERSON", 1));
            //System.out.println(tom.newFNameByID(2, "mamali"));
            //test.executeQuery(asd.addPersonQuery(tom));
            //test.executeQuery(asd.getIDInQuery("PERSON", 1));
            //test.executeQuery(tom.newFNameByID(2, "mamali"));
            //ResultSet rs = test.executeQuery(asd.getIDInQuery("PERSON", 1));
            //ResultSet rs = test.executeQuery(getTableQuery("PERSON"));
            /*
            while(rs.next()) {
                System.out.println(rs.getInt(1) + " " + rs.getString(2) + " " + rs.getString(3) + " " + rs.getString(4));
            }
            */
//st.executeQuery(p.addToDB());

//ResultSet rs = test.executeQuery(getTableQuery("PERSON"));
            /*
            while(rs.next()) {
                System.out.println(rs.getInt(1) + " " + rs.getString(2) + " " + rs.getString(3) + " " + rs.getString(4));
            }
            */

