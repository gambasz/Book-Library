package com.mbox;

public class Publisher {

    private String title;
    private String contact_information;
    private String description;

    public Publisher(){

        title = "";
        contact_information = "";
        description = "";

    }

    public Publisher(String t, String contact, String desc){

        this.title = t;
        this.contact_information = contact;
        this.description = desc;
    }

    public void setTitle(String t){

        this.title = t;
    }

    public void setContactInformation(String contact){

        this.contact_information = contact;
    }

    public void setDescription(String desc){

        this.description = desc;
    }

    public String getTitle(){

        return this.title;
    }

    public String getContactInformation(){

        return this.contact_information;
    }

    public String getDescription(){

        return this.description;
    }

    @Override
    public String toString(){

        return "Title: " + this.title + "\n" + "Contact Info: \n" + this.contact_information;
    }
}
