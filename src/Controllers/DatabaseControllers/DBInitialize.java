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

            queries = content.split(";");

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

    private static boolean dropTables(){
        String fileName = "Drop_Tables.sql";

        try {
            String content = new String ( Files.readAllBytes( Paths.get(fileName) ) );
            PreparedStatement delTables;
            delTables = conn.prepareStatement(content);
            delTables.executeQuery();

            System.out.println("All tables successfully dropped!");
            return true;
        } catch (FileNotFoundException ex) {
            System.out.println(
                    "Unable to open file '" +
                            fileName + "'");
        } catch (IOException ex) {
            System.out.println(
                    "Error reading file '"
                            + fileName + "'");
        }
        catch (SQLException ex){
            ex.printStackTrace();

        }
        return false;

    }

    private static void createTables(){
        String fileName = "Tables creating.sql";
        String[] queries = readQueries(fileName);
        PreparedStatement inTables;

        try {
            for (String query : queries) {
                inTables = conn.prepareStatement(query);
                inTables.executeQuery();
            }
            System.out.println("All tables successfully created! :-)");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        InitDBTables();
    }
}

