package data;

import java.util.Objects;

public class Person {

    private String lastName, firstName;
    private int ID;
    private Enum type;


    public Person(String lastName, String firstName, int ID, Enum type) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.ID = ID;
        this.type = type;
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

    public Enum getType() {
        return type;
    }

    public void setType(Enum type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(lastName, person.lastName) &&
                Objects.equals(firstName, person.firstName) &&
                Objects.equals(ID, person.ID);
    }

    @Override
    public int hashCode() {

        return Objects.hash(lastName, firstName, ID);
    }

    @Override
    public String toString() {
        return "Person{" +
                "lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", ID='" + ID + '\'' +
                ", type=" + type +
                '}';
    }
}
