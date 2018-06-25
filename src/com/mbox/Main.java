package com.mbox;

import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        DBManager.openConnection();

        //Course[] mamali = DBManager.relationalReadByCourseID(11);
//        ArrayList<Course> lopm = DBManager.returnEverything(57);

        DBManager.closeConnection();
        }

    }