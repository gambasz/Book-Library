package com.mbox;

import java.sql.*;

public class Main {


    public static void main(String[] args) {

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {


            Connection conn = DriverManager.getConnection("jdbc:oracle:thin:USERNAME/PASSWORD@HOST:PORT:SID");

            System.out.println("Successfully connected to the database! (I guess...)");


            Statement test = conn.createStatement();


            DBManager asd = new DBManager();
            Resource c = new Resource("Nani", "Nani", "Nani", "Nani", 1, 2, "Nani");

            System.out.println(asd.addResourceQuery(c));

            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}
