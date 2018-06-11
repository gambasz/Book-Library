package com.mbox;

import javax.swing.plaf.nimbus.State;
import java.sql.*;

public class Main {

    public static void main(String[] args) {

        DBManager.openConnection();
        DBManager.printSeparator();
        DBManager.getTableResources();
        DBManager.printSeparator();
        System.out.println(DBManager.searchByResource("Book"));
        DBManager.closeConnection();

        }
    }