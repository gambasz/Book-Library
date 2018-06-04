package com.company;

public class Publisher {

    private String title;
    private String contact_information;

    public Publisher(){

        title = "";
        contact_information = "";

    }

    public Publisher(String title, String contact_information){

        this.title = title;
        this.contact_information = contact_information;
    }

    public void setTitle(String title){

        this.title = title;
    }

    public void setContactInformation(String contact_info){

        this.contact_information = contact_info;
    }

    public String getTitle(){

        return this.title;
    }

    public String getContactInformation(){

        return this.contact_information;
    }

    @Override
    public String toString(){

        return "Title: " + this.title + "\n" + "Contact Info: \n" + this.contact_information;
    }
}
