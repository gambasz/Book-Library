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

    public String addToDB(){


        String quer = String.format("INSERT INTO PERSON (FIRSTNAME, LASTNAME, TYPE) VALUES ('%s', '%s', '%s')", this.first_name, this.last_name, this.type);

        try {

            ResultSet result = Main.st.executeQuery(quer);

            System.out.println("haha datar obegir" + result);


        }
        catch (Exception e){

        }


        return quer;
    }


    //-------DB METHODS UPDATE by ID--
    public String update(int identifier, String first, String last, String type) {

        this.id = identifier;
        this.first_name = first;
        this.last_name = last;
        this.type = type;
        return String.format("UPDATE PERSON SET FIRSTNAME = '%s', LASTNAME = '%s', TYPE = '%s' WHERE ID = %s", first, last, type, this.id);
    }
    



}
