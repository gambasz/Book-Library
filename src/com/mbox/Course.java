package com.mbox;

public class Course {

    private int id;
    private String title;
    private String crn;
    private String description;
    private String department;

    public Course(){

        this.id = 0;
        this.title = "";
        this.crn = "";
        this.description = "";
        this.department = "";

    }

    public Course(String title, String crn, String desc, String dept){

        this.id = 0;
        this.title = title;
        this.crn = crn;
        this.description = desc;
        this.department = dept;
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

    @Override
    public String toString(){

        return "Course title: " + this.title + " " + "CRN: " + this.crn + " " + "Description: " + this.description +
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
    public String addToDB(){

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


}
