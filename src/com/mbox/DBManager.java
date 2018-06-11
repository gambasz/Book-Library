package com.mbox;
import java.sql.*;
import com.mbox.Main;

public class DBManager {

    public static Statement st;
    public static Connection conn;

    // As of right now returns a connection. Should be void.
    public static void openConnection() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            conn = DriverManager.getConnection("jdbc:oracle:thin:USERNAME/PASSWORD@HOST:PORT:SID");
            System.out.println("Connection to the database was successful");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void closeConnection(){

        try{

            if(conn.isClosed()){

                System.out.println("The connection has already been closed");
            }

            conn.close();
            System.out.println("The connection to the database has been successfully terminated.");

        } catch(SQLException e){

            System.out.println("Something went wrong when trying to close the connection");
        }
    }

    // Returns the whole table in the form of a string. (Either "Person", "Course", etc) [CURRENTLY NOT WORKING]
    public static void getTable(String table){

        ResultSet rs;

        try{

            switch(table){

                case "Person":

                    if(st.isClosed()){

                        System.out.println("Scanner is closed. Fix me (Person)");
                    }

                    rs = st.executeQuery("SELECT * FROM PERSON");

                    while(rs.next()){

                        System.out.println(rs.getInt(1) + "|" + rs.getString(2) + "|" +
                                rs.getString(3) + "|" + rs.getString(4));
                    }

                    break;

                case "Course":

                    if(st.isClosed()){

                        System.out.println("Scanner is closed. Fix me (Course)");
                    }

                    rs = st.executeQuery("SELECT * FROM COURSECT");

                    while(rs.next()){

                        System.out.println(rs.getInt(1) + "|" + rs.getString(2) + "|" +
                                rs.getString(3) + "|" + rs.getString(4) + "|" +
                                rs.getString(5));
                    }
                    break;

                case "Resource":

                    if(st.isClosed()){

                        System.out.println("Scanner is closed. Fix me (Resource)");
                    }

                    rs = st.executeQuery("SELECT * FROM RESOURCES");

                    while(rs.next()){

                        System.out.println(rs.getInt(1) + "|" + rs.getString(2) + "|" +
                                rs.getString(3) + "|" + rs.getString(4) + "|" +
                                rs.getInt(5) + "|" + rs.getInt(6) + "|" +
                                rs.getString(7));
                    }

                    break;

                case "Publisher":

                    if(st.isClosed()){

                        System.out.println("Scanner is closed. Fix me (Publisher)");
                    }

                    rs = st.executeQuery("SELECT * FROM PUBLISHERS");

                    while(rs.next()){

                        System.out.println(rs.getInt(1) + "|" + rs.getString(2) + "|" +
                                rs.getString(3) + "|" + rs.getString(4));
                    }

                    break;
                default:

                    System.out.println("Something very weird happened.");

                    break;
            }
        }catch(SQLException e){

            System.out.println("Something went wrong when trying to get the " + table + " table.");
        }
    }

    // Given a table and id, returns all that matches it. As of right now, there is repetition in the method, find
    // a way to integrate "getTable()" and "findByID()" together. [CURRENTLY NOT WORKING]
    public static String findByID(String table, int id){

        String returntable = "";
        ResultSet rs;

        try{

            switch(table){

                case "Person":

                    if(st.isClosed()){

                        System.out.println("Scanner is closed. Fix me (Person)");
                    }

                    rs = st.executeQuery(String.format("SELECT * FROM PERSON WHERE ID = %d", id));

                    while(rs.next()){

                        System.out.println(rs.getInt(1) + "|" + rs.getString(2) + "|" +
                                rs.getString(3) + "|" + rs.getString(4));
                    }

                    break;

                case "Course":

                    if(st.isClosed()){

                        System.out.println("Scanner is closed. Fix me (Course)");
                    }

                    rs = st.executeQuery(String.format("SELECT * FROM COURSECT WHERE ID = %d", id));

                    while(rs.next()){

                        System.out.println(rs.getInt(1) + "|" + rs.getString(2) + "|" +
                                rs.getString(3) + "|" + rs.getString(4) + "|" +
                                rs.getString(5));
                    }
                    break;

                case "Resource":

                    if(st.isClosed()){

                        System.out.println("Scanner is closed. Fix me (Resource)");
                    }

                    rs = st.executeQuery(String.format("SELECT * FROM RESOURCES WHERE ID = %d", id));

                    while(rs.next()){

                        System.out.println(rs.getInt(1) + "|" + rs.getString(2) + "|" +
                                rs.getString(3) + "|" + rs.getString(4) + "|" +
                                rs.getInt(5) + "|" + rs.getInt(6) + "|" +
                                rs.getString(7));
                    }

                    break;

                case "Publisher":

                    if(st.isClosed()){

                        System.out.println("Scanner is closed. Fix me (Publisher)");
                    }

                    rs = st.executeQuery(String.format("SELECT * FROM PUBLISHERS WHERE ID = %d", id));

                    while(rs.next()){

                        System.out.println(rs.getInt(1) + "|" + rs.getString(2) + "|" +
                                rs.getString(3) + "|" + rs.getString(4));
                    }

                    break;
                default:

                    System.out.println("Something very weird happened.");

                    break;
            }
        }catch(SQLException e){

            System.out.println("Something went wrong when trying to get the " + table + " table.");
        }

        return returntable;
    }

    // Used to execute a non return query. Meaning? Either an update, insert or delete.
    public static void executeNoReturnQuery(String nrq){

        try{

            st = conn.createStatement();

            st.executeQuery(nrq);

        }catch(SQLException e){

            System.out.println("Something went wrong with the statement. Fix me");
        }
    }

