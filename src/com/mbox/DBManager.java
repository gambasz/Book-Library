package com.mbox;
import java.sql.*;
import java.util.*;
import com.mbox.Main;
import jdk.management.resource.ResourceContext;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import javax.xml.transform.Result;

public class DBManager {
    public static Statement st;
    public static Connection conn;

    public DBManager() {
        //Method is empty for now
    }

    public static void openConnection() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            conn = DriverManager.getConnection("jdbc:oracle:thin:USERNAME/PASSWORD@HOST:PORT:SID");

            System.out.println("Successfully connected to the database");
            st = conn.createStatement();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Connection to the database was NOT successful");

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

    // Used to execute a non return query. Meaning? Either an update, insert or delete.
    public static void executeNoReturnQuery(String nrq){

        try{

            st = conn.createStatement();

            st.executeQuery(nrq);

        }catch(SQLException e){

            System.out.println("Something went wrong with the statement. Fix me");
        }
    }

    //============================================== PRINT METHODS =====================================================

    //======================================SELECT METHODS(PRINT TO SCREEN)=============================================

    //============================================PRINTS WHOLE TABLE====================================================

    // Regular Tables
    public static void printTablePerson(){

        try{

            ResultSet rs = st.executeQuery(getTablePersonQuery());

            while(rs.next()){

                System.out.println(rs.getInt(1) + "|" + rs.getString(2) + "|" +
                        rs.getString(3) + "|" + rs.getString(4));
            }

        }catch(SQLException e){

            e.printStackTrace();
            System.out.println("ERROR: Failed to execute 'printTablePerson()' method.");

        }
    }
    public static void printTableCourses(){

        try{

            ResultSet rs = st.executeQuery(getTableCourseQuery());

            while(rs.next()){

                System.out.println(rs.getInt(1) + "|" + rs.getString(2) + "|" +
                        rs.getString(3) + "|" + rs.getString(4) + "|" +
                        rs.getString(5));
            }

        }catch(SQLException e){

            e.printStackTrace();
            System.out.println("ERROR: Failed to execute 'printTableCourses()' method.");
        }
    }
    public static void printTableResources(){

        try{

            Statement st = conn.createStatement();

            ResultSet rs = st.executeQuery(getTableResourceQuery());

            while(rs.next()){

                System.out.println(rs.getInt(1) + "|" + rs.getString(2) + "|" +
                        rs.getString(3) + "|" + rs.getString(4) + "|" +
                        rs.getInt(5) + "|" + rs.getInt(6) + rs.getString(7));
            }

        }catch(SQLException e){

            e.printStackTrace();
            System.out.println("ERROR: Failed to execute 'printTableResources()' method.");

        }
    }
    public static void printTablePublishers(){

        try {

            ResultSet rs = st.executeQuery(getTablePublisherQuery());

            while (rs.next()) {

                System.out.println(rs.getInt(1) + "|" + rs.getString(2) + "|" +
                        rs.getString(3) + "|" + rs.getString(4));

            }
        }catch(SQLException e){

            e.printStackTrace();
            System.out.println("ERROR: Failed to execute 'printTablePublishers()' method.");
        }
    }

    // Relationship Tables
    public static void printTableCoursePerson(){

        try{

            ResultSet rs = st.executeQuery(getTableCoursePersonQuery());
            while(rs.next()){

                System.out.println(rs.getInt(1) + "|" + rs.getInt(2));
            }

        }catch(SQLException e){

            e.printStackTrace();
            System.out.println("Exception when executing printTableCoursePerson() method.");
        }

    }
    public static void printTableCourseResource(){

        try{

            ResultSet rs = st.executeQuery(getTableCourseResourceQuery());
            while(rs.next()){

                System.out.println(rs.getInt(1) + "|" + rs.getInt(2));
            }

        }catch(SQLException e){

            e.printStackTrace();
            System.out.println("Exception when executing printTableCourseResource() method.");
        }
    }
    public static void printTableCourseSemester(){

        try{

            ResultSet rs = st.executeQuery(getTableCourseSemesterQuery());
            while(rs.next()){

                System.out.println(rs.getInt(1) + "|" + rs.getInt(2));
            }

        }catch(SQLException e){

            e.printStackTrace();
            System.out.println("Exception when executing printTableCourseSemester() method.");
        }

    }
    public static void printTablePersonResource(){

        try{

            ResultSet rs = st.executeQuery(getTablePersonResourceQuery());
            while(rs.next()){

                System.out.println(rs.getInt(1) + "|" + rs.getInt(2));
            }

        }catch(SQLException e){

            e.printStackTrace();
            System.out.println("Exception when executing printTablePersonResource() method.");
        }

    }
    public static void printTablePublisherResource(){

        try{

            ResultSet rs = st.executeQuery(getTablePublisherResourceQuery());
            while(rs.next()){

                System.out.println(rs.getInt(1) + "|" + rs.getInt(2));
            }

        }catch(SQLException e){

            e.printStackTrace();
            System.out.println("Exception when executing printTablePublisherResource() method.");
        }

    }

