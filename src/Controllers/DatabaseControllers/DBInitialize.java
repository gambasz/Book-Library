package Controllers.DatabaseControllers;

import Controllers.DBManager;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.io.*;


public class DBInitialize {

    private static Connection conn;
    private static String directory = "src/Controllers/DatabaseControllers/Initializing db/";

    public static void InitDBTables(){
        try {
            connect();
            dropTables();
            createTables();
            dropSequences();
            createIDTriggers();
            createCommonIDTriggers();
            initCourseTable();
            initSemesterTable();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void connect() throws SQLException, FileNotFoundException, ClassNotFoundException {

        String url = DBManager.readDBinfo();
        Class.forName("oracle.jdbc.driver.OracleDriver");
        conn = DriverManager.getConnection(url);
    }

    private static String[] readQueries(String fileName) {
        // The name of the file to open.
        String[] queries = null;
        try {
            String content = new String ( Files.readAllBytes( Paths.get(fileName) ) );

            queries = content.split("@");

            return queries;


        } catch (FileNotFoundException ex) {
            System.out.println(
                    "Unable to open file '" +
                            fileName + "'");
        } catch (IOException ex) {
            System.out.println(
                    "Error reading file '"
                            + fileName + "'");

        }
        return null;
    }
    private static boolean runQueryList(String[] queries, boolean runPrepared){

        try {
            if(runPrepared) {

                PreparedStatement exQuery;
                for (String query : queries) {
//                    System.out.println("\n\n" + query +"\n---------\n");
                exQuery = conn.prepareStatement(query);
                exQuery.executeQuery();
                }
            }

            else{
                Statement exQuery = conn.createStatement();
                for (String query : queries) {
//                    System.out.println("\n\n" + query +"\n---------\n");
                    exQuery.executeQuery(query);
                }
            }

            System.out.println(" Successfully worked! :-)");
            return true;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(" failed! :-(");
        return false;
    }
    private static boolean dropTables(){
        String fileName = directory + "Drop_Tables.sql";
        String[] queries = readQueries(fileName);

        System.out.print("Drop table method ");
        return runQueryList(queries, true);
    }

    private static boolean createTables(){
        String fileName = directory + "Tables creating.sql";
        String[] queries = readQueries(fileName);

        System.out.print("Create all tables method ");
        return runQueryList(queries, true);

    }
    private static boolean dropSequences(){
        String fileName =  directory + "drop Sequences.sql";
        String[] queries = readQueries(fileName);

        System.out.print("Drop all Sequences method ");
        return runQueryList(queries, true);

    }

    private static boolean createIDTriggers(){
        String fileName =  directory + "IDTriggers.sql";
        String[] queries = readQueries(fileName);

        System.out.print("Create all Triggers method ");
        return runQueryList(queries, false);
    }

    private static boolean createCommonIDTriggers(){
        String fileName =  directory + "commonID Triggers.sql";
        String[] queries = readQueries(fileName);

        System.out.print("Create all Common IDs method ");
        return runQueryList(queries, false);

    }

    private static void initCourseTable(){
        try {
            String line, title, CNUMBER, description;

            Statement stmt = conn.createStatement();

            InputStreamReader reader = new InputStreamReader(new FileInputStream("CMSC.txt"));

            BufferedReader bufferedReader = new BufferedReader(reader);

            //read and put to database
            while ((line = bufferedReader.readLine()) != null ) {
                title = line.substring(0,4);
                CNUMBER = line.substring(5,8);
                description = line.substring(11);

                stmt.execute("INSERT INTO COURSECT (TITLE, CNUMBER, DESCRIPTION,DEPARTMENT) " +
                        "VALUES ('" +  title + "','"+ CNUMBER +"','" + description+"','Computer Science')");
            }

            bufferedReader.close();
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    private static void initSemesterTable(){
        try {
            String[] semester = {"Spring","Summer 1","Summer 2","Fall","Winter"};
            Statement stmt = conn.createStatement();
            int year = 2018;
            for (int i = 0; i < 13; i++) {
                for (int a = 0; a < 5; a++) {
                    stmt.execute("INSERT INTO SEMESTER (SEASON, YEAR) VALUES ('" + semester[a] + "','" + year + "')");
                }
                year++;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        InitDBTables();
//
//        String lol = "CREATE OR REPLACE TRIGGER coursect_tr\n" +
//                "  BEFORE INSERT ON coursect\n" +
//                "  FOR EACH ROW\n" +
//                "BEGIN\n" +
//                "  SELECT course_sequence.nextval\n" +
//                "  INTO :new.id\n" +
//                "  FROM dual;\n" +
//                "END";
//        try {
//            connect();
//            PreparedStatement test = conn.prepareStatement(lol);
//
//            test.executeQuery();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
    }
}

