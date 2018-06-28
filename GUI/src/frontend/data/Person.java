package frontend.data;

import java.util.ArrayList;
import java.util.Objects;

public class Person {

    private String lastName, firstName;
    private int ID;
    private Enum type;
    private ArrayList<Resource> resources;

    public Person(String lastName, String firstName, int ID, Enum type, ArrayList<Resource> resources) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.ID = ID;
        this.type = type;
        this.resources = resources;
    }

    public Person(String lastName, String firstName, String type) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.type = PersonType.valueOf(type);
    }

    public Person(String lastName, String firstName, int ID, String type) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.ID = ID;
        this.type = PersonType.valueOf(type);
    }

    public Person(Person another) {
        this.ID = another.getID();
        this.lastName = another.getLastName();
        this.firstName = another.getFirstName();
        this.type = PersonType.valueOf(another.getType());
    }

    public void setType(Enum type) {
        this.type = type;
    }

    public ArrayList<Resource> getResources() {
        return resources;
    }

    public void setResources(ArrayList<Resource> resources) {
        this.resources = resources;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public int getID() {
        return ID;
    }

    public String getType() {
        return type.toString();
    }

    public void setType(String  type) {
        this.type = PersonType.valueOf(type);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(ID, person.ID)&& Objects.equals(lastName, person.lastName)&& Objects.equals(firstName, person.firstName)&& Objects.equals(type, person.type);
    }

    @Override
    public int hashCode() {

        return Objects.hash(lastName, firstName,type, ID);
    }

    @Override
    public String toString() {
        return firstName.concat(" ").concat(lastName).concat(" | ").concat(type.toString());

    }
}