    //===========================================PRINTS MATCHING ID=====================================================

    public static void printPersonInTable(int id){

        try{

            ResultSet rs = st.executeQuery(getPersonInTableQuery(id));
            while(rs.next()){
                System.out.println(rs.getInt(1) + "|" + rs.getString(2) +
                        rs.getString(3) + rs.getString(4));
            }

        }catch(SQLException e){

            e.printStackTrace();
            System.out.println("ERROR: Exception when trying to execute printPersonInTable() method.");
        }


        // return String.format("SELECT * FROM PERSON WHERE ID = %d", id);
    }
    public static void printCourseInTable(int id){

        try{

            ResultSet rs = st.executeQuery(getCourseInTableQuery(id));

            while(rs.next()){
                System.out.println(rs.getInt(1) + "|" + rs.getString(2) +
                        rs.getString(3) + rs.getString(4) + "|" + rs.getString(5));
            }

        }catch(SQLException e){

            e.printStackTrace();
            System.out.println("ERROR: Exception when trying to execute printCourseInTable() method.");

        }
    }
    public static void printResourcesInTable(int id){

        try{

            ResultSet rs = st.executeQuery(getResourceInTableQuery(id));

            while(rs.next()){
                System.out.println(rs.getInt(1) + "|" + rs.getString(2) +
                        rs.getString(3) + rs.getString(4) + rs.getInt(5) +
                        rs.getInt(6) + rs.getString(7));
            }

        }catch(SQLException e){

            e.printStackTrace();
            System.out.println("ERROR: Exception when trying to execute printResourcesInTable() method.");
        }

    }
    public static void printPublisherInTable(int id){

        try{

            ResultSet rs = st.executeQuery(getPublisherInTableQuery(id));

            while(rs.next()){
                System.out.println(rs.getInt(1) + "|" + rs.getString(2) +
                        rs.getString(3) + rs.getString(4));
            }

        }catch(SQLException e){

            e.printStackTrace();
            System.out.println("ERROR: Exception when trying to execute printPublisherInTable() method.");
        }
    }

    //================================ Just prints a long line of equal signs===========================================
    public static void printSeparator(){

        System.out.println("=========================================================================================");
    }

    //================================================= QUERIES ========================================================

    //===========================================SELECT METHODS (QUERIES)===============================================

    //===========================================SELECT WHOLE TABLE QUERY===============================================

    //Regular Tables
    public static String getTablePersonQuery(){

        return "SELECT * FROM PERSON";
    }
    public static String getTableCourseQuery(){

        return "SELECT * FROM COURSECT";
    }
    public static String getTableResourceQuery(){

        return "SELECT * FROM RESOURCES";
    }
    public static String getTablePublisherQuery(){

        return "SELECT * FROM PUBLISHERS";
    }

    //Relationship Tables
    public static String getTableCoursePersonQuery(){

        return "SELECT * FROM RELATION_COURSE_PERSON";

    }
    public static String getTableCourseResourceQuery(){

        return "SELECT * FROM RELATION_COURSE_RESOURCES";
    }
    public static String getTableCourseSemesterQuery(){

        return "SELECT * FROM RELATION_SEMESTER_COURSE";
    }
    public static String getTablePersonResourceQuery(){

        return "SELECT * FROM RELATION_PERSON_RESOURCES";
    }
    public static String getTablePublisherResourceQuery(){

        return "SELECT * FROM RELATION_PUBLISHER_RESOURCE";
    }

