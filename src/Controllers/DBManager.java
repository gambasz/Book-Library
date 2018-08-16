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
    }

    public static String readDBinfo() throws FileNotFoundException {
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
        } catch (IOException ex) {
            System.out.println(
                    "Error reading file '"
                            + fileName + "'");

        }
        return null;
    }


    public static void openConnection() throws SQLException, FileNotFoundException, ClassNotFoundException {

        String url = readDBinfo();
        if (url == null) {
            throw new FileNotFoundException();
        }
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
            if (conn != null)
                if (conn.isClosed()) {

                    System.out.println("The connection has already been closed");
                } else {
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
                semester.setYear(rs.getInt("YEAR"));
                String tempSeason = rs.getString("SEASON");
                tempSeason = tempSeason.toUpperCase();
                if (tempSeason == "SUMMER 1" || tempSeason == "SUMMER 2")
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

    public static PreparedStatement getResourceInTableQuery(int id) throws SQLException {

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
            stl.setString(1, person.getFirstName());
            stl.setString(2, person.getLastName());
            stl.setString(3, person.getType());
            rs = stl.executeQuery();

            if (rs.next()) {
                // Check if there is repetitive data in the db
                return rs.getInt("ID");

            }
            rs.close();
            String query1 = String.format("INSERT INTO PERSON (FIRSTNAME, LASTNAME, TYPE) VALUES (?, ?, ?)");
            PreparedStatement stl1 = conn.prepareStatement(query1);
            stl1.setString(1, person.getFirstName());
            stl1.setString(2, person.getLastName());
            stl1.setString(3, person.getType());
            stl1.executeQuery();


            String query2 = String.format("SELECT * FROM PERSON WHERE FIRSTNAME= ? AND LASTNAME= ? AND TYPE = ?");
            PreparedStatement stl2 = conn.prepareStatement(query2);
            stl2.setString(1, person.getFirstName());
            stl2.setString(2, person.getLastName());
            stl2.setString(3, person.getType());

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
            stl.setString(1, cSplit[0]);
            stl.setString(2, cSplit[1]);
            rs = stl.executeQuery();


            if (rs.next()) {
                id = (rs.getInt(1));
                return id;
            }
            rs.close();
            String query = String.format("INSERT INTO COURSECT (TITLE, CNUMBER, DESCRIPTION, DEPARTMENT) VALUES " +
                    "(?, ?, ?, ?)");
            PreparedStatement stl2 = conn.prepareStatement(query);
            stl2.setString(1, cSplit[0]);
            stl2.setString(2, cSplit[1]);
            stl2.setString(3, course.getDescription());
            stl2.setString(4, course.getDepartment());
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
        try {
            PreparedStatement stl = conn.prepareStatement(query);
            stl.setString(1, publisher.getTitle());
            stl.setString(2, publisher.getContactInformation());
            stl.setString(3, publisher.getDescription());
            stl.executeQuery();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //============================================Update Methods Queries================================================


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
            String courseTitle = "", courseNumber = "";

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


    public static void updateResource(Resource resource) {

        String query = String.format("UPDATE RESOURCES SET TYPE = ?, TITLE = ?, AUTHOR = ?, ISBN = ?, " +
                "TOTAL_AMOUNT = ?, CURRENT_AMOUNT = ?, DESCRIPTION = ?, ISBN13 = ?," +
                "EDITION = ? WHERE ID = ?");
        try {
            PreparedStatement stl = conn.prepareStatement(query);
            stl.setString(1, resource.getType());
            stl.setString(2, resource.getTitle());
            stl.setString(3, resource.getAuthor());
            stl.setString(4, resource.getISBN());
            stl.setInt(5, resource.getTotalAmount());
            stl.setInt(6, resource.getCurrentAmount());
            stl.setString(7, resource.getDescription());
            stl.setString(8, resource.getIsbn13());
            stl.setString(9, resource.getEdition());
            stl.setInt(10, resource.getID());
            stl.executeQuery();

        } catch (Exception e) {
            e.printStackTrace();
        }

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


    public static void getHistory() throws SQLException {
        int i = 0;
        StringBuilder lsd = new StringBuilder();
        ResultSet ts3 = st.executeQuery("select v.SQL_TEXT,\n" +
                "           v.FIRST_LOAD_TIME,\n" +
                "           v.DISK_READS,\n" +
                "           v.ROWS_PROCESSED,\n" +
                "           v.ELAPSED_TIME\n" +
                "           from v$sql v\n" +
                "where to_date(v.FIRST_LOAD_TIME,'YYYY-MM-DD hh24:mi:ss')>ADD_MONTHS(trunc(sysdate,'MM'),-1) " +
                " ORDER BY v.FIRST_LOAD_TIME DESC");
        while (ts3.next()) {
            i++;
            if (i < 800)
                continue;

            lsd.append(String.format("%s: ", Integer.toString(i)));
            lsd.append(ts3.getString("SQL_TEXT") + "\t" + ts3.getString("FIRST_LOAD_TIME") + "\n");
            lsd.append(String.format("____________________________\n"));
        }
        ts3.close();
        try (PrintWriter out = new PrintWriter("DBHistory.txt")) {
            out.println(lsd.toString());

        } catch (Exception e) {
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
                    resource1.getID()));

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

    public static ArrayList<Models.frontend.Resource> findResourcesCourse2(int courseID, int commonID, Map<Integer, Models.frontend.Resource> tempCach) {

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
                if (tempCach.containsKey(resourceID))
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
                        if (rss.getString("EDITION") != "")
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

    public static ArrayList<Models.frontend.Course> relationalReadByCourseID2(Map<Integer, ArrayList<Integer>> courseIds, Semester semester) {
        long startTime = System.nanoTime();
        String tempSemester = semester.getSeason(),
                courseNote = null;
        tempSemester = tempSemester.toUpperCase();
        tempSemester = tempSemester.replace(' ', '_');
        semester.setSeason(tempSemester);


        ArrayList<Models.frontend.Course> courseList = new ArrayList<>();

        Map<Integer, Models.frontend.Person> cachedPersons = new HashMap<Integer, Models.frontend.Person>();
        Map<Integer, Models.frontend.Resource> cachedResources = new HashMap<Integer, Models.frontend.Resource>();

        //Map<Integer, Course> cachedCourses = new HashMap<Integer, Course>();  Think about caching course later on


        int personID = 0, i, cID = 0, courseID = 0;
        Integer CRN = null;
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
            for (Map.Entry<Integer, ArrayList<Integer>> entry : courseIds.entrySet()) {
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
                } else return courseList;

                //=======================Finding and creating Person teaching the course=============================


                for (Integer tempCommonID : entry.getValue()) {
                    // Big O (commonIDs.Size)

                    rsTmp = stTemp2.executeQuery("SELECT * FROM RELATION_COURSE_PERSON WHERE COMMONID = " +
                            tempCommonID);

                    if (rsTmp.next()) {
                        System.out.println("semester: " + semester.getSeason());
                        courseList.add(new Models.frontend.Course(cID, tempCommonID, semester.getYear(), semester.getSeason(),
                                cTitle, cDepartment, personTmp, cDescription, courseResources));
                        courseList.get(i).setCommonID(tempCommonID);

                        personID = rsTmp.getInt("PERSONID");


                        rsTmp.getString("COURSENOTES");
                        if (!rsTmp.wasNull())
                            courseNote = rsTmp.getString("COURSENOTES");

                        rsTmp.getObject("COURSECRN");
                        if (!rsTmp.wasNull())
                            CRN = rsTmp.getInt("COURSECRN");


                        if (cachedPersons.containsKey(personID))

                            courseList.get(i).setProfessor(cachedPersons.get(personID));

                        else {
                            rsTemp = stTemp.executeQuery(getPersonInTableQuery(personID));
                            if (rsTemp.next()) {

                                PersonType enumTmp;
                                switch (rsTemp.getString("TYPE")) {
                                    case "ProgramCoordinator":
                                        enumTmp = PersonType.ProgramCoordinator;
                                        break;
                                    case "CourseCoordinator":
                                        enumTmp = PersonType.CourseCoordinator;
                                        break;
                                    case "CourseInstructor":
                                        enumTmp = PersonType.CourseInstructor;
                                        break;
                                    default:
                                        enumTmp = PersonType.CourseInstructor;
                                }

                                personTmp = new Models.frontend.Person(rsTemp.getString("LASTNAME"),
                                        rsTemp.getString("FIRSTNAME"), personID,
                                        enumTmp.toString());

                                cachedPersons.put(personID, personTmp);

                                courseList.get(i).setProfessor(personTmp);
                            } else {
                                personTmp = new Models.frontend.Person("NOT FOUND",
                                        "NOT FOUND", personID, "CourseInstructor");
                                courseList.get(i).setProfessor(personTmp);
                            }

                        }
                        //=======================Finding and creating course resources=============================


                        courseResources = findResourcesCourse2(courseID, tempCommonID, cachedResources);
                        courseList.get(i).setResource(courseResources);
                        if (CRN != null) {
                            courseList.get(i).setCRN(CRN.intValue());
                            CRN = null;
                        }
                        if (courseNote != null) {
                            courseList.get(i).setNotes(courseNote);
                            courseNote = null;
                        }
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

        int before = 0;
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

                } else {
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
        int personid = c.getProfessor().getID(), commonID = 0;

        semester = semester.toLowerCase();
        semester = semester.substring(0, 1).toUpperCase() + semester.substring(1);
        semester = semester.replace('_', ' ');

        int semesterid = getSemesterIDByName(semester, year);

        System.out.println("Semester: " + semester + " ID foudn: " + semesterid);

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
            if (rs.next()) {
                commonID = rs.getInt("ID");
            }


            executeNoReturnQuery(String.format("INSERT INTO RELATION_COURSE_PERSON" +
                    " (COURSEID, PERSONID) VALUES ('%d', '%d')", id, personid));

            for (int j = 0; j < resourceidlist.length; j++) {

                executeNoReturnQuery(String.format("INSERT INTO RELATION_COURSE_RESOURCES" +
                        " (COURSEID, RESOURCEID) VALUES ('%d', '%d')", id, resourceidlist[j]));
            }


            for (int l = 0; l < resourceidlist.length; l++) {
                if (r.get(l).getPublisher() != null) {
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

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return c;

    }

    public static void deleteRelationCourseResources(Models.frontend.Course c) {

        try {

            String query = String.format("DELETE FROM RELATION_COURSE_RESOURCES WHERE" +
                    " COMMONID = ?");
            PreparedStatement stl = conn.prepareStatement(query);
            stl.setInt(1, c.getCommonID());
            stl.executeQuery();

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
            } else {
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
            stl.setString(1, title);
            ResultSet rs = stl.executeQuery();

            if (rs.next()) {
                pubID = rs.getInt(1);
                p.setID(pubID);
                if (!rs.getString("DESCRIPTION").equals(descrip) ||
                        !rs.getString("CONTACT_INFO").equals(info)) {
                    query = String.format("UPDATE PUBLISHERS SET DESCRIPTION = ?, CONTACT_INFO = ?" +
                            " WHERE ID = ?");
                    stl = conn.prepareStatement(query);
                    stl.setString(1, descrip);
                    stl.setString(2, info);
                    stl.setInt(3, rs.getInt("ID"));

                    stl.executeQuery();
                }
                rs.close();

                return true;
            } else {


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
                if (rs.getString("ISBN13") != null)
                    p.setISBN13(rs.getString("ISBN13"));
                if (rs.getString("EDITION") != null)
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


    public static void updateCoursePersonSemester(Models.frontend.Course course) {

        try {
            int semesterID = 0;
            semesterID = getSemesterIDByName(controller.convertSeasonGUItoDB(course.getSEMESTER()),
                    String.valueOf(course.getYEAR()));


            //Updating Semester_Course
            String queryString = String.format("UPDATE RELATION_SEMESTER_COURSE SET COURSEID = ?, SEMESTERID = ?" +
                    " WHERE ID = ?");
            PreparedStatement stl = conn.prepareStatement(queryString);
            stl.setInt(1, course.getID());
            stl.setInt(2, semesterID);
            stl.setInt(3, course.getCommonID());
            stl.executeQuery();

            // Updatin Course_Perosn
            String query = String.format("UPDATE RELATION_COURSE_PERSON SET COURSEID = ?, PERSONID = ? WHERE " +
                    "COMMONID = ?");
            PreparedStatement stl1 = conn.prepareStatement(query);
            stl1.setInt(1, course.getID());
            stl1.setInt(2, course.getProfessor().getID());
            stl1.setInt(3, course.getCommonID());
            stl1.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
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
            stl.setString(1, title);
            stl.setString(2, cnumber);
            ResultSet rs = stl.executeQuery();

            while (rs.next()) {

                id = rs.getInt("id");
            }

            if (id == -1) {

                String query1 = String.format("INSERT INTO COURSECT (TITLE, CNUMBER, DESCRIPTION, DEPARTMENT) VALUES " +
                        "(?,?, ?, ?, ?)");
                PreparedStatement stl1 = conn.prepareStatement(query1);
                stl1.setString(1, title);
                stl1.setString(2, cnumber);
                stl1.setString(3, c.getDescription());
                stl1.setString(4, c.getDepartment());

                stl1.executeQuery();


                String query2 = String.format("SELECT * FROM COURSECT WHERE TITLE = ? AND CNUMBER = ?");
                PreparedStatement stl2 = conn.prepareStatement(query2);
                stl2.setString(1, title);
                stl2.setString(2, cnumber);
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
        p.setFirstName(p.getFirstName().substring(0, 1).toUpperCase() + p.getFirstName().substring(1).toLowerCase());
        p.setLastName(p.getLastName().substring(0, 1).toUpperCase() + p.getLastName().substring(1).toLowerCase());
        System.out.println("Find this person " + p.getFirstName());

        int id = -1;

        try {


            String query = String.format("SELECT * FROM PERSON WHERE FIRSTNAME = ? AND LASTNAME = ?");
            PreparedStatement stl = conn.prepareStatement(query);
            stl.setString(1, p.getFirstName());
            stl.setString(2, p.getLastName());

            ResultSet rs = stl.executeQuery();

            while (rs.next()) {

                id = rs.getInt("ID");
            }

            if (id == -1) {

                String query1 = String.format("INSERT INTO PERSON (FIRSTNAME, LASTNAME, TYPE) VALUES(?, ?, ?)");
                PreparedStatement stl1 = conn.prepareStatement(query1);
                stl1.setString(1, p.getFirstName());
                stl1.setString(2, p.getLastName());
                stl1.setString(3, p.getType());
                stl1.executeQuery();

                String query2 = String.format("SELECT * FROM PERSON WHERE FIRSTNAME = ? AND LASTNAME = ?");
                PreparedStatement stl2 = conn.prepareStatement(query2);
                stl2.setString(1, p.getFirstName());
                stl2.setString(2, p.getLastName());
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
        int commonID = 0, before = 0;
        ArrayList<Models.frontend.Resource> resourcesList = new ArrayList<Models.frontend.Resource>();
        ArrayList<Integer> listOfIDs = new ArrayList<Integer>();
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resourcesList;
    }


    public static ArrayList<Integer> find_classids_by_resource_name(String name) {

        ArrayList<Integer> resourceids = new ArrayList<>();
        ArrayList<Integer> classids = new ArrayList<>();

        try {

            Statement statement = conn.createStatement();
            Statement statement2 = conn.createStatement();
//            ResultSet rs = statement.executeQuery("SELECT * FROM RESOURCES WHERE TITLE LIKE '%"+name+"%' OR AUTHOR LIKE '%"+name+"%'");

            String queryl = String.format("SELECT * FROM RESOURCES WHERE TITLE LIKE ? OR AUTHOR LIKE ?");
            PreparedStatement stl = conn.prepareStatement(queryl);
            stl.setString(1, "%" + name + "%");
            stl.setString(2, "%" + name + "%");
            ResultSet rs = stl.executeQuery();


            while (rs.next()) {

                resourceids.add(rs.getInt("ID"));
            }

            rs.close();

            ResultSet rs2;

            for (int i = 0; i < resourceids.size(); i++) {

                rs2 = statement2.executeQuery(String.format("SELECT * FROM RELATION_COURSE_RESOURCES WHERE RESOURCEID = %d", resourceids.get(i)));

                while (rs2.next()) {

                    classids.add(rs2.getInt("COMMONID"));
                }

                rs2.close();
            }


            return classids;

        } catch (SQLException e) {
            System.out.println("Something went wrong with find_classids_by_resource_name()");
        }

        return classids;
    }

    public static ArrayList<Integer> find_classids_by_course_name(String courseTitle, String name) {

        ArrayList<Integer> courseids = new ArrayList<>();
        ArrayList<Integer> classids = new ArrayList<>();
        ArrayList<Integer> tmp;


        try {

            Statement st = conn.createStatement();
//            ResultSet rs = st.executeQuery("SELECT * FROM COURSECT WHERE CNUMBER LIKE  '" +
//                        name + "%' AND TITLE LIKE '" + courseTitle+ "%' " );

            String queryl = String.format("SELECT * FROM COURSECT WHERE CNUMBER LIKE ? AND TITLE LIKE ?");
            PreparedStatement stl = conn.prepareStatement(queryl);
            stl.setString(1, "%" + name + "%");
            stl.setString(2, "%" + courseTitle + "%");
            ResultSet rs = stl.executeQuery();

            while (rs.next()) {

                courseids.add(rs.getInt("ID"));
            }

            rs.close();


            for (int i = 0; i < courseids.size(); i++) {

                tmp = find_classids_by_courseid(courseids.get(i));
                for (int j = 0; j < tmp.size(); j++) {

                    classids.add(tmp.get(j));
                }

            }

            return classids;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Something went wrong with find_classes_by_course_name(String name)");
        }

        return classids;


    }

    public static void insertRelationResourcePublisher(Models.frontend.Resource resource) {
        int resID = 0;
        try {

            ResultSet rs = st.executeQuery("SELECT * FROM RELATION_PUBLISHER_RESOURCE WHERE PUBLISHERID = " +
                    resource.getPublisher().getID() + " AND RESOURCEID = " + resource.getID());

            while (rs.next()) {
                resID = rs.getInt(2);
            }

            if (resID == 0) {
                st.executeQuery(String.format("INSERT INTO RELATION_PUBLISHER_RESOURCE (PUBLISHERID, RESOURCEID) VALUES " +
                        "('%d','%d')", resource.getPublisher().getID(), resource.getID()));
            }

            rs.close();


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static ArrayList<Integer> find_classids_by_courseid(int id) {

        ArrayList<Integer> classids = new ArrayList<>();

        try {

            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(String.format("SELECT * FROM RELATION_SEMESTER_COURSE WHERE COURSEID = %d", id));

            while (rs.next()) {

                classids.add(rs.getInt("ID"));
            }

            rs.close();

            return classids;

        } catch (SQLException e) {

            System.out.println("Something went wrong with find_classid_by_courseid(int id)");
        }

        return classids;
    }


    public static ArrayList<Integer> find_classids_by_professor_name(String name) {

        ArrayList<Integer> courseids = new ArrayList<>();
        ArrayList<Integer> personids = new ArrayList<>();

        try {

            Statement st = conn.createStatement();
            Statement st2 = conn.createStatement();
            ResultSet rs2;

            String queryl = String.format("SELECT * FROM PERSON WHERE FIRSTNAME LIKE ? OR LASTNAME LIKE ?");
            PreparedStatement stl = conn.prepareStatement(queryl);
            stl.setString(1, "%" + name + "%");
            stl.setString(2, "%" + name + "%");
            ResultSet rs = stl.executeQuery();

            while (rs.next()) {

                personids.add(rs.getInt("ID"));
            }

            rs.close();


            for (int i = 0; i < personids.size(); i++) {

                rs2 = st2.executeQuery(String.format("SELECT * FROM RELATION_COURSE_PERSON WHERE PERSONID = %d", personids.get(i)));

                while (rs2.next()) {

                    courseids.add(rs2.getInt("COMMONID"));
                }

                rs2.close();
            }

            return courseids;


        } catch (SQLException e) {

            System.out.println("Something went wrong with find_course_by_professor_name(String name)");
        }

        return courseids;
    }

    public static Models.frontend.Course find_class_by_commonid(int id) {

        Models.frontend.Person person = find_person_by_commonid(id);
        Models.frontend.Course course = new Models.frontend.Course();
        ArrayList<Models.frontend.Resource> resources = find_resources_by_commonid(id);


        int courseid = -1;

        try {


            String query = String.format("SELECT * FROM RELATION_SEMESTER_COURSE WHERE ID = ?");
            PreparedStatement stl = conn.prepareStatement(query);
            stl.setString(1, String.valueOf(id));
            ResultSet testing = stl.executeQuery();

            while (testing.next()) {

                courseid = testing.getInt("COURSEID");

            }

            Statement st2 = conn.createStatement();


            ResultSet rs2 = st.executeQuery(String.format("SELECT * FROM COURSECT WHERE ID = %d", courseid));
            course.setCommonID(id);

            String[] semester = new String[2];
            semester = get_semester_by_semesterid(find_semester_id_by_commonid(course.getCommonID()));

            while (rs2.next()) {

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

        } catch (SQLException e) {

            System.out.println("Something went wrong with find_class_by_commonid()");
        }

        return course;
    }


    public static ArrayList<Models.frontend.Resource> find_resources_by_commonid(int id) {

        ArrayList<Integer> resids = new ArrayList<>();
        ArrayList<Models.frontend.Resource> reslist = new ArrayList<>();

        try {

            Statement st = conn.createStatement();

            ResultSet rs = st.executeQuery(String.format("SELECT * FROM RELATION_COURSE_RESOURCES WHERE COMMONID=%d",
                    id));

            while (rs.next()) {

                resids.add(rs.getInt("RESOURCEID"));

            }

            rs.close();

            for (int i = 0; i < resids.size(); i++) {

                reslist.add(find_resource_by_id(resids.get(i)));

            }

            return reslist;


        } catch (SQLException e) {

            System.out.println("Something went wrong with find_resources_by_commonid(int id)");

        }

        return reslist;

    }

    public static Models.frontend.Person find_person_by_commonid(int id) {

        Models.frontend.Person person = new Models.frontend.Person("", "", PersonType.valueOf("CourseInstructor").toString());

        int idtmp = -1;

        try {

            Statement st = conn.createStatement();

            ResultSet rs = st.executeQuery(String.format("SELECT * FROM RELATION_COURSE_PERSON WHERE COMMONID = %d", id));

            while (rs.next()) {

                idtmp = rs.getInt("PERSONID");
            }

            rs.close();

            person = find_person_by_id(idtmp);

            return person;

        } catch (SQLException e) {

            System.out.println("Something went wrong with find_person_by_commonid(int id)");
        }

        return person;
    }

    public static Models.frontend.Person find_person_by_id(int id) {

        Models.frontend.Person person = new Models.frontend.Person("", "", PersonType.valueOf("CourseInstructor").toString());

        try {

            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(String.format("SELECT * FROM PERSON WHERE ID = %d", id));

            while (rs.next()) {

                person.setID(id);
                person.setType(rs.getString("TYPE"));
                person.setFirstName(rs.getString("FIRSTNAME"));
                person.setLastName(rs.getString("LASTNAME"));
            }

            rs.close();

            return person;

        } catch (SQLException e) {

            System.out.println("Something went wrong with find_resources_by_commonid(int id)");
        }

        return person;
    }

    public static Models.frontend.Resource find_resource_by_id(int id) {

        Models.frontend.Resource resource = new Models.frontend.Resource("", -1);
        Models.frontend.Publisher publisher = new Models.frontend.Publisher("", "", "");
        int publisherid = -1;

        try {

            Statement st = conn.createStatement();
            Statement st2 = conn.createStatement();

            ResultSet rs = st.executeQuery(String.format("SELECT * FROM RELATION_PUBLISHER_RESOURCE WHERE RESOURCEID = %d", id));
            ResultSet rs2 = st2.executeQuery(String.format("SELECT * FROM RESOURCES WHERE ID = %d", id));


            while (rs.next()) {

                publisherid = rs.getInt("PUBLISHERID");

            }

            rs.close();

            if (publisherid == -1) {

                //Assign empty publisher

            } else {

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

        } catch (SQLException e) {

            System.out.println("Something went wrong when trying to execute find_resource_by_id(int id)");

        }

        return resource;
    }

    public static Models.frontend.Publisher find_publisher_by_id(int id) {

        Models.frontend.Publisher publisher = new Models.frontend.Publisher(-1, "", "", "");

        try {

            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(String.format("SELECT * FROM PUBLISHERS WHERE ID = %d", id));

            while (rs.next()) {

                publisher.setID(rs.getInt("ID"));
                publisher.setName(rs.getString("TITLE"));
                publisher.setDescription(rs.getString("DESCRIPTION"));
                publisher.setContacts(rs.getString("CONTACT_INFO"));
            }

            rs.close();


            if (publisher.getID() == -1) {

            } else {

                return publisher;
            }


        } catch (SQLException e) {

            System.out.println("Something went wrong when trying to findPublisherByID(int id)");

        }

        return publisher;

    }


    public static void deleteResourceInDB(Models.frontend.Resource r) {
        try {
            //delete in resource table
            st.executeQuery("DELETE FROM RESOURCES WHERE ID = " + r.getID());
            //delete course resource relation
            st.executeQuery("DELETE FROM RELATION_COURSE_RESOURCES WHERE RESOURCEID = " + r.getID());
            //delete person resource relation
            st.executeQuery("DELETE FROM RELATION_PERSON_RESOURCES WHERE RESOURCEID = " + r.getID());
            //delete publisher resource relation
            st.executeQuery("DELETE FROM RELATION_PUBLISHER_RESOURCE WHERE RESOURCEID = " + r.getID());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void deletePublisherInDB(Models.frontend.Publisher p) {
        try {
            //delete in publisher table
            st.executeQuery("DELETE FROM PUBLISHERS WHERE ID = " + p.getID());
            //delete publisher resource relation
            st.executeQuery("DELETE FROM RELATION_PUBLISHER_RESOURCE WHERE PUBLISHERID = " + p.getID());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void deletePersonEveyrwhere(Models.frontend.Person person) {

        ArrayList<Integer> commonIDs = new ArrayList<>();

        try {

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
            for (Integer commonID : commonIDs) {

                state1.executeQuery(String.format("DELETE FROM RELATION_SEMESTER_COURSE WHERE ID = %d", commonID));
                state1.executeQuery(String.format("DELETE FROM RELATION_COURSE_RESOURCES WHERE COMMONID = %d", commonID));
            }

            state1.executeQuery(String.format("DELETE FROM PERSON WHERE ID = %d", person.getID()));

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public static ArrayList<Integer> find_classids_by_semester_id(int id) {

        ArrayList<Integer> classids = new ArrayList<>();

        try {

            Statement state1 = conn.createStatement();
            ResultSet rs = state1.executeQuery(String.format("SELECT * FROM RELATION_SEMESTER_COURSE WHERE SEMESTERID = %d", id));

            while (rs.next()) {

                classids.add(rs.getInt("ID"));

            }

            rs.close();

            return classids;


        } catch (SQLException e) {

            System.out.println("Something went wrong @ find_classids_by_semester_id()");

        }

        return null;

    }

    public static int find_semester_id_by_commonid(int id) {

        try {

            Statement state = conn.createStatement();
            ResultSet rs = state.executeQuery(String.format("SELECT * FROM RELATION_SEMESTER_COURSE WHERE ID = %d", id));

            while (rs.next()) {

                return rs.getInt("SEMESTERID");

            }

            rs.close();

        } catch (SQLException e) {

            System.out.println("Something went wrong with find_semester_id_by_commonid()");

        }

        return -1;

    }

    public static String[] get_semester_by_semesterid(int id) {

        String[] semester = new String[2];

        try {

            Statement state = conn.createStatement();
            ResultSet rs = state.executeQuery(String.format("SELECT * FROM SEMESTER WHERE ID = %d", id));

            while (rs.next()) {

                semester[0] = rs.getString("SEASON").toUpperCase();
                semester[1] = rs.getString("YEAR").toUpperCase();
            }

            rs.close();

            if (semester[0] == "SUMMER 1" || semester[0] == "SUMMER 2")
                semester[0].replace(' ', '_');

            System.out.println(semester[0]);

            return semester;

        } catch (SQLException e) {

            System.out.println("Something went wrong @ get_semester_by_semesterid()");

        }

        return null;

    }

    public static void updateCRNAndNoteForClass(String CRN, String note, int commonID) {
        try {
            if (note != null && !note.isEmpty()) {

                String query = String.format("UPDATE RELATION_COURSE_PERSON SET COURSENOTES = ?" +
                        " WHERE COMMONID = " + commonID);
                PreparedStatement stl = conn.prepareStatement(query);
                stl.setString(1, note);
                stl.executeQuery();
            }
            if (CRN != null && !CRN.isEmpty()) {
                String query = String.format("UPDATE RELATION_COURSE_PERSON SET COURSECRN = ?" +
                        " WHERE COMMONID = " + commonID);
                PreparedStatement stl = conn.prepareStatement(query);
                stl.setString(1, CRN);
                stl.executeQuery();
            }


        } catch (
                Exception e)

        {
            e.printStackTrace();
        }
    }


}



