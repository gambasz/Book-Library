package com.company;

import com.sun.xml.internal.fastinfoset.util.StringArray;

import java.io.*;
import java.util.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;;
import java.sql.PreparedStatement;

/* Notes:

I want a table in the databae with 4columns nad N rows. N is the number of courses.

1. Index: think about like a counter that starts from 0. Integer

2. Instructor: repeat the instructors:
Dr. Webb, Dr. Kuijt, Dr. Tarek, Dr. Alexander

3. Course: You will read the courses from a TXT file and split them. Then put them in the course row.

4. Book: Repeat the books: Java book, Cpp book, Python book, Robotics book

WARNING:

The path to the file cmsc.txt or whichever text file to read should be written inside the string "filePath", same thing
goes for the database file.

 */

public class Main {

    public Scanner x;
    public String pathToDatabase = "home/ga/Projects/MBox/tests.db";
    public String[] instructor = {"Dr. Webb", "Dr. Kuijt", "Dr. Tarek", "Dr. Alexander"};
    public String[] books = {"Java Book","C++ Book","Python Book","Robotics Book"};

    // Path to txt file. Replace with actual path
    public String filePath = "/home/ga/Projects/MBox/cmsc.txt";

    public void main(String[] args) {

        openfile(filePath);
        readFile();
        closeFile();

        createNewTable();
        magicInsert();


    }

    //-----------------------------------------------
    //Reading the file

    public void openfile(String filePath){

        try{

            x = new Scanner(new File(filePath));
            System.out.println("We good");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void readFile(){

        while(x.hasNextLine()){

            System.out.println(x.nextLine());
        }
    }

    public void closeFile(){

        x.close();
    }

    //--------------------------------------------------

    //Creating the DB

    public void createNewTable() {
        // SQLite connection string, replace with actual path

        // SQL statement for creating a new table
        String sql = "CREATE TABLE testMCBooks (\n"
                + "	id integer PRIMARY KEY,\n"
                + "	instructor text NOT NULL,\n"
                + "	course text NOT NULL\n"
                + " book text NOT NULL\n"
                + ");";

        try (Connection conn = DriverManager.getConnection(pathToDatabase);
             Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    //---------------------------------------------------

    // Connecting to the Database

    private Connection connect() {
        // SQLite connection string
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(pathToDatabase);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    //------------------------------------------------

    //Populating the table (manually)

    public void insert(String instructor, String course, String book) {
        String sqlQuery = "INSERT INTO testMCBooks(instructor,course, book) VALUES(?,?,?)";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sqlQuery)) {
            pstmt.setString(1, instructor);
            pstmt.setString(2, course);
            pstmt.setString(3, book);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    //----------------------------------------------------

    // Auto-populate like mamali asked:

    public void magicInsert(){

        int i = 0;
        String sql = "INSERT INTO testMCBooks(instructor, course, book) VALUES(?,?,?)";

        while(x.hasNextLine()){

            insert(instructor[i], x.nextLine(), books[i]);
            i++;

            if(i>=4) i = 0;

        }

    }

}