    //==============================================SELECT BY ID QUERY==================================================
    public static String getPersonInTableQuery(int id){

        return String.format("SELECT * FROM PERSON WHERE ID=%d", id);
    }
    public static String getCourseInTableQuery(int id){

        return String.format("SELECT * FROM COURSECT WHERE ID=%d", id);
    }
    public static String getResourceInTableQuery(int id){

        return String.format("SELECT * FROM RESOURCES WHERE ID=%d", id);
    }
    public static String getPublisherInTableQuery(int id){

        return String.format("SELECT * FROM PUBLISHERS WHERE ID=%d", id);
    }

    //===========================================Insert Method Queries==================================================

    public String insertPersonQuery(Person person) {

        return String.format("INSERT INTO PERSON (FIRSTNAME, LASTNAME, TYPE) VALUES ('%s', '%s', '%s')",
                person.getFirstName(), person.getLastName(), person.getType());
    }
    public String insertCourseQuery(Course course){

        return String.format("INSERT INTO COURSECT (TITLE, CNUMBER, DESCRIPTION, DEPARTMENT) VALUES " +
                        "('%s', '%s', '%s', '%s')", course.getTitle(), course.getCRN(), course.getDescription(),
                course.getDepartment());

    }
    public String insertResourceQuery(Resource resource){

        return String.format("INSERT INTO RESOURCES (TYPE, TITLE, AUTHOR, ISBN, TOTAL_AMOUNT, CURRENT_AMOUNT, " +
                        "DESCRIPTION) VALUES ('%s', '%s', '%s', '%s', %d, %d, '%s')",
                resource.getType(), resource.getTitle(), resource.getAuthor(), resource.getISBN(),
                resource.getTotalAmount(), resource.getCurrentAmount(), resource.getDescription());

    }
    public String insertPublisherQuery(Publisher publisher){

        return String.format("INSERT INTO PUBLISHER (TITLE, CONTACT_INFO, DESCRIPTION) VALUES ('%s', '%s', '%s')",
                publisher.getTitle(), publisher.getContactInformation(), publisher.getDescription());

    }

    //============================================Update Methods Queries================================================

    private String updatePersonQuery(Person person){

        return String.format("UPDATE PERSON SET FIRSTNAME = '%s', LASTNAME = '%s', TYPE = '%s' WHERE ID = %d",
                person.getFirstName(), person.getLastName(), person.getType(), person.getID());
    }
    private String updateCourseQuery(Course course){

        return String.format("UPDATE COURSECT SET TITLE = '%s', CNUMBER = '%s', DESCRIPTION = '%s', DEPARTMENT = '%s" +
                        "WHERE ID = %d", course.getTitle(), course.getCRN(), course.getDescription(), course.getDepartment(),
                course.getID());

    }
    private String updateResourceQuery(Resource resource){

        return String.format("UPDATE RESOURCES SET TYPE = '%s', TITLE = '%s', AUTHOR = '%s', ISBN = '%s, " +
                        "TOTAL_AMOUNT = %d, CURRENT_AMOUNT = %d, DESCRIPTION = '%s' WHERE ID = %d",
                resource.getType(), resource.getTitle(), resource.getAuthor(), resource.getISBN(),
                resource.getTotalAmount(), resource.getCurrentAmount(), resource.getDescription());

    }
    private String updatePublisherQuery(Publisher publisher){

        return String.format("UPDATE PUBLISHER SET TITLE = '%s', CONTACT_INFO = '%s', DESCRIPTION = '%s' WHERE ID = %d",
                publisher.getTitle(), publisher.getContactInformation(), publisher.getDescription(),
                publisher.getID());
    }

    //==========================================Delete Method Queries===================================================

