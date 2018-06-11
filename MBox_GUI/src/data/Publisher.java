package data;

import java.util.Objects;

public class Publisher {

    int ID;
    private String Description, name, contacts ;

    public Publisher(String name, String contacts, int ID) {
        this.name = name;
        this.contacts = contacts;
        this.ID = ID;
    }

    public Publisher(int ID, String name, String Description, String contacts) {
        this.name = name;
        this.Description = Description;
        this.ID = ID;
        this.contacts = contacts;
    }


    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return Description;
    }

    public int getID() {
        return ID;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public void setDescription(String description) {
        this.Description = description;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Publisher publisher = (Publisher) o;
        return ID == publisher.ID;
    }

    @Override
    public int hashCode() {

        return Objects.hash(ID);
    }

    @Override
    public String toString() {
        return "Publisher{" +
                "name='" + name + '\'' +
                ", Description='" + Description + '\'' +
                ", ID=" + ID +
                ", contacts=" + contacts +
                '}';
    }

}
