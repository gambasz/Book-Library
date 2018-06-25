package frontend.data;

import java.util.Objects;

public class Publisher {

    int ID;
    private String description, name, contacts;

    public Publisher(String name, String contacts, String Description) {
        this.name = name;
        this.contacts = contacts;
        this.description = Description;
    }

    public Publisher(int ID, String name, String Description, String contacts) {
        this.name = name;
        this.description = Description;
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
        return description;
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
        this.description = description;
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
        return name;
    }

}
