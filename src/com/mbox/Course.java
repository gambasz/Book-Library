package com.mbox;

import java.sql.*;

public class Course {

    private int id;
    private String title, crn, description, department;
    private Person personInstance;
    private Resource resourceInstance[];

    public Course(){

        this.id = 0;
        this.title = "";
        this.crn = "";
        this.description = "";
        this.department = "";
        this.personInstance = new Person();
        this.resourceInstance = new Resource[20];
    }

    public Course(int id, String title, String desc, String dept, String crn){

        this.id = id;
        this.title = title;
        this.crn = crn;
        this.description = desc;
        this.department = dept;
    }

    public Course(int id){

        this.id = id;
        this.title = "";
        this.crn = "";
        this.description = "";
        this.department = "";
    }

    public Course(String title){

        this.id = 0;
        this.title = title;
        this.crn = "";
        this.description = "";
        this.department = "";
    }

    public void setID(int id){

        this.id = id;
    }

    public void setTitle(String title){

        this.title = title;

    }

    public void setCRN(String crn){

        this.crn = crn;

    }

    public void setDescription(String desc){

        this.description = desc;

    }

    public void setDepartment(String dept){

        this.department = dept;

    }

    public void setPersonInstance(Person personInst){

        this.personInstance = personInst;
    }

    public void setResourceInstance(Resource[] resourceInst){

        this.resourceInstance = resourceInst;
    }

    public int getID(){

        return this.id;
    }

    public String getTitle(){

        return this.title;
    }

    public String getCRN(){

        return this.crn;
    }

    public String getDescription(){

        return this.description;
    }

    public String getDepartment(){

        return this.department;
    }

    public Person getPersonInstance(){

        return this.personInstance;
    }

    public Resource[] getResourceInstance(){

        return this.resourceInstance;
    }

    @Override
    public String toString(){

        return "ID: " + this.id + " " +  "Course title: " + this.title + " " + "CRN: " + this.crn + " " + "Description: " + this.description +
                " " + "Department: " + this.department;
    }

    //------- DB SEARCH METHODS --------------

    public String searchByIDQuery(int id){

        return String.format("SELECT * FROM COURSE_CATALOG WHERE ID=%s", id);

    }

    public String searchByTitleQuery(String t){

        return String.format("SELECT * FROM COURSE_CATALOG WHERE TITLE='%s'", t);

    }

    public String searchByCRN(String crn){

        return String.format("SELECT * FROM COURSE_CATALOG WHERE CNUMBER='%s'", crn);

    }

    public String searchByDepartment(String dp){

        return String.format("SELECT * FROM COURSE_CATALOG WHERE DEPARTMENT='%s'", dp);

    }

    //----------- DB ADD METHODS -------------------
    public String addToDBOLD(){

        return String.format("INSERT INTO COURSECT (TITLE, CNUMBER, DESCRIPTION, DEPARTMENT) VALUES ('%s', '%s', '%s', '%s')", this.title, this.crn, this.description, this.department);

    }

    // --- DB UPDATE METHOD ---
    public String update(int identifier, String t, String c, String d, String desc) {

        this.id = identifier;
        this.title = t;
        this.crn = c;
        this.description = d;
        this.department = desc;
        return String.format("UPDATE COURSECT SET TITLE = '%s', CNUMBER = '%s', DESCRIPTION = '%s', DEPARTMENT = '%s' WHERE ID = %s", this.title, this.crn, this.description, this.department, this.id);
    }

    public String getAllSemesters(){

        //while(rs.next()){

          //  System.out.println(String.valueOf(rs.getInt(1)) + " " + rs.getString(2) + " " + rs.getString(3));
        return "";
        }

    // INSERT TO DB AND GET INTO OBJECT:

    public void addToDB(){

        try {

            insertToDB();

            ResultSet rs = DBManager.st.executeQuery("SELECT MAX(ID) FROM COURSECT");


            while(rs.next()){
                this.id = rs.getInt(1);
                this.title = rs.getString(2);
                this.crn = rs.getString(3);
                this.description = rs.getString(4);
                this.department = rs.getString(5);
            }
        }catch (Exception e){

        }
    }

    private void insertToDB(){

        String quer = String.format("INSERT INTO COURSECT (TITLE, CNUMBER, DESCRIPTION, DEPARTMENT) VALUES ('%s', '%s', '%s', '%s')", this.title, this.crn, this.description, this.department);

        try{
            DBManager.st.executeQuery(quer);
        }catch(Exception e){

        }


    }

}

