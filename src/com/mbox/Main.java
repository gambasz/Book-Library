package com.mbox;

import javax.swing.plaf.nimbus.State;
import java.sql.*;

public class Main {

    public static void main(String[] args) {

        DBManager.openConnection();

        Course[] mamali = DBManager.relationalReadByCourseID(11);

        DBManager.closeConnection();
        }

        public static int[] getCourseIdsBySemesterID(int id){

            int i = 0;
            String query = String.format("SELECT * FROM RELATION_SEMESTER_COURSE WHERE SEMESTERID=%d", id);

            try{

                Statement st = DBManager.conn.createStatement();
                ResultSet rs = st.executeQuery(query);

                while(rs.next()){
                    i++;
                }

                int[] ids = new int[i];

                rs = st.executeQuery(query);
                i = 0;
                while(rs.next()){

                    ids[i] = rs.getInt(1);
                    i++;
                }

                return ids;


            }catch(SQLException e){

                System.out.println("Something went wrong");

            }

            return null;
        }


        // Returns id needs to return title;
        public static Course[] getCourseTitlesByID(int[] ids){

        Course[] c;
        try{

            Statement st = DBManager.conn.createStatement();
            c = new Course[ids.length];

            for(int i = 0; i < ids.length; i++){

                ResultSet rs = st.executeQuery(String.format("SELECT * FROM COURSECT WHERE ID=%d", ids[i]));
                while(rs.next()){

                    c[i] = new Course(rs.getString(2) + rs.getString(3));
                }

                return c;

            }


        }catch(SQLException e){

        }
            return null;
        }
    }