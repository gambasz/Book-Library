package Controllers.DatabaseControllers;

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
            createTables();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


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

    private static void connect() throws SQLException, ClassNotFoundException{

        String url = "PUT_DATABSE_URL_HERE";
        Class.forName("oracle.jdbc.driver.OracleDriver");
        conn = DriverManager.getConnection(url);



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
        }


        catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public static void main(String[] args) {
        InitDBTables();
    }
}

