package com.mbox;
import java.sql.*;
import com.mbox.Main;

public class DBManager {

    public static final String PERSON_TABLE = "PERSON";
    public static final String COURSE_TABLE = "COURSECT";
    public static final String PUBLISHER_TABLE = "PUBLISHERS";
    public static final String RESOURCE_TABLE = "RESOURCES";
    public static final String SEMESTER_TABLE = "SEMESTER";
    public static final String RELATION_COURSE_PERSON = "RELATION_COURSE_PERSON";
    public static final String RELATION_COURSE_RESOURCES = "RELATION_COURSE_RESOURCES";
    public static final String RELATION_COURSE_SEMESTER = "RELATION_COURSE_SEMESTER";
    public static final String RELATION_PUBLISHER_RESOURCE = "RELATION_PUBLISHER_RESOURCE";

    public static Connection establishDB() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection conn = DriverManager.getConnection("jdbc:oracle:thin:USERNAME/PASSWORD@HOST:PORT:SID");
            return conn;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


   /* public static String addPersonQuery(Person person) {

        String first, last, type;
        first = person.getFirstName();
        last = person.getLastName();
        type = person.getType();
        String query = String.format("INSERT INTO PERSON (FIRSTNAME, LASTNAME, TYPE) VALUES ('%s', '%s', '%s')", first, last, type);
        return query;
    }


        public String addCourseQuery(Course course){

            String title, crn, description, department;
            title = course.getTitle();
            crn = course.getCRN();
            description = course.getDescription();
            department = course.getDepartment();

            return String.format("INSERT INTO COURSECT (TITLE, CNUMBER, DESCRIPTION, DEPARTMENT) VALUES ('%s', '%s', '%s', '%s')", title, crn, description, department);

        }

        public String addResourceQuery(Resource resource){

            String type, title, author, isbn, desc;
            int total_amount, current_amount;

            type = resource.getType();
            title = resource.getTitle();
            author = resource.getAuthor();
            isbn = resource.getISBN();
            total_amount = resource.getTotalAmount();
            current_amount = resource.getCurrentAmount();
            desc = resource.getDescription();

            return String.format("INSERT INTO RESOURCES (TYPE, TITLE, AUTHOR, ISBN, TOTAL_AMOUNT, CURRENT_AMOUNT, DESCRIPTION) VALUES ('%s', '%s', '%s', '%s', %d, %d, '%s')", type, title, author, isbn, total_amount, current_amount, desc);

        }

        public String addPublisherQuery(Publisher publisher){

            String title, contact_info, description;
            title = publisher.getTitle();
            contact_info = publisher.getContactInformation();
            description = publisher.getDescription();

            return String.format("INSERT INTO PUBLISHER (TITLE, CONTACT_INFO, DESCRIPTION) VALUES ('%s', '%s', '%s')", title, contact_info, description);

        }

        public String editPersonDB(Person pe){

            int id = pe.getID();
            String first, last, type;
            first = pe.getFirstName();
            last = pe.getLastName();
            type = pe.getType();
            String query = String.format("\"UPDATE PERSON SET FIRSTNAME = '%s', LASTNAME = '%s', TYPE = '%s' WHERE ID = %s\"", first, last, type, id);
            return query;

        }

        public String getTableQuery(String table){

            return String.format("SELECT * FROM %s", table);
        }

        public String getIDInQuery(String table, int id){

            return String.format("SELECT * FROM %s WHERE ID=%s", table, id);
        }

     */

}
