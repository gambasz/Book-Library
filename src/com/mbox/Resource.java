package com.mbox;

import java.sql.*;

public class Resource {

    private String type, title, author, isbn, description;
    private int total_amount, current_amount, id;
    private Publisher publisherInstance;

    public Resource(){

        id = 0;
        type = "";
        title = "";
        author = "";
        isbn = "";
        total_amount = 0;
        current_amount = 0;
        description = "";
        publisherInstance = new Publisher("Title", "Contact Information", "Description");

    }

    public Resource(int id, String type, String title, String author, String isbn, int total, int current,
                    String desc){

        this.id = id;
        this.type = type;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.total_amount = total;
        this.current_amount = current;
        this.description = desc;
        //this.publisherInstance = publisherInst;

    }

    public Resource(int id, String type, String title, String author, String isbn, int total, int current,
                    String desc, Publisher publisherInst){

        this.id = id;
        this.type = type;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.total_amount = total;
        this.current_amount = current;
        this.description = desc;
        this.publisherInstance = publisherInst;

    }

    public frontend.data.Resource initResourceGUI() {
        frontend.data.Resource tmp = new frontend.data.Resource(this.isbn, this.type, this.title, this.author,
                this.description, true, this.total_amount, this.id, this.current_amount, this.publisherInstance.initPublisherGUI());
        return tmp;
    }



    public void setID(int id){

        this.id = id;
    }

    public void setType(String ty){

        this.type = ty;

    }

    public void setTitle(String ti){

        this.title = ti;

    }

    public void setAuthor(String au){

        this.author = au;

    }

    public void setISBN(String is){

        this.isbn = is;

    }

    public void setTotalAmount(int total){

        this.total_amount = total;
    }

    public void setCurrentAmount(int current){

        this.current_amount = current;
    }

    public void setDescription(String desc){

        this.description = desc;
    }

    public void setPublisherInstance(Publisher inst){

        this.publisherInstance = inst;
    }

    public int getID(){

        return this.id;
    }

    public String getType(){

        return this.type;

    }

    public String getTitle(){

        return this.title;

    }

    public String getAuthor(){

        return this.author;
    }

    public String getISBN(){

        return this.isbn;
    }

    public int getTotalAmount(){

        return this.total_amount;
    }

    public int getCurrentAmount(){

        return this.current_amount;
    }

    public String getDescription(){

        return this.description;
    }

    public Publisher getPublisherInstance(){

        return this.publisherInstance;
    }


    //-------adding to db method------

    public String addtoDB(){

        String query = String.format("INSERT INTO RESOURCES (TYPE, TITLE, AUTHOR, ISBN, TOTAL_AMOUNT, CURRENT_AMOUNT, DESCRIPTION) VALUES ('%s', '%s', '%s', '%s', %d, %d, '%s')",
                this.type, this.title, this.author, this.isbn, this.total_amount, this.current_amount, this.description);
        return query;
    }
    public String update (String type, String title, String author, String isbn, int total_amount, int current_amount, String description){

        this.type = type;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.total_amount = total_amount;
        this.current_amount = current_amount;
        this.description = description;

        return String.format("UPDATE RESOURCES SET TYPE = '%s', TITLE = '%s', AUTHOR = '%s', ISBN = '%s', TOTAL_AMOUNT = '%s', CURRENT_AMOUNT = '%s', DESCRIPTION = '%s'  WHERE ID = %s",
                this.type, this.title, this.author, this.isbn, this.total_amount, this.current_amount, this.description, this.id);

    }

    @Override
    public String toString(){

        return "ID: " + this.id + " "
        +"Type: " + this.type + " "
        + "Title: " + this.title + " "
        + "Author: " + this.author + " "
        + "ISBN: " + this.isbn + " "
        + "Total Amount: " + String.valueOf(this.total_amount) + " "
        + "Current Amount: " + String.valueOf(this.current_amount) + " "
        + "Description: " + this.description;
    }

    public void addToDB(){

        try {

            insertToDB();

            ResultSet rs = DBManager.st.executeQuery("SELECT MAX(ID) FROM RESOURCES");


            while(rs.next()){
                this.id = rs.getInt(1);
                this.type = rs.getString(2);
                this.title = rs.getString(3);
                this.author = rs.getString(4);
                this.isbn = rs.getString(5);
                this.total_amount = rs.getInt(6);
                this.current_amount = rs.getInt(7);
                this.description = rs.getString(8);
            }
        }catch (Exception e){

        }
    }

    private void insertToDB(){

        String quer = String.format("INSERT INTO RESOURCES (TYPE, TITLE, AUTHOR, ISBN, TOTAL_AMOUNT, CURRENT_AMOUNT, DESCRIPTION) VALUES ('%s', '%s', '%s', '%s', %s, %s, '%s')"
                , this.type, this.title, this.author, this.isbn, this.total_amount, this.current_amount, this.description);

        try{
            DBManager.st.executeQuery(quer);
        }catch(Exception e){

        }


    }
}
