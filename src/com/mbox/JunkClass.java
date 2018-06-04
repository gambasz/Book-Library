package com.mbox;

public class JunkClass {

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