    //============================================Update Methods Queries================================================

    private String quPerson(Person person){

        return String.format("UPDATE PERSON SET FIRSTNAME = '%s', LASTNAME = '%s', TYPE = '%s' WHERE ID = %d",
                person.getFirstName(), person.getLastName(), person.getType(), person.getID());
    }
    private String quCourse(Course course){

        return String.format("UPDATE COURSECT SET TITLE = '%s', CNUMBER = '%s', DESCRIPTION = '%s', DEPARTMENT = '%s" +
                "WHERE ID = %d", course.getTitle(), course.getCRN(), course.getDescription(), course.getDepartment(),
                course.getID());

    }
    private String quResource(Resource resource){

        return String.format("UPDATE RESOURCES SET TYPE = '%s', TITLE = '%s', AUTHOR = '%s', ISBN = '%s, " +
                        "TOTAL_AMOUNT = %d, CURRENT_AMOUNT = %d, DESCRIPTION = '%s' WHERE ID = %d",
                resource.getType(), resource.getTitle(), resource.getAuthor(), resource.getISBN(),
                resource.getTotalAmount(), resource.getCurrentAmount(), resource.getDescription());

    }
    private String quPublisher(Publisher publisher){

        return String.format("UPDATE PUBLISHER SET TITLE = '%s', CONTACT_INFO = '%s', DESCRIPTION = '%s' WHERE ID = %d",
                publisher.getTitle(), publisher.getContactInformation(), publisher.getDescription(),
                publisher.getID());
    }

    //===========================================Insert Method Queries==================================================

        // Query Makers: Given an object, create a query to insert into the database.

    private String qiPerson(Person person) {

        return String.format("INSERT INTO PERSON (FIRSTNAME, LASTNAME, TYPE) VALUES ('%s', '%s', '%s')",
                person.getFirstName(), person.getLastName(), person.getType());
    }
    private String qiCourse(Course course){

        return String.format("INSERT INTO COURSECT (TITLE, CNUMBER, DESCRIPTION, DEPARTMENT) VALUES " +
                "('%s', '%s', '%s', '%s')", course.getTitle(), course.getCRN(), course.getDescription(),
                course.getDepartment());

    }
    private String qiResource(Resource resource){

        return String.format("INSERT INTO RESOURCES (TYPE, TITLE, AUTHOR, ISBN, TOTAL_AMOUNT, CURRENT_AMOUNT, " +
                        "DESCRIPTION) VALUES ('%s', '%s', '%s', '%s', %d, %d, '%s')",
                resource.getType(), resource.getTitle(), resource.getAuthor(), resource.getISBN(),
                resource.getTotalAmount(), resource.getCurrentAmount(), resource.getDescription());

    }
    private String qiPublisher(Publisher publisher){

        return String.format("INSERT INTO PUBLISHER (TITLE, CONTACT_INFO, DESCRIPTION) VALUES ('%s', '%s', '%s')",
                publisher.getTitle(), publisher.getContactInformation(), publisher.getDescription());

    }

    //==========================================Delete Method Queries===================================================
    // Deletes it by ID (getting it from the object passed into the method.

    private String qdPerson(Person person){

        return String.format("DELETE FROM PERSON WHERE ID = %d", person.getID());
    }
    private String qdPerson(Course course){

        return String.format("DELETE FROM COURSECT WHERE ID = %d", course.getID());
    }
    private String qdResource(Resource resource){

        return String.format("DELETE FROM RESOURCES WHERE ID = %d", resource.getID());
    }
    private String qdPublisher(Publisher publisher){

        return String.format("DELETE FROM PUBLISHERS WHERE ID = %d", publisher.getID());
    }

    //==========================================SELECT METHODS (EXECUTED)===============================================
    public static void getTablePerson(){

        try{

            Statement st = conn.createStatement();

            ResultSet rs = st.executeQuery("SELECT * FROM PERSON");

            while(rs.next()){

                System.out.println(rs.getInt(1) + "|" + rs.getString(2) + "|" +
                        rs.getString(3) + "|" + rs.getString(4));
            }

        }catch(SQLException e){

        }
    }
    public static void getTableCourses(){

        try{

            Statement st = conn.createStatement();

            ResultSet rs = st.executeQuery("SELECT * FROM COURSECT");

            while(rs.next()){

                System.out.println(rs.getInt(1) + "|" + rs.getString(2) + "|" +
                        rs.getString(3) + "|" + rs.getString(4) + "|" +
                        rs.getString(5));
            }

        }catch(SQLException e){

        }
    }
    public static void getTableResources(){

        try{

            Statement st = conn.createStatement();

            ResultSet rs = st.executeQuery("SELECT * FROM RESOURCES");

            while(rs.next()){

                System.out.println(rs.getInt(1) + "|" + rs.getString(2) + "|" +
                        rs.getString(3) + "|" + rs.getString(4) + "|" +
                        rs.getInt(5) + "|" + rs.getInt(6) + rs.getString(7));
            }

        }catch(SQLException e){

        }
    }
    public static void getTablePublishers(){

        try {

            Statement st = conn.createStatement();

            ResultSet rs = st.executeQuery("SELECT * FROM PUBLISHERS");

            while (rs.next()) {

                System.out.println(rs.getInt(1) + "|" + rs.getString(2) + "|" +
                        rs.getString(3) + "|" + rs.getString(4));

            }
        }catch(SQLException e){

        }
    }

    // Just prints a long line of equal signs
    public static void printSeparator(){

        System.out.println("=========================================================================================");
    }



}