    private String deletePersonQuery(Person person){

        return String.format("DELETE FROM PERSON WHERE ID = %d", person.getID());
    }
    private String deleteCourseQuery(Course course){

        return String.format("DELETE FROM COURSECT WHERE ID = %d", course.getID());
    }
    private String deleteResourceQuery(Resource resource){

        return String.format("DELETE FROM RESOURCES WHERE ID = %d", resource.getID());
    }
    private String deletePublisherQuery(Publisher publisher){

        return String.format("DELETE FROM PUBLISHERS WHERE ID = %d", publisher.getID());
    }

    //============================================GUI METHODS===========================================================

    // 1st view:
    // Search by: Professor (name), Course(title), Resource(title), Semester-Year
    public static Person[] searchByProfessor(String name){

        int i = 0;

        try{

            Statement st = conn.createStatement();
            String query = String.format("SELECT * FROM PERSON WHERE FIRSTNAME='%s'", name);
            ResultSet rs = st.executeQuery(query);

            while(rs.next()){
                i++;
            }

            Person[] p = new Person[i];

            rs = st.executeQuery(query);
            i = 0;
            while(rs.next()){

                p[i] = new Person(rs.getInt(1), rs.getString(2), rs.getString(3),
                        rs.getString(4));
                i++;
                return p;
            }

        }catch(SQLException e){

        }

        return null;
    }
    public static Course[] searchByCourse(String title){
        int i = 0;

        try{

            Statement st = conn.createStatement();
            String query = String.format("SELECT * FROM COURSECT WHERE TITLE='%s'", title);
            ResultSet rs = st.executeQuery(query);

            while(rs.next()){
                i++;
            }

            Course[] c = new Course[i];

            rs = st.executeQuery(query);
            i = 0;
            while(rs.next()){

                c[i] = new Course(rs.getInt(1), rs.getString(2), rs.getString(3),
                        rs.getString(4), rs.getString(5));
                i++;
            }
            return c;
        }catch(SQLException e){
            System.out.println(e);
        }

        return null;
    }
    public static Resource[] searchByResource(String title){

        int i = 0;

        try{

            Statement st = conn.createStatement();
            String query = String.format("SELECT * FROM RESOURCES WHERE TITLE='%s'", title);
            ResultSet rs = st.executeQuery(query);

            while(rs.next()){
                i++;
            }

            Resource[] r = new Resource[i];

            rs = st.executeQuery(query);
            i = 0;
            while(rs.next()){

                r[i] = new Resource(rs.getInt(1), rs.getString(2), rs.getString(3),
                        rs.getString(4), rs.getString(5), rs.getInt(6),
                        rs.getInt(7), rs.getString(8));
                i++;
            }

            return r;

        }catch(SQLException e){

        }

        return null;
    }

    // Work in progress
    public static Course[] searchBySemester(String semester, String year){

        Course[] c = new Course[1];
        return c;

        /*

        //ID 49 for Semster -> Spring 2018
        int id[];
        int i = 0;
        Course[] c;

        try{

            String query = String.format("SELECT * FROM SEMESTER WHERE SEMESTER='%s', YEAR='%s'", semester, year);
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);

            while(rs.next()){

                i++;
            }

            id = new int[i];
            i = 0;

            while(rs.next()){

                id[i] = rs.getInt(1);
                i++;
            }


        }catch(SQLException e){

        }


        return c;
    }

    public static Course[] searchBySemester(String semester, String year){
        int i = 0;
        int j = 0;
        int[] arrayids;

        try{

            Statement st = conn.createStatement();
            String query = String.format("SELECT * FROM SEMESTER WHERE SEASON='%s', YEAR='%s'", semester, year);
            ResultSet rs = st.executeQuery(query);

            while(rs.next()){
                i++;
                j++;
            }

            arrayids = new int[j];

            Course[] c = new Course[i];

            rs = st.executeQuery(query);
            i = 0;
            while(rs.next()){

                c[i] = new Person(rs.getInt(1), rs.getString(2), rs.getString(3),
                        rs.getString(4));
                i++;
                return p;
            }

        }catch(SQLException e){

        }

        return null;
    }
    */
    }

    //================================All select methods returning object===============================================

