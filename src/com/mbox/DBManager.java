package com.mbox;
import java.sql.*;
import java.util.*;
import com.mbox.Main;
import com.sun.istack.internal.Nullable;
import jdk.management.resource.ResourceContext;
import jdk.management.resource.ResourceId;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import java.io.*;
import javax.xml.transform.Result;

public class DBManager {
    public static Statement st;
    public static Statement stt;
    public static Statement st3;
    public static Statement st4;
    public static Connection conn;
    public DBManager() {
        //Method is empty for now
    }

    public static String readFromFile(){
        // The name of the file to open.
        String fileName = "DBinformation.txt";

        // This will reference one line at a time
        String line = null;

        try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader =
                    new FileReader(fileName);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader =
                    new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) {
                return line;
            }

            // Always close files.
            bufferedReader.close();
        }
        catch(FileNotFoundException ex) {
            System.out.println(
                    "Unable to open file '" +
                            fileName + "'");
        }
        catch(IOException ex) {
            System.out.println(
                    "Error reading file '"
                            + fileName + "'");
            // Or we could just do this:
            // ex.printStackTrace();
        }
        return null;
    }




    public static void openConnection() {
        try {
            String url = readFromFile();
            Class.forName("oracle.jdbc.driver.OracleDriver");
            conn = DriverManager.getConnection(url);


            System.out.println("Successfully connected to the database");
            st = conn.createStatement();
            stt=conn.createStatement();
            st3=conn.createStatement();
            st4=conn.createStatement();

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



    public static int getSemesterIDByName(String season, String year){

        int id = 0;
        season = season.toLowerCase();
        season = season.substring(0, 1).toUpperCase() + season.substring(1);
        try{
            Statement st = conn.createStatement();
            String query = String.format("SELECT * FROM SEMESTER WHERE SEASON='%s' AND  YEAR='%s'", season, year);
            ResultSet rs = st.executeQuery(query);

            while(rs.next()){
                id = rs.getInt(1);
            }

            return id;

        }catch(SQLException e){

        }

        return 0;

    }


    public static String[] getSemesterNameByID(int id){

        try{
            Statement st = conn.createStatement();
            String query = String.format("SELECT * FROM SEMESTER WHERE ID='%s'", id);
            ResultSet rs = st.executeQuery(query);
            String semester[]= new String[2];
            semester[0]= "FALL"; semester[1] = "2018";

            while(rs.next()){
                semester[0] = rs.getString(2);
                semester[1] = rs.getString(3);
            }

            return semester;

        }catch(SQLException e){

        }

        return null;

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

    public static ArrayList<Integer> searchGetCoursesIdsByProfessorName(String name){

        ArrayList<Integer> arr = new ArrayList<>();

        try{

            Statement st = conn.createStatement();
            String query = String.format("SELECT * FROM PERSON WHERE FIRSTNAME='%s' OR LASTNAME='s'", name, name);

            ResultSet rs = st.executeQuery(query);

            ArrayList<Integer> professorids = new ArrayList<>();

            while(rs.next()){

                professorids.add(rs.getInt(1));
            }

            for(int i = 0; i < professorids.size(); i++){

                rs = st.executeQuery(String.format("SELECT * FROM RELATION_COURSE_PERSON WHERE PERSONID = %d",
                        professorids.get(i)));

                while(rs.next()){

                    arr.add(rs.getInt(2));
                }
            }

            return arr;

        }catch(SQLException e){

            System.out.println("Something went wrong");

        }

        return null;
    }
    public static ArrayList<frontend.data.Course> getCourseArrayByIDs(ArrayList<Integer> timmy){

        ArrayList<frontend.data.Course> c = new ArrayList<>();
        frontend.data.Course course;

        try{

            Statement st = conn.createStatement();
            ResultSet rs;

            for (int i = 0; i < timmy.size(); i++){

                rs = st.executeQuery(String.format("SELECT * FROM COURSECT WHERE ID=%d", timmy.get(i)));

                while(rs.next()){

                    course = new frontend.data.Course(rs.getInt(1), rs.getString(2),
                            rs.getString(5), rs.getString(4));

                    c.add(course);
                }
            }

            return c;

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


    public static Person setResourcesForPerson(Person person1){
        ResultSet rss;
        int resourceID;
        int i = 0;

        Resource[] resourcesList = new Resource[20];

        try {
            rss = stt.executeQuery("SELECT * FROM RELATION_PERSON_RESOURCES WHERE PERSONID = " + person1.getID());
            while (rss.next()) {
                //there will be a list of all reousrces ID that is owned by Person
                resourceID = rss.getInt(2);

                rss = st3.executeQuery(getResourceInTableQuery(resourceID));
                while(rss.next()) {
                    // ID, Type, Title, Author, ISBN, total, current, desc
                    resourcesList[i] = new Resource(rss.getInt(1), rss.getString(2),
                            rss.getString(3), rss.getString(4), rss.getString(5),
                            rss.getInt(6), rss.getInt(7), rss.getString(8));
                    setPublisherForResource(resourcesList[i]);

                    i++;
                }
            }
            person1.setResourcesPerson(resourcesList);
            return person1;
        }
        catch (SQLException err){
            err.printStackTrace();
        }
        // Adding the list of the resources to the person object
        return null;

    }



    public static Resource[] findResourcesCourse(int courseID)
    {

        ResultSet rs;
        int resourceID=0;
        int i = 0;
        Resource[] resourcesList = new Resource[20];


        try {

        rs = st.executeQuery("SELECT * FROM RELATION_COURSE_RESOURCES WHERE COURSEID = " + courseID);

        while (rs.next()) {
            resourceID = rs.getInt(2);
            rs = st.executeQuery(getResourceInTableQuery(resourceID));
            //System.out.println("lol");

            while(rs.next()) {
                System.out.println("");
                // ID, Type, Title, Author, ISBN, total, current, desc
                resourcesList[i] = new Resource(rs.getInt(1), rs.getString(2),
                        rs.getString(3), rs.getString(4), rs.getString(5),
                        rs.getInt(6), rs.getInt(7), rs.getString(8));
                setPublisherForResource(resourcesList[i]);
                i++;
            }

        }

            return resourcesList;
        }
        catch (SQLException err){
            System.out.println(err);
        }
        // Adding the list of the resources to the person object
        return null;

    }

    public static Resource setPublisherForResource(Resource resource1){
        ResultSet rss;
        ResultSet rs2;
        int publisherID = 0, i = 0;

        Publisher publisherInstance = new Publisher();

        //Resource[] resourcesList = new Resource[20];

        try {
            Statement stpublisher1 = conn.createStatement();
            Statement stpublisher2 = conn.createStatement();

            rs2 = stpublisher1.executeQuery("SELECT * FROM RELATION_PUBLISHER_RESOURCE WHERE RESOURCEID = " +
                    resource1.getID());

            while (rs2.next()) {
                //there will be a list of all reousrces ID that is owned by Person
                publisherID = rs2.getInt(1);

                rss = stpublisher2.executeQuery(getPublisherInTableQuery(publisherID));
                while(rss.next()) {
                    // ID, Type, Title, Author, ISBN, total, current, desc
                    publisherInstance = new Publisher(rss.getInt(1), rss.getString(2),
                            rss.getString(3), rss.getString(4));
                    i++;
                }
            }
            resource1.setPublisherInstance(publisherInstance);
            return resource1;
        }
        catch (SQLException err){
            err.printStackTrace();
        }
        // Adding the list of the resources to the person object
        return null;

    }




    public static ArrayList<Course> relationalReadByCourseID(int courseID) {
        // This method is only accept ONE courseID and will find all relations to that course
        //So you may need to call the function N times with different courseID to get all information stored in table
        Course[] courseArray = new Course[20]; //Will make it a dynamic array list
        ArrayList<Course>  courseList = new ArrayList<>();

        int personID = 0, i=0, pID=0, cID = 0;
        int[] pr = new int[20], cr = new int[20];
        ResultSet rs;
        String cTitle = "", cDescription="", cDepartment="";

        Person personTmp = new Person();
        Resource[] courseResources = new Resource[20];


        try {

            //=======================Getting information to create the course object====================================

            rs = st.executeQuery(getCourseInTableQuery(courseID));

            while(rs.next()) {

                cTitle = rs.getString(2) + rs.getString(3);
                cID = rs.getInt(1);
                cDescription = rs.getString(4);
                cDepartment = rs.getString(5);
                System.out.println("courseID "+courseID);
            }
            courseResources = findResourcesCourse(courseID);


            //=======================Finding and creating Persons list teaching that course=============================
            i = 0;
            rs = st.executeQuery("SELECT * FROM RELATION_COURSE_PERSON WHERE COURSEID = " + courseID);
            //it supposed to get a list of all persons teaching that course. Assuming one person for now.
            while (rs.next()) {
                courseArray[i] = new Course(cID, cTitle, cDepartment, cDescription, "CRN");
                courseList.add(new Course(cID, cTitle, cDepartment, cDescription, "CRN"));

                personID = rs.getInt(2);
                rs = st.executeQuery(getPersonInTableQuery(personID));
                System.out.println("PersonID is: "+personID);
                while(rs.next()) {
                    personTmp = new Person(personID, rs.getString(3), rs.getString(4),
                            rs.getString(2));

                    personTmp = setResourcesForPerson(personTmp);

                    courseArray[i].setPersonInstance(personTmp);
                    courseArray[i].setResourceInstance(courseResources);
                    courseList.get(i).setPersonInstance(personTmp);
                    courseList.get(i).setResourceInstance(courseResources);

                    i++;
                }


            }

            //=======================Just printing out data=============================================================
//            System.out.println("Name :" + courseArray[0].getPersonInstance().getFirstName() + " " +
//                    courseArray[0].getPersonInstance().getLastName() );
//            System.out.println("Course :" + courseArray[0].getTitle());
//            System.out.println("Resource :" + courseArray[0].getResourceInstance()[0].getTitle());

            return courseList;

        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }


    //==================================================================================================================
    //==================================================================================================================

    public static void relationalInsertByID(){

        Scanner scan = new Scanner(System.in);
        //DBManager DB = new DBManager();
        System.out.println("Format: <CourseID> <PersonID> <ResourceID> <PublisherID> <SemesterID>");
        System.out.println("Enter <exit> to exit.");

        while(true) {
            System.out.println("Enter  CourseID, PersonID, ResourceID, PublisherID, SemesterID: ");
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

    public static void relationalInsertByID2(frontend.data.Course c){

        //getting all the data
        int id = c.getID();
        int crn = c.getCRN();
        String year = String.valueOf(c.getYEAR());
        String semester = c.getSEMESTER();
        String title = c.getTitle();
        String dept = c.getDepartment();
        String desc = c.getDescription();
        int personid = c.getProfessor().getID();
        int semesterid = getSemesterIDByName(semester, year);

        System.out.println(personid);
        System.out.println(semesterid);

        // Fall 2018 ID = 52
//        int semesterid = 57;
        ArrayList<frontend.data.Resource> r = c.getResource();

        int[] resourceidlist = new int[r.size()];

        for(int i = 0; i < c.getResource().size(); i++){

            resourceidlist[i] = r.get(i).getID();
        }

        executeNoReturnQuery(String.format("INSERT INTO RELATION_COURSE_PERSON" +
                " (COURSEID, PERSONID) VALUES ('%d', '%d')",id ,personid));

        for(int j = 0; j < resourceidlist.length; j++){

            executeNoReturnQuery(String.format("INSERT INTO RELATION_COURSE_RESOURCES" +
                    " (COURSEID, RESOURCEID) VALUES ('%d', '%d')",id, resourceidlist[j]));
        }

        executeNoReturnQuery(String.format("INSERT INTO RELATION_SEMESTER_COURSE" +
                " (COURSEID, SEMESTERID) VALUES ('%d', '%d')",id, semesterid));

        for(int k = 0; k < resourceidlist.length; k++){

            executeNoReturnQuery(String.format("INSERT INTO RELATION_PERSON_RESOURCES" +
                    " (PERSONID, RESOURCEID) VALUES ('%d', '%d')",personid,resourceidlist[k]));
        }

        for(int l = 0; l < resourceidlist.length; l++){

            executeNoReturnQuery(String.format("INSERT INTO RELATION_PUBLISHER_RESOURCE" +
                    " (PUBLISHERID, RESOURCEID) VALUES ('%d', '%d')", r.get(l).getPublisher().getID(),
                    resourceidlist[l]));

        }


    }

    //==================================================================================================================
    //                                                  Next
    //==================================================================================================================

    //method without user's input
    public static void relationalInsertByID(int courseID, int personID, int resourceID, int publisherID, int semesterID){


        executeNoReturnQuery(String.format("INSERT INTO RELATION_COURSE_PERSON" +
                " (COURSEID, PERSONID) VALUES ('%d', '%d')",courseID,personID));
        executeNoReturnQuery(String.format("INSERT INTO RELATION_COURSE_RESOURCES" +
                " (COURSEID, RESOURCEID) VALUES ('%d', '%d')",courseID,resourceID));
        executeNoReturnQuery(String.format("INSERT INTO RELATION_SEMESTER_COURSE" +
                " (COURSEID, SEMESTERID) VALUES ('%d', '%d')",courseID,semesterID));
        executeNoReturnQuery(String.format("INSERT INTO RELATION_PERSON_RESOURCES" +
                " (PERSONID, RESOURCEID) VALUES ('%d', '%d')",personID,resourceID));
        executeNoReturnQuery(String.format("INSERT INTO RELATION_PUBLISHER_RESOURCE" +
                " (PUBLISHERID, RESOURCEID) VALUES ('%d', '%d')",publisherID,resourceID));
        System.out.println("Added ID");

    }


    public static ArrayList<Integer> getCourseIdsBySemesterID(int id){

        int i = 0;
        String query = String.format("SELECT * FROM RELATION_SEMESTER_COURSE WHERE SEMESTERID=%d", id);
        ArrayList<Integer> idsList= new ArrayList<Integer>();

        try{

            Statement st = DBManager.conn.createStatement();
            ResultSet rs = st.executeQuery(query);

            while(rs.next()){
                i++;
            }


            rs = st.executeQuery(query);
            i = 0;
            while(rs.next()){
                idsList.add(rs.getInt(1));
                i++;
            }

            return idsList;


        }catch(SQLException e){

            System.out.println("Something went wrong");

        }

        return null;
    }


    public static ArrayList<frontend.data.Course> returnEverything(int semesterid) {
        String[] semester = getSemesterNameByID(semesterid);
        semester[0] = semester[0].toUpperCase();

        ArrayList<Integer> arr = getCourseIdsBySemesterID(semesterid);

        ArrayList<frontend.data.Course> hugeshit2 = new ArrayList<>();

        for(int i = 0; i < arr.size(); i++){

            ArrayList<Course> tmpCourse = DBManager.relationalReadByCourseID(arr.get(i));

            for(int j = 0; j < tmpCourse.size(); j++) {

                hugeshit2.add(tmpCourse.get(j).initCourseGUI(semester[0],semester[1]));

            }
        }

        return hugeshit2;
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


    // =======The methods to get the array of objects========================
    public static ArrayList<Person> getPersonFromTable(){
        DBManager DB = new DBManager();
        ArrayList<Person> arr = new ArrayList<>();
        try {


            String query = String.format("SELECT * FROM PERSON");
            ResultSet rs = DB.st.executeQuery(query);

            while (rs.next()) {
                Person p = new Person(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4));
                arr.add(p);
            }
            return arr;
        }catch(Exception e){
            System.out.println("DATA not found");
        }

        return  null;
    }

    public static ArrayList<Course> getCourseFromTable(){
        DBManager DB = new DBManager();
        ArrayList<Course> arr = new ArrayList<>();
        try {


            String query = String.format("SELECT * FROM COURSECT");
            ResultSet rs = DB.st.executeQuery(query);

            while (rs.next()) {
                Course p = new Course(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4)
                        ,rs.getString(5));
                arr.add(p);
            }
            return arr;
        }catch(Exception e){
            System.out.println("DATA not found");
        }

        return  null;
    }

    public static ArrayList<Resource> getResourceFromTable(){
        DBManager DB = new DBManager();
        ArrayList<Resource> arr = new ArrayList<>();
        try {


            String query = String.format("SELECT * FROM RESOURCES");
            ResultSet rs = DB.st.executeQuery(query);

            while (rs.next()) {
                Resource p = new Resource(rs.getInt(1),rs.getString(2),rs.getString(3),
                        rs.getString(4),rs.getString(5),
                        Integer.parseInt(rs.getString(6)),Integer.parseInt(rs.getString(7)),rs.getString(8));
                arr.add(p);
            }
            return arr;
        }catch(Exception e){
            System.out.println("DATA not found");
        }

        return  null;
    }

    public static ArrayList<Publisher> getPublisherFromTable(){
        DBManager DB = new DBManager();
        ArrayList<Publisher> arr = new ArrayList<>();
        try {


            String query = String.format("SELECT * FROM PUBLISHERS");
            ResultSet rs = DB.st.executeQuery(query);

            while (rs.next()) {
                Publisher p = new Publisher(rs.getString(1),rs.getString(2),rs.getString(3));
                arr.add(p);
            }
            return arr;
        }catch(Exception e){
            System.out.println("DATA not found");
        }

        return  null;
    }
    //======================================================================================

    public static ArrayList<frontend.data.Course> convertArrayCC(ArrayList<com.mbox.Course> c){
        ArrayList<frontend.data.Course> arr = new ArrayList<>();
        for(int i=0;i<c.size();i++){
            arr.add(c.get(i).initCourseGUI());
        }
        return arr;
    }

    public static ArrayList<frontend.data.Course> getCourseListByName(String fname, String lname){
        ArrayList<frontend.data.Course> arr = new ArrayList<>();
        int[] courseIDArr = new int[10];
        Person person;
        int personID=0;
        String personType="";
        int resourceID=0;
        String cDescription="",cDepartment="",cTitle="";
        Resource[] courseResources = new Resource[20];
        int cID;
        try {
            String query = String.format("SELECT * FROM PERSON WHERE FIRSTNAME = '&s' and LASTNAME = '&s'", fname, lname);
            Statement st = DBManager.conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while(rs.next()){
                personID = rs.getInt(1);
                personType = rs.getString(2);
            }
            person = new Person(personID,fname,lname,personType);
            rs = st.executeQuery(String.format("SELECT * FROM RELATION_COURSE_PERSON"));
            int i=0;
            while(rs.next()){
                courseIDArr[i] = rs.getInt(1);
                i++;
            }

        person = new Person(personID,fname, lname,personType);

            for(int a=0;a<courseIDArr.length;a++){

                rs = st.executeQuery(getCourseInTableQuery(courseIDArr[i]));

                while(rs.next()) {

                    cTitle = rs.getString(2) + rs.getString(3);
                    cID = rs.getInt(1);
                    cDescription = rs.getString(4);
                    cDepartment = rs.getString(5);
                    System.out.println("courseID "+courseIDArr[i]);
                }
                courseResources = findResourcesCourse(courseIDArr[i]);
                frontend.data.Course tempCourse = new frontend.data.Course(courseIDArr[i],cTitle,cDepartment,cDescription);
                for(int b=0;b<courseResources.length;b++) {
                    tempCourse.addResource(courseResources[b].initResourceGUI());
                }
                tempCourse.setProfessor(person.initPersonGUI());

                arr.add(tempCourse);
            }
            return arr;
        }catch(Exception e){
            System.out.println("wtf");
        }
        return arr;
    }
}






