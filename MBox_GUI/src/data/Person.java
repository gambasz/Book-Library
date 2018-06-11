package data;

import java.util.Objects;

public class Person {

    String lastName, firstName, id;
    private Enum type;


    public Person(String lastName, String firstName, String id, Enum type) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.id = id;
        this.type = type;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getId() {
        return id;
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
                Objects.equals(id, person.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(lastName, firstName, id);
    }

    @Override
    public String toString() {
        return "Person{" +
                "lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", ID='" + id + '\'' +
                ", type=" + type +
                '}';
    }
}