    public static Person getPersonObject(int id){

        Person p = new Person();

        try{

            ResultSet rs = st.executeQuery(getPersonInTableQuery(id));

            while(rs.next()){

                p.setID(rs.getInt(1));
                p.setType(rs.getString(2));
                p.setFirstName(rs.getString(3));
                p.setLastName(rs.getString(4));
            }

        }catch(SQLException e){

            System.out.println("Something went wrong inside of the method getPersonObject()");

        }

        return p;

    }
    public static Course getCourseObject(int id){

        Course c = new Course();

        try{

            ResultSet rs = st.executeQuery(getCourseInTableQuery(id));

            while(rs.next()){

              c.setID(rs.getInt(1));
              c.setTitle(rs.getString(2));
              c.setCRN(rs.getString(3));
              c.setDescription(rs.getString(4));
              c.setDepartment(rs.getString(5));
            }

        }catch(SQLException e){

            System.out.println("Something went wrong inside of the method getPersonObject()");

        }

        return c;

    }
    public static Resource getResourceObject(int id){

        Resource r = new Resource();

        try{

            ResultSet rs = st.executeQuery(getPersonInTableQuery(id));

            while(rs.next()){

                r.setID(rs.getInt(1));
                r.setType(rs.getString(2));
                r.setTitle(rs.getString(3));
                r.setAuthor(rs.getString(4));
                r.setISBN(rs.getString(5));
                r.setTotalAmount(rs.getInt(6));
                r.setCurrentAmount(rs.getInt(7));
                r.setDescription(rs.getString(8));
            }

        }catch(SQLException e){

            System.out.println("Something went wrong inside of the method getPersonObject()");

        }

        return r;

    }
    public static Publisher getPublisherObject(int id){

        Publisher p = new Publisher();

        try{

            ResultSet rs = st.executeQuery(getPersonInTableQuery(id));

            while(rs.next()){

                p.setID(rs.getInt(1));
                p.setTitle(rs.getString(2));
                p.setContactInformation(rs.getString(3));
                p.setDescription(rs.getString(4));
            }

        }catch(SQLException e){

            System.out.println("Something went wrong inside of the method getPersonObject()");

        }

        return p;
    }

    //=====================================Methods for searching relationship tables====================================

    public static Person getPersonRelationTable(int id){

        Person p = new Person();

        try{

            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(String.format("SELECT * FROM RELATION_COURSE_PERSON WHERE COURSEID=%d", id));

            while(rs.next()){

                p.setID(rs.getInt(2));
            }


            return p;


        }catch(SQLException e){

            System.out.println("Something went wrong.");

        }

        return null;

    }
    public static Resource[] getResourcesRelationTable(int id){

        Resource r[];
        try{

            st = conn.createStatement();
            ResultSet rs = st.executeQuery(String.format("SELECT * FROM RELATION_COURSE_RESOURCES WHERE COURSEID=%d",id));


            int i = 0;
            while(rs.next()){

                i++;
            }

            r = new Resource[i];

            int j = 0;

            ResultSet sr = st.executeQuery(String.format("SELECT * FROM RELATION_COURSE_RESOURCES WHERE COURSEID=%d",id));

            while(rs.next()){

                r[j].setID(sr.getInt(2));
            }

            return r;


        } catch(SQLException e){

            System.out.println("Something went wrong");
        }

        return null;

    }

    //==================================================================================================================
    //                                                  Relational tables methods
    //==================================================================================================================

