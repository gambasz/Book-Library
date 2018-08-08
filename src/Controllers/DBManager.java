package Controllers;

import Models.backend.*;
import Models.frontend.PersonType;
import javafx.scene.control.ComboBox;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class DBManager {
    public static Statement st;
    public static Statement stt;
    public static Statement st3;
    public static Statement st4;
    public static Statement st5;

    public static Connection conn;

    public DBManager() {
        //Method is empty for now
    }

    public static String readFromFile() {
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

            while ((line = bufferedReader.readLine()) != null) {
                return line;
            }

            // Always close files.
            bufferedReader.close();
        } catch (FileNotFoundException ex) {
            System.out.println(
                    "Unable to open file '" +
                            fileName + "'");
        } catch (IOException ex) {
            System.out.println(
                    "Error reading file '"
                            + fileName + "'");
            // Or we could just do this:
            // ex.printStackTrace();
        }
        return null;
    }


    public static void openConnection() throws SQLException, ClassNotFoundException {
            String url = readFromFile();
            Class.forName("oracle.jdbc.driver.OracleDriver");
            conn = DriverManager.getConnection(url);


            System.out.println("Successfully connected to the database");
            st = conn.createStatement();
            stt = conn.createStatement();
            st3 = conn.createStatement();
            st4 = conn.createStatement();
            st5 = conn.createStatement();


    }

    public static void closeConnection() {
        try {
            if(conn !=null)
            if (conn.isClosed()) {

                System.out.println("The connection has already been closed");
            }
            else {
                conn.close();
                System.out.println("The connection to the database has been successfully terminated.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Used to execute a non return query. Meaning? Either an update, insert or delete.
    public static void executeNoReturnQuery(String nrq) {

        try {

            st = conn.createStatement();

            st.executeQuery(nrq);

        } catch (SQLException e) {

            System.out.println("Something went wrong with the statement. Fix me");
        }
    }


    public static int getSemesterIDByName(String season, String year) {

        int id = 0;
        season = season.toLowerCase();
        season = season.substring(0, 1).toUpperCase() + season.substring(1);
        try {
            Statement st = conn.createStatement();
            String query = String.format("SELECT * FROM SEMESTER WHERE SEASON='%s' AND  YEAR='%s'", season, year);
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                id = rs.getInt(1);
            }

            return id;

        } catch (SQLException e) {

        }

        return 0;

    }


    public static Semester getSemesterNameByID(int id) {

        try {
            Statement st = conn.createStatement();
            String query = String.format("SELECT * FROM SEMESTER WHERE ID='%s'", id);
            ResultSet rs = st.executeQuery(query);
            Semester semester = new Semester();

            if (rs.next()) {
                semester.setYear( rs.getInt("YEAR"));
                String tempSeason = rs.getString("SEASON");
                tempSeason = tempSeason.toUpperCase();
                if(tempSeason == "SUMMER 1" || tempSeason == "SUMMER 2")
                    tempSeason.replace(' ', '_');
                semester.setSeason(tempSeason);

            }
            semester.setId(id);
            return semester;

        } catch (SQLException e) {

        }

        return null;

    }
    //============================================== PRINT METHODS =====================================================

    //======================================SELECT METHODS(PRINT TO SCREEN)=============================================

    //============================================PRINTS WHOLE TABLE====================================================

    // Regular Tables
    public static void printTablePerson() {

        try {

            ResultSet rs = st.executeQuery(getTablePersonQuery());

            while (rs.next()) {

                System.out.println(rs.getInt(1) + "|" + rs.getString(2) + "|" +
                        rs.getString(3) + "|" + rs.getString(4));
            }

        } catch (SQLException e) {

            e.printStackTrace();
            System.out.println("ERROR: Failed to execute 'printTablePerson()' method.");

        }
    }

    public static void printTableCourses() {

        try {

            ResultSet rs = st.executeQuery(getTableCourseQuery());

            while (rs.next()) {

                System.out.println(rs.getInt(1) + "|" + rs.getString(2) + "|" +
                        rs.getString(3) + "|" + rs.getString(4) + "|" +
                        rs.getString(5));
            }

        } catch (SQLException e) {

            e.printStackTrace();
            System.out.println("ERROR: Failed to execute 'printTableCourses()' method.");
        }
    }

    public static void printTableResources() {

        try {

            Statement st = conn.createStatement();

            ResultSet rs = st.executeQuery(getTableResourceQuery());

            while (rs.next()) {

                System.out.println(rs.getInt(1) + "|" + rs.getString(2) + "|" +
                        rs.getString(3) + "|" + rs.getString(4) + "|" +
                        rs.getInt(5) + "|" + rs.getInt(6) + rs.getString(7));
            }

        } catch (SQLException e) {

            e.printStackTrace();
            System.out.println("ERROR: Failed to execute 'printTableResources()' method.");

        }
    }

    public static void printTablePublishers() {

        try {

            ResultSet rs = st.executeQuery(getTablePublisherQuery());

            while (rs.next()) {

                System.out.println(rs.getInt(1) + "|" + rs.getString(2) + "|" +
                        rs.getString(3) + "|" + rs.getString(4));

            }
        } catch (SQLException e) {

            e.printStackTrace();
            System.out.println("ERROR: Failed to execute 'printTablePublishers()' method.");
        }
    }

    // Relationship Tables
    public static void printTableCoursePerson() {

        try {

            ResultSet rs = st.executeQuery(getTableCoursePersonQuery());
            while (rs.next()) {

                System.out.println(rs.getInt(1) + "|" + rs.getInt(2));
            }

        } catch (SQLException e) {

            e.printStackTrace();
            System.out.println("Exception when executing printTableCoursePerson() method.");
        }

    }

    public static void printTableCourseResource() {

        try {

            ResultSet rs = st.executeQuery(getTableCourseResourceQuery());
            while (rs.next()) {

                System.out.println(rs.getInt(1) + "|" + rs.getInt(2));
            }

        } catch (SQLException e) {

            e.printStackTrace();
            System.out.println("Exception when executing printTableCourseResource() method.");
        }
    }

    public static void printTableCourseSemester() {

        try {

            ResultSet rs = st.executeQuery(getTableCourseSemesterQuery());
            while (rs.next()) {

                System.out.println(rs.getInt(1) + "|" + rs.getInt(2) + "|" + rs.getInt(3));
            }

        } catch (SQLException e) {

            e.printStackTrace();
            System.out.println("Exception when executing printTableCourseSemester() method.");
        }

    }

    public static void printTablePersonResource() {

        try {

            ResultSet rs = st.executeQuery(getTablePersonResourceQuery());
            while (rs.next()) {

                System.out.println(rs.getInt(1) + "|" + rs.getInt(2));
            }

        } catch (SQLException e) {

            e.printStackTrace();
            System.out.println("Exception when executing printTablePersonResource() method.");
        }

    }

    public static void printTablePublisherResource() {

        try {

            ResultSet rs = st.executeQuery(getTablePublisherResourceQuery());
            while (rs.next()) {

                System.out.println(rs.getInt(1) + "|" + rs.getInt(2));
            }

        } catch (SQLException e) {

            e.printStackTrace();
            System.out.println("Exception when executing printTablePublisherResource() method.");
        }

    }

    //===========================================PRINTS MATCHING ID=====================================================

    public static void printPersonInTable(int id) {

        try {

            ResultSet rs = st.executeQuery(getPersonInTableQuery(id));
            while (rs.next()) {
                System.out.println(rs.getInt(1) + "|" + rs.getString(2) +
                        rs.getString(3) + rs.getString(4));
            }

        } catch (SQLException e) {

            e.printStackTrace();
            System.out.println("ERROR: Exception when trying to execute printPersonInTable() method.");
        }


        // return String.format("SELECT * FROM PERSON WHERE ID = %d", id);
    }

    public static void printCourseInTable(int id) {

        try {

            ResultSet rs = st.executeQuery(getCourseInTableQuery(id));

            while (rs.next()) {
                System.out.println(rs.getInt(1) + "|" + rs.getString(2) +
                        rs.getString(3) + rs.getString(4) + "|" + rs.getString(5));
            }

        } catch (SQLException e) {

            e.printStackTrace();
            System.out.println("ERROR: Exception when trying to execute printCourseInTable() method.");

        }
    }

    public static void printResourcesInTable(int id) {

        try {

            ResultSet rs = getResourceInTableQuery(id).executeQuery();

            while (rs.next()) {
                System.out.println(rs.getInt(1) + "|" + rs.getString(2) +
                        rs.getString(3) + rs.getString(4) + rs.getInt(5) +
                        rs.getInt(6) + rs.getString(7));
            }

        } catch (SQLException e) {

            e.printStackTrace();
            System.out.println("ERROR: Exception when trying to execute printResourcesInTable() method.");
        }

    }

    public static void printPublisherInTable(int id) {

        try {

            ResultSet rs = st.executeQuery(getPublisherInTableQuery(id));

            while (rs.next()) {
                System.out.println(rs.getInt(1) + "|" + rs.getString(2) +
                        rs.getString(3) + rs.getString(4));
            }

        } catch (SQLException e) {

            e.printStackTrace();
            System.out.println("ERROR: Exception when trying to execute printPublisherInTable() method.");
        }
    }



    //================================================= QUERIES ========================================================

    //===========================================SELECT METHODS (QUERIES)===============================================

    //===========================================SELECT WHOLE TABLE QUERY===============================================

    //Regular Tables
    public static String getTablePersonQuery() {

        return "SELECT * FROM PERSON";
    }

    public static String getTableCourseQuery() {

        return "SELECT * FROM COURSECT";
    }

    public static String getTableResourceQuery() {

        return "SELECT * FROM RESOURCES";
    }

    public static String getTablePublisherQuery() {

        return "SELECT * FROM PUBLISHERS";
    }

    //Relationship Tables
    public static String getTableCoursePersonQuery() {

        return "SELECT * FROM RELATION_COURSE_PERSON";

    }

    public static String getTableCourseResourceQuery() {

        return "SELECT * FROM RELATION_COURSE_RESOURCES";
    }

    public static String getTableCourseSemesterQuery() {

        return "SELECT * FROM RELATION_SEMESTER_COURSE";
    }

    public static String getTablePersonResourceQuery() {

        return "SELECT * FROM RELATION_PERSON_RESOURCES";
    }

    public static String getTablePublisherResourceQuery() {

        return "SELECT * FROM RELATION_PUBLISHER_RESOURCE";
    }

    //==============================================SELECT BY ID QUERY==================================================
    public static String getPersonInTableQuery(int id) {

        return String.format("SELECT * FROM PERSON WHERE ID=%d", id);
    }

    public static String getCourseInTableQuery(int id) {

        return String.format("SELECT * FROM COURSECT WHERE ID=%d", id);
    }

    public static PreparedStatement getResourceInTableQuery(int id) throws SQLException{

        String query = String.format("SELECT * FROM RESOURCES WHERE ID= ?");
        PreparedStatement stl = conn.prepareStatement(query);
        stl.setInt(1, id);

        return stl;
    }

    public static String getPublisherInTableQuery(int id) {

        return String.format("SELECT * FROM PUBLISHERS WHERE ID=%d", id);
    }

    //===========================================Insert Method Queries==================================================

    public static String insertPersonQuery(Person person) {

        return String.format("INSERT INTO PERSON (FIRSTNAME, LASTNAME, TYPE) VALUES ('%s', '%s', '%s')",
                person.getFirstName(), person.getLastName(), person.getType());
    }

    public static int insertPersonQuery(Models.frontend.Person person) {
        ResultSet rs;
        int id = 0;
        //boolean exit = false;
        person.setFirstName(person.getFirstName().toLowerCase());
        person.setFirstName(person.getFirstName().substring(0, 1).toUpperCase() + person.getFirstName().substring(1));

        person.setLastName(person.getLastName().toLowerCase());
        person.setLastName(person.getLastName().substring(0, 1).toUpperCase() + person.getLastName().substring(1));


        try {
            String query = String.format("SELECT * FROM PERSON WHERE FIRSTNAME= ? AND LASTNAME= ?  AND TYPE= ?");
            PreparedStatement stl = conn.prepareStatement(query);
            stl.setString(1,person.getFirstName());
            stl.setString(2,person.getLastName());
            stl.setString(3,person.getType());
            rs = stl.executeQuery();

            if (rs.next()) {
                // Check if there is repetitive data in the db
                return rs.getInt("ID");

            }
            rs.close();
            String query1 = String.format("INSERT INTO PERSON (FIRSTNAME, LASTNAME, TYPE) VALUES (?, ?, ?)");
            PreparedStatement stl1 = conn.prepareStatement(query1);
            stl1.setString(1,person.getFirstName());
            stl1.setString(2,person.getLastName());
            stl1.setString(3,person.getType());
            stl1.executeQuery();



            String query2 = String.format("SELECT * FROM PERSON WHERE FIRSTNAME= ? AND LASTNAME= ? AND TYPE = ?");
            PreparedStatement stl2 = conn.prepareStatement(query2);
            stl2.setString(1,person.getFirstName());
            stl2.setString(2,person.getLastName());
            stl2.setString(3,person.getType());

            rs = stl2.executeQuery();

            while (rs.next()) {

                id = (rs.getInt(1));
            }
            rs.close();
            return id;

        } catch (SQLException err) {
            err.printStackTrace();
            return 0;

        }
    }

    public static String insertCourseQuery(Course course) {

        return String.format("INSERT INTO COURSECT (TITLE, CNUMBER, DESCRIPTION, DEPARTMENT) VALUES " +
                        "('%s', '%s', '%s', '%s')", course.getTitle(), course.getCRN(), course.getDescription(),
                course.getDepartment());

    }

    public static int insertCourseQuery(Models.frontend.Course course) {
        ResultSet rs;
        int id = 0;
        course.setTitle(course.getTitle().toUpperCase());
        course.setTitle(course.getTitle().replaceAll("\\s", ""));
        String[] cSplit = course.getTitle().split("(?<=\\D)(?=\\d)");

        try {

            String query2 = String.format("SELECT * FROM COURSECT WHERE TITLE= ?  AND CNUMBER = ?");
            PreparedStatement stl = conn.prepareStatement(query2);
            stl.setString(1,cSplit[0]);
            stl.setString(2,cSplit[1]);
            rs = stl.executeQuery();


            if (rs.next()) {
                id = (rs.getInt(1));
                return id;
            }
            rs.close();
            String query = String.format("INSERT INTO COURSECT (TITLE, CNUMBER, DESCRIPTION, DEPARTMENT) VALUES " +
                            "(?, ?, ?, ?)");
            PreparedStatement stl2 = conn.prepareStatement(query);
            stl2.setString(1,cSplit[0]);
            stl2.setString(2, cSplit[1]);
            stl2.setString(3,course.getDescription());
            stl2.setString(4,course.getDepartment());
            stl2.executeQuery();

            rs = stl.executeQuery();
            if (rs.next()) {
                id = (rs.getInt(1));
            }
            rs.close();
            return id;


        } catch (SQLException err) {
            System.out.println(err);
            err.printStackTrace();
            return 0;
        }

    }


    public static PreparedStatement insertResourceQuery(Resource resource) throws SQLException {

        String queryl = String.format("INSERT INTO RESOURCES (TYPE, TITLE, AUTHOR, ISBN, TOTAL_AMOUNT, CURRENT_AMOUNT, " +
                "DESCRIPTION, ISBN13, EDITION) VALUES (?, ?, ?, ?, ?, ?, ?, ?,?)");
        PreparedStatement stl = conn.prepareStatement(queryl);

        stl.setString(1, resource.getType());
        stl.setString(2, resource.getTitle());
        stl.setString(3, resource.getAuthor());
        stl.setString(4, resource.getISBN());
        stl.setInt(5, resource.getTotalAmount());
        stl.setInt(6, resource.getCurrentAmount());
        stl.setString(7, resource.getDescription());
        stl.setString(8, resource.getIsbn13());
        stl.setString(9, resource.getEdition());

        return stl;

    }

    public static void insertPublisher(Publisher publisher) {

        String query = String.format("INSERT INTO PUBLISHERS (TITLE, CONTACT_INFO, DESCRIPTION) VALUES (?, ?, ?)");
        try{
        PreparedStatement stl = conn.prepareStatement(query);
        stl.setString(1,publisher.getTitle());
        stl.setString(2,publisher.getContactInformation());
        stl.setString(3,publisher.getDescription());
        stl.executeQuery();

        }catch(Exception e){
            e.printStackTrace();
        }

    }

    //============================================Update Methods Queries================================================

    public static String updatePersonQuery(Person person) {

        return String.format("UPDATE PERSON SET FIRSTNAME = '%s', LASTNAME = '%s', TYPE = '%s' WHERE ID = %d",
                person.getFirstName(), person.getLastName(), person.getType(), person.getID());
    }

    public static void updatePersonDB(Models.frontend.Person person) {
        String query = "UPDATE PERSON SET FIRSTNAME = ?, LASTNAME = ?, TYPE = ? WHERE ID = ? ";

        try {
            PreparedStatement stl = conn.prepareStatement(query);
            stl.setString(1, person.getFirstName());
            stl.setString(2, person.getLastName());
            stl.setString(3, person.getType());
            stl.setInt(4, person.getID());
            stl.executeQuery();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void updateCourseDB(Models.frontend.Course course) {
        String query = "UPDATE COURSECT SET TITLE = ?, CNUMBER = ?, DESCRIPTION = ?, DEPARTMENT = ? WHERE ID = ?";
        try {
            course.setTitle(course.getTitle().replaceAll("\\s", ""));
            String[] titleSplit = course.getTitle().split("(?<=\\D)(?=\\d)");

            // Seperate corusfe title from course number HERE
            String courseTitle="", courseNumber="";

            PreparedStatement stl = conn.prepareStatement(query);
            stl.setString(1, titleSplit[0].toUpperCase());
            stl.setString(2, titleSplit[1]);
            stl.setString(3, course.getDescription());
            stl.setString(4, course.getDepartment());
            stl.setInt(5, course.getID());
            stl.executeQuery();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void updateCourseQuery(Course c) {
        System.out.println("Title: " + c.getTitle().substring(0, 4) + "  CNUMBER: " + c.getTitle().substring(4) +
                "  DESCRIPTION: " + c.getDescription() + "  DEPARTMENT: " + c.getDepartment() + "  ID: " + c.getID());

        String titletochange = c.getTitle();
        titletochange = titletochange.replaceAll("\\s", "");
        String[] part = titletochange.split("(?<=\\D)(?=\\d)");
        System.out.println(part[0]);
        System.out.println(part[1]);

        String query = String.format("UPDATE COURSECT SET TITLE = '%s', CNUMBER = '%s', DESCRIPTION = '%s', DEPARTMENT = '%s' " +
                "WHERE ID = %d", part[0], part[1], c.getDescription(), c.getDepartment(), c.getID());
        try {
            st.executeQuery(query);
        } catch (Exception e) {
            System.out.println("Update fail because of course error");
        }
    }

    //==================================

    // does not check person for now
    public static void updateCourseQuery2(Models.frontend.Course c) {

        // check if the course already exists, check if the person already exists.
        // if course does not exist, create it and assign it.
        // if both do exist, just update

        //getting the person id

        String person_name = c.getProfessor().getFirstName();
        String person_last_name = c.getProfessor().getLastName();
        String person_type = c.getProfessor().getType();

        int id_1 = -1;

        //check if exists:

        try {

            ResultSet rs_1 = st3.executeQuery(String.format("SELECT * FROM PERSON WHERE FIRSTNAME = '%s' AND LASTNAME = '%s' AND TYPE = '%s'",
                    person_name, person_last_name, person_type));

            while (rs_1.next()) {

                id_1 = rs_1.getInt(1);
            }

            // if -1 means it found nothing
            if (id_1 == -1) {

                //create a new person

                System.out.println("It got here");
            } else {

                c.getProfessor().setID(id_1);
                System.out.println("or here");
            }

        } catch (SQLException e) {

            System.out.println("Something went wrong when looking for professors.");

        }


        //getting the semester:

        String semester = c.getSEMESTER();
        String year = String.valueOf(c.getYEAR());

        int semester_id = getSemesterIDByName(semester, year);

        // get c number:

        String title = c.getTitle();
        String cnumber;
        title = title.replaceAll("\\s", "");
        String[] part = title.split("(?<=\\D)(?=\\d)");

        title = part[0];
        cnumber = part[1];

        int id = -1;
        int new_id = -1;
        // determine if that course exists:

        try {

            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(String.format("SELECT * FROM COURSECT WHERE TITLE = '%s' AND CNUMBER = '%s'",
                    title, cnumber));


            while (rs.next()) {

                id = rs.getInt(1);
            }

            // if -1 means that it does not exist
            if (id == -1) {

                //create it here
                new_id = insertCourseQuery(c);


                //insert this new id into the semester_course table

                statement.executeQuery(String.format("UPDATE RELATION_SEMESTER_COURSE SET COURSEID = %d, SEMESTERID = %d WHERE COURSEID = %d", new_id, semester_id, c.getID()));
                statement.executeQuery(String.format("UPDATE RELATION_COURSE_PERSON SET COURSEID = %d, PERSONID = %d WHERE COURSEID = %d", new_id, c.getProfessor().getID(), c.getID()));

                if (c.getResource().isEmpty()) {

                } else {

                    statement.executeQuery(String.format("INSERT INTO RELATION_COURSE_RESOURCES (COURSEID, RESOURCEID) VALUES (%d, %d)", new_id, c.getResource().get(0).getID()));

                }


            } else {

                //execute update method here
                executeNoReturnQuery(String.format("UPDATE COURSECT SET TITLE = '%s', CNUMBER = '%s', DESCRIPTION = '%s', DEPARTMENT = '%s' " +
                        "WHERE ID = %d", title, cnumber, c.getDescription(), c.getDepartment(), c.getID()));

            }

        } catch (SQLException e) {

            System.out.println("Turns out we are getting an exception @ updateCourseQuery2()");

        }


    }


    //==================================

    public static void updateResource(Resource resource) {

        String query =  String.format("UPDATE RESOURCES SET TYPE = ?, TITLE = ?, AUTHOR = ?, ISBN = ?, " +
                        "TOTAL_AMOUNT = ?, CURRENT_AMOUNT = ?, DESCRIPTION = ?, ISBN13 = ?," +
                        "EDITION = ? WHERE ID = ?");
        try{
        PreparedStatement stl = conn.prepareStatement(query);
        stl.setString(1,resource.getType());
        stl.setString(2,resource.getTitle());
        stl.setString(3,resource.getAuthor());
        stl.setString(4,resource.getISBN());
        stl.setInt(5,resource.getTotalAmount());
        stl.setInt(6,resource.getCurrentAmount());
        stl.setString(7,resource.getDescription());
        stl.setString(8,resource.getIsbn13());
        stl.setString(9,resource.getEdition());
        stl.setInt(10,resource.getID());
        stl.executeQuery();

        }catch(Exception e){
            e.printStackTrace();
        }

    }

    private String updatePublisherQuery(Publisher publisher) {

        return String.format("UPDATE PUBLISHER SET TITLE = '%s', CONTACT_INFO = '%s', DESCRIPTION = '%s' WHERE ID = %d",
                publisher.getTitle(), publisher.getContactInformation(), publisher.getDescription(),
                publisher.getID());
    }

    //==========================================Delete Method Queries===================================================

    private String deletePersonQuery(Person person) {

        return String.format("DELETE FROM PERSON WHERE ID = %d", person.getID());
    }

    private String deleteCourseQuery(Course course) {

        return String.format("DELETE FROM COURSECT WHERE ID = %d", course.getID());
    }

    private String deleteResourceQuery(Resource resource) {

        return String.format("DELETE FROM RESOURCES WHERE ID = %d", resource.getID());
    }

    private String deletePublisherQuery(Publisher publisher) {

        return String.format("DELETE FROM PUBLISHERS WHERE ID = %d", publisher.getID());
    }

    //============================================GUI METHODS===========================================================

    // 1st view:
    // Search by: Professor (name), Course(title), Resource(title), Semester-Year
    public static Person[] searchByProfessor(String name) {

        int i = 0;

        try {

            Statement st = conn.createStatement();
            String query = String.format("SELECT * FROM PERSON WHERE FIRSTNAME='%s'", name);
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                i++;
            }

            Person[] p = new Person[i];

            rs = st.executeQuery(query);
            i = 0;
            while (rs.next()) {

                p[i] = new Person(rs.getInt(1), rs.getString(2), rs.getString(3),
                        rs.getString(4));
                i++;
                return p;
            }

        } catch (SQLException e) {

        }

        return null;
    }

    public static ArrayList<Integer> searchGetCoursesIdsByProfessorName(String name) {

        ArrayList<Integer> arr = new ArrayList<>();

        try {

            Statement st = conn.createStatement();
            String query = String.format("SELECT * FROM PERSON WHERE FIRSTNAME='%s' OR LASTNAME='s'", name, name);

            ResultSet rs = st.executeQuery(query);

            ArrayList<Integer> professorids = new ArrayList<>();

            while (rs.next()) {

                professorids.add(rs.getInt(1));
            }

            for (int i = 0; i < professorids.size(); i++) {

                rs = st.executeQuery(String.format("SELECT * FROM RELATION_COURSE_PERSON WHERE PERSONID = %d",
                        professorids.get(i)));

                while (rs.next()) {

                    arr.add(rs.getInt(2));
                }
            }

            return arr;

        } catch (SQLException e) {

            System.out.println("Something went wrong");

        }

        return null;
    }

    public static ArrayList<Models.frontend.Course> getCourseArrayByIDs(ArrayList<Integer> timmy) {

        ArrayList<Models.frontend.Course> c = new ArrayList<>();
        Models.frontend.Course course;

        try {

            Statement st = conn.createStatement();
            ResultSet rs;

            for (int i = 0; i < timmy.size(); i++) {

                rs = st.executeQuery(String.format("SELECT * FROM COURSECT WHERE ID=%d", timmy.get(i)));

                while (rs.next()) {

                    course = new Models.frontend.Course(rs.getInt(1), rs.getString(2),
                            rs.getString(5), rs.getString(4));

                    c.add(course);
                }
            }

            return c;

        } catch (SQLException e) {


        }

        return null;

    }

    public static Course[] searchByCourse(String title) {
        int i = 0;

        try {

            Statement st = conn.createStatement();
            String query = String.format("SELECT * FROM COURSECT WHERE TITLE='%s'", title);
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                i++;
            }

            Course[] c = new Course[i];

            rs = st.executeQuery(query);
            i = 0;
            while (rs.next()) {

                c[i] = new Course(rs.getInt(1), rs.getString(2), rs.getString(3),
                        rs.getString(4), rs.getString(5));
                i++;
            }
            return c;
        } catch (SQLException e) {
            System.out.println(e);
        }

        return null;
    }

    public static Resource[] searchByResource(String title) {

        int i = 0;

        try {

            Statement st = conn.createStatement();
            String query = String.format("SELECT * FROM RESOURCES WHERE TITLE='%s'", title);
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                i++;
            }

            Resource[] r = new Resource[i];

            rs = st.executeQuery(query);
            i = 0;
            while (rs.next()) {

                r[i] = new Resource(rs.getInt(1), rs.getString(2), rs.getString(3),
                        rs.getString(4), rs.getString(5), rs.getInt(6),
                        rs.getInt(7), rs.getString(8));
                i++;
            }

            return r;

        } catch (SQLException e) {

        }

        return null;
    }

    // Work in progress
    public static Course[] searchBySemester(String semester, String year) {

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

    public static Person getPersonObject(int id) {

        Person p = new Person();

        try {

            ResultSet rs = st.executeQuery(getPersonInTableQuery(id));

            while (rs.next()) {

                p.setID(rs.getInt(1));
                p.setType(rs.getString(2));
                p.setFirstName(rs.getString(3));
                p.setLastName(rs.getString(4));
            }

        } catch (SQLException e) {

            System.out.println("Something went wrong inside of the method getPersonObject()");

        }

        return p;

    }

    public static Course getCourseObject(int id) {

        Course c = new Course();

        try {

            ResultSet rs = st.executeQuery(getCourseInTableQuery(id));

            while (rs.next()) {

                c.setID(rs.getInt(1));
                c.setTitle(rs.getString(2));
                c.setCRN(rs.getString(3));
                c.setDescription(rs.getString(4));
                c.setDepartment(rs.getString(5));
            }

        } catch (SQLException e) {

            System.out.println("Something went wrong inside of the method getPersonObject()");

        }

        return c;

    }

    public static Resource getResourceObject(int id) {

        Resource r = new Resource();

        try {

            ResultSet rs = st.executeQuery(getPersonInTableQuery(id));

            while (rs.next()) {

                r.setID(rs.getInt(1));
                r.setType(rs.getString(2));
                r.setTitle(rs.getString(3));
                r.setAuthor(rs.getString(4));
                r.setISBN(rs.getString(5));
                r.setTotalAmount(rs.getInt(6));
                r.setCurrentAmount(rs.getInt(7));
                r.setDescription(rs.getString(8));
            }

        } catch (SQLException e) {

            System.out.println("Something went wrong inside of the method getPersonObject()");

        }

        return r;

    }

    public static Publisher getPublisherObject(int id) {

        Publisher p = new Publisher();

        try {

            ResultSet rs = st.executeQuery(getPersonInTableQuery(id));

            while (rs.next()) {

                p.setID(rs.getInt(1));
                p.setTitle(rs.getString(2));
                p.setContactInformation(rs.getString(3));
                p.setDescription(rs.getString(4));
            }

        } catch (SQLException e) {

            System.out.println("Something went wrong inside of the method getPersonObject()");

        }

        return p;
    }

    //=====================================Methods for searching relationship tables====================================

    public static Person getPersonRelationTable(int id) {

        Person p = new Person();

        try {

            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(String.format("SELECT * FROM RELATION_COURSE_PERSON WHERE COURSEID=%d", id));

            while (rs.next()) {

                p.setID(rs.getInt(2));
            }


            return p;


        } catch (SQLException e) {

            System.out.println("Something went wrong.");

        }

        return null;

    }

    public static Resource[] getResourcesRelationTable(int id) {

        Resource r[];
        try {

            st = conn.createStatement();
            ResultSet rs = st.executeQuery(String.format("SELECT * FROM RELATION_COURSE_RESOURCES WHERE COURSEID=%d", id));


            int i = 0;
            while (rs.next()) {

                i++;
            }

            r = new Resource[i];

            int j = 0;

            ResultSet sr = st.executeQuery(String.format("SELECT * FROM RELATION_COURSE_RESOURCES WHERE COURSEID=%d", id));

            while (rs.next()) {

                r[j].setID(sr.getInt(2));
            }

            return r;


        } catch (SQLException e) {

            System.out.println("Something went wrong");
        }

        return null;

    }

    //==================================================================================================================
    //                                                  Relational tables methods
    //==================================================================================================================


    public static Person setResourcesForPerson(Person person1) {
        ResultSet rss, rss2;

        int resourceID;
        ArrayList<Resource> tempResourceList = new ArrayList<Resource>();

        try {
            String queryl = String.format("SELECT * FROM RELATION_PERSON_RESOURCES WHERE PERSONID = ?");
            PreparedStatement stl = conn.prepareStatement(queryl);
            stl.setInt(1, person1.getID());

            rss = stl.executeQuery();
            while (rss.next()) {
                //there will be a list of all reousrces ID that is owned by Person
                resourceID = rss.getInt(2);
                rss2 = getResourceInTableQuery(resourceID).executeQuery();
                while (rss2.next()) {
                    Resource tempRes = new Resource(rss2.getInt(1), rss2.getString(2),
                            rss2.getString(3), rss2.getString(4), rss2.getString(5),
                            rss2.getInt(6), rss2.getInt(7), rss2.getString(8));
                        tempRes.setEdition(rss2.getString("EDITION"));
                        tempRes.setIsbn13(rss2.getString("ISBN13"));
                        setPublisherForResource(tempRes);

                        tempResourceList.add(tempRes);

                }
            }
            person1.setResourceList(tempResourceList);
            return person1;
        } catch (SQLException err) {
            err.printStackTrace();
            return null;

        }
    }


    public static ArrayList<Resource> findResourcesCourseAvoidRepetitive(int courseID) {

        ResultSet rs;
        int resourceID = 0,
                i = 0, before = -1;
        ArrayList<Resource> listResources = new ArrayList<Resource>();

        try {

            rs = st.executeQuery("SELECT * FROM RELATION_COURSE_RESOURCES WHERE COURSEID = " + courseID +
                    "ORDER BY RESOURCEID ASC");
            //here all have a result set of all resources for courdeID

            while (rs.next()) {
                resourceID = rs.getInt("RESOURCEID");
                if (before != resourceID) {
                    before = resourceID;
                    ResultSet rss = getResourceInTableQuery(resourceID).executeQuery();

                    while (rss.next()) {
                        System.out.println();
                        // ID, Type, Title, Author, ISBN, total, current, desc

                        listResources.add(new Resource(rss.getInt(1), rss.getString(2),
                                rss.getString(3), rss.getString(4), rss.getString(5),
                                rss.getInt(6), rss.getInt(7), rss.getString(8)));
//                setPublisherForResource(resourcesList[i]);
                        setPublisherForResource(listResources.get(i));

                        i++;
                    }
                }

            }

            //return resourcesList;
            return listResources;
        } catch (SQLException err) {
            System.out.println(err);
        }
        // Adding the list of the resources to the person object
        listResources.clear();
        return listResources;

    }


    public static ArrayList<Resource> findResourcesCourse(int courseID, int commonID) {

        ResultSet rs;
        int resourceID = 0;
        int i = 0;
        ArrayList<Resource> listResources = new ArrayList<Resource>();

        try {

            rs = st.executeQuery(String.format("SELECT * FROM RELATION_COURSE_RESOURCES WHERE COURSEID = %d AND COMMONID = %d",
                    courseID, commonID));
            //here all have a result set of all resources for courdeID

            while (rs.next()) {
                resourceID = rs.getInt(2);
                ResultSet rss = getResourceInTableQuery(resourceID).executeQuery();

                while (rss.next()) {
                    // ID, Type, Title, Author, ISBN, total, current, desc
                    listResources.add(new Resource(rss.getInt(1), rss.getString(2),
                            rss.getString(3), rss.getString(4), rss.getString(5),
                            rss.getInt(6), rss.getInt(7), rss.getString(8)));
                    listResources.get(i).setCommonID(commonID);
                    setPublisherForResource(listResources.get(i));

                    i++;
                }

            }

            return listResources;
        } catch (SQLException err) {
            System.out.println(err);
            err.printStackTrace();
        }
        // Adding the list of the resources to the person object
        return null;

    }


    public static Resource setPublisherForResource(Resource resource1) {
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
                while (rss.next()) {
                    // ID, Type, Title, Author, ISBN, total, current, desc
                    publisherInstance = new Publisher(rss.getInt(1), rss.getString(2),
                            rss.getString(3), rss.getString(4));
                    i++;
                }
                rss.close();
            }
            rs2.close();
            resource1.setPublisherInstance(publisherInstance);
            return resource1;
        } catch (SQLException err) {
            err.printStackTrace();
        }
        // Adding the list of the resources to the person object
        return null;

    }


    public static ArrayList<Course> relationalReadByCourseID(int courseID) {
        long startTime = System.nanoTime();

        // This method is only accept ONE courseID and will find all relations to that course
        //So you may need to call the function N times with different courseID to get all information stored in table
        //Course[] courseArray = new Course[20]; //Will make it a dynamic array list
        ArrayList<Course> courseList = new ArrayList<>();
        ResultSet rsTmp;

        int personID = 0, i = 0, pID = 0, cID = 0, commonID = 0;
        int[] pr = new int[20], cr = new int[20];
        ResultSet rs;
        String cTitle = "", cDescription = "", cDepartment = "";

        Person personTmp = new Person();
        //Resource[] courseResources = new Resource[20];
        ArrayList<Resource> courseResources = new ArrayList<Resource>();

        try {
            Statement stTemp = conn.createStatement();
            Statement stTemp2 = conn.createStatement();

            ResultSet rsTemp;


            //=======================Getting information to create the course object====================================

            rs = st.executeQuery(getCourseInTableQuery(courseID));

            while (rs.next()) {

                cTitle = rs.getString("TITLE") + " " + rs.getString("CNUMBER");
                cID = rs.getInt("ID");
                cDescription = rs.getString("DESCRIPTION");
                cDepartment = rs.getString("DEPARTMENT");
                System.out.println("\ncourseID " + courseID);
            }
            //courseResources = findResourcesCourse(courseID);


            //=======================Finding and creating Persons list teaching that course=============================

            i = 0;
            rsTmp = stTemp2.executeQuery("SELECT * FROM RELATION_COURSE_PERSON WHERE COURSEID = " + courseID);
            //it supposed to get a list of all persons teaching that course. Assuming one person for now.

            int j = 0;
            while (rsTmp.next()) {

                //courseArray[i] = new Course(cID, cTitle, cDepartment, cDescription, "CRN");
                courseList.add(new Course(cID, cTitle, cDescription, cDepartment, "CRN"));
                System.out.println("\nThis is the: " + j);
                j++;

                personID = rsTmp.getInt(2);
                commonID = rsTmp.getInt("commonid");
                System.out.println("Commonid is: " + commonID);
                System.out.println("PersonID is: " + personID);
                courseList.get(i).setCommonID(commonID);


                rsTemp = stTemp.executeQuery(getPersonInTableQuery(personID));
                while (rsTemp.next()) {
                    // I'm getting all the info for each person,
                    // this loop would run n times for n persons, but must be only one result
                    //meaning the loop runs only once each time.

                    personTmp = new Person(personID, rsTemp.getString(3), rsTemp.getString(4),
                            rsTemp.getString(2));
                    personTmp.setCommonid(commonID);


                    personTmp = setResourcesForPerson(personTmp);
                    //courseArray[i].setPersonInstance(personTmp);
                    //courseArray[i].setResourceInstance(courseResources);
                    //courseArray[i].setResourceInstances(courseResources);
                    courseList.get(i).setPersonInstance(personTmp);

                    //mneed to check for resources with the same commonid
                    courseResources = findResourcesCourse(courseID, commonID);
                    courseList.get(i).setResourceInstances(courseResources);

                    i++;
                }


            }

            long endTime = System.nanoTime();
            long duration = (endTime - startTime);
            System.out.println("It took this time to run Read Relational: " + duration / 1000000 + "ms For " + courseList.size() + " courses.\n");
            return courseList;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static void getHistory() throws SQLException{
        int i=0;
        StringBuilder lsd = new StringBuilder();
        ResultSet ts3 = st.executeQuery("select v.SQL_TEXT,\n" +
                "           v.FIRST_LOAD_TIME,\n" +
                "           v.DISK_READS,\n" +
                "           v.ROWS_PROCESSED,\n" +
                "           v.ELAPSED_TIME\n" +
                "           from v$sql v\n" +
                "where to_date(v.FIRST_LOAD_TIME,'YYYY-MM-DD hh24:mi:ss')>ADD_MONTHS(trunc(sysdate,'MM'),-1) " +
                " ORDER BY v.FIRST_LOAD_TIME DESC");
            while (ts3.next()){
                i++;
                    if(i<800)
                        continue;

                    lsd.append(String.format("%s: ",Integer.toString(i)));
                    lsd.append(ts3.getString("SQL_TEXT") +"\t"+ts3.getString("FIRST_LOAD_TIME")+"\n");
                    lsd.append(String.format("____________________________\n"));
            }
        ts3.close();
        try (PrintWriter out = new PrintWriter("DBHistory.txt")) {
            out.println(lsd.toString());

        }
        catch (Exception e){
            e.printStackTrace();
        }

    }


//======================================================================================================================
//                                 Efficient new methods, reading db
// =====================================================================================================================

    public static Models.frontend.Resource setPublisherForResource2(Models.frontend.Resource resource1) {
        ResultSet rss, rs2;
        int publisherID = 0, i = 0;

        Models.frontend.Publisher publisherInstance = new Models.frontend.Publisher();

        try {
            Statement stpublisher1 = conn.createStatement();
            Statement stpublisher2 = conn.createStatement();

            rs2 = stpublisher1.executeQuery(String.format("SELECT * FROM RELATION_PUBLISHER_RESOURCE WHERE RESOURCEID = %s ORDER BY PUBLISHERID DESC",
                    resource1.getID() ));

            if (rs2.next()) {

                //there will be a list of all reousrces ID that is owned by Person
                publisherID = rs2.getInt("PUBLISHERID");

                    rss = stpublisher2.executeQuery(getPublisherInTableQuery(publisherID));
                    if (rss.next()) {

                        publisherInstance = new Models.frontend.Publisher(rss.getInt("ID"),
                                rss.getString("TITLE"),
                                 rss.getString("DESCRIPTION"), rss.getString("CONTACT_INFO"));
                        i++;
                    }
                    rss.close();
                    resource1.setPublisher(publisherInstance);


            }
            rs2.close();
            return resource1;
        } catch (SQLException err) {
            err.printStackTrace();
        }
        return resource1;

    }

public static ArrayList<Models.frontend.Resource> findResourcesCourse2(int courseID, int commonID, Map<Integer, Models.frontend.Resource> tempCach ) {

    ResultSet rs, rss;
    int resourceID = 0, i = 0;
    Statement stTemp;
    Models.frontend.Resource tempResource;
    ArrayList<Models.frontend.Resource> listResources = new ArrayList<Models.frontend.Resource>();

    try {
        stTemp = conn.createStatement();

        rs = st.executeQuery(String.format("SELECT * FROM RELATION_COURSE_RESOURCES WHERE COURSEID = %d AND COMMONID = %d",
                courseID, commonID));
        //here all have a result set of all resources for courdeID

        while (rs.next()) {
            resourceID = rs.getInt("RESOURCEID");
            if(tempCach.containsKey(resourceID))
                listResources.add(tempCach.get(resourceID));

            else {

                rss = getResourceInTableQuery(resourceID).executeQuery();

                if (rss.next()) {
                    // ID, Type, Title, Author, ISBN, total, current, desc
                    tempResource = new Models.frontend.Resource(rss.getInt("ID"),
                            rss.getString("ISBN"), rss.getString("TYPE"),
                            rss.getString("TITLE"), rss.getString("AUTHOR"),
                            rss.getString("DESCRIPTION"), rss.getInt("TOTAL_AMOUNT"),
                            rss.getInt("CURRENT_AMOUNT"));

                    tempResource.setISBN13(rss.getString("ISBN13"));
                    if ( rss.getString("EDITION")!="")
                        tempResource.setEdition(rss.getString("EDITION"));

                    listResources.add(tempResource);
                    setPublisherForResource2(tempResource);
                    tempCach.put(resourceID, tempResource);

                    i++;
                }
            }



        }

        return listResources;
    } catch (SQLException err) {
        System.out.println(err);
        err.printStackTrace();
    }
    // Adding the list of the resources to the person object
    return null;

}

    public static ArrayList<Models.frontend.Course> relationalReadByCourseID2(Map<Integer, ArrayList<Integer>> courseIds, Semester semester ) {
        long startTime = System.nanoTime();
       String tempSemester = semester.getSeason();
       tempSemester = tempSemester.toUpperCase();
       tempSemester = tempSemester.replace(' ', '_');
       semester.setSeason(tempSemester);

        ArrayList<Models.frontend.Course> courseList = new ArrayList<>();

        Map<Integer, Models.frontend.Person> cachedPersons = new HashMap<Integer, Models.frontend.Person>();
            Map<Integer, Models.frontend.Resource> cachedResources = new HashMap<Integer, Models.frontend.Resource>();

        //Map<Integer, Course> cachedCourses = new HashMap<Integer, Course>();  Think about caching course later on


        int personID = 0, i, cID = 0, courseID =0;
        ResultSet rs, rsTmp, rsTemp;
        Statement stTemp, stTemp2;

        String cTitle = "", cDescription = "", cDepartment = "";

        Models.frontend.Person personTmp = new Models.frontend.Person();
        ArrayList<Models.frontend.Resource> courseResources = new ArrayList<Models.frontend.Resource>();

        try {
             stTemp = conn.createStatement();
             stTemp2 = conn.createStatement();

            //=======================Getting information to create the course====================================
            i = 0;
            for (Map.Entry<Integer, ArrayList<Integer>> entry : courseIds.entrySet()){
                // Technically there should be only one entry, since there is only one key
                // Big O (1)
                courseID = entry.getKey();

            rs = st.executeQuery(getCourseInTableQuery(courseID));

            if (rs.next()) {
                cID = rs.getInt("ID");
                cTitle = rs.getString("TITLE") + " " + rs.getString("CNUMBER");
                cDescription = rs.getString("DESCRIPTION");
                cDepartment = rs.getString("DEPARTMENT");
                System.out.println("\ncourseID " + courseID);
            }

            else return courseList;

            //=======================Finding and creating Person teaching the course=============================


                for (Integer tempCommonID: entry.getValue()) {
                    // Big O (commonIDs.Size)

                    rsTmp = stTemp2.executeQuery("SELECT * FROM RELATION_COURSE_PERSON WHERE COMMONID = " +
                            tempCommonID);
                    if(rsTmp.next()){
                        System.out.println("semester: " + semester.getSeason());
                        courseList.add(new Models.frontend.Course(cID, tempCommonID, semester.getYear(), semester.getSeason(),
                                cTitle, cDepartment, personTmp, cDescription, courseResources));
                        courseList.get(i).setCommonID(tempCommonID);

                        personID = rsTmp.getInt("PERSONID");

                        if(cachedPersons.containsKey(personID))

                            courseList.get(i).setProfessor(cachedPersons.get(personID));

                        else {
                            rsTemp = stTemp.executeQuery(getPersonInTableQuery(personID));
                            if (rsTemp.next()) {

                                PersonType enumTmp;
                                switch (rsTemp.getString("TYPE")){
                                    case "ProgramCoordinator" : enumTmp = PersonType.ProgramCoordinator; break;
                                    case "CourseCoordinator" : enumTmp = PersonType.CourseCoordinator; break;
                                    case "CourseInstructor" : enumTmp = PersonType.CourseInstructor; break;
                                    default: enumTmp = PersonType.CourseInstructor;
                                }

                                personTmp = new Models.frontend.Person( rsTemp.getString("LASTNAME"),
                                        rsTemp.getString("FIRSTNAME"), personID,
                                        enumTmp.toString());

                                    cachedPersons.put(personID, personTmp);

                                courseList.get(i).setProfessor(personTmp);
                            }
                            else{
                                personTmp = new Models.frontend.Person("NOT FOUND",
                                        "NOT FOUND", personID,"CourseInstructor");
                                courseList.get(i).setProfessor(personTmp);
                            }

                        }
                        //=======================Finding and creating course resources=============================


                        courseResources = findResourcesCourse2(courseID, tempCommonID, cachedResources);
                        courseList.get(i).setResource(courseResources);
                        i++;

                    }

                }

            }

            long endTime = System.nanoTime();
            long duration = (endTime - startTime);
            System.out.println("It took this time to run Read Relational: " + duration / 1000000 + "ms For " +
                    courseList.size() + " courses.\n");
            return courseList;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static Map<Integer, ArrayList<Integer>> getCourseIdsBySemesterID2(int id) {

        int before =0;
        Map<Integer, ArrayList<Integer>> courseIDs = new HashMap<Integer, ArrayList<Integer>>();
        String query = String.format("SELECT * FROM RELATION_SEMESTER_COURSE WHERE SEMESTERID=%d ORDER BY COURSEID DESC",
                id);
        ArrayList<Integer> commonIDs = new ArrayList<Integer>();


        try {

            Statement st = DBManager.conn.createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                if (before == rs.getInt("COURSEID")) {
                    commonIDs.add(rs.getInt("ID"));

                }
                else{
                    before = rs.getInt("COURSEID");
                    commonIDs = new ArrayList<Integer>();
                    commonIDs.add(rs.getInt("ID"));
                    courseIDs.put(rs.getInt("COURSEID"), commonIDs);
                }
            }
            rs.close();

            return courseIDs;


        } catch (SQLException e) {
            e.printStackTrace();

        }

        return courseIDs;
    }


    public static ArrayList<Models.frontend.Course> returnEverything2(int semesterid) {
        Semester semester = getSemesterNameByID(semesterid);
        Map<Integer, ArrayList<Integer>> courseIDs = getCourseIdsBySemesterID2(semesterid);
        ArrayList<Models.frontend.Course> courses = new ArrayList<>();

        courses = relationalReadByCourseID2(courseIDs, semester);


        return courses;
    }


//======================================================================================================================
//                                       End of Efficient new methods, reading db
// =====================================================================================================================


    //==================================================================================================================
    //==================================================================================================================

    public static void relationalInsertByID() {

        Scanner scan = new Scanner(System.in);
        //DBManager DB = new DBManager();
        System.out.println("Format: <CourseID> <PersonID> <ResourceID> <PublisherID> <SemesterID>");
        System.out.println("Enter <exit> to exit.");

        while (true) {
            System.out.println("Enter  CourseID, PersonID, ResourceID, PublisherID, SemesterID: ");
            String input = scan.nextLine();
            if (!input.contains("exit")) {
                String[] values = input.split(" ");

                executeNoReturnQuery(String.format("INSERT INTO RELATION_SEMESTER_COURSE" +
                        " (COURSEID, SEMESTERID) VALUES ('%d', '%d')", Integer.parseInt(values[0]), Integer.parseInt(values[4])));


                executeNoReturnQuery(String.format("INSERT INTO RELATION_COURSE_PERSON" +
                        " (COURSEID, PERSONID) VALUES ('%d', '%d')", Integer.parseInt(values[0]), Integer.parseInt(values[1])));
                executeNoReturnQuery(String.format("INSERT INTO RELATION_COURSE_RESOURCES" +
                        " (COURSEID, RESOURCEID) VALUES ('%d', '%d')", Integer.parseInt(values[0]), Integer.parseInt(values[2])));
//
//                executeNoReturnQuery(String.format("INSERT INTO RELATION_PERSON_RESOURCES" +
//                        " (PERSONID, RESOURCEID) VALUES ('%d', '%d')", Integer.parseInt(values[1]), Integer.parseInt(values[2])));
                executeNoReturnQuery(String.format("INSERT INTO RELATION_PUBLISHER_RESOURCE" +
                        " (PUBLISHERID, RESOURCEID) VALUES ('%d', '%d')", Integer.parseInt(values[3]), Integer.parseInt(values[2])));
                System.out.println("Added ID");
            } else {
                break;
            }
        }
    }

    public static Models.frontend.Course relationalInsertByID2(Models.frontend.Course c) {

        //getting all the data
        int id = c.getID();
        String year = String.valueOf(c.getYEAR());
        String semester = c.getSEMESTER();
        String title = c.getTitle();
        String dept = c.getDepartment();
        String desc = c.getDescription();
        int personid = c.getProfessor().getID(), commonID=0;

        semester = semester.toLowerCase();
        semester = semester.substring(0,1).toUpperCase() + semester.substring(1);
        semester = semester.replace('_',' ');

        int semesterid = getSemesterIDByName(semester, year);

        System.out.println("Semester: "+semester + " ID foudn: " + semesterid);

        System.out.println("PersonID: " + personid);
        System.out.println(semesterid);

        // Fall 2018 ID = 52
//        int semesterid = 57;
        ArrayList<Models.frontend.Resource> r = c.getResource();

        System.out.println();
        System.out.println("CHECKING " + r.size());

        int[] resourceidlist = new int[r.size()];

        for (int i = 0; i < c.getResource().size(); i++) {

            resourceidlist[i] = r.get(i).getID();
        }

        try {
            Statement st = conn.createStatement();
            ResultSet rs;
            executeNoReturnQuery(String.format("INSERT INTO RELATION_SEMESTER_COURSE" +
                    " (COURSEID, SEMESTERID) VALUES ('%d', '%d')", id, semesterid));
            rs = st.executeQuery("select id from (select * from RELATION_SEMESTER_COURSE  order by id desc) where rownum = 1");
            if(rs.next()){
                commonID = rs.getInt("ID");
            }


            executeNoReturnQuery(String.format("INSERT INTO RELATION_COURSE_PERSON" +
                    " (COURSEID, PERSONID) VALUES ('%d', '%d')", id, personid));

            for (int j = 0; j < resourceidlist.length; j++) {

                executeNoReturnQuery(String.format("INSERT INTO RELATION_COURSE_RESOURCES" +
                        " (COURSEID, RESOURCEID) VALUES ('%d', '%d')", id, resourceidlist[j]));
            }




            for (int l = 0; l < resourceidlist.length; l++) {
                if(r.get(l).getPublisher() != null) {
                    if (!availablePublisher(r.get(l).getPublisher())) {

                        executeNoReturnQuery(String.format("INSERT INTO RELATION_PUBLISHER_RESOURCE" +
                                        " (PUBLISHERID, RESOURCEID) VALUES ('%d', '%d')", r.get(l).getPublisher().getID(),
                                resourceidlist[l]));
                    }
                }


            }
            c.setCommonID(commonID);
            rs.close();
            return c;

        }

        catch (SQLException e){
            e.printStackTrace();
        }
        return c;

    }

    public static void deleteRelationCourseResources(Models.frontend.Course c) {
        int courseID = c.getID();

        try {

            String query = String.format("DELETE FROM RELATION_COURSE_RESOURCES WHERE" +
                    " COMMONID = ?");
            PreparedStatement stl = conn.prepareStatement(query);
            stl.setInt(1,c.getCommonID());
            stl.executeQuery();

//          DONOT TOUCH publiser resource relation! :| :|
//            for (int i = 0; i < c.getResource().size(); i++) {
//                String qery = String.format("DELETE FROM RELATION_PUBLISHER_RESOURCE WHERE" +
//                        " RESOURCEID = ?");
//                PreparedStatement stl1 = conn.prepareStatement(qery);
//                stl1.setInt(1,c.getResource().get(i).getID());
//            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

    }

    public static void updateRelationCourseResources(Models.frontend.Course course) {
        try {

            deleteRelationCourseResources(course);
            //getting all the data
            int id = course.getID();
            int commonID = course.getCommonID();

            String tempQuerry = String.format("INSERT INTO RELATION_COURSE_RESOURCES" +
                    " (COURSEID, RESOURCEID, COMMONID) VALUES (?, ?, ?)");
            PreparedStatement stl = conn.prepareStatement(tempQuerry);


            for (Models.frontend.Resource resource : course.getResource()) {

                stl.setInt(1, id);
                stl.setInt(2, resource.getID());
                stl.setInt(3, commonID);
                stl.executeQuery();

            }
//
//            for (int k = 0; k < r.size(); k++) {
//
//                int resourceID = 0;
//
//
//                if(r.get(k).getPublisher() != null) {
//
//
//                    String queryl = String.format("SELECT * FROM RELATION_PUBLISHER_RESOURCE WHERE RESOURCEID = ? AND " +
//                    "PUBLISHERID = ?");
//                    PreparedStatement stl = conn.prepareStatement(queryl);
//                    stl.setInt(1, r.get(k).getID());
//                    stl.setInt(2, r.get(k).getPublisher().getID());
//
//
//                    ResultSet rs = stl.executeQuery();
//
//                    while (rs.next()) {
//                        resourceID = rs.getInt(2);
//                    }
//                    System.out.println("resourceID now " + resourceID);
//                    if (resourceID == 0) {
//                        String query = String.format("INSERT INTO RELATION_PUBLISHER_RESOURCE" +
//                                        " (PUBLISHERID, RESOURCEID) VALUES (?, ?)");
//
//                        PreparedStatement stl1 = conn.prepareStatement(query);
//                        stl1.setInt(1,r.get(k).getPublisher().getID());
//                        stl1.setInt(2,r.get(k).getID());
//                        stl1.executeQuery();
//
//                    }
//                    rs.close();
//                }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }




    public static void setIDforResource(Models.frontend.Resource resource) {

        try {
            Statement st = conn.createStatement();
            int resourceID = 0;

            String queryl = String.format("SELECT * FROM RESOURCES WHERE UPPER(TITLE) = UPPER(?) AND UPPER(AUTHOR) = " +
                    "UPPER(?) AND EDITION = ? AND TYPE = ? ");
            PreparedStatement stl = conn.prepareStatement(queryl);
            stl.setString(1, resource.getTitle());
            stl.setString(2, resource.getAuthor());
            stl.setString(3, resource.getEdition());
            stl.setString(4, resource.getTYPE());



            ResultSet rs = stl.executeQuery();

            if (resource.getDescription().isEmpty()) {
                resource.setDescription(" ");
            }
            if (rs.next()) {
                resourceID = rs.getInt(1);
                resource.setID(resourceID);
                System.out.println("Resource Found, ID: " + resourceID);
                rs.close();
            }
            else {
                rs.close();
                Resource tempRes = new Resource(0, resource.getTYPE(), resource.getTitle(), resource.getAuthor(),
                        resource.getISBN(), resource.getTotalAmount(), resource.getCurrentAmount(),
                        resource.getDescription());
                tempRes.setIsbn13(resource.getISBN13());
                tempRes.setEdition(resource.getEdition());

                PreparedStatement tempInsertSt = insertResourceQuery(tempRes);
                tempInsertSt.executeQuery();

                 queryl = String.format("SELECT * FROM RESOURCES WHERE TITLE = ? AND AUTHOR = ? AND " +
                        "EDITION = ? AND TYPE = ? ");
                 stl = conn.prepareStatement(queryl);
                stl.setString(1, tempRes.getTitle());
                stl.setString(2, tempRes.getAuthor());
                stl.setString(3, tempRes.getEdition());
                stl.setString(4, tempRes.getType());

                rs = stl.executeQuery();

                if (rs.next()) {
                    resourceID = rs.getInt(1);
                    resource.setID(resourceID);
                    System.out.println("Resource NOT Found, ID: " + resourceID);

                }
                rs.close();


            }
        } catch (Exception e) {
            System.out.println("Error in returnIDinResource");
            e.printStackTrace();
        }

    }


//    public static void setIDinResourceFromArrayList(ArrayList<Models.frontend.Resource> resources) {
//
//        try {
//            Statement st = conn.createStatement();
//
//            String title;
//            String type;
//            String author;
//
//            for (int i = 0; i < resources.size(); i++) {
//                int resourceID = 0;
//                title = resources.get(i).getTitle();
//                type = resources.get(i).getTYPE();
//                author = resources.get(i).getAuthor();
//
//
//                String query = String.format("SELECT * FROM RESOURCES WHERE TITLE='%s' AND AUTHOR ='%s' AND " +
//                        "EDITION = '%s' AND TYPE = 's' ", title, author,resources.get(i).getEdition(), type);
//                ResultSet rs = st.executeQuery(query);
//
//                if (rs.next()) {
//                    resourceID = rs.getInt(1);
//                    resources.get(i).setID(resourceID);
//                    continue;
//                }
//                if(resources.get(i).getDescription().isEmpty()){
//                    resources.get(i).setDescription(" ");
//                }
//                Resource tempRes = new Resource(0, resources.get(i).getTYPE(), resources.get(i).getTitle(), resources.get(i).getAuthor(),
//                        resources.get(i).getISBN(), resources.get(i).getTotalAmount(), resources.get(i).getCurrentAmount(),
//                        resources.get(i).getDescription());
//                tempRes.setIsbn13(resources.get(i).getISBN13());
//                tempRes.setEdition(resources.get(i).getEdition());
//
//                PreparedStatement tempInsertSt = insertResourceQuery(tempRes);
//                tempInsertSt.executeQuery();
//
//                rs = st.executeQuery(String.format("SELECT * FROM RESOURCES WHERE TITLE='%s'  AND AUTHOR ='%s'",
//                        tempRes.getTitle(),  tempRes.getAuthor()));
//                while (rs.next()) {
//                    resourceID = rs.getInt(1);
//                    resources.get(i).setID(resourceID);
//
//                }
//            }
//
//        } catch (Exception e) {
//            System.out.println("Error in returnIDinResource");
//            e.printStackTrace();
//        }
//    }

    public static boolean availablePublisher(Models.frontend.Publisher p) {
        try {

            String title;
            String info;
            String descrip;
            int pubID = 0;

            title = p.getName();
            info = p.getContacts();
            descrip = p.getDescription();
            if (info.isEmpty()) {
                info = " ";
            }
            if (descrip.isEmpty()) {
                descrip = " ";
            }

            String query = String.format("SELECT * FROM PUBLISHERS WHERE TITLE= ?");
            PreparedStatement stl = conn.prepareStatement(query);
            stl.setString(1,title);
            ResultSet rs = stl.executeQuery();

            if (rs.next()) {
                pubID = rs.getInt(1);
                p.setID(pubID);
                if (!rs.getString("DESCRIPTION").equals(descrip) ||
                        !rs.getString("CONTACT_INFO").equals(info))
                {
                    query = String.format( "UPDATE PUBLISHERS SET DESCRIPTION = ?, CONTACT_INFO = ?" +
                            " WHERE ID = ?");
                    stl = conn.prepareStatement(query);
                    stl.setString(1,descrip);
                    stl.setString(2,info);
                    stl.setInt(3, rs.getInt("ID"));

                    stl.executeQuery();
                }
                    rs.close();

                return true;
            }

            else {


                Publisher tempPub = new Publisher(0, title, info, descrip);

                insertPublisher(tempPub);

                String query1 = String.format("SELECT * FROM PUBLISHERS WHERE TITLE= ? AND CONTACT_INFO = ? AND DESCRIPTION = ?");
                PreparedStatement stl1 = conn.prepareStatement(query1);
                stl1.setString(1, title);
                stl1.setString(2, info);
                stl1.setString(3, descrip);
                rs = stl1.executeQuery();
                while (rs.next()) {
                    pubID = rs.getInt(1);

                }
                p.setID(pubID);
                rs.close();

                return false;


            }



        } catch (Exception e) {
            System.out.println("Error in availablePublisher");
            e.printStackTrace();
        }
        return false;
    }

    //==================================================================================================================
    //                                                  Next
    //==================================================================================================================

    //method without user's input
    public static void relationalInsertByID(int courseID, int personID, int resourceID, int publisherID, int semesterID) {


        executeNoReturnQuery(String.format("INSERT INTO RELATION_COURSE_PERSON" +
                " (COURSEID, PERSONID) VALUES ('%d', '%d')", courseID, personID));
        executeNoReturnQuery(String.format("INSERT INTO RELATION_COURSE_RESOURCES" +
                " (COURSEID, RESOURCEID) VALUES ('%d', '%d')", courseID, resourceID));
        executeNoReturnQuery(String.format("INSERT INTO RELATION_SEMESTER_COURSE" +
                " (COURSEID, SEMESTERID) VALUES ('%d', '%d')", courseID, semesterID));
        executeNoReturnQuery(String.format("INSERT INTO RELATION_PERSON_RESOURCES" +
                " (PERSONID, RESOURCEID) VALUES ('%d', '%d')", personID, resourceID));
        executeNoReturnQuery(String.format("INSERT INTO RELATION_PUBLISHER_RESOURCE" +
                " (PUBLISHERID, RESOURCEID) VALUES ('%d', '%d')", publisherID, resourceID));
        System.out.println("Added ID");

    }


    public static ArrayList<Integer> getCourseIdsBySemesterID(int id) {

        int i = 0;
        String query = String.format("SELECT * FROM RELATION_SEMESTER_COURSE WHERE SEMESTERID=%d ORDER BY COURSEID ASC",
                id);
        ArrayList<Integer> idsList = new ArrayList<Integer>();

        try {

            Statement st = DBManager.conn.createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                i++;
            }


            rs = st.executeQuery(query);
            i = 0;
            while (rs.next()) {
                idsList.add(rs.getInt(1));
                i++;
            }

            return idsList;


        } catch (SQLException e) {

            System.out.println("Something went wrong");

        }

        return null;
    }


    public static ArrayList<Models.frontend.Course> returnEverything(int semesterid) {
        Semester semester = getSemesterNameByID(semesterid);
        int lastCourseID = 0;

        ArrayList<Integer> courseIDs = getCourseIdsBySemesterID(semesterid);

        ArrayList<Models.frontend.Course> hugeshit2 = new ArrayList<>();

        for (int i = 0; i < courseIDs.size(); i++) {
            if (lastCourseID == courseIDs.get(i)) {
                continue;
            }
            ArrayList<Course> tmpCourse = DBManager.relationalReadByCourseID(courseIDs.get(i));
            lastCourseID = courseIDs.get(i);

            for (int j = 0; j < tmpCourse.size(); j++) {

                hugeshit2.add(tmpCourse.get(j).initCourseGUI(semester.getYear(), semester.getSeason()));


            }
        }

        return hugeshit2;
    }

    // Returns id needs to return title;
    public static Course[] getCourseTitlesByID(int[] ids) {

        Course[] c;
        try {

            Statement st = DBManager.conn.createStatement();
            c = new Course[ids.length];

            for (int i = 0; i < ids.length; i++) {

                ResultSet rs = st.executeQuery(String.format("SELECT * FROM COURSECT WHERE ID=%d", ids[i]));
                while (rs.next()) {

                    c[i] = new Course(rs.getString(2) + rs.getString(3));
                }

                return c;

            }


        } catch (SQLException e) {

        }
        return null;
    }


    // =======The methods to get the array of objects========================
    public static ArrayList<Person> getPersonFromTable() {
        ArrayList<Person> arr = new ArrayList<>();
        try {


            String query = String.format("SELECT * FROM PERSON ORDER BY LASTNAME ASC");
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                Person p = new Person(rs.getInt(1), rs.getString(3), rs.getString(4), rs.getString(2));
                arr.add(p);
            }
            return arr;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static ArrayList<Course> getCourseFromTable() {
        DBManager DB = new DBManager();
        ArrayList<Course> arr = new ArrayList<>();
        try {


            String query = String.format("SELECT * FROM COURSECT ORDER BY CNUMBER ASC");
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                Course p = new Course(rs.getInt(1), rs.getString(2) + rs.getString(3), rs.getString(4)
                        , rs.getString(5), rs.getString(1));
                arr.add(p);
            }
            return arr;
        } catch (Exception e) {
            System.out.println("DATA not found");
        }

        return null;
    }

    public static ArrayList<Models.frontend.Resource> getResourceFromTable() {
        ArrayList<Models.frontend.Resource> arr = new ArrayList<>();
        try {

            String query = String.format("SELECT * FROM RESOURCES ORDER BY TITLE ASC");
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                Models.frontend.Resource p = new Models.frontend.Resource(rs.getInt("ID"),
                        rs.getString("ISBN"), rs.getString("TYPE"),
                        rs.getString("TITLE"), rs.getString("AUTHOR"),
                        rs.getString("DESCRIPTION"),
                        Integer.parseInt(rs.getString("TOTAL_AMOUNT")),
                        Integer.parseInt(rs.getString("CURRENT_AMOUNT")));
                if(rs.getString("ISBN13")!=null)
                    p.setISBN13(rs.getString("ISBN13"));
                if(rs.getString("EDITION")!=null)
                    p.setEdition(rs.getString("EDITION"));
                setPublisherForResource2(p);

                arr.add(p);
            }
            rs.close();
            return arr;
        } catch (Exception e) {
            System.out.println("DATA not found");
        }

        return null;
    }

    public static ArrayList<Publisher> getPublisherFromTable() {
        DBManager DB = new DBManager();
        ArrayList<Publisher> arr = new ArrayList<>();
        try {


            String query = String.format("SELECT * FROM PUBLISHERS ORDER BY TITLE ASC");
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                Publisher p = new Publisher(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4));
                arr.add(p);
            }
            return arr;
        } catch (Exception e) {
            System.out.println("DATA not found");
        }

        return null;
    }
    //======================================================================================




    public static ArrayList<Models.frontend.Resource> findResourcesCourseReturnList(int courseID) {

        ResultSet rs;
        int resourceID = 0;
        int i = 0;
        ArrayList<Models.frontend.Resource> resourceList = new ArrayList<>();


        try {

            rs = st.executeQuery("SELECT * FROM RELATION_COURSE_RESOURCES WHERE COURSEID = " + courseID);

            while (rs.next()) {
                resourceID = rs.getInt(2);
                rs = getResourceInTableQuery(resourceID).executeQuery();
                //System.out.println("lol");

                while (rs.next()) {
                    System.out.println();

                    // ID, Type, Title, Author, ISBN, total, current, desc
                    Models.frontend.Resource resource = new Resource(rs.getInt(1), rs.getString(2),
                            rs.getString(3), rs.getString(4), rs.getString(5),
                            rs.getInt(6), rs.getInt(7), rs.getString(8)).initResourceGUI();
                    resourceList.add(resource);
                    i++;
                }

            }

            return resourceList;
        } catch (SQLException err) {
            System.out.println(err);
        }
        // Adding the list of the resources to the person object
        return null;

    }

    public static ArrayList<Models.frontend.Course> searchByNameSemesterCourseList(String fname, String lname, String semester, String year) {
        int semesterID = getSemesterIDByName(semester, year);
        ArrayList<Models.frontend.Course> courseSemesterList = returnEverything(semesterID);
        ArrayList<Models.frontend.Course> arr = new ArrayList<>();
        ArrayList<Integer> courseIDArr = new ArrayList<>();
        Person person;
        int personID = 0;
        String personType = "";
        lname = lname.substring(0, 1).toUpperCase() + lname.substring(1).toLowerCase();
        fname = fname.substring(0, 1).toUpperCase() + fname.substring(1).toLowerCase();
        int resourceID = 0;
        String cDescription = "", cDepartment = "", cTitle = "";
        Resource[] courseResources = new Resource[20];
        ArrayList<Models.frontend.Resource> resourceList = new ArrayList<>();
        int cID = 0;
        try {
            String query = String.format("SELECT * FROM PERSON WHERE FIRSTNAME = '%s' and LASTNAME = '%s' ", fname, lname);
            Statement st = DBManager.conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                personID = rs.getInt(1);
                personType = rs.getString(2);
            }

            person = new Person(personID, fname, lname, personType);
            rs = st.executeQuery("SELECT * FROM RELATION_COURSE_PERSON WHERE PERSONID = " + personID);
            int i = 0;
            while (rs.next()) {
                courseIDArr.add(rs.getInt(1));
                i++;
            }

            person = new Person(personID, fname, lname, personType);
            System.out.println("This is the size of courseIDarr" + courseIDArr.size());
            for (int a = 0; a < courseIDArr.size(); a++) {

                rs = st.executeQuery(getCourseInTableQuery(courseIDArr.get(a)));

                while (rs.next()) {

                    cTitle = rs.getString(2) + rs.getString(3);
                    cID = rs.getInt(1);
                    cDescription = rs.getString(4);
                    cDepartment = rs.getString(5);
                }

                Models.frontend.Course tempCourse = new Models.frontend.Course(cID, cTitle, cDepartment, cDescription);
                resourceList = findResourcesCourseReturnList(courseIDArr.get(a));

                tempCourse.setResource(resourceList);
                tempCourse.setProfessor(person.initPersonGUI());
                System.out.println(cID + cTitle + cDepartment + cDescription);
                System.out.println(person.initPersonGUI().toString());
                //Restrict search by name with semester, haven’t test yet

                //Need to fix the semester
                for (int c = 0; c < courseSemesterList.size(); c++) {
                    if (tempCourse.getID() == courseSemesterList.get(c).getID()
                            && tempCourse.getProfessor().getID() == courseSemesterList.get(c).getProfessor().getID()
                            ) {
                        tempCourse.setSEMESTER(semester);
                        tempCourse.setYEAR(Integer.parseInt(year));
                        arr.add(tempCourse);
                    }
                }

            }

            return arr;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("wtf");
        }
        return arr;
    }


    public static ArrayList<Models.frontend.Course> searchByNameCourseList(String fname, String lname) {
        ArrayList<Models.frontend.Course> arr = new ArrayList<>();
        ArrayList<Integer> courseIDArr = new ArrayList<>();
        Person person;
        int personID = 0;
        String personType = "";
        lname = lname.substring(0, 1).toUpperCase() + lname.substring(1).toLowerCase();
        fname = fname.substring(0, 1).toUpperCase() + fname.substring(1).toLowerCase();
        int resourceID = 0;
        String cDescription = "", cDepartment = "", cTitle = "";
        Resource[] courseResources = new Resource[20];
        ArrayList<Models.frontend.Resource> resourceList = new ArrayList<>();
        int cID = 0;
        try {
            String query = String.format("SELECT * FROM PERSON WHERE FIRSTNAME = '%s' and LASTNAME = '%s' ", fname, lname);
            Statement st = DBManager.conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                personID = rs.getInt(1);
                personType = rs.getString(2);
            }

            person = new Person(personID, fname, lname, personType);
            rs = st.executeQuery("SELECT * FROM RELATION_COURSE_PERSON WHERE PERSONID = " + personID);
            int i = 0;
            while (rs.next()) {
                courseIDArr.add(rs.getInt(1));
                i++;
            }

            person = new Person(personID, fname, lname, personType);
            System.out.println("This is the size of courseIDarr" + courseIDArr.size());
            for (int a = 0; a < courseIDArr.size(); a++) {

                rs = st.executeQuery(getCourseInTableQuery(courseIDArr.get(a)));

                while (rs.next()) {

                    cTitle = rs.getString(2) + rs.getString(3);
                    cID = rs.getInt(1);
                    cDescription = rs.getString(4);
                    cDepartment = rs.getString(5);
                }

                Models.frontend.Course tempCourse = new Models.frontend.Course(cID, cTitle, cDepartment, cDescription);
                resourceList = findResourcesCourseReturnList(courseIDArr.get(a));

                tempCourse.setResource(resourceList);
                tempCourse.setProfessor(person.initPersonGUI());
                System.out.println(cID + cTitle + cDepartment + cDescription);
                System.out.println(person.initPersonGUI().toString());
                //Restrict search by name with semester, haven’t test yet
                arr.add(tempCourse);


            }

            return arr;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("wtf");
        }
        return arr;
    }

    public static void delete_relation_course(Models.frontend.Course course) {

        try {
            int commonID = course.getCommonID();
            Statement st = conn.createStatement();


                st.executeQuery(String.format("DELETE FROM RELATION_SEMESTER_COURSE WHERE ID = " +
                        "%d", commonID));
                st.executeQuery(String.format("DELETE FROM RELATION_COURSE_PERSON WHERE  COMMONID = " +
                        "%d", commonID));
                st.executeQuery(String.format("DELETE FROM RELATION_COURSE_RESOURCES WHERE COMMONID = " +
                        "%d", commonID));


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void delete_course(Models.frontend.Course c) {

        executeNoReturnQuery(String.format("DELETE FROM RELATION_SEMESTER_COURSE WHERE COURSEID = %d", c.getID()));
        executeNoReturnQuery(String.format("DELETE FROM RELATION_COURSE_PERSON WHERE COURSEID = %d", c.getID()));
        executeNoReturnQuery(String.format("DELETE FROM RELATION_COURSE_RESOURCES WHERE COURSEID = %d",
                c.getID()));
        executeNoReturnQuery(String.format("DELETE FROM COURSECT WHERE ID = %d", c.getID()));
    }



    public static void delete_person(Models.frontend.Person p) {

        ArrayList<Integer> list_of_course_ids = new ArrayList<>();
        ArrayList<Integer> count_of_course_ids = new ArrayList<>();
        ArrayList<Integer> to_be_deleted = new ArrayList<>();
        int counter = 1;

        try {

            // get all the courses professor teaches.
            // delete the exact amount for every different course
            // execute the rest

            Statement st = conn.createStatement();

            ResultSet rs = st.executeQuery(String.format("SELECT * FROM RELATION_COURSE_PERSON WHERE PERSONID = %d " +
                            "ORDER BY COURSEID ASC",
                    p.getID()));

            while (rs.next()) {

                list_of_course_ids.add(rs.getInt(1));

            }

            int previousone = 0;
            int thisone = 0;
            int i = 0;

            while (list_of_course_ids.size() > i) {

                thisone = list_of_course_ids.get(i);

                if (i > 1) {

                    previousone = list_of_course_ids.get(i - 1);
                }

                if (thisone == previousone) {

                    counter++;

                } else {

                    counter = 1;
                }

                count_of_course_ids.add(counter);
                i++;
            }


            Statement statement = conn.createStatement();
            ResultSet result_set;
            String query;
            previousone = 0;
            thisone = 0;
            i = 0;

            while (list_of_course_ids.size() > i) {

                query = String.format("SELECT * FROM RELATION_SEMESTER_COURSE WHERE COURSEID = %d", list_of_course_ids.get(i));
                result_set = statement.executeQuery(query);

                if (i > 1) {

                    previousone = list_of_course_ids.get(i - 1);
                }
                while (result_set.next()) {

                    if (previousone != thisone) {

                        to_be_deleted.add(result_set.getInt(3));
                    }
                }

                i++;

            }

            for (int a = 0; a < list_of_course_ids.size(); a++) {

                System.out.println(list_of_course_ids.get(a));
            }

            for (int b = 0; b < list_of_course_ids.size(); b++) {

                System.out.println(count_of_course_ids.get(b));
            }

            for (int j = 0; j < to_be_deleted.size(); j++) {

                System.out.println(to_be_deleted.get(j));
            }


//            String query = "";
//            for(int i = 0; i < to_be_deleted.size(); i++){
//
//                query = String.format("DELETE FROM RELATION_SEMESTER_COURSE WHERE ID = %d",
//                        to_be_deleted.get(i));
//                System.out.println(query);
//                //executeNoReturnQuery(query);
//            }

//            executeNoReturnQuery(String.format("DELETE FROM RELATION_PERSON_RESOURCES WHERE PERSONID = %d", p.getID()));
//            executeNoReturnQuery(String.format("DELETE FROM RELATION_COURSE_PERSON WHERE PERSONID = %d", p.getID()));
//            executeNoReturnQuery(String.format("DELETE FROM PERSON WHERE ID = %d", p.getID()));

        } catch (SQLException e) {

            System.out.println("Something went wrong with the delete_person function");

        }

    }


    public static void deletePerson(Models.frontend.Person person) {

        try {

            Statement st = conn.createStatement();
            Statement st2 = conn.createStatement();
            Statement st3 = conn.createStatement();
            Statement st4 = conn.createStatement();
            Statement st5 = conn.createStatement();
            Statement st6 = conn.createStatement();

            ArrayList<Integer> commonids_to_be_deleted = new ArrayList<>();

            System.out.println("asdfasdfasdf: 1");

            ResultSet rs = st.executeQuery(String.format("SELECT * FROM RELATION_COURSE_PERSON WHERE PERSONID = %d",
                    person.getID()));

            while (rs.next()) {
                commonids_to_be_deleted.add(rs.getInt("COMMONID"));
            }

            System.out.println("asdfasdfasdf: 2");

            for(int i = 0; i < commonids_to_be_deleted.size(); i++){

                st2.executeQuery(String.format("DELETE FROM RELATION_SEMESTER_COURSE WHERE ID = %d",
                        commonids_to_be_deleted.get(i)));

                System.out.println("Deleting from RELATION_SEMESTER_COURSE. COMMONID : " + commonids_to_be_deleted.get(i));
            }

            System.out.println("asdfasdfasdf: 3");

            for(int i = 0; i < commonids_to_be_deleted.size(); i++){

                st3.executeQuery(String.format("DELETE FORM RELATION_COURSE_RESOURCES WHERE COMMONID = %d",
                        commonids_to_be_deleted.get(i)));

                System.out.println("Deleting from RELATION_COURSE_RESOURCES. COMMONID : " + commonids_to_be_deleted.get(i));
            }

            System.out.println("asdfasdfasdf: 4");


            st4.executeQuery(String.format("DELETE FROM RELATION_COURSE_PERSON WHERE PERSONID = %d",
                    person.getID()));

            System.out.println("DELETED FROM RELATION_COURSE_PERSON: " + person.getID());

            st5.executeQuery(String.format("DELETE FROM RELATION_PERSON_RESOURCES WHERE PERSONID = %d",
                    person.getID()));

            System.out.println("DELETED FROM RELATION_PERSON_RESOURCES: " + person.getID());

            st6.executeQuery(String.format("DELETE FROM PERSON WHERE ID = %d", person.getID()));

            System.out.println("DELETED FROM PERSON: " + person.getID());

            System.out.println("asdfasdfasdf: 5");

        } catch (SQLException e) {
            System.out.println("Something went wrong @ deletePerson() @ DBManager.java");
        }
    }


    public static ArrayList<Models.frontend.Resource> getResourceList() {
        ArrayList<Models.frontend.Resource> resList = getResourceFromTable();

        return resList;
    }

    public static void updateCourseGUI(Models.frontend.Course course) {
        updateCourseDB(course);

    }

    public static void updatePersonGUI(Models.frontend.Person person) {
        System.out.println("Firstname; " + person.getFirstName() + " Lastname: " + person.getLastName() +
        " ID: " + person.getID() + " Type: " + person.getType());
        updatePersonDB(person);
    }

    public static void updateCourseQuery123(ArrayList<Models.frontend.Course> c) {

        if (c.isEmpty()) {

            System.out.println("Course array is empty. Something went wrong.");

        } else if (c.get(0).equals(c.get(1))) {


            //needs to check for non existing professor

            c.get(0).getProfessor().setID(find_person_by_name(c.get(0).getProfessor()));


            try {

                Statement st = conn.createStatement();

                st.executeQuery(String.format("UPDATE RELATION_COURSE_PERSON SET PERSONID = %d WHERE " +
                        "COMMONID = %d", c.get(0).getProfessor().getID(), c.get(0).getCommonID()));

            } catch (SQLException e) {

                System.out.println("Something went wrong when adding a new professor to the RELATION_COURSE_PERSON TABLE");

            }

            System.out.println("Second IF in updateCourseQuery123()");

        } else {

            try {

                Statement st = conn.createStatement();

                c.get(1).setID(find_courseid_by_title(c.get(1)));

                // if it exists
                st.executeQuery(String.format("UPDATE RELATION_SEMESTER_COURSE SET COURSEID = %d WHERE ID = %d",
                        c.get(1).getID(), c.get(0).getCommonID()));


                c.get(1).getProfessor().setID(find_person_by_name(c.get(1).getProfessor()));
                System.out.println(c.get(1).getProfessor().getID());


                st.executeQuery(String.format("UPDATE RELATION_COURSE_PERSON SET COURSEID = %d, PERSONID = %d WHERE " +
                                "COMMONID = %d", c.get(1).getID(), c.get(1).getProfessor().getID(),
                        c.get(0).getCommonID()));

            } catch (SQLException e) {

                System.out.println("Something went wrong when trying to update course and person table");
            }

        }

    }


    public static void updateCoursePersonSemester(Models.frontend.Course course) {

            try {
                int semesterID = 0;
                semesterID = getSemesterIDByName(controller.convertSeasonGUItoDB(course.getSEMESTER()),
                        String.valueOf(course.getYEAR()));


                //Updating Semester_Course
                String queryString = String.format("UPDATE RELATION_SEMESTER_COURSE SET COURSEID = ?, SEMESTERID = ?" +
                        " WHERE ID = ?");
                PreparedStatement stl = conn.prepareStatement(queryString);
                stl.setInt(1,course.getID());
                stl.setInt(2,semesterID);
                stl.setInt(3,course.getCommonID());
                stl.executeQuery();

                // Updatin Course_Perosn
                String query = String.format("UPDATE RELATION_COURSE_PERSON SET COURSEID = ?, PERSONID = ? WHERE " +
                                "COMMONID = ?");
                PreparedStatement stl1 = conn.prepareStatement(query);
                stl1.setInt(1,course.getID());
                stl1.setInt(2,course.getProfessor().getID());
                stl1.setInt(3,course.getCommonID());
                stl1.executeQuery();

            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

    public static void updateSemester(Models.frontend.Course c) {

        int semesterID = getSemesterIDByName(controller.convertSeasonGUItoDB(c.getSEMESTER()),String.valueOf(c.getYEAR()));
        int commonID = c.getCommonID();
        System.out.println("Semester ID now " + semesterID);

        try {




            String query = String.format("UPDATE RELATION_SEMESTER_COURSE SET SEMESTERID = ? WHERE ID = ?");
            PreparedStatement stl = conn.prepareStatement(query);
            stl.setInt(1,semesterID);
            stl.setInt(2,commonID);
            stl.executeQuery();

        } catch (SQLException e) {

            System.out.println("Something went wrong when trying to update course and person table");
        }

    }



    public static int find_courseid_by_title(Models.frontend.Course c) {

        String title = c.getTitle();
        String cnumber;

        title = title.replaceAll("\\s", "");
        String[] part = title.split("(?<=\\D)(?=\\d)");

        title = part[0];
        cnumber = part[1];

        int id = -1;

        try {



            String query = String.format("SELECT * FROM COURSECT WHERE TITLE = ? AND CNUMBER = ?");
            PreparedStatement stl = conn.prepareStatement(query);
            stl.setString(1,title);
            stl.setString(2,cnumber);
            ResultSet rs = stl.executeQuery();

            while (rs.next()) {

                id = rs.getInt("id");
            }

            if (id == -1) {

                String query1 = String.format("INSERT INTO COURSECT (TITLE, CNUMBER, DESCRIPTION, DEPARTMENT) VALUES " +
                        "(?,?, ?, ?, ?)");
                PreparedStatement stl1 = conn.prepareStatement(query1);
                stl1.setString(1,title);
                stl1.setString(2,cnumber);
                stl1.setString(3,c.getDescription());
                stl1.setString(4, c.getDepartment());

                stl1.executeQuery();


                String query2 = String.format("SELECT * FROM COURSECT WHERE TITLE = ? AND CNUMBER = ?");
                PreparedStatement stl2 = conn.prepareStatement(query2);
                stl2.setString(1,title);
                stl2.setString(2,cnumber);
                rs = stl2.executeQuery();

                while (rs.next()) {

                    id = rs.getInt("id");
                }
                rs.close();
                return id;

            } else {
                rs.close();
                return id;
            }


        } catch (SQLException e) {

            System.out.println("Something went wrong when trying to create / find a course @ find_courseid_by_title()");
        }

        return -999;

    }

    public static int find_person_by_name(Models.frontend.Person p) {
        p.setFirstName(p.getFirstName().substring(0,1).toUpperCase()+p.getFirstName().substring(1).toLowerCase());
        p.setLastName(p.getLastName().substring(0,1).toUpperCase()+p.getLastName().substring(1).toLowerCase());
        System.out.println("Find this person " + p.getFirstName());

        int id = -1;

        try {


            String query = String.format("SELECT * FROM PERSON WHERE FIRSTNAME = ? AND LASTNAME = ?");
            PreparedStatement stl = conn.prepareStatement(query);
            stl.setString(1,p.getFirstName());
            stl.setString(2,p.getLastName());

            ResultSet rs = stl.executeQuery();

            while (rs.next()) {

                id = rs.getInt("ID");
            }

            if (id == -1) {

                String query1 = String.format("INSERT INTO PERSON (FIRSTNAME, LASTNAME, TYPE) VALUES(?, ?, ?)");
                PreparedStatement stl1 = conn.prepareStatement(query1);
                stl1.setString(1,p.getFirstName());
                stl1.setString(2,p.getLastName());
                stl1.setString(3,p.getType());
                stl1.executeQuery();

                String query2 = String.format("SELECT * FROM PERSON WHERE FIRSTNAME = ? AND LASTNAME = ?");
                PreparedStatement stl2 = conn.prepareStatement(query2);
                stl2.setString(1,p.getFirstName());
                stl2.setString(2,p.getLastName());
                rs = stl2.executeQuery();

                while (rs.next()) {

                    id = rs.getInt("ID");
                }

                return id;

            } else {
                return id;
            }

        } catch (SQLException e) {

            System.out.println("Something went wrong when trying to find a person @ find_person_by_name()");
        }

        return -999;

    }


    public static void updatePublisherForResource(Models.frontend.Resource r, Models.frontend.Publisher p) {
        try {
            st.executeQuery("DELETE FROM RELATION_PUBLISHER_RESOURCE WHERE RESOURCEID =" + r.getID());
            st.executeQuery(String.format("INSERT INTO RELATION_PUBLISHER_RESOURCE (RESOURCEID, PUBLISHERID) VALUES ('%d','%d')", r.getID(), p.getID()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void insertPersonResources(Models.frontend.Person person) {

        int commonID = 0;
//        setIDinResourceFromArrayList(person.getResources());

        executeNoReturnQuery(String.format("DELETE FROM RELATION_PERSON_RESOURCES WHERE" +
                " PERSONID = '%d' ", person.getID()));

        for (Models.frontend.Resource resource : person.getResources()) {
            String tempQuery = String.format("INSERT INTO RELATION_PERSON_RESOURCES" +
                            " (PERSONID, RESOURCEID, COMMONID) VALUES ('%d', '%d','%d')", person.getID(),
                    resource.getID(), commonID);
            executeNoReturnQuery(tempQuery);
        }
        System.out.println("All resources succesfully imported to db for the person!");

    }


    public static String exportCSVCourseResources(ComboBox season, ComboBox year) {
        int semesterID = getSemesterIDByName(controller.convertSeasonGUItoDB(season.getSelectionModel().getSelectedItem().toString()),
                year.getSelectionModel().getSelectedItem().toString());
        ArrayList<Models.frontend.Course> allCourses = returnEverything2(semesterID);
        StringBuilder sb = new StringBuilder();

        sb.append("id,");
        sb.append("Title,");
        sb.append("Description,");
        sb.append("Department,");
        sb.append("Resources -->");
        sb.append('\n');

        for (Models.frontend.Course course : allCourses) {
            sb.append(course.getID());
            sb.append(",");
            sb.append(course.getTitle().replace(",", ""));
            sb.append(",");
            sb.append(course.getDescription().replace(",", ""));
            sb.append(",");
            sb.append(course.getDepartment().replace(",", ""));
            sb.append(",");
            for (Models.frontend.Resource resource : course.getResource()) {
                sb.append(resource.getTitle().replace(",", ""));
                sb.append(",");
            }
            sb.deleteCharAt(sb.length() - 1);
            sb.append("\n");
        }

        //pw.write(sb.toString());
        System.out.println("done!");
        return sb.toString();

    }

    public static String exportCSVPublisherInfo() {

        ArrayList<Publisher> allPublishers = getPublisherFromTable();
//        PrintWriter pw = new PrintWriter(new File("Course_Resources.csv"));
        StringBuilder sb = new StringBuilder();
        sb.append("id,");
        sb.append("Title,");
        sb.append("Contact Info,");
        sb.append("Description");
        sb.append('\n');

        for (Publisher publisher : allPublishers) {
            sb.append(publisher.getID());
            sb.append(",");
            sb.append(publisher.getTitle().replace(",", ""));
            sb.append(",");
            sb.append(publisher.getContactInformation().replace(",", ""));
            sb.append(",");
            sb.append(publisher.getDescription().replace(",", ""));
            sb.append("\n");

        }

        return sb.toString();
    }


    public static String exportCSVResourcePublisher() {
        ArrayList<Models.frontend.Resource> allResources = getResourceFromTable();

//        PrintWriter pw = new PrintWriter(new File("Course_Resources.csv"));
        StringBuilder sb = new StringBuilder();
        sb.append("id,");
        sb.append("Type,");
        sb.append("Title,");
        sb.append("Author,");
        sb.append("ISBN,");
        sb.append("ISBN13,");
        sb.append("Edition,");
        sb.append("Total amount,");
        sb.append("Current amount,");
        sb.append("Description,");
        sb.append("Publisher Title,");
        sb.append("Publisher contact info");
        sb.append('\n');

        for (Models.frontend.Resource resource : allResources) {
            sb.append(resource.getID());
            sb.append(",");
            sb.append(resource.getTYPE());
            sb.append(",");
            sb.append(resource.getTitle().replace(",", ""));
            sb.append(",");
            sb.append(resource.getAuthor().replace(",", ""));
            sb.append(",");
            sb.append(resource.getISBN());
            sb.append(",");
            sb.append(resource.getISBN13());
            sb.append(",");
            sb.append(resource.getEdition());
            sb.append(",");
            sb.append(resource.getTotalAmount());
            sb.append(",");
            sb.append(resource.getCurrentAmount());
            sb.append(",");
            sb.append(resource.getDescription().replace(",", ""));
            sb.append(",");
            sb.append(resource.getPublisher().getName().replace(",", ""));
            sb.append(",");
            sb.append(resource.getPublisher().getContacts().replace(",", ""));
            sb.append("\n");
        }
        return sb.toString();

    }

    public static String exportCSVPersonResources() {
        ArrayList<Person> allPerson = getPersonFromTable();
        StringBuilder sb = new StringBuilder();

        sb.append("id,");
        sb.append("First Name,");
        sb.append("Last Name,");
        sb.append("Type,");
        sb.append("Resources-->");
        sb.append('\n');

        for (Person person : allPerson) {
            setResourcesForPerson(person);
            sb.append(person.getID() + ",");
            sb.append(person.getFirstName().replace(",", "") + ",");
            sb.append(person.getLastName().replace(",", "") + ",");
            sb.append(person.getType() + ",");
            for (Resource resource : person.getResourceList()) {
                sb.append(resource.getTitle().replace(",", "") + ",");
            }
            sb.deleteCharAt(sb.length() - 1);
            sb.append('\n');

        }
        return sb.toString();
    }

    public static ArrayList<Models.frontend.Resource> getAllResourcesNeededForPerson(Models.frontend.Person person,
                                                                                     String semester, String year) {
        int semesterID = getSemesterIDByName(controller.convertSeasonGUItoDB(semester), year);

        //GO to person-course, get a list of commonids of the courses being teached by the person
        ResultSet rss, rs, rs3;
        int commonID = 0, before=0;
        ArrayList<Models.frontend.Resource> resourcesList = new ArrayList<Models.frontend.Resource>();
        ArrayList<Integer> listOfIDs= new ArrayList<Integer>();
        Resource tempResource;

        try {
            Statement st = conn.createStatement();
            Statement st2 = conn.createStatement();
            PreparedStatement stl;
            String query = "SELECT * FROM RELATION_COURSE_PERSON cp " +
                    "INNER JOIN RELATION_SEMESTER_COURSE sc " +
                    "ON sc.SEMESTERID = ? " +
                    "AND sc.ID = cp.COMMONID AND cp.PERSONID = ? ";

            stl = conn.prepareStatement(query);
            stl.setInt(1, semesterID);
            stl.setInt(2, person.getID());


            rss = stl.executeQuery();
            while (rss.next()) {
                commonID = (rss.getInt("COMMONID"));
                rs = st2.executeQuery(String.format("SELECT * FROM RELATION_COURSE_RESOURCES WHERE COMMONID = '%d' ORDER BY RESOURCEID ASC",
                        commonID));

                while (rs.next()) {

                    if (before != rs.getInt("RESOURCEID") &&
                            !listOfIDs.contains(new Integer(rs.getInt("RESOURCEID")))) {

                        before = rs.getInt("RESOURCEID");
                        listOfIDs.add(before);
                        rs3 = getResourceInTableQuery(rs.getInt("RESOURCEID")).executeQuery();

                        while (rs3.next()) {
                            // ID, Type, Title, Author, ISBN, total, current, desc
                            tempResource = new Resource(rs3.getInt(1), rs3.getString(2),
                                    rs3.getString(3), rs3.getString(4), rs3.getString(5),
                                    rs3.getInt(6), rs3.getInt(7), rs3.getString(8));
                            tempResource.setCommonID(commonID);
                            setPublisherForResource(tempResource);
                            resourcesList.add(tempResource.initResourceGUI());
                        }
                    }

                }
            }

            return resourcesList;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return resourcesList;
    }


    public static ArrayList<Models.frontend.Resource> findDifferene(Models.frontend.Person person, ArrayList<Models.frontend.Resource> personRequiredResources){

        ArrayList<Models.frontend.Resource> differences = new ArrayList<Models.frontend.Resource>();


        for (Models.frontend.Resource resource : personRequiredResources){
            if (!person.getResources().contains(resource)){
                differences.add(resource);
            }
        }

        return differences;

    }

    public static ArrayList<Models.frontend.Course> return_everything_by_commonid(ArrayList<Integer> idlist){

        ArrayList<Models.frontend.Course> courses = new ArrayList<>();

            for(int i : idlist){

                courses.add(find_class_by_commonid(idlist.get(i)));
            }

            return courses;

    }

    public static ArrayList<Models.frontend.Course> find_classes_by_course_name(String name){

        ArrayList<Models.frontend.Course> courses = new ArrayList<>();
        ArrayList<Integer> courseids = new ArrayList<>();
        ArrayList<Integer> classids = new ArrayList<>();
        ArrayList<Integer> tmp;


        try{

            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM COURSECT WHERE TITLE LIKE '%"+name+"%' OR CNUMBER LIKE '%"+name+"%'");

            while(rs.next()){

                courseids.add(rs.getInt("ID"));
            }

            rs.close();


            for(int i = 0; i < courseids.size(); i++){

                tmp = find_classids_by_courseid(courseids.get(i));
                for(int j = 0; j < tmp.size(); j++){

                    classids.add(tmp.get(j));
                }

            }



            for(int i = 0; i < classids.size(); i++){

                courses.add(find_class_by_commonid(classids.get(i)));
            }

            return courses;

        }catch(SQLException e){

            System.out.println("Something went wrong with find_classes_by_course_name(String name)");
        }

        return courses;

    }

    public static ArrayList<Integer> find_classids_by_resource_name(String name){

        ArrayList<Integer> resourceids = new ArrayList<>();
        ArrayList<Integer> classids = new ArrayList<>();

        try{

            Statement statement = conn.createStatement();
            Statement statement2 = conn.createStatement();
//            ResultSet rs = statement.executeQuery("SELECT * FROM RESOURCES WHERE TITLE LIKE '%"+name+"%' OR AUTHOR LIKE '%"+name+"%'");

            String queryl = String.format("SELECT * FROM RESOURCES WHERE TITLE LIKE ? OR AUTHOR LIKE ?");
            PreparedStatement stl = conn.prepareStatement(queryl);
            stl.setString(1, "%" + name + "%");
            stl.setString(2, "%" + name + "%");
            ResultSet rs = stl.executeQuery();


            while(rs.next()) {

                resourceids.add(rs.getInt("ID"));
            }

            rs.close();

            ResultSet rs2;

            for(int i = 0; i < resourceids.size(); i++){

                rs2 = statement2.executeQuery(String.format("SELECT * FROM RELATION_COURSE_RESOURCES WHERE RESOURCEID = %d", resourceids.get(i)));

                while(rs2.next()){

                    classids.add(rs2.getInt("COMMONID"));
                }

                rs2.close();
            }


            return classids;

        }catch(SQLException e) { System.out.println("Something went wrong with find_classids_by_resource_name()"); }

        return classids;
    }

    public static ArrayList<Integer> find_classids_by_course_name(String courseTitle, String name){

        ArrayList<Integer> courseids = new ArrayList<>();
        ArrayList<Integer> classids = new ArrayList<>();
        ArrayList<Integer> tmp;


        try{

            Statement st = conn.createStatement();
//            ResultSet rs = st.executeQuery("SELECT * FROM COURSECT WHERE CNUMBER LIKE  '" +
//                        name + "%' AND TITLE LIKE '" + courseTitle+ "%' " );

            String queryl = String.format("SELECT * FROM COURSECT WHERE CNUMBER LIKE ? AND TITLE LIKE ?");
            PreparedStatement stl = conn.prepareStatement(queryl);
            stl.setString(1, "%" + name + "%");
            stl.setString(2, "%" + courseTitle + "%");
            ResultSet rs = stl.executeQuery();

            while(rs.next()){

                courseids.add(rs.getInt("ID"));
            }

            rs.close();


            for(int i = 0; i < courseids.size(); i++){

                tmp = find_classids_by_courseid(courseids.get(i));
                for(int j = 0; j < tmp.size(); j++){

                    classids.add(tmp.get(j));
                }

            }

            return classids;

        }catch(SQLException e){
            e.printStackTrace();
            System.out.println("Something went wrong with find_classes_by_course_name(String name)");
        }

        return classids;


    }

    public static void insertRelationResourcePublisher(Models.frontend.Resource resource){
        int resID = 0;
        try{

            ResultSet rs = st.executeQuery("SELECT * FROM RELATION_PUBLISHER_RESOURCE WHERE PUBLISHERID = "+
            resource.getPublisher().getID() + " AND RESOURCEID = " + resource.getID());

            while(rs.next()){
                resID = rs.getInt(2);
            }

            if(resID == 0){
                st.executeQuery(String.format("INSERT INTO RELATION_PUBLISHER_RESOURCE (PUBLISHERID, RESOURCEID) VALUES " +
                        "('%d','%d')",resource.getPublisher().getID(),resource.getID()));
            }

            rs.close();


        }catch(Exception e){
            e.printStackTrace();
        }

    }

    public static ArrayList<Integer> find_classids_by_courseid(int id){

        ArrayList<Integer> classids = new ArrayList<>();

        try{

            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(String.format("SELECT * FROM RELATION_SEMESTER_COURSE WHERE COURSEID = %d", id));

            while(rs.next()){

                classids.add(rs.getInt("ID"));
            }

            rs.close();

            return classids;

        }catch(SQLException e){

            System.out.println("Something went wrong with find_classid_by_courseid(int id)");
        }

        return classids;
    }

    public static ArrayList<Models.frontend.Course> find_classes_by_professor_name(String name){

        ArrayList<Integer> courseids = new ArrayList<>();
        ArrayList<Integer> personids = new ArrayList<>();
        ArrayList<Models.frontend.Course> courses = new ArrayList<>();

        try{

            Statement st = conn.createStatement();
            Statement st2 = conn.createStatement();
            Statement st3 = conn.createStatement();
            ResultSet rs3;

            ResultSet rs = st.executeQuery("SELECT * FROM PERSON WHERE FIRSTNAME LIKE '%"+name+"%'");

            while(rs.next()){

                personids.add(rs.getInt("ID"));
            }

            rs.close();

            ResultSet rs2 = st.executeQuery("SELECT * FROM PERSON WHERE LASTNAME LIKE '%"+name+"%'");

            while(rs2.next()){

                personids.add(rs2.getInt("ID"));
            }

            rs2.close();


            for(int i = 0; i < personids.size(); i++){

                 rs3 = st3.executeQuery(String.format("SELECT * FROM RELATION_COURSE_PERSON WHERE PERSONID = %d", personids.get(i)));

                while(rs3.next()){

                    courseids.add(rs3.getInt("COMMONID"));
                }

                rs3.close();

            }

            for(int i = 0; i < courseids.size(); i++){

                courses.add(find_class_by_commonid(courseids.get(i)));
            }

            return courses;


        }catch(SQLException e){

            System.out.println("Something went wrong with find_course_by_professor_name(String name)");
        }

        return courses;


    }

    public static ArrayList<Integer> find_classids_by_professor_name(String name){

        ArrayList<Integer> courseids = new ArrayList<>();
        ArrayList<Integer> personids = new ArrayList<>();

        try{

            Statement st = conn.createStatement();
            Statement st2 = conn.createStatement();
            ResultSet rs2;

            String queryl = String.format("SELECT * FROM PERSON WHERE FIRSTNAME LIKE ? OR LASTNAME LIKE ?");
            PreparedStatement stl = conn.prepareStatement(queryl);
            stl.setString(1, "%" + name + "%");
            stl.setString(2, "%" + name + "%");
            ResultSet rs = stl.executeQuery();

            while(rs.next()){

                personids.add(rs.getInt("ID"));
            }

            rs.close();


            for(int i = 0; i < personids.size(); i++){

                rs2 = st2.executeQuery(String.format("SELECT * FROM RELATION_COURSE_PERSON WHERE PERSONID = %d", personids.get(i)));

                while(rs2.next()){

                    courseids.add(rs2.getInt("COMMONID"));
                }

                rs2.close();
            }

            return courseids;


        }catch(SQLException e){

            System.out.println("Something went wrong with find_course_by_professor_name(String name)");
        }

        return courseids;
    }

    public static Models.frontend.Course find_class_by_commonid(int id){

        Models.frontend.Person person = find_person_by_commonid(id);
        Models.frontend.Course course = new Models.frontend.Course();
        ArrayList<Models.frontend.Resource> resources = find_resources_by_commonid(id);


        int courseid = -1;

        try{


            String query = String.format("SELECT * FROM RELATION_SEMESTER_COURSE WHERE ID = ?");
            PreparedStatement stl = conn.prepareStatement(query);
            stl.setString(1, String.valueOf(id));
            ResultSet testing = stl.executeQuery();

            while(testing.next()){

                courseid = testing.getInt("COURSEID");

            }

            Statement st2 = conn.createStatement();


            ResultSet rs2 = st.executeQuery(String.format("SELECT * FROM COURSECT WHERE ID = %d", courseid));
            course.setCommonID(id);

            String[] semester = new String[2];
            semester = get_semester_by_semesterid(find_semester_id_by_commonid(course.getCommonID()));

            while(rs2.next()){

                course.setID(rs2.getInt("ID"));
                course.setTitle(rs2.getString("TITLE") + rs2.getString("CNUMBER"));
                course.setDescription(rs2.getString("DESCRIPTION"));
                course.setDepartment(rs2.getString("DEPARTMENT"));
            }

            rs2.close();

            semester[0] = semester[0].replace(' ', '_');

            course.setSEMESTER(Models.frontend.Semester.valueOf(semester[0]).toString());
            course.setYEAR(Integer.parseInt(semester[1]));

            course.setResource(resources);
            course.setProfessor(person);


            return course;

        }catch(SQLException e){

            System.out.println("Something went wrong with find_class_by_commonid()");
        }

        return course;
    }

    public static ArrayList<Models.frontend.Resource> find_resources_by_name(String name) {

        ArrayList<Models.frontend.Resource> resource = new ArrayList<>();
        ArrayList<Integer> resourceids = new ArrayList<>();
        Models.frontend.Resource tmpresource;

        try{

            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM RESOURCES WHERE TITLE LIKE '%"+name+"%' OR AUTHOR LIKE '%"+name+"%'");

            while(rs.next()) {

                resourceids.add(rs.getInt("ID"));
            }

            rs.close();

            for(int i : resourceids){

                resource.add(find_resource_by_id(i));
            }

            return resource;

        }catch(SQLException e) { System.out.println("Something went wrong with find_resources_by_name()"); }

        return resource;
    }

    public static ArrayList<Models.frontend.Resource> find_resources_by_commonid(int id){

        ArrayList<Integer> resids = new ArrayList<>();
        ArrayList<Models.frontend.Resource> reslist = new ArrayList<>();

        try{

            Statement st = conn.createStatement();

            ResultSet rs = st.executeQuery(String.format("SELECT * FROM RELATION_COURSE_RESOURCES WHERE COMMONID=%d",
                    id));

            while(rs.next()){

                resids.add(rs.getInt("RESOURCEID"));

            }

            rs.close();

            for(int i = 0; i < resids.size(); i++){

                reslist.add(find_resource_by_id(resids.get(i)));

            }

            return reslist;


        }catch(SQLException e){

            System.out.println("Something went wrong with find_resources_by_commonid(int id)");

        }

        return reslist;

    }

    public static Models.frontend.Person find_person_by_commonid(int id){

        Models.frontend.Person person = new Models.frontend.Person("","",PersonType.valueOf("CourseInstructor").toString());

        int idtmp = -1;

        try{

            Statement st = conn.createStatement();

            ResultSet rs = st.executeQuery(String.format("SELECT * FROM RELATION_COURSE_PERSON WHERE COMMONID = %d", id));

            while(rs.next()){

                idtmp = rs.getInt("PERSONID");
            }

            rs.close();

            person = find_person_by_id(idtmp);

            return person;

        }catch(SQLException e){

            System.out.println("Something went wrong with find_person_by_commonid(int id)");
        }

        return person;
    }

    public static Models.frontend.Person find_person_by_id(int id){

        Models.frontend.Person person = new Models.frontend.Person("","",PersonType.valueOf("CourseInstructor").toString());

        try{

            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(String.format("SELECT * FROM PERSON WHERE ID = %d", id));

            while(rs.next()){

                person.setID(id);
                person.setType(rs.getString("TYPE"));
                person.setFirstName(rs.getString("FIRSTNAME"));
                person.setLastName(rs.getString("LASTNAME"));
            }

            rs.close();

            return person;

        }catch(SQLException e){

            System.out.println("Something went wrong with find_resources_by_commonid(int id)");
        }

        return person;
    }

    public static Models.frontend.Resource find_resource_by_id(int id){

        Models.frontend.Resource resource = new Models.frontend.Resource("", -1);
        Models.frontend.Publisher publisher = new Models.frontend.Publisher("","","");
        int publisherid = -1;

        try{

            Statement st = conn.createStatement();
            Statement st2 = conn.createStatement();

            ResultSet rs = st.executeQuery(String.format("SELECT * FROM RELATION_PUBLISHER_RESOURCE WHERE RESOURCEID = %d", id));
            ResultSet rs2 = st2.executeQuery(String.format("SELECT * FROM RESOURCES WHERE ID = %d", id));


            while(rs.next()){

                publisherid = rs.getInt("PUBLISHERID");

            }

            rs.close();

            if (publisherid == -1){

                //Assign empty publisher

            }else{

                publisher = find_publisher_by_id(publisherid);

            }

            while (rs2.next()) {

                resource.setID(rs2.getInt("ID"));
                resource.setTYPE(rs2.getString("TYPE"));
                resource.setTitle(rs2.getString("TITLE"));
                resource.setEdition("EDITION");
                resource.setAuthor(rs2.getString("AUTHOR"));
                resource.setISBN(rs2.getString("ISBN"));
                resource.setISBN13(rs2.getString("ISBN13"));
                resource.setTotalAmount(rs2.getInt("TOTAL_AMOUNT"));
                resource.setCurrentAmount(rs2.getInt("CURRENT_AMOUNT"));
                resource.setDescription(rs2.getString("DESCRIPTION"));
                resource.setPublisher(publisher);

            }

            return resource;

        }catch(SQLException e){

            System.out.println("Something went wrong when trying to execute find_resource_by_id(int id)");

        }

        return resource;
    }

    public static Models.frontend.Publisher find_publisher_by_id(int id){

        Models.frontend.Publisher publisher = new Models.frontend.Publisher(-1, "", "", "");

        try{

            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(String.format("SELECT * FROM PUBLISHERS WHERE ID = %d", id));

            while(rs.next()){

                publisher.setID(rs.getInt("ID"));
                publisher.setName(rs.getString("TITLE"));
                publisher.setDescription(rs.getString("DESCRIPTION"));
                publisher.setContacts(rs.getString("CONTACT_INFO"));
            }

            rs.close();


            if(publisher.getID() == -1){

            }else{

                return publisher;
            }


        }catch(SQLException e){

            System.out.println("Something went wrong when trying to findPublisherByID(int id)");

        }

        return publisher;

    }

    public static void print_semester_by_commonid(int id){

        try{

            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(String.format("SELECT * FROM RELATION_SEMESTER_COURSE WHERE ID = %d", id));

            while(rs.next()){

                System.out.println("|" + rs.getInt("SEMESTERID") + "|");
            }

            rs.close();

        }catch(SQLException e){

            System.out.println("asd");
        }
    }

    public static void muhcode(){

        //        ArrayList<Integer> asdf = DBManager.find_classids_by_course_name("140");
//
//        int[] varname = new int[asdf.size()];
//
//        for(int i = 0; i < asdf.size(); i++){
//
//            varname[i] = asdf.get(i);
//        }
//
//        Arrays.sort(varname);
//
//        for(int i = 0; i < asdf.size(); i++){
//
//            System.out.println(asdf.get(i));
//        }
//
//        System.out.println("===========");
//
//        for(int i = 0; i < varname.length; i++){
//
//            System.out.println(varname[i]);
//        }
//
//        int[] asdf = {1,2,3,3,4};
//        Set<Integer> asd = new HashSet<>();
//
//        for(int i = 0; i < asdf.length; i++){
//
//            asd.add(asdf[i]);
//        }
//
//        for(int i = 0; i < asdf.length; i++){
//
//            System.out.println(asdf[i]);
//        }
//
//        System.out.println("=============");
//
//        asd.toArray(asdf);
//
//        for(int i = 0; i < asdf.length; i++){
//
//            System.out.println(asdf[i]);
//        }

    }


    public static void deleteResourceInDB(Models.frontend.Resource r){
        try {
            //delete in resource table
            st.executeQuery("DELETE FROM RESOURCES WHERE ID = " + r.getID());
            //delete course resource relation
            st.executeQuery("DELETE FROM RELATION_COURSE_RESOURCES WHERE RESOURCEID = " + r.getID());
            //delete person resource relation
            st.executeQuery("DELETE FROM RELATION_PERSON_RESOURCES WHERE RESOURCEID = " + r.getID());
            //delete publisher resource relation
            st.executeQuery("DELETE FROM RELATION_PUBLISHER_RESOURCE WHERE RESOURCEID = " +r.getID());
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void deletePublisherInDB(Models.frontend.Publisher p){
        try{
            //delete in publisher table
            st.executeQuery("DELETE FROM PUBLISHERS WHERE ID = " +p.getID());
            //delete publisher resource relation
            st.executeQuery("DELETE FROM RELATION_PUBLISHER_RESOURCE WHERE PUBLISHERID = " +p.getID());
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void deletePersonEveyrwhere(Models.frontend.Person person){

        ArrayList<Integer> commonIDs = new ArrayList<>();

        try{

            Statement state1 = conn.createStatement(); // get the commonids of that professor id

            ResultSet rs = state1.executeQuery(String.format("SELECT * FROM RELATION_COURSE_PERSON WHERE PERSONID = %d",
                    person.getID()));

            while (rs.next()) {
                commonIDs.add(rs.getInt("COMMONID"));
            }
            rs.close();
            state1.executeQuery("DELETE FROM RELATION_COURSE_PERSON WHERE PERSONID = " + person.getID());
            state1.executeQuery(String.format("DELETE FROM RELATION_PERSON_RESOURCES WHERE PERSONID = %d",
                    person.getID()));

            //deleting
            for(Integer commonID : commonIDs) {

                state1.executeQuery(String.format("DELETE FROM RELATION_SEMESTER_COURSE WHERE ID = %d", commonID));
                state1.executeQuery(String.format("DELETE FROM RELATION_COURSE_RESOURCES WHERE COMMONID = %d", commonID));
            }

            state1.executeQuery(String.format("DELETE FROM PERSON WHERE ID = %d", person.getID()));

        }catch(SQLException e){
            e.printStackTrace();
        }


    }

    public static ArrayList<Integer> find_classids_by_semester_id(int id){

        ArrayList<Integer> classids = new ArrayList<>();

        try{

            Statement state1 = conn.createStatement();
            ResultSet rs = state1.executeQuery(String.format("SELECT * FROM RELATION_SEMESTER_COURSE WHERE SEMESTERID = %d", id));

            while(rs.next()){

                classids.add(rs.getInt("ID"));

            }

            rs.close();

            return classids;


        }catch(SQLException e){

            System.out.println("Something went wrong @ find_classids_by_semester_id()");

        }

        return null;

    }

    public static int find_semester_id_by_commonid(int id){

        try{

            Statement state = conn.createStatement();
            ResultSet rs = state.executeQuery(String.format("SELECT * FROM RELATION_SEMESTER_COURSE WHERE ID = %d", id));

            while(rs.next()){

                return rs.getInt("SEMESTERID");

            }

            rs.close();

        }catch(SQLException e){

            System.out.println("Something went wrong with find_semester_id_by_commonid()");

        }

        return -1;

    }

    public static String[] get_semester_by_semesterid(int id){

        String[] semester = new String[2];

        try{

            Statement state = conn.createStatement();
            ResultSet rs = state.executeQuery(String.format("SELECT * FROM SEMESTER WHERE ID = %d", id));

            while(rs.next()){

                semester[0] = rs.getString("SEASON").toUpperCase();
                semester[1] = rs.getString("YEAR").toUpperCase();
            }

            rs.close();

            if(semester[0] == "SUMMER 1" || semester[0] == "SUMMER 2")
                semester[0].replace(' ', '_');

            System.out.println(semester[0]);

            return semester;

        }catch(SQLException e){

            System.out.println("Something went wrong @ get_semester_by_semesterid()");

        }

        return null;

    }

    public static void updateCRNAndNoteForClass(String CRN, String note, int commonID){
        try{
            if(!CRN.isEmpty() && !note.isEmpty()) {

                int intCRN = Integer.parseInt(CRN);
                String query = String.format("UPDATE RELATION_COURSE_PERSON SET COURSECRN = ?, COURSENOTES = ?" +
                        " WHERE COMMONID = " + commonID);
                PreparedStatement stl = conn.prepareStatement(query);
                stl.setInt(1, intCRN);
                stl.setString(2, note);
                stl.executeQuery();
            }else if(!note.isEmpty()) {

                String query = String.format("UPDATE RELATION_COURSE_PERSON SET COURSENOTES = ?" +
                        " WHERE COMMONID = " + commonID);
                PreparedStatement stl = conn.prepareStatement(query);
                stl.setString(1, note);
                stl.executeQuery();
            }else if(!CRN.isEmpty()){
                String query = String.format("UPDATE RELATION_COURSE_PERSON SET COURSECRN = ?" +
                        " WHERE COMMONID = " + commonID);
                PreparedStatement stl = conn.prepareStatement(query);
                stl.setString(1, CRN);
                stl.executeQuery();
            }


        }catch(Exception e){
            e.printStackTrace();
        }
    }


}



