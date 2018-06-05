package com.mbox;

public class Person {

    private int id;
    private String first_name, last_name, type;

    public Person(){

        id = 0;
        first_name = "";
        last_name = "";
        type = "";

    }

    public Person(String first_name, String last_name, String type){

        this.id = 0;
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

    public int getID(){

        return this.id;
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

    @Override
    public String toString(){

        return "Name: " + this.first_name + " " + this.last_name + " " + "Role: " + this.type;
    }

    //------- DB METHODS --------------

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

    //-------DB METHODS to change index by ID--
    public String newFirstName(String name){
        
        this.setFirstName(name);
        return String.format("UPDATE PERSON SET FIRSTNAME = '%s' WHERE ID = %s", name, this.id);
        
    }
    
    public String newLNameByID(String last){

        this.setLastName(last);
        return String.format("UPDATE PERSON SET LASTNAME = '%s' WHERE ID = %s", last, this.id);
        //return String.format("UPDATE PERSON SET LASTNAME = '" + newLName + "' WHERE ID = '" +ID+"'");
        
    }
    
    public String newType(String t){

        this.setType(t);
        return String.format("UPDATE PERSON SET TYPE = '%s' WHERE ID = %s", t, this.id);
        //return String.format("UPDATE PERSON SET TYPE = '" + newType + "' WHERE ID = '" +ID+"'");
        
    }
    
    



}
