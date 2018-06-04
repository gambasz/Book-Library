package com.company;

public class Course {

    private String title;
    private String crn;
    private String description;
    private String department;

    public Course(){

        this.title = "";
        this.crn = "";
        this.description = "";
        this.department = "";

    }

    public Course(String title, String crn, String desc, String dept){

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
}
