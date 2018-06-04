package com.company;

public class Resource {

    private String type, title, author, isbn, description;
    private int total_amount, current_amount;

    public Resource(){

        this.type = "";
        this.title = "";
        this.author = "";
        this.isbn = "";
        this.total_amount = 0;
        this.current_amount = 0;
        this.description = "";

    }

    public Resource(String ty, String ti, String au, String is, int total, int current, String desc){

        this.type = ty;
        this.title = ti;
        this.author = au;
        this.isbn = is;
        this.total_amount = total;
        this.current_amount = current;
        this.description = desc;

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