    public static void relationalReadByCourseID(int courseID) {
        try {
            Scanner scan = new Scanner(System.in);

            int personID = 0;
            int[] cr = new int[20];
            int[] pr = new int[20];
            //get personID
            ResultSet rs;

            rs = st.executeQuery("SELECT * FROM RELATION_COURSE_PERSON WHERE COURSEID = " + courseID);
            //it supposed to get a list of all persons teaching that course. Assuming one person for now.
            while (rs.next()) {
                personID = rs.getInt(2);
            }

            //get resourceID
            rs = st.executeQuery("SELECT * FROM RELATION_COURSE_RESOURCES WHERE COURSEID = " + courseID);
            int i = 0;
            while (rs.next()) {
                cr[i] = rs.getInt(2);
                i++;
            }

            //from person get resource
            rs = st.executeQuery("SELECT * FROM RELATION_PERSON_RESOURCES WHERE PERSONID = " + personID);
            int a = 0;
            while (rs.next()) {
                pr[a] = rs.getInt(2);
                a++;
            }

            int[] comm = new int[20];
            for(int k=0;i<cr.length;i++){
                for(int j=0;j<pr.length;j++){
                    if(cr[i]==pr[j]){
                        comm[k] = cr[k];
                    }
                }
            }

            String fName = "", lName = "", pType = "", cTitle = "", cDescription="", cDepartment="";
            int pID=0, cID = 0;
            String resource = "";

            rs = st.executeQuery(getPersonInTableQuery(personID));
            while(rs.next()) {
                pID = rs.getInt(1);
                fName = rs.getString(3);
                lName = rs.getString(4);
                pType = rs.getString(2);
            }

            rs = st.executeQuery(getCourseInTableQuery(courseID));
            while(rs.next()) {
                cTitle = rs.getString(2) + rs.getString(3);
                cID = rs.getInt(1);
                cDescription = rs.getString(4);
                cDepartment = rs.getString(5);

            }

            rs = st.executeQuery(getResourceInTableQuery(comm[0]));
            Resource rInst = new Resource(1,"s","s","s","s",1,2,"s");
            while(rs.next()) {
                // ID, Type, Title, Author, ISBN, total, current, desc
                // initilizing the object
                   rInst = new Resource(rs.getInt(1), rs.getString(2),
                        rs.getString(3), rs.getString(4), rs.getString(5),
                        rs.getInt(6), rs.getInt(7), rs.getString(8));
            }

            Person pInst = new Person(pID, fName, lName, pType);
            Course cInst = new Course(cID, cTitle, cDescription, cDepartment, "0");

            System.out.println("Name :" + pInst.getFirstName() + " " + pInst.getLastName() );
            System.out.println("Course :" + cInst.getTitle());
            System.out.println("Resource :" + rInst.getTitle());
        }catch(Exception e){
            System.out.println("Error");
        }
    }

    public static void relationalInsertByID(){

        Scanner scan = new Scanner(System.in);
        //DBManager DB = new DBManager();
        System.out.println("Format: <CourseID> <PersonID> <ResourceID> <PublisherID> <SemesterID>");
        System.out.println("Enter <exit> to exit.");

        while(true) {
            System.out.println("Enter PersonID, CourseID, ResourceID, PublisherID: ");
            String input = scan.nextLine();
            if(!input.contains("exit")){
                String[] values = input.split(" ");

                executeNoReturnQuery(String.format("INSERT INTO RELATION_COURSE_PERSON" +
                        " (COURSEID, PERSONID) VALUES ('%d', '%d')",Integer.parseInt(values[0]),Integer.parseInt(values[1])));
                executeNoReturnQuery(String.format("INSERT INTO RELATION_COURSE_RESOURCES" +
                        " (COURSEID, RESOURCEID) VALUES ('%d', '%d')",Integer.parseInt(values[0]),Integer.parseInt(values[2])));
                executeNoReturnQuery(String.format("INSERT INTO RELATION_SEMESTER_COURSE" +
                        " (COURSEID, SEMESTERID) VALUES ('%d', '%d')",Integer.parseInt(values[0]),Integer.parseInt(values[4])));
                executeNoReturnQuery(String.format("INSERT INTO RELATION_PERSON_RESOURCES" +
                        " (PERSONID, RESOURCEID) VALUES ('%d', '%d')",Integer.parseInt(values[1]),Integer.parseInt(values[2])));
                executeNoReturnQuery(String.format("INSERT INTO RELATION_PUBLISHER_RESOURCE" +
                        " (PUBLISHERID, RESOURCEID) VALUES ('%d', '%d')",Integer.parseInt(values[3]),Integer.parseInt(values[2])));
                System.out.println("Added ID");
            } else { break;}
        }
    }

    //==================================================================================================================
    //                                                  Next
    //==================================================================================================================





}






