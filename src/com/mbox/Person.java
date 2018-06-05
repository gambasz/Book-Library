package com.mbox;

public class Person {

    private String first_name, last_name, type;

    public Person(){

        first_name = "";
        last_name = "";
        type = "";

    }

    public Person(String first_name, String last_name, String type){

        this.first_name = first_name;
        this.last_name = last_name;
        this.type = type;
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

    public String searchByIDQuery(int id){

        return String.format("SELECT * FROM PERSON WHERE ID=%s", id);

    }

    public String searchByFirstNameQuery(String first){

        return String.format("SELECT * FROM PERSON WHERE FIRSTNAME='%s'", first);

    }

    public String searchByLastNameQuery(String last){

        return String.format("SELECT * FROM PERSON WHERE LASTNAME='%s'", last);

    }

    public String searchByType(String ty){

        return String.format("SELECT * FROM PERSON WHERE TYPE='%s'", ty);

    }




}
