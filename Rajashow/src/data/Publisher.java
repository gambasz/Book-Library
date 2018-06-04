package data;

import java.util.ArrayList;
import java.util.Objects;

public class Publisher {
    // NAME , ID, contact, DESCRIPTION,
    final String NAME, DESCRIPTION;
    final int ID;
    private ArrayList<String> contacts;

    public Publisher(String NAME, String DESCRIPTION, int ID) {
        this.NAME = NAME;
        this.DESCRIPTION = DESCRIPTION;
        this.ID = ID;
    }

    public Publisher(String NAME, String DESCRIPTION, int ID, ArrayList<String> contacts) {
        this.NAME = NAME;
        this.DESCRIPTION = DESCRIPTION;
        this.ID = ID;
        this.contacts = contacts;
    }

    public String getNAME() {
        return NAME;
    }

    public String getDESCRIPTION() {
        return DESCRIPTION;
    }

    public int getID() {
        return ID;
    }

    public ArrayList<String> getContacts() {
        return contacts;
    }

    public void setContacts(ArrayList<String> contacts) {
        this.contacts = contacts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Publisher publisher = (Publisher) o;
        return ID == publisher.ID &&
                Objects.equals(NAME, publisher.NAME) &&
                Objects.equals(DESCRIPTION, publisher.DESCRIPTION);
    }

    @Override
    public int hashCode() {

        return Objects.hash(NAME, DESCRIPTION, ID);
    }

    @Override
    public String toString() {
        return "Publisher{" +
                "NAME='" + NAME + '\'' +
                ", DESCRIPTION='" + DESCRIPTION + '\'' +
                ", ID=" + ID +
                ", contacts=" + contacts +
                '}';
    }
}
