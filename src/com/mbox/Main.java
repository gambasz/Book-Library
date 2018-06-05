package com.mbox;

import java.sql.*;

public class Main {

    public static Statement st;
    public static Connection con;

    public static void main(String[] args) {

        try{

            con = DBManager.establishDB();
            System.out.println("Connection to the database has been established.");
            //conn.close();

            st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM PERSON WHERE ID=1");
            //while(rs.next()){
              //  System.out.println(rs.getInt(1) + rs.getString(2) + rs.getString(3) + rs.getString(4));
            //}

            // This code goes inside of the object. use it

            while(rs.next()){
                int id = rs.getInt(1);
                String firstname = rs.getString(2);
                String lastname = rs.getString(3);
                String type = rs.getString(4);
                System.out.println((String.valueOf(id) + firstname + lastname + type));
            }



            //ResultSet rs = st.executeQuery("SELECT * FROM SEMESTER");
            //while(rs.next()){
              //  System.out.println(rs.getInt(1) + " " + rs.getString(2) + " " + rs.getString(3));
            // Both statement and connection need to be closed with the program.
            st.close();
            con.close();
            }catch(Exception e){

        }
    }
}
