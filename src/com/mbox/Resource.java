package com.mbox;

public class Resource {

    private String type, title, author, isbn, description;
    private int total_amount, current_amount, id;

    public Resource(){

        this.type = "";
        this.title = "";
        this.author = "";
        this.isbn = "";
        this.total_amount = 0;
        this.current_amount = 0;
        this.description = "";
        this.id =0 ;

    }

    public Resource(int id, String type, String title, String author, String isbn, int total, int current, String desc){

        this.type = type;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.total_amount = total;
        this.current_amount = current;
        this.description = desc;
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

        return "Type: " + this.type + " "
        + "Title: " + this.title + " "
        + "Author: " + this.author + " "
        + "ISBN: " + this.isbn + " "
        + "Total Amount: " + String.valueOf(this.total_amount) + " "
        + "Current Amount: " + String.valueOf(this.current_amount) + " "
        + "Description: " + this.description;
    }
}
