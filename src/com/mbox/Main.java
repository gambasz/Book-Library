package com.mbox;

import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        DBManager.openConnection();

        //Course[] mamali = DBManager.relationalReadByCourseID(11);
//        ArrayList<Course> lopm = DBManager.returnEverything(57);
        ArrayList<frontend.data.Resource> resList = new ArrayList<>();
        frontend.data.Publisher pub = new frontend.data.Publisher(25,"Scholastic","I'm not different at all",
                "Email: testing@icloud,com");
        frontend.data.Resource r1 =  new frontend.data.Resource("54321","Book","Java Programming","Poornachandra Sarang",
                "it's obviously good",true,12,3,1,pub);
        frontend.data.Resource r2 = new frontend.data.Resource("12345","Book","Testing 2","Khanh Hoang",
                "this is a test",true,10,0,1,pub);
        resList.add(r1);
        resList.add(r2);

//        DBManager.setIDinResourceFromArrayList(resList);
        System.out.println("Here is the ID of the first one" +resList.get(0).getID());
        System.out.println("Here is the ID of the second one" +resList.get(1).getID());

        frontend.data.Person p = new frontend.data.Person("Halwa","Adam",97,"CourseInstructor");


        frontend.data.Course c = new frontend.data.Course(19,19,2018,"FALL","CMSC214","Computer Science",p,"Advanced Java Programming",
                resList);
        DBManager.relationalInsertByID2(c);
        DBManager.closeConnection();
        }

    }