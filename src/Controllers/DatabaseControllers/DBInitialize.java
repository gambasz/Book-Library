package Controllers.DatabaseControllers;

import Controllers.DBManager;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class DBInitialize {

    public static Connection conn;

    public static void InitDBTables(){
        try {
            connect();
            dropTables();
            createTables();
            dropSequences();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void connect() throws SQLException, ClassNotFoundException{
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
    private static boolean runQueryList(String[] queries){

        PreparedStatement exQuery;
        try {
            for (String query : queries) {
                exQuery = conn.prepareStatement(query);
                exQuery.executeQuery();
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
        String fileName = "Drop_Tables.sql";
        String[] queries = readQueries(fileName);

        System.out.print("Drop table method ");
        return runQueryList(queries);
    }

    private static boolean createTables(){
        String fileName = "Tables creating.sql";
        String[] queries = readQueries(fileName);

        System.out.print("Create all tables method ");
        return runQueryList(queries);

    }
    private static boolean dropSequences(){
        String fileName = "drop Sequences.sql";
        String[] queries = readQueries(fileName);

        System.out.print("Drop all Sequences method ");
        return runQueryList(queries);

    }


    public static void main(String[] args) {
        InitDBTables();
    }
}

