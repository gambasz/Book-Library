package com.mbox;

public class DBManager {


        public String addPersonQuery(Person person){

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

        public String getTableQuery(String table){

            return String.format("SELECT * FROM %s", table);
        }

        public String getIDInQuery(String table, int id){

            return String.format("SELECT * FROM %s WHERE ID=%s", table, id);
        }


    }
