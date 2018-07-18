package com.mbox;
import frontend.data.PersonType;
import java.util.ArrayList;
import java.util.Objects;


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
            case "ProgramCoordinator" : enumTmp = PersonType.ProgramCoordinator; break;
            case "CourseCoordinator" : enumTmp = PersonType.CourseCoordinator; break;
            case "CourseInstructor" : enumTmp = PersonType.CourseInstructor; break;
            default: enumTmp = PersonType.CourseInstructor;

        }
        frontend.data.Person p = new frontend.data.Person(this.last_name,this.first_name,this.id, enumTmp.toString());
        p.setResources(initResourceListGUI(this.resourceList));
        return p;
    }

//        @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        Person person = (Person) o;
//        return Objects.equals(id, person.id)&& Objects.equals(last_name, person.first_name)&& Objects.equals(first_name, person.last_name)&& Objects.equals(type, person.type);
//    }
//
//    @Override
//    public int hashCode() {
//
//        return Objects.hash(last_name, first_name,type, id);
//    }



}
