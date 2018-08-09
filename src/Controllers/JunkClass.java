package Controllers;

import Models.backend.Course;
import Models.backend.Person;
import Models.backend.Publisher;
import Models.backend.Resource;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class JunkClass {


// **********************











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

    public static String updatePersonQuery(Person person) {

        return String.format("UPDATE PERSON SET FIRSTNAME = '%s', LASTNAME = '%s', TYPE = '%s' WHERE ID = %d",
                person.getFirstName(), person.getLastName(), person.getType(), person.getID());
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


    //==================================

    //Regular Tables







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

    













    //**









             /* String query1 = "CREATE TABLE SEMESTER (id NUMBER(10) NOT NULL, title VARCHAR2(100) NOT NULL)";

        String tableCreateQuery = "create table JBT(name varchar2(10), course varchar2(10))";

         String query2 = "ALTER TABLE SEMESTER\n" +
                "  ADD (\n" +
                "    CONSTRAINT semester_pk PRIMARY KEY  (id));";

         String query3 = "CREATE SEQUENCE semester_sequence;";

         String query4 = "CREATE OR REPLACE TRIGGER semester_on_insert\n" +
                "  BEFORE INSERT ON SEMESTER\n" +
                "  FOR EACH ROW\n" +
                "BEGIN\n" +
                "  SELECT semester_sequence.nextval\n" +
                "  INTO :new.id\n" +
                "  FROM dual;\n" +
                "END;\n"; */

    // Currently using 'old' format of URL for connecting to the database.
    // We should consider changing the format (we need to request a way to coneect without the use of SID.

    // How I figured it out: https://stackoverflow.com/questions/4832056/java-jdbc-how-to-connect-to-oracle-using-service-name-instead-of-sid


    //String testSQL = "INSERT INTO BOOKS (title) VALUES ('asrasdas')";
    //String testSQL2 = "DELETE FROM BOOKS WHERE ID=2";

    //System.out.println(testSQL);

    //test.executeQuery(query1);

    //step3 create the statement object
       /* Statement stmt=conn.createStatement();

        //step4 execute query

        ResultSet rs=stmt.executeQuery("SELECT * FROM PERSON");
        while(rs.next()) {
            System.out.println(rs.getInt(1) + "  " + rs.getString(2) + "  " + rs.getString(3) + " " + rs.getString(4));
        } */

    //step5 close the connection object

       /* public static void createConnectionToDB(){

        try{

            // Currently using 'old' format of URL for connecting to the database.
            // We should consider changing the format (we need to request a way to coneect without the use of SID.

            Connection conn = DriverManager.getConnection("jdbc:oracle:thin:USERNAME/PASSWORD@HOST:PORT:SID");

            System.out.println("Successfully connected to the database! (I guess...)");

            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    */

  /* public static void testInsert(){

       System.out.println("sup");
   }

   public static String getTableQuery(String table){

       return "SELECT * FROM " + table;

   }

   public static String insertIntoPerson(String firstname, String lastname, String type){



       return "";

       }

       public void createTable(){



       }


   } */

/*

https://stackoverflow.com/questions/4832056/java-jdbc-how-to-connect-to-oracle-using-service-name-instead-of-sid

 */

   /*  String insertTableSQL = "INSERT INTO PERSON"
                    + "(ID, FIRSTNAME, LASTNAME, TYPE) " + "VALUES"
                    + "(1,'Guido-was-here','Guido-was-here','Guido-was-here'))";

            String testSQL = "INSERT INTO PERSON VALUES (1, 'Guido-was-here', 'Guido-was-here', 'Guido-was-here')";

            Statement test = conn.createStatement();

            System.out.println(testSQL);

            test.executeQuery(testSQL);
            */
}

      /* try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
*/
      /*


           //System.out.print(asd.addPersonQuery(guido));
            //System.out.println(asd.getIDInQuery("PERSON", 1));
            //System.out.println(tom.newFNameByID(2, "mamali"));
            //test.executeQuery(asd.addPersonQuery(tom));
            //test.executeQuery(asd.getIDInQuery("PERSON", 1));
            //test.executeQuery(tom.newFNameByID(2, "mamali"));
            //ResultSet rs = test.executeQuery(asd.getIDInQuery("PERSON", 1));
            //ResultSet rs = test.executeQuery(getTableQuery("PERSON"));
            /*
            while(rs.next()) {
                System.out.println(rs.getInt(1) + " " + rs.getString(2) + " " + rs.getString(3) + " " + rs.getString(4));
            }
            */
//st.executeQuery(p.addToDB());

//ResultSet rs = test.executeQuery(getTableQuery("PERSON"));
            /*
            while(rs.next()) {
                System.out.println(rs.getInt(1) + " " + rs.getString(2) + " " + rs.getString(3) + " " + rs.getString(4));
            }
            */

