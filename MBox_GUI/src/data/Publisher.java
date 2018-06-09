package data;

import java.util.Objects;

public class Publisher {
    final String NAME, DESCRIPTION;
    int ID;
    private String contacts;

    public Publisher(String NAME, String DESCRIPTION, int ID) {
        this.NAME = NAME;
        this.DESCRIPTION = DESCRIPTION;
        this.ID = ID;
    }

    public Publisher(String NAME, String DESCRIPTION, int ID, String contacts) {
        this.NAME = NAME;
        this.DESCRIPTION = DESCRIPTION;
        this.ID = ID;
        this.contacts = contacts;
    }

    public void setID(int ID) {
        this.ID = ID;
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

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
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
