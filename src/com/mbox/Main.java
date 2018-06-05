package com.mbox;

import java.sql.*;

public class Main {

    public static Statement st;


    public static void main(String[] args) {

        try{

            Connection con = DBManager.establishDB();
            System.out.println("Connection to the database has been established.");
            //conn.close();

            st = con.createStatement();

            ResultSet rs = st.executeQuery("SELECT * FROM COURSECT");
            while(rs.next()){
                System.out.println(rs.getInt(1) + " " + rs.getString(2) + " " + rs.getString(3) + " " + rs.getString(4));
            }


            // Both statement and connection need to be closed with the program.
            st.close();
            con.close();

        }catch(Exception e){

        }
    }
}
