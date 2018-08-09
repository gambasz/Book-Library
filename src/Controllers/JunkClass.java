package Controllers;

import Models.backend.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import static Controllers.DBManager.*;

public class JunkClass {

//
//// **********************
//
//
//
//
//
//
//
//    public static ArrayList<Course> relationalReadByCourseID(int courseID) {
//        long startTime = System.nanoTime();
//
//        // This method is only accept ONE courseID and will find all relations to that course
//        //So you may need to call the function N times with different courseID to get all information stored in table
//        //Course[] courseArray = new Course[20]; //Will make it a dynamic array list
//        ArrayList<Course> courseList = new ArrayList<>();
//        ResultSet rsTmp;
//
//        int personID = 0, i = 0, pID = 0, cID = 0, commonID = 0;
//        int[] pr = new int[20], cr = new int[20];
//        ResultSet rs;
//        String cTitle = "", cDescription = "", cDepartment = "";
//
//        Person personTmp = new Person();
//        //Resource[] courseResources = new Resource[20];
//        ArrayList<Resource> courseResources = new ArrayList<Resource>();
//
//        try {
//            Statement stTemp = conn.createStatement();
//            Statement stTemp2 = conn.createStatement();
//
//            ResultSet rsTemp;
//
//
//            //=======================Getting information to create the course object====================================
//
//            rs = st.executeQuery(getCourseInTableQuery(courseID));
//
//            while (rs.next()) {
//
//                cTitle = rs.getString("TITLE") + " " + rs.getString("CNUMBER");
//                cID = rs.getInt("ID");
//                cDescription = rs.getString("DESCRIPTION");
//                cDepartment = rs.getString("DEPARTMENT");
//                System.out.println("\ncourseID " + courseID);
//            }
//            //courseResources = findResourcesCourse(courseID);
//
//
//            //=======================Finding and creating Persons list teaching that course=============================
//
//            i = 0;
//            rsTmp = stTemp2.executeQuery("SELECT * FROM RELATION_COURSE_PERSON WHERE COURSEID = " + courseID);
//            //it supposed to get a list of all persons teaching that course. Assuming one person for now.
//
//            int j = 0;
//            while (rsTmp.next()) {
//
//                //courseArray[i] = new Course(cID, cTitle, cDepartment, cDescription, "CRN");
//                courseList.add(new Course(cID, cTitle, cDescription, cDepartment, "CRN"));
//                System.out.println("\nThis is the: " + j);
//                j++;
//
//                personID = rsTmp.getInt(2);
//                commonID = rsTmp.getInt("commonid");
//                System.out.println("Commonid is: " + commonID);
//                System.out.println("PersonID is: " + personID);
//                courseList.get(i).setCommonID(commonID);
//
//
//                rsTemp = stTemp.executeQuery(getPersonInTableQuery(personID));
//                while (rsTemp.next()) {
//                    // I'm getting all the info for each person,
//                    // this loop would run n times for n persons, but must be only one result
//                    //meaning the loop runs only once each time.
//
//                    personTmp = new Person(personID, rsTemp.getString(3), rsTemp.getString(4),
//                            rsTemp.getString(2));
//                    personTmp.setCommonid(commonID);
//
//
//                    personTmp = setResourcesForPerson(personTmp);
//                    //courseArray[i].setPersonInstance(personTmp);
//                    //courseArray[i].setResourceInstance(courseResources);
//                    //courseArray[i].setResourceInstances(courseResources);
//                    courseList.get(i).setPersonInstance(personTmp);
//
//                    //mneed to check for resources with the same commonid
//                    courseResources = findResourcesCourse(courseID, commonID);
//                    courseList.get(i).setResourceInstances(courseResources);
//
//                    i++;
//                }
//
//
//            }
//
//            long endTime = System.nanoTime();
//            long duration = (endTime - startTime);
//            System.out.println("It took this time to run Read Relational: " + duration / 1000000 + "ms For " + courseList.size() + " courses.\n");
//            return courseList;
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//
//    public static ArrayList<Resource> findResourcesCourse(int courseID, int commonID) {
//
//        ResultSet rs;
//        int resourceID = 0;
//        int i = 0;
//        ArrayList<Resource> listResources = new ArrayList<Resource>();
//
//        try {
//
//            rs = st.executeQuery(String.format("SELECT * FROM RELATION_COURSE_RESOURCES WHERE COURSEID = %d AND COMMONID = %d",
//                    courseID, commonID));
//            //here all have a result set of all resources for courdeID
//
//            while (rs.next()) {
//                resourceID = rs.getInt(2);
//                ResultSet rss = getResourceInTableQuery(resourceID).executeQuery();
//
//                while (rss.next()) {
//                    // ID, Type, Title, Author, ISBN, total, current, desc
//                    listResources.add(new Resource(rss.getInt(1), rss.getString(2),
//                            rss.getString(3), rss.getString(4), rss.getString(5),
//                            rss.getInt(6), rss.getInt(7), rss.getString(8)));
//                    listResources.get(i).setCommonID(commonID);
//                    setPublisherForResource(listResources.get(i));
//
//                    i++;
//                }
//
//            }
//
//            return listResources;
//        } catch (SQLException err) {
//            System.out.println(err);
//            err.printStackTrace();
//        }
//        // Adding the list of the resources to the person object
//        return null;
//
//    }
//
//
//    // Relationship Tables
//    public static void printTableCoursePerson() {
//
//        try {
//
//            ResultSet rs = st.executeQuery(getTableCoursePersonQuery());
//            while (rs.next()) {
//
//                System.out.println(rs.getInt(1) + "|" + rs.getInt(2));
//            }
//
//        } catch (SQLException e) {
//
//            e.printStackTrace();
//            System.out.println("Exception when executing printTableCoursePerson() method.");
//        }
//
//    }
//
//    public static void printTableCourseResource() {
//
//        try {
//
//            ResultSet rs = st.executeQuery(getTableCourseResourceQuery());
//            while (rs.next()) {
//
//                System.out.println(rs.getInt(1) + "|" + rs.getInt(2));
//            }
//
//        } catch (SQLException e) {
//
//            e.printStackTrace();
//            System.out.println("Exception when executing printTableCourseResource() method.");
//        }
//    }
//
//    public static void printTableCourseSemester() {
//
//        try {
//
//            ResultSet rs = st.executeQuery(getTableCourseSemesterQuery());
//            while (rs.next()) {
//
//                System.out.println(rs.getInt(1) + "|" + rs.getInt(2) + "|" + rs.getInt(3));
//            }
//
//        } catch (SQLException e) {
//
//            e.printStackTrace();
//            System.out.println("Exception when executing printTableCourseSemester() method.");
//        }
//
//    }
//
//    public static void printTablePersonResource() {
//
//        try {
//
//            ResultSet rs = st.executeQuery(getTablePersonResourceQuery());
//            while (rs.next()) {
//
//                System.out.println(rs.getInt(1) + "|" + rs.getInt(2));
//            }
//
//        } catch (SQLException e) {
//
//            e.printStackTrace();
//            System.out.println("Exception when executing printTablePersonResource() method.");
//        }
//
//    }
//
//    public static void printTablePublisherResource() {
//
//        try {
//
//            ResultSet rs = st.executeQuery(getTablePublisherResourceQuery());
//            while (rs.next()) {
//
//                System.out.println(rs.getInt(1) + "|" + rs.getInt(2));
//            }
//
//        } catch (SQLException e) {
//
//            e.printStackTrace();
//            System.out.println("Exception when executing printTablePublisherResource() method.");
//        }
//
//    }
//
//    //===========================================PRINTS MATCHING ID=====================================================
//
//    public static void printPersonInTable(int id) {
//
//        try {
//
//            ResultSet rs = st.executeQuery(getPersonInTableQuery(id));
//            while (rs.next()) {
//                System.out.println(rs.getInt(1) + "|" + rs.getString(2) +
//                        rs.getString(3) + rs.getString(4));
//            }
//
//        } catch (SQLException e) {
//
//            e.printStackTrace();
//            System.out.println("ERROR: Exception when trying to execute printPersonInTable() method.");
//        }
//
//
//        // return String.format("SELECT * FROM PERSON WHERE ID = %d", id);
//    }
//
//    public static void printCourseInTable(int id) {
//
//        try {
//
//            ResultSet rs = st.executeQuery(getCourseInTableQuery(id));
//
//            while (rs.next()) {
//                System.out.println(rs.getInt(1) + "|" + rs.getString(2) +
//                        rs.getString(3) + rs.getString(4) + "|" + rs.getString(5));
//            }
//
//        } catch (SQLException e) {
//
//            e.printStackTrace();
//            System.out.println("ERROR: Exception when trying to execute printCourseInTable() method.");
//
//        }
//    }
//
//    public static void printResourcesInTable(int id) {
//
//        try {
//
//            ResultSet rs = getResourceInTableQuery(id).executeQuery();
//
//            while (rs.next()) {
//                System.out.println(rs.getInt(1) + "|" + rs.getString(2) +
//                        rs.getString(3) + rs.getString(4) + rs.getInt(5) +
//                        rs.getInt(6) + rs.getString(7));
//            }
//
//        } catch (SQLException e) {
//
//            e.printStackTrace();
//            System.out.println("ERROR: Exception when trying to execute printResourcesInTable() method.");
//        }
//
//    }
//
//    public static void printPublisherInTable(int id) {
//
//        try {
//
//            ResultSet rs = st.executeQuery(getPublisherInTableQuery(id));
//
//            while (rs.next()) {
//                System.out.println(rs.getInt(1) + "|" + rs.getString(2) +
//                        rs.getString(3) + rs.getString(4));
//            }
//
//        } catch (SQLException e) {
//
//            e.printStackTrace();
//            System.out.println("ERROR: Exception when trying to execute printPublisherInTable() method.");
//        }
//    }
//
//    public static String updatePersonQuery(Person person) {
//
//        return String.format("UPDATE PERSON SET FIRSTNAME = '%s', LASTNAME = '%s', TYPE = '%s' WHERE ID = %d",
//                person.getFirstName(), person.getLastName(), person.getType(), person.getID());
//    }
//
//
//    public static void updateCourseQuery(Course c) {
//        System.out.println("Title: " + c.getTitle().substring(0, 4) + "  CNUMBER: " + c.getTitle().substring(4) +
//                "  DESCRIPTION: " + c.getDescription() + "  DEPARTMENT: " + c.getDepartment() + "  ID: " + c.getID());
//
//        String titletochange = c.getTitle();
//        titletochange = titletochange.replaceAll("\\s", "");
//        String[] part = titletochange.split("(?<=\\D)(?=\\d)");
//        System.out.println(part[0]);
//        System.out.println(part[1]);
//
//        String query = String.format("UPDATE COURSECT SET TITLE = '%s', CNUMBER = '%s', DESCRIPTION = '%s', DEPARTMENT = '%s' " +
//                "WHERE ID = %d", part[0], part[1], c.getDescription(), c.getDepartment(), c.getID());
//        try {
//            st.executeQuery(query);
//        } catch (Exception e) {
//            System.out.println("Update fail because of course error");
//        }
//    }
//
//    //==================================
//
//    // does not check person for now
//    public static void updateCourseQuery2(Models.frontend.Course c) {
//
//        // check if the course already exists, check if the person already exists.
//        // if course does not exist, create it and assign it.
//        // if both do exist, just update
//
//        //getting the person id
//
//        String person_name = c.getProfessor().getFirstName();
//        String person_last_name = c.getProfessor().getLastName();
//        String person_type = c.getProfessor().getType();
//
//        int id_1 = -1;
//
//        //check if exists:
//
//        try {
//
//            ResultSet rs_1 = st3.executeQuery(String.format("SELECT * FROM PERSON WHERE FIRSTNAME = '%s' AND LASTNAME = '%s' AND TYPE = '%s'",
//                    person_name, person_last_name, person_type));
//
//            while (rs_1.next()) {
//
//                id_1 = rs_1.getInt(1);
//            }
//
//            // if -1 means it found nothing
//            if (id_1 == -1) {
//
//                //create a new person
//
//                System.out.println("It got here");
//            } else {
//
//                c.getProfessor().setID(id_1);
//                System.out.println("or here");
//            }
//
//        } catch (SQLException e) {
//
//            System.out.println("Something went wrong when looking for professors.");
//
//        }
//
//
//        //getting the semester:
//
//        String semester = c.getSEMESTER();
//        String year = String.valueOf(c.getYEAR());
//
//        int semester_id = getSemesterIDByName(semester, year);
//
//        // get c number:
//
//        String title = c.getTitle();
//        String cnumber;
//        title = title.replaceAll("\\s", "");
//        String[] part = title.split("(?<=\\D)(?=\\d)");
//
//        title = part[0];
//        cnumber = part[1];
//
//        int id = -1;
//        int new_id = -1;
//        // determine if that course exists:
//
//        try {
//
//            Statement statement = conn.createStatement();
//            ResultSet rs = statement.executeQuery(String.format("SELECT * FROM COURSECT WHERE TITLE = '%s' AND CNUMBER = '%s'",
//                    title, cnumber));
//
//
//            while (rs.next()) {
//
//                id = rs.getInt(1);
//            }
//
//            // if -1 means that it does not exist
//            if (id == -1) {
//
//                //create it here
//                new_id = insertCourseQuery(c);
//
//
//                //insert this new id into the semester_course table
//
//                statement.executeQuery(String.format("UPDATE RELATION_SEMESTER_COURSE SET COURSEID = %d, SEMESTERID = %d WHERE COURSEID = %d", new_id, semester_id, c.getID()));
//                statement.executeQuery(String.format("UPDATE RELATION_COURSE_PERSON SET COURSEID = %d, PERSONID = %d WHERE COURSEID = %d", new_id, c.getProfessor().getID(), c.getID()));
//
//                if (c.getResource().isEmpty()) {
//
//                } else {
//
//                    statement.executeQuery(String.format("INSERT INTO RELATION_COURSE_RESOURCES (COURSEID, RESOURCEID) VALUES (%d, %d)", new_id, c.getResource().get(0).getID()));
//
//                }
//
//
//            } else {
//
//                //execute update method here
//                executeNoReturnQuery(String.format("UPDATE COURSECT SET TITLE = '%s', CNUMBER = '%s', DESCRIPTION = '%s', DEPARTMENT = '%s' " +
//                        "WHERE ID = %d", title, cnumber, c.getDescription(), c.getDepartment(), c.getID()));
//
//            }
//
//        } catch (SQLException e) {
//
//            System.out.println("Turns out we are getting an exception @ updateCourseQuery2()");
//
//        }
//
//
//    }
//
//
//
//    private String updatePublisherQuery(Publisher publisher) {
//
//        return String.format("UPDATE PUBLISHER SET TITLE = '%s', CONTACT_INFO = '%s', DESCRIPTION = '%s' WHERE ID = %d",
//                publisher.getTitle(), publisher.getContactInformation(), publisher.getDescription(),
//                publisher.getID());
//    }
//
//    //==========================================Delete Method Queries===================================================
//
//    private String deletePersonQuery(Person person) {
//
//        return String.format("DELETE FROM PERSON WHERE ID = %d", person.getID());
//    }
//
//    private String deleteCourseQuery(Course course) {
//
//        return String.format("DELETE FROM COURSECT WHERE ID = %d", course.getID());
//    }
//
//    private String deleteResourceQuery(Resource resource) {
//
//        return String.format("DELETE FROM RESOURCES WHERE ID = %d", resource.getID());
//    }
//
//    private String deletePublisherQuery(Publisher publisher) {
//
//        return String.format("DELETE FROM PUBLISHERS WHERE ID = %d", publisher.getID());
//    }
//
//    //============================================GUI METHODS===========================================================
//
//    // 1st view:
//    // Search by: Professor (name), Course(title), Resource(title), Semester-Year
//    public static Person[] searchByProfessor(String name) {
//
//        int i = 0;
//
//        try {
//
//            Statement st = conn.createStatement();
//            String query = String.format("SELECT * FROM PERSON WHERE FIRSTNAME='%s'", name);
//            ResultSet rs = st.executeQuery(query);
//
//            while (rs.next()) {
//                i++;
//            }
//
//            Person[] p = new Person[i];
//
//            rs = st.executeQuery(query);
//            i = 0;
//            while (rs.next()) {
//
//                p[i] = new Person(rs.getInt(1), rs.getString(2), rs.getString(3),
//                        rs.getString(4));
//                i++;
//                return p;
//            }
//
//        } catch (SQLException e) {
//
//        }
//
//        return null;
//    }
//
//    public static ArrayList<Integer> searchGetCoursesIdsByProfessorName(String name) {
//
//        ArrayList<Integer> arr = new ArrayList<>();
//
//        try {
//
//            Statement st = conn.createStatement();
//            String query = String.format("SELECT * FROM PERSON WHERE FIRSTNAME='%s' OR LASTNAME='s'", name, name);
//
//            ResultSet rs = st.executeQuery(query);
//
//            ArrayList<Integer> professorids = new ArrayList<>();
//
//            while (rs.next()) {
//
//                professorids.add(rs.getInt(1));
//            }
//
//            for (int i = 0; i < professorids.size(); i++) {
//
//                rs = st.executeQuery(String.format("SELECT * FROM RELATION_COURSE_PERSON WHERE PERSONID = %d",
//                        professorids.get(i)));
//
//                while (rs.next()) {
//
//                    arr.add(rs.getInt(2));
//                }
//            }
//
//            return arr;
//
//        } catch (SQLException e) {
//
//            System.out.println("Something went wrong");
//
//        }
//
//        return null;
//    }
//
//    public static ArrayList<Models.frontend.Course> getCourseArrayByIDs(ArrayList<Integer> timmy) {
//
//        ArrayList<Models.frontend.Course> c = new ArrayList<>();
//        Models.frontend.Course course;
//
//        try {
//
//            Statement st = conn.createStatement();
//            ResultSet rs;
//
//            for (int i = 0; i < timmy.size(); i++) {
//
//                rs = st.executeQuery(String.format("SELECT * FROM COURSECT WHERE ID=%d", timmy.get(i)));
//
//                while (rs.next()) {
//
//                    course = new Models.frontend.Course(rs.getInt(1), rs.getString(2),
//                            rs.getString(5), rs.getString(4));
//
//                    c.add(course);
//                }
//            }
//
//            return c;
//
//        } catch (SQLException e) {
//
//
//        }
//
//        return null;
//
//    }
//
//    public static Course[] searchByCourse(String title) {
//        int i = 0;
//
//        try {
//
//            Statement st = conn.createStatement();
//            String query = String.format("SELECT * FROM COURSECT WHERE TITLE='%s'", title);
//            ResultSet rs = st.executeQuery(query);
//
//            while (rs.next()) {
//                i++;
//            }
//
//            Course[] c = new Course[i];
//
//            rs = st.executeQuery(query);
//            i = 0;
//            while (rs.next()) {
//
//                c[i] = new Course(rs.getInt(1), rs.getString(2), rs.getString(3),
//                        rs.getString(4), rs.getString(5));
//                i++;
//            }
//            return c;
//        } catch (SQLException e) {
//            System.out.println(e);
//        }
//
//        return null;
//    }
//
//    public static Resource[] searchByResource(String title) {
//
//        int i = 0;
//
//        try {
//
//            Statement st = conn.createStatement();
//            String query = String.format("SELECT * FROM RESOURCES WHERE TITLE='%s'", title);
//            ResultSet rs = st.executeQuery(query);
//
//            while (rs.next()) {
//                i++;
//            }
//
//            Resource[] r = new Resource[i];
//
//            rs = st.executeQuery(query);
//            i = 0;
//            while (rs.next()) {
//
//                r[i] = new Resource(rs.getInt(1), rs.getString(2), rs.getString(3),
//                        rs.getString(4), rs.getString(5), rs.getInt(6),
//                        rs.getInt(7), rs.getString(8));
//                i++;
//            }
//
//            return r;
//
//        } catch (SQLException e) {
//
//        }
//
//        return null;
//    }
//
//    // Work in progress
//    public static Course[] searchBySemester(String semester, String year) {
//
//        Course[] c = new Course[1];
//        return c;

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
//    }
//
//    //================================All select methods returning object===============================================
//
//    public static Person getPersonObject(int id) {
//
//        Person p = new Person();
//
//        try {
//
//            ResultSet rs = st.executeQuery(getPersonInTableQuery(id));
//
//            while (rs.next()) {
//
//                p.setID(rs.getInt(1));
//                p.setType(rs.getString(2));
//                p.setFirstName(rs.getString(3));
//                p.setLastName(rs.getString(4));
//            }
//
//        } catch (SQLException e) {
//
//            System.out.println("Something went wrong inside of the method getPersonObject()");
//
//        }
//
//        return p;
//
//    }
//
//    public static Course getCourseObject(int id) {
//
//        Course c = new Course();
//
//        try {
//
//            ResultSet rs = st.executeQuery(getCourseInTableQuery(id));
//
//            while (rs.next()) {
//
//                c.setID(rs.getInt(1));
//                c.setTitle(rs.getString(2));
//                c.setCRN(rs.getString(3));
//                c.setDescription(rs.getString(4));
//                c.setDepartment(rs.getString(5));
//            }
//
//        } catch (SQLException e) {
//
//            System.out.println("Something went wrong inside of the method getPersonObject()");
//
//        }
//
//        return c;
//
//    }
//
//    public static Resource getResourceObject(int id) {
//
//        Resource r = new Resource();
//
//        try {
//
//            ResultSet rs = st.executeQuery(getPersonInTableQuery(id));
//
//            while (rs.next()) {
//
//                r.setID(rs.getInt(1));
//                r.setType(rs.getString(2));
//                r.setTitle(rs.getString(3));
//                r.setAuthor(rs.getString(4));
//                r.setISBN(rs.getString(5));
//                r.setTotalAmount(rs.getInt(6));
//                r.setCurrentAmount(rs.getInt(7));
//                r.setDescription(rs.getString(8));
//            }
//
//        } catch (SQLException e) {
//
//            System.out.println("Something went wrong inside of the method getPersonObject()");
//
//        }
//
//        return r;
//
//    }
//
//    public static Publisher getPublisherObject(int id) {
//
//        Publisher p = new Publisher();
//
//        try {
//
//            ResultSet rs = st.executeQuery(getPersonInTableQuery(id));
//
//            while (rs.next()) {
//
//                p.setID(rs.getInt(1));
//                p.setTitle(rs.getString(2));
//                p.setContactInformation(rs.getString(3));
//                p.setDescription(rs.getString(4));
//            }
//
//        } catch (SQLException e) {
//
//            System.out.println("Something went wrong inside of the method getPersonObject()");
//
//        }
//
//        return p;
//    }
//
//    //=====================================Methods for searching relationship tables====================================
//
//    public static Person getPersonRelationTable(int id) {
//
//        Person p = new Person();
//
//        try {
//
//            Statement st = conn.createStatement();
//            ResultSet rs = st.executeQuery(String.format("SELECT * FROM RELATION_COURSE_PERSON WHERE COURSEID=%d", id));
//
//            while (rs.next()) {
//
//                p.setID(rs.getInt(2));
//            }
//
//
//            return p;
//
//
//        } catch (SQLException e) {
//
//            System.out.println("Something went wrong.");
//
//        }
//
//        return null;
//
//    }
//
//    public static Resource[] getResourcesRelationTable(int id) {
//
//        Resource r[];
//        try {
//
//            st = conn.createStatement();
//            ResultSet rs = st.executeQuery(String.format("SELECT * FROM RELATION_COURSE_RESOURCES WHERE COURSEID=%d", id));
//
//
//            int i = 0;
//            while (rs.next()) {
//
//                i++;
//            }
//
//            r = new Resource[i];
//
//            int j = 0;
//
//            ResultSet sr = st.executeQuery(String.format("SELECT * FROM RELATION_COURSE_RESOURCES WHERE COURSEID=%d", id));
//
//            while (rs.next()) {
//
//                r[j].setID(sr.getInt(2));
//            }
//
//            return r;
//
//
//        } catch (SQLException e) {
//
//            System.out.println("Something went wrong");
//        }
//
//        return null;
//
//    }
//
//
//
//    public static ArrayList<Resource> findResourcesCourseAvoidRepetitive(int courseID) {
//
//        ResultSet rs;
//        int resourceID = 0,
//                i = 0, before = -1;
//        ArrayList<Resource> listResources = new ArrayList<Resource>();
//
//        try {
//
//            rs = st.executeQuery("SELECT * FROM RELATION_COURSE_RESOURCES WHERE COURSEID = " + courseID +
//                    "ORDER BY RESOURCEID ASC");
//            //here all have a result set of all resources for courdeID
//
//            while (rs.next()) {
//                resourceID = rs.getInt("RESOURCEID");
//                if (before != resourceID) {
//                    before = resourceID;
//                    ResultSet rss = getResourceInTableQuery(resourceID).executeQuery();
//
//                    while (rss.next()) {
//                        System.out.println();
//                        // ID, Type, Title, Author, ISBN, total, current, desc
//
//                        listResources.add(new Resource(rss.getInt(1), rss.getString(2),
//                                rss.getString(3), rss.getString(4), rss.getString(5),
//                                rss.getInt(6), rss.getInt(7), rss.getString(8)));
////                setPublisherForResource(resourcesList[i]);
//                        setPublisherForResource(listResources.get(i));
//
//                        i++;
//                    }
//                }
//
//            }
//
//            //return resourcesList;
//            return listResources;
//        } catch (SQLException err) {
//            System.out.println(err);
//        }
//        // Adding the list of the resources to the person object
//        listResources.clear();
//        return listResources;
//
//    }
//
//
//    //==================================
//
//    //Regular Tables
//
//
//
//
//
//
//
//    public static ArrayList<Models.frontend.Course> searchByNameSemesterCourseList(String fname, String lname, String semester, String year) {
//        int semesterID = getSemesterIDByName(semester, year);
//        ArrayList<Models.frontend.Course> courseSemesterList = returnEverything(semesterID);
//        ArrayList<Models.frontend.Course> arr = new ArrayList<>();
//        ArrayList<Integer> courseIDArr = new ArrayList<>();
//        Person person;
//        int personID = 0;
//        String personType = "";
//        lname = lname.substring(0, 1).toUpperCase() + lname.substring(1).toLowerCase();
//        fname = fname.substring(0, 1).toUpperCase() + fname.substring(1).toLowerCase();
//        int resourceID = 0;
//        String cDescription = "", cDepartment = "", cTitle = "";
//        Resource[] courseResources = new Resource[20];
//        ArrayList<Models.frontend.Resource> resourceList = new ArrayList<>();
//        int cID = 0;
//        try {
//            String query = String.format("SELECT * FROM PERSON WHERE FIRSTNAME = '%s' and LASTNAME = '%s' ", fname, lname);
//            Statement st = DBManager.conn.createStatement();
//            ResultSet rs = st.executeQuery(query);
//            while (rs.next()) {
//                personID = rs.getInt(1);
//                personType = rs.getString(2);
//            }
//
//            person = new Person(personID, fname, lname, personType);
//            rs = st.executeQuery("SELECT * FROM RELATION_COURSE_PERSON WHERE PERSONID = " + personID);
//            int i = 0;
//            while (rs.next()) {
//                courseIDArr.add(rs.getInt(1));
//                i++;
//            }
//
//            person = new Person(personID, fname, lname, personType);
//            System.out.println("This is the size of courseIDarr" + courseIDArr.size());
//            for (int a = 0; a < courseIDArr.size(); a++) {
//
//                rs = st.executeQuery(getCourseInTableQuery(courseIDArr.get(a)));
//
//                while (rs.next()) {
//
//                    cTitle = rs.getString(2) + rs.getString(3);
//                    cID = rs.getInt(1);
//                    cDescription = rs.getString(4);
//                    cDepartment = rs.getString(5);
//                }
//
//                Models.frontend.Course tempCourse = new Models.frontend.Course(cID, cTitle, cDepartment, cDescription);
//                resourceList = findResourcesCourseReturnList(courseIDArr.get(a));
//
//                tempCourse.setResource(resourceList);
//                tempCourse.setProfessor(person.initPersonGUI());
//                System.out.println(cID + cTitle + cDepartment + cDescription);
//                System.out.println(person.initPersonGUI().toString());
//                //Restrict search by name with semester, haven’t test yet
//
//                //Need to fix the semester
//                for (int c = 0; c < courseSemesterList.size(); c++) {
//                    if (tempCourse.getID() == courseSemesterList.get(c).getID()
//                            && tempCourse.getProfessor().getID() == courseSemesterList.get(c).getProfessor().getID()
//                            ) {
//                        tempCourse.setSEMESTER(semester);
//                        tempCourse.setYEAR(Integer.parseInt(year));
//                        arr.add(tempCourse);
//                    }
//                }
//
//            }
//
//            return arr;
//        } catch (Exception e) {
//            e.printStackTrace();
//            System.out.println("wtf");
//        }
//        return arr;
//    }
//
//
//    public static ArrayList<Models.frontend.Course> searchByNameCourseList(String fname, String lname) {
//        ArrayList<Models.frontend.Course> arr = new ArrayList<>();
//        ArrayList<Integer> courseIDArr = new ArrayList<>();
//        Person person;
//        int personID = 0;
//        String personType = "";
//        lname = lname.substring(0, 1).toUpperCase() + lname.substring(1).toLowerCase();
//        fname = fname.substring(0, 1).toUpperCase() + fname.substring(1).toLowerCase();
//        int resourceID = 0;
//        String cDescription = "", cDepartment = "", cTitle = "";
//        Resource[] courseResources = new Resource[20];
//        ArrayList<Models.frontend.Resource> resourceList = new ArrayList<>();
//        int cID = 0;
//        try {
//            String query = String.format("SELECT * FROM PERSON WHERE FIRSTNAME = '%s' and LASTNAME = '%s' ", fname, lname);
//            Statement st = DBManager.conn.createStatement();
//            ResultSet rs = st.executeQuery(query);
//            while (rs.next()) {
//                personID = rs.getInt(1);
//                personType = rs.getString(2);
//            }
//
//            person = new Person(personID, fname, lname, personType);
//            rs = st.executeQuery("SELECT * FROM RELATION_COURSE_PERSON WHERE PERSONID = " + personID);
//            int i = 0;
//            while (rs.next()) {
//                courseIDArr.add(rs.getInt(1));
//                i++;
//            }
//
//            person = new Person(personID, fname, lname, personType);
//            System.out.println("This is the size of courseIDarr" + courseIDArr.size());
//            for (int a = 0; a < courseIDArr.size(); a++) {
//
//                rs = st.executeQuery(getCourseInTableQuery(courseIDArr.get(a)));
//
//                while (rs.next()) {
//
//                    cTitle = rs.getString(2) + rs.getString(3);
//                    cID = rs.getInt(1);
//                    cDescription = rs.getString(4);
//                    cDepartment = rs.getString(5);
//                }
//
//                Models.frontend.Course tempCourse = new Models.frontend.Course(cID, cTitle, cDepartment, cDescription);
//                resourceList = findResourcesCourseReturnList(courseIDArr.get(a));
//
//                tempCourse.setResource(resourceList);
//                tempCourse.setProfessor(person.initPersonGUI());
//                System.out.println(cID + cTitle + cDepartment + cDescription);
//                System.out.println(person.initPersonGUI().toString());
//                //Restrict search by name with semester, haven’t test yet
//                arr.add(tempCourse);
//
//
//            }
//
//            return arr;
//        } catch (Exception e) {
//            e.printStackTrace();
//            System.out.println("wtf");
//        }
//        return arr;
//    }
//
//
//
//
//    public static void print_semester_by_commonid(int id){
//
//        try{
//
//            Statement st = conn.createStatement();
//            ResultSet rs = st.executeQuery(String.format("SELECT * FROM RELATION_SEMESTER_COURSE WHERE ID = %d", id));
//
//            while(rs.next()){
//
//                System.out.println("|" + rs.getInt("SEMESTERID") + "|");
//            }
//
//            rs.close();
//
//        }catch(SQLException e){
//
//            System.out.println("asd");
//        }
//    }
//
//    public static void muhcode(){
//
//        //        ArrayList<Integer> asdf = DBManager.find_classids_by_course_name("140");
////
////        int[] varname = new int[asdf.size()];
////
////        for(int i = 0; i < asdf.size(); i++){
////
////            varname[i] = asdf.get(i);
////        }
////
////        Arrays.sort(varname);
////
////        for(int i = 0; i < asdf.size(); i++){
////
////            System.out.println(asdf.get(i));
////        }
////
////        System.out.println("===========");
////
////        for(int i = 0; i < varname.length; i++){
////
////            System.out.println(varname[i]);
////        }
////
////        int[] asdf = {1,2,3,3,4};
////        Set<Integer> asd = new HashSet<>();
////
////        for(int i = 0; i < asdf.length; i++){
////
////            asd.add(asdf[i]);
////        }
////
////        for(int i = 0; i < asdf.length; i++){
////
////            System.out.println(asdf[i]);
////        }
////
////        System.out.println("=============");
////
////        asd.toArray(asdf);
////
////        for(int i = 0; i < asdf.length; i++){
////
////            System.out.println(asdf[i]);
////        }
//
//    }
//
//
//    public static ArrayList<Models.frontend.Resource> find_resources_by_name(String name) {
//
//        ArrayList<Models.frontend.Resource> resource = new ArrayList<>();
//        ArrayList<Integer> resourceids = new ArrayList<>();
//        Models.frontend.Resource tmpresource;
//
//        try{
//
//            Statement statement = conn.createStatement();
//            ResultSet rs = statement.executeQuery("SELECT * FROM RESOURCES WHERE TITLE LIKE '%"+name+"%' OR AUTHOR LIKE '%"+name+"%'");
//
//            while(rs.next()) {
//
//                resourceids.add(rs.getInt("ID"));
//            }
//
//            rs.close();
//
//            for(int i : resourceids){
//
//                resource.add(find_resource_by_id(i));
//            }
//
//            return resource;
//
//        }catch(SQLException e) { System.out.println("Something went wrong with find_resources_by_name()"); }
//
//        return resource;
//    }
//
//
//
//
//    public static ArrayList<Models.frontend.Course> find_classes_by_professor_name(String name){
//
//        ArrayList<Integer> courseids = new ArrayList<>();
//        ArrayList<Integer> personids = new ArrayList<>();
//        ArrayList<Models.frontend.Course> courses = new ArrayList<>();
//
//        try{
//
//            Statement st = conn.createStatement();
//            Statement st2 = conn.createStatement();
//            Statement st3 = conn.createStatement();
//            ResultSet rs3;
//
//            ResultSet rs = st.executeQuery("SELECT * FROM PERSON WHERE FIRSTNAME LIKE '%"+name+"%'");
//
//            while(rs.next()){
//
//                personids.add(rs.getInt("ID"));
//            }
//
//            rs.close();
//
//            ResultSet rs2 = st.executeQuery("SELECT * FROM PERSON WHERE LASTNAME LIKE '%"+name+"%'");
//
//            while(rs2.next()){
//
//                personids.add(rs2.getInt("ID"));
//            }
//
//            rs2.close();
//
//
//            for(int i = 0; i < personids.size(); i++){
//
//                rs3 = st3.executeQuery(String.format("SELECT * FROM RELATION_COURSE_PERSON WHERE PERSONID = %d", personids.get(i)));
//
//                while(rs3.next()){
//
//                    courseids.add(rs3.getInt("COMMONID"));
//                }
//
//                rs3.close();
//
//            }
//
//            for(int i = 0; i < courseids.size(); i++){
//
//                courses.add(find_class_by_commonid(courseids.get(i)));
//            }
//
//            return courses;
//
//
//        }catch(SQLException e){
//
//            System.out.println("Something went wrong with find_course_by_professor_name(String name)");
//        }
//
//        return courses;
//
//
//    }
//
//
//
//
//
//    public static ArrayList<Models.frontend.Course> find_classes_by_course_name(String name){
//
//        ArrayList<Models.frontend.Course> courses = new ArrayList<>();
//        ArrayList<Integer> courseids = new ArrayList<>();
//        ArrayList<Integer> classids = new ArrayList<>();
//        ArrayList<Integer> tmp;
//
//
//        try{
//
//            Statement st = conn.createStatement();
//            ResultSet rs = st.executeQuery("SELECT * FROM COURSECT WHERE TITLE LIKE '%"+name+"%' OR CNUMBER LIKE '%"+name+"%'");
//
//            while(rs.next()){
//
//                courseids.add(rs.getInt("ID"));
//            }
//
//            rs.close();
//
//
//            for(int i = 0; i < courseids.size(); i++){
//
//                tmp = find_classids_by_courseid(courseids.get(i));
//                for(int j = 0; j < tmp.size(); j++){
//
//                    classids.add(tmp.get(j));
//                }
//
//            }
//
//
//
//            for(int i = 0; i < classids.size(); i++){
//
//                courses.add(find_class_by_commonid(classids.get(i)));
//            }
//
//            return courses;
//
//        }catch(SQLException e){
//
//            System.out.println("Something went wrong with find_classes_by_course_name(String name)");
//        }
//
//        return courses;
//
//    }
//
//
//
//    public static ArrayList<Models.frontend.Resource> findDifferene(Models.frontend.Person person, ArrayList<Models.frontend.Resource> personRequiredResources){
//
//        ArrayList<Models.frontend.Resource> differences = new ArrayList<Models.frontend.Resource>();
//
//
//        for (Models.frontend.Resource resource : personRequiredResources){
//            if (!person.getResources().contains(resource)){
//                differences.add(resource);
//            }
//        }
//
//        return differences;
//
//    }
//
//    public static ArrayList<Models.frontend.Course> return_everything_by_commonid(ArrayList<Integer> idlist){
//
//        ArrayList<Models.frontend.Course> courses = new ArrayList<>();
//
//        for(int i : idlist){
//
//            courses.add(find_class_by_commonid(idlist.get(i)));
//        }
//
//        return courses;
//
//    }
//
//
//
//
//    public static void updateCourseQuery123(ArrayList<Models.frontend.Course> c) {
//
//        if (c.isEmpty()) {
//
//            System.out.println("Course array is empty. Something went wrong.");
//
//        } else if (c.get(0).equals(c.get(1))) {
//
//
//            //needs to check for non existing professor
//
//            c.get(0).getProfessor().setID(find_person_by_name(c.get(0).getProfessor()));
//
//
//            try {
//
//                Statement st = conn.createStatement();
//
//                st.executeQuery(String.format("UPDATE RELATION_COURSE_PERSON SET PERSONID = %d WHERE " +
//                        "COMMONID = %d", c.get(0).getProfessor().getID(), c.get(0).getCommonID()));
//
//            } catch (SQLException e) {
//
//                System.out.println("Something went wrong when adding a new professor to the RELATION_COURSE_PERSON TABLE");
//
//            }
//
//            System.out.println("Second IF in updateCourseQuery123()");
//
//        } else {
//
//            try {
//
//                Statement st = conn.createStatement();
//
//                c.get(1).setID(find_courseid_by_title(c.get(1)));
//
//                // if it exists
//                st.executeQuery(String.format("UPDATE RELATION_SEMESTER_COURSE SET COURSEID = %d WHERE ID = %d",
//                        c.get(1).getID(), c.get(0).getCommonID()));
//
//
//                c.get(1).getProfessor().setID(find_person_by_name(c.get(1).getProfessor()));
//                System.out.println(c.get(1).getProfessor().getID());
//
//
//                st.executeQuery(String.format("UPDATE RELATION_COURSE_PERSON SET COURSEID = %d, PERSONID = %d WHERE " +
//                                "COMMONID = %d", c.get(1).getID(), c.get(1).getProfessor().getID(),
//                        c.get(0).getCommonID()));
//
//            } catch (SQLException e) {
//
//                System.out.println("Something went wrong when trying to update course and person table");
//            }
//
//        }
//
//    }
//
//
//
//    public static void deletePerson(Models.frontend.Person person) {
//
//        try {
//
//            Statement st = conn.createStatement();
//            Statement st2 = conn.createStatement();
//            Statement st3 = conn.createStatement();
//            Statement st4 = conn.createStatement();
//            Statement st5 = conn.createStatement();
//            Statement st6 = conn.createStatement();
//
//            ArrayList<Integer> commonids_to_be_deleted = new ArrayList<>();
//
//            System.out.println("asdfasdfasdf: 1");
//
//            ResultSet rs = st.executeQuery(String.format("SELECT * FROM RELATION_COURSE_PERSON WHERE PERSONID = %d",
//                    person.getID()));
//
//            while (rs.next()) {
//                commonids_to_be_deleted.add(rs.getInt("COMMONID"));
//            }
//
//            System.out.println("asdfasdfasdf: 2");
//
//            for(int i = 0; i < commonids_to_be_deleted.size(); i++){
//
//                st2.executeQuery(String.format("DELETE FROM RELATION_SEMESTER_COURSE WHERE ID = %d",
//                        commonids_to_be_deleted.get(i)));
//
//                System.out.println("Deleting from RELATION_SEMESTER_COURSE. COMMONID : " + commonids_to_be_deleted.get(i));
//            }
//
//            System.out.println("asdfasdfasdf: 3");
//
//            for(int i = 0; i < commonids_to_be_deleted.size(); i++){
//
//                st3.executeQuery(String.format("DELETE FORM RELATION_COURSE_RESOURCES WHERE COMMONID = %d",
//                        commonids_to_be_deleted.get(i)));
//
//                System.out.println("Deleting from RELATION_COURSE_RESOURCES. COMMONID : " + commonids_to_be_deleted.get(i));
//            }
//
//            System.out.println("asdfasdfasdf: 4");
//
//
//            st4.executeQuery(String.format("DELETE FROM RELATION_COURSE_PERSON WHERE PERSONID = %d",
//                    person.getID()));
//
//            System.out.println("DELETED FROM RELATION_COURSE_PERSON: " + person.getID());
//
//            st5.executeQuery(String.format("DELETE FROM RELATION_PERSON_RESOURCES WHERE PERSONID = %d",
//                    person.getID()));
//
//            System.out.println("DELETED FROM RELATION_PERSON_RESOURCES: " + person.getID());
//
//            st6.executeQuery(String.format("DELETE FROM PERSON WHERE ID = %d", person.getID()));
//
//            System.out.println("DELETED FROM PERSON: " + person.getID());
//
//            System.out.println("asdfasdfasdf: 5");
//
//        } catch (SQLException e) {
//            System.out.println("Something went wrong @ deletePerson() @ DBManager.java");
//        }
//    }
//
//
//
//
//
//
//    public static void delete_person(Models.frontend.Person p) {
//
//        ArrayList<Integer> list_of_course_ids = new ArrayList<>();
//        ArrayList<Integer> count_of_course_ids = new ArrayList<>();
//        ArrayList<Integer> to_be_deleted = new ArrayList<>();
//        int counter = 1;
//
//        try {
//
//            // get all the courses professor teaches.
//            // delete the exact amount for every different course
//            // execute the rest
//
//            Statement st = conn.createStatement();
//
//            ResultSet rs = st.executeQuery(String.format("SELECT * FROM RELATION_COURSE_PERSON WHERE PERSONID = %d " +
//                            "ORDER BY COURSEID ASC",
//                    p.getID()));
//
//            while (rs.next()) {
//
//                list_of_course_ids.add(rs.getInt(1));
//
//            }
//
//            int previousone = 0;
//            int thisone = 0;
//            int i = 0;
//
//            while (list_of_course_ids.size() > i) {
//
//                thisone = list_of_course_ids.get(i);
//
//                if (i > 1) {
//
//                    previousone = list_of_course_ids.get(i - 1);
//                }
//
//                if (thisone == previousone) {
//
//                    counter++;
//
//                } else {
//
//                    counter = 1;
//                }
//
//                count_of_course_ids.add(counter);
//                i++;
//            }
//
//
//            Statement statement = conn.createStatement();
//            ResultSet result_set;
//            String query;
//            previousone = 0;
//            thisone = 0;
//            i = 0;
//
//            while (list_of_course_ids.size() > i) {
//
//                query = String.format("SELECT * FROM RELATION_SEMESTER_COURSE WHERE COURSEID = %d", list_of_course_ids.get(i));
//                result_set = statement.executeQuery(query);
//
//                if (i > 1) {
//
//                    previousone = list_of_course_ids.get(i - 1);
//                }
//                while (result_set.next()) {
//
//                    if (previousone != thisone) {
//
//                        to_be_deleted.add(result_set.getInt(3));
//                    }
//                }
//
//                i++;
//
//            }
//
//            for (int a = 0; a < list_of_course_ids.size(); a++) {
//
//                System.out.println(list_of_course_ids.get(a));
//            }
//
//            for (int b = 0; b < list_of_course_ids.size(); b++) {
//
//                System.out.println(count_of_course_ids.get(b));
//            }
//
//            for (int j = 0; j < to_be_deleted.size(); j++) {
//
//                System.out.println(to_be_deleted.get(j));
//            }
//
//
////            String query = "";
////            for(int i = 0; i < to_be_deleted.size(); i++){
////
////                query = String.format("DELETE FROM RELATION_SEMESTER_COURSE WHERE ID = %d",
////                        to_be_deleted.get(i));
////                System.out.println(query);
////                //executeNoReturnQuery(query);
////            }
//
////            executeNoReturnQuery(String.format("DELETE FROM RELATION_PERSON_RESOURCES WHERE PERSONID = %d", p.getID()));
////            executeNoReturnQuery(String.format("DELETE FROM RELATION_COURSE_PERSON WHERE PERSONID = %d", p.getID()));
////            executeNoReturnQuery(String.format("DELETE FROM PERSON WHERE ID = %d", p.getID()));
//
//        } catch (SQLException e) {
//
//            System.out.println("Something went wrong with the delete_person function");
//
//        }
//
//    }
//
//
//
//
//    //======================================================================================
//
//
//
//
//    public static ArrayList<Models.frontend.Resource> findResourcesCourseReturnList(int courseID) {
//
//        ResultSet rs;
//        int resourceID = 0;
//        int i = 0;
//        ArrayList<Models.frontend.Resource> resourceList = new ArrayList<>();
//
//
//        try {
//
//            rs = st.executeQuery("SELECT * FROM RELATION_COURSE_RESOURCES WHERE COURSEID = " + courseID);
//
//            while (rs.next()) {
//                resourceID = rs.getInt(2);
//                rs = getResourceInTableQuery(resourceID).executeQuery();
//                //System.out.println("lol");
//
//                while (rs.next()) {
//                    System.out.println();
//
//                    // ID, Type, Title, Author, ISBN, total, current, desc
//                    Models.frontend.Resource resource = new Resource(rs.getInt(1), rs.getString(2),
//                            rs.getString(3), rs.getString(4), rs.getString(5),
//                            rs.getInt(6), rs.getInt(7), rs.getString(8)).initResourceGUI();
//                    resourceList.add(resource);
//                    i++;
//                }
//
//            }
//
//            return resourceList;
//        } catch (SQLException err) {
//            System.out.println(err);
//        }
//        // Adding the list of the resources to the person object
//        return null;
//
//    }
//
//
//
//    public static ArrayList<Models.frontend.Course> returnEverything(int semesterid) {
//        Semester semester = getSemesterNameByID(semesterid);
//        int lastCourseID = 0;
//
//        ArrayList<Integer> courseIDs = getCourseIdsBySemesterID(semesterid);
//
//        ArrayList<Models.frontend.Course> hugeshit2 = new ArrayList<>();
//
//        for (int i = 0; i < courseIDs.size(); i++) {
//            if (lastCourseID == courseIDs.get(i)) {
//                continue;
//            }
//            ArrayList<Course> tmpCourse = DBManager.relationalReadByCourseID(courseIDs.get(i));
//            lastCourseID = courseIDs.get(i);
//
//            for (int j = 0; j < tmpCourse.size(); j++) {
//
//                hugeshit2.add(tmpCourse.get(j).initCourseGUI(semester.getYear(), semester.getSeason()));
//
//
//            }
//        }
//
//        return hugeshit2;
//    }
//
//    // Returns id needs to return title;
//    public static Course[] getCourseTitlesByID(int[] ids) {
//
//        Course[] c;
//        try {
//
//            Statement st = DBManager.conn.createStatement();
//            c = new Course[ids.length];
//
//            for (int i = 0; i < ids.length; i++) {
//
//                ResultSet rs = st.executeQuery(String.format("SELECT * FROM COURSECT WHERE ID=%d", ids[i]));
//                while (rs.next()) {
//
//                    c[i] = new Course(rs.getString(2) + rs.getString(3));
//                }
//
//                return c;
//
//            }
//
//
//        } catch (SQLException e) {
//
//        }
//        return null;
//    }
//
//
//
//    //==================================================================================================================
//    //                                                  Next
//    //==================================================================================================================
//
//    //method without user's input
//    public static void relationalInsertByID(int courseID, int personID, int resourceID, int publisherID, int semesterID) {
//
//
//        executeNoReturnQuery(String.format("INSERT INTO RELATION_COURSE_PERSON" +
//                " (COURSEID, PERSONID) VALUES ('%d', '%d')", courseID, personID));
//        executeNoReturnQuery(String.format("INSERT INTO RELATION_COURSE_RESOURCES" +
//                " (COURSEID, RESOURCEID) VALUES ('%d', '%d')", courseID, resourceID));
//        executeNoReturnQuery(String.format("INSERT INTO RELATION_SEMESTER_COURSE" +
//                " (COURSEID, SEMESTERID) VALUES ('%d', '%d')", courseID, semesterID));
//        executeNoReturnQuery(String.format("INSERT INTO RELATION_PERSON_RESOURCES" +
//                " (PERSONID, RESOURCEID) VALUES ('%d', '%d')", personID, resourceID));
//        executeNoReturnQuery(String.format("INSERT INTO RELATION_PUBLISHER_RESOURCE" +
//                " (PUBLISHERID, RESOURCEID) VALUES ('%d', '%d')", publisherID, resourceID));
//        System.out.println("Added ID");
//
//    }
//
//
//    public static ArrayList<Integer> getCourseIdsBySemesterID(int id) {
//
//        int i = 0;
//        String query = String.format("SELECT * FROM RELATION_SEMESTER_COURSE WHERE SEMESTERID=%d ORDER BY COURSEID ASC",
//                id);
//        ArrayList<Integer> idsList = new ArrayList<Integer>();
//
//        try {
//
//            Statement st = DBManager.conn.createStatement();
//            ResultSet rs = st.executeQuery(query);
//
//            while (rs.next()) {
//                i++;
//            }
//
//
//            rs = st.executeQuery(query);
//            i = 0;
//            while (rs.next()) {
//                idsList.add(rs.getInt(1));
//                i++;
//            }
//
//            return idsList;
//
//
//        } catch (SQLException e) {
//
//            System.out.println("Something went wrong");
//
//        }
//
//        return null;
//    }
//
//
//
//
//
//
//
//
//
//
//    //**
//
//
//
//
//
//
//
//
//
//             /* String query1 = "CREATE TABLE SEMESTER (id NUMBER(10) NOT NULL, title VARCHAR2(100) NOT NULL)";
//
//        String tableCreateQuery = "create table JBT(name varchar2(10), course varchar2(10))";
//
//         String query2 = "ALTER TABLE SEMESTER\n" +
//                "  ADD (\n" +
//                "    CONSTRAINT semester_pk PRIMARY KEY  (id));";
//
//         String query3 = "CREATE SEQUENCE semester_sequence;";
//
//         String query4 = "CREATE OR REPLACE TRIGGER semester_on_insert\n" +
//                "  BEFORE INSERT ON SEMESTER\n" +
//                "  FOR EACH ROW\n" +
//                "BEGIN\n" +
//                "  SELECT semester_sequence.nextval\n" +
//                "  INTO :new.id\n" +
//                "  FROM dual;\n" +
//                "END;\n"; */

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

