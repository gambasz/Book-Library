package com.mbox;

import javax.swing.plaf.nimbus.State;
import java.sql.*;

public class Main {

    public static void main(String[] args) {

        DBManager.openConnection();

        //This two methods need to be put in the DBManager. I'll do that as well as add the other ones later tonight.
        // getCourseTitleById Recieves an id (for semester) and returns an array of ints (ids for Course)
        // This is used by getCourseTitlesByID to get all titles for Courses and returns them in an array of objects
        // Raja will use this to display it in the tables. (I'll do this for the rest of the methods to fill that table.
        // Note: I added a new constrcuctor to the Course class (to create objects just with titles).

        Course c[] = getCourseTitlesByID(getCourseIdsBySemesterID(49));
        System.out.println(c[0].getTitle());

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