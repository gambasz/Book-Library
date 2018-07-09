package com.mbox.Future;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.*;
import java.util.Scanner;

public class DataBaseManager {
    private Connection DBConnection;
    private Statement statements[];

    public DataBaseManager() {
        try {
            String url = readFromFile("DBinformation.txt");
            Class.forName("oracle.jdbc.driver.OracleDriver");
            DBConnection = DriverManager.getConnection(url);

            System.out.println("Successfully connected to the database");
            statements = new Statement[5];
            for (Statement statement : statements) {
                statement = DBConnection.createStatement();
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Connection to the database was NOT successful");

        }
    }

    protected String readFromFile(String fileName) {
        try {
            Scanner reader = new Scanner(new FileReader(fileName));
            String line = reader.nextLine();
            if (line != null) {
                reader.close();
                return line;
            }

        } catch (FileNotFoundException ex) {

            System.err.print(ex.getMessage());
//            return readFromFile();

        }
        return null;
    }

    private void endConnection() {
        try {
            if (DBConnection.isClosed()) {

                System.out.println("The connection has already been closed");
            } else {
                DBConnection.close();
                System.out.println("The connection to the database has been successfully terminated.");
            }
        } catch (SQLException e) {
            System.out.println("Something went wrong when trying to close the connection");
        }
    }

    /*
     *
     * ========================================The old code  Start==================================================
     * */
    // Used to execute a non return query. Meaning? Either an update, insert or delete.
    public void executeNoReturnQuery(String nrq) {

        try {

            statements[0] = DBConnection.createStatement();

            statements[0].executeQuery(nrq);

        } catch (SQLException e) {

            System.out.println("Something went wrong with the statement. Fix me");
        }
    }

    public int getSemesterIDByName(String season, String year) {

        int id = 0;
        season = season.toLowerCase();
        season = season.substring(0, 1).toUpperCase() + season.substring(1);
        try {
            Statement st = DBConnection.createStatement();
            String query = String.format("SELECT * FROM SEMESTER WHERE SEASON='%s' AND  YEAR='%s'", season, year);
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                id = rs.getInt(1);
            }

            return id;

        } catch (SQLException e) {

        }

        return 0;

    }

    /*
     *
     * ========================================The old code  End==================================================
     * */
    @Override
    protected void finalize() throws Throwable {
        endConnection();
        super.finalize();
    }


}
