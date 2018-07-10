package com.mbox;
import frontend.data.PersonType;

import java.sql.*;
import java.util.ArrayList;

public class Person {

    private int id, commonid = 0;
    private String first_name, last_name, type;
    Resource resourcePerson[];


    ArrayList<Resource> resourceList = new ArrayList<Resource>();


    public Person(){

        id = 0;
        first_name = "";
        last_name = "";
        type = "";
        this.resourcePerson = new Resource[20];

    }

    public Person(int id, String first_name, String last_name, String type){

        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.type = type;
    }

    public void setID(int identifier){

        this.id = identifier;
    }

    public void setFirstName(String name){

        if(name.isEmpty()){

            throw new IllegalArgumentException("First name can't be empty");
        } else{

            this.first_name = name;
        }

    }


    public void setLastName(String name){

        if(name.isEmpty()){

            throw new IllegalArgumentException("Last name can't be empty");
        } else{

            this.last_name = name;
        }

    }

    public void setType(String type){

        if(!type.equals("Coordinator") && !type.equals("Instructor")){

            throw new IllegalArgumentException("Invalid person type: " + type);
        }

        else{

            this.type = type;
        }

    }

    public void setResourcesPerson(Resource[] resourcesPerson){

        this.resourcePerson = resourcesPerson;
    }

    public void setCommonid(int commonid){
        this.commonid = commonid;
    }

    public int getID(){

        return this.id;
    }

    public void setResourceList(ArrayList<Resource> resourceList) {
        this.resourceList = resourceList;
    }


    public String getFirstName(){

        return this.first_name;
    }


    public String getLastName(){

        return this.last_name;
    }


    public String getType(){

        return this.type;
    }

    public Resource[] getResourcePerson(){

        return this.resourcePerson;
    }
    public int getCommonid(){
        return this.commonid;
    }
    public ArrayList<Resource> getResourceList() {

        return resourceList;
    }

    @Override
    public String toString(){

        return "ID: " + this.id + "Name: " + this.first_name + " " + this.last_name + " " + "Role: " + this.type;
    }

    //------- DB SEARCH METHODS --------------

    public String searchByIDQuery(){

        return String.format("SELECT * FROM PERSON WHERE ID=%s", this.id);

    }

    public String searchFirstNameQuery(){

        return String.format("SELECT * FROM PERSON WHERE FIRSTNAME='%s'", this.getFirstName());

    }

    public String searchLastNameQuery(){

        return String.format("SELECT * FROM PERSON WHERE LASTNAME='%s'", this.getLastName());

    }

    public String searchTypeQuery(){

        return String.format("SELECT * FROM PERSON WHERE TYPE='%s'", this.getType());

    }

    // -----DB INSERT METHODS------------------

    public void addToDB(){

        try {

            insertToDB();

            ResultSet rs = DBManager.st.executeQuery("SELECT MAX(ID) FROM PERSON");


            while(rs.next()){
                this.id = rs.getInt(1);
                this.type = rs.getString(2);
                this.first_name = rs.getString(3);
                this.last_name = rs.getString(4);
            }
        }catch (Exception e){

        }
    }

    private void insertToDB(){

        String quer = String.format("INSERT INTO PERSON (FIRSTNAME, LASTNAME, TYPE) VALUES ('%s', '%s', '%s')", this.first_name, this.last_name, this.type);

        try{
            DBManager.st.executeQuery(quer);
        }catch(Exception e){

        }


    }


    //-------DB METHODS UPDATE by ID--
    public String update(int identifier, String first, String last, String type) {

        this.id = identifier;
        this.first_name = first;
        this.last_name = last;
        this.type = type;
        return String.format("UPDATE PERSON SET FIRSTNAME = '%s', LASTNAME = '%s', TYPE = '%s' WHERE ID = %s", first, last, type, this.id);
    }


    private ArrayList<frontend.data.Resource> initResourceListGUI(ArrayList<Resource> tempList){
        ArrayList<frontend.data.Resource> returnedList = new ArrayList<frontend.data.Resource>();
        for (Resource resource : tempList){
            returnedList.add(resource.initResourceGUI());
        }

        return returnedList;

    }


    public frontend.data.Person initPersonGUI(){

        PersonType enumTmp;

        switch (this.type){
            case "Program Coordinator" : enumTmp = PersonType.ProgramCoordinator; break;
            case "Course Coordinator" : enumTmp = PersonType.CourseCoordinator; break;
            case "Course Instructor" : enumTmp = PersonType.CourseInstructor; break;
            default: enumTmp = PersonType.CourseInstructor;

        }
        frontend.data.Person p = new frontend.data.Person(this.last_name,this.first_name,this.id, enumTmp.toString());
        p.setResources(initResourceListGUI(this.resourceList));
        return p;
    }



}
