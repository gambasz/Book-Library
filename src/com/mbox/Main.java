package com.mbox;

import java.sql.*;

public class Main {

    public static Statement st;
    public static Connection con;
    //public static PreparedStatement ps;

    public static void main(String[] args) {

        try {

            con = DBManager.establishDB();
            System.out.println("Connection to the database has been established.");


            st = con.createStatement();


            Resource r = new Resource("TRO" , "LO", "LO", "LO", 0, 0, "LOL");


            //ResultSet rs = st.executeQuery("SELECT MAX(ID) FROM SEMESTER");

            r.addToDB();
            System.out.println(r.toString());

            ResultSet rs = st.executeQuery("SELECT * FROM RESOURCES");

            while(rs.next()){

                System.out.println(String.valueOf(rs.getInt(1)) + "--" + rs.getString(2) +
                "--" + rs.getString(3) + "--" + rs.getString(4) + "--" + rs.getString(5) + "--" + String.valueOf(rs.getInt(6)) + " " +
                        String.valueOf(rs.getInt(7)) + " " + rs.getString(8));

            }

            //p.addToDB();

            //System.out.println(p.toString());

            // This code goes inside of the object. use it

            /*

            while (rs.next()) {
                int id = rs.getInt(1);
                String firstname = rs.getString(2);
                String lastname = rs.getString(3);
                String type = rs.getString(4);
                System.out.println((String.valueOf(id) + " " + firstname + " " + lastname + " " + type));
                */
                st.close();
                con.close();
            }catch (Exception e) {
        }

        }
    }




        //ResultSet rs = st.executeQuery("SELECT * FROM SEMESTER");
            //while(rs.next()){
              //  System.out.println(rs.getInt(1) + " " + rs.getString(2) + " " + rs.getString(3));
            // Both statement and connection need to be closed with the program.
