package com.mbox;

public class Publisher {

    private int id;
    private String title;
    private String contact_information;
    private String description;

    public Publisher(){

        id = 0;
        title = "";
        contact_information = "";
        description = "";

    }

    public Publisher(String t, String contact, String desc){

        this.id = 0;
        this.title = t;
        this.contact_information = contact;
        this.description = desc;
    }

    public void setID(int id){

        this.id = id;
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

    public int getID(){

        return this.id;
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

    // --- DB ADD METHOD ----

    public String addToDB(){

        return String.format("INSERT INTO PUBLISHERS (TITLE, CONTACT_INFO, DESCRIPTION) VALUES ('%s', '%s', '%s')", this.title, this.contact_information, this.description);

    }

    // --- DB UPDATE METHOD ---
    public String update(int identifier, String t, String c, String d) {

        this.id = identifier;
        this.title = t;
        this.contact_information = c;
        this.description = d;
        return String.format("UPDATE PUBLISHERS SET TITLE = '%s', CONTACT_INFO = '%s', DESCRIPTION = '%s' WHERE ID = %s", this.title, this.contact_information, this.description, this.id);
    }

}
