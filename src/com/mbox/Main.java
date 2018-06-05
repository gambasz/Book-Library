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
            Person tom = new Person("tom", "jerry", "Awesome dude");
           //System.out.print(asd.addPersonQuery(guido));
            System.out.println(asd.getIDInQuery("PERSON", 1));
            //test.executeQuery(asd.addPersonQuery(tom));
            //test.executeQuery(asd.getIDInQuery("PERSON", 1));

            ResultSet rs = test.executeQuery(asd.getIDInQuery("PERSON", 1));
            //ResultSet rs = test.executeQuery(getTableQuery("PERSON"));

            while(rs.next()) {
                System.out.println(rs.getInt(1) + " " + rs.getString(2) + " " + rs.getString(3) + " " + rs.getString(4));
            }

            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}
