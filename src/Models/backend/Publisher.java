package Models.backend;

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
    public Publisher(int id,String title, String contact, String description){

        this.id = id;
        this.title = title;
        this.contact_information = contact;
        this.description = description;
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

        return "ID: " + this.id + " " + "Title: " + this.title + "\n" + "Contact Info: " + this.contact_information + " " + "Description: " + this.description;
    }



    public Models.frontend.Publisher initPublisherGUI(){
        Models.frontend.Publisher pub = new Models.frontend.Publisher(this.id,this.title,this.description,this.contact_information);
        return pub;
    }

}
