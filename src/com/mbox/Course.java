package com.mbox;

import java.util.ArrayList;

public class Course {

    private int id;
    private String title, crn, description, department;
    private Person personInstance;
    private Resource resourceInstance[];
    private ArrayList<Resource> resourceInstances;
    private int commonID = 0;

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
        this.crn = ""+id;
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


    public frontend.data.Course initCourseGUI(){
        ArrayList <frontend.data.Resource> tmpResource = new ArrayList<frontend.data.Resource>();
        tmpResource.add(this.resourceInstance[0].initResourceGUI());

        frontend.data.Course tmp = new frontend.data.Course(this.id, this.id, 2018,"FALL", this.title, this.department,
                this.personInstance.initPersonGUI(), this.description, tmpResource);
        tmp.setCommonID(this.commonID);
        return tmp;

    }

    public frontend.data.Course initCourseGUIBasic(){
        frontend.data.Course tmp = new frontend.data.Course(this.id,this.title,this.department,this.description);
        tmp.setCommonID(this.commonID);
        return tmp;
    }

    public frontend.data.Course initCourseGUI(String year, String semester){
        ArrayList <frontend.data.Resource> tmpResource = new ArrayList<frontend.data.Resource>();

        //A method to convert resource arraylist to GUI resource array list
        for (int i=0; i<this.resourceInstances.size(); i++ ){

            tmpResource.add(this.resourceInstances.get(i).initResourceGUI());
        }

        //tmpResource.add(this.resourceInstance[0].initResourceGUI());
        year = "2018"; semester = "FALL";


        frontend.data.Course tmp = new frontend.data.Course(this.id, this.id, Integer.valueOf(year),semester, this.title, this.department,
                this.personInstance.initPersonGUI(), this.description, tmpResource);
        tmp.setCommonID(this.commonID);
        return tmp;

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

    public void setCommonID(int commonID) {
        this.commonID = commonID;
    }

    public void setPersonInstance(Person personInst){

        this.personInstance = personInst;
    }

    public void setResourceInstance(Resource[] resourceInst){

        this.resourceInstance = resourceInst;
    }

    public int getCommonID() {
        return commonID;
    }

    public void setResourceInstances(ArrayList<Resource> resourceInstances){
        this.resourceInstances = resourceInstances;
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
    public ArrayList<Resource> getResourceInstances(){
        return this.resourceInstances;
    }

    @Override
    public String toString(){

        return "ID: " + this.id + " " +  "Course title: " + this.title + " " + "CRN: " + this.crn + " " + "Description: " + this.description +
                " " + "Department: " + this.department;
    }



}

