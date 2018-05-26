package data;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.ArrayList;

/**
 * This is main data stut. for the program. Every class has a CRN which the unique/primary key, a name, department,
 * professor, and the book list.
 *
 * @author Rajahsow Parajuli
 */
public class Course {


    final private SimpleIntegerProperty CRN;
    private SimpleStringProperty professor, name, department;
    private ArrayList<String> books;


    /**
     * The constructor takes a CRN, the name of the prof., the name of the class, the department code, and the books to
     * return a class object representing all the information about the class
     *
     * @param CRN        is the data.Course regestation number. it is unquie for all classes
     * @param professor  name of the professor teaching the class
     * @param name       of the class
     * @param department is the department's code
     * @param books      is a string list of books that is required for the course
     */
    public Course(int CRN, String professor, String name, String department, ArrayList<String> books) {
        this.CRN =  new SimpleIntegerProperty(CRN);
        this.professor = new SimpleStringProperty(professor);
        this.name = new SimpleStringProperty (name);
        this.department = new SimpleStringProperty (department);
        this.books = books;
    }

    /**
     * Return the unique CRN
     *
     * @return CRN of the class
     */
    public int getCRN() {
        return CRN.get();
    }

    /**
     * Return the name of the professor teaching the class
     *
     * @return professor 's name teaching the class
     */
    public String getProfessor() {
        return professor.get();
    }

    /**
     * set professor 's name teaching the class
     *
     * @param professor name of the professor teaching the class
     */
    public void setProfessor(String professor) {
        this.professor = new SimpleStringProperty(professor);
    }

    /**
     * Return the name of the class
     *
     * @return name of the class
     */
    public String getName() {
        return name.get();
    }

    /**
     * set name of the class
     *
     * @param name of the professor teaching the class
     */
    public void setName(String name) {
        this.name = new SimpleStringProperty (name);
    }

    /**
     * Return the deparatment's code
     *
     * @return deparatment 's code
     */
    public String getDepartment() {
        return department.get();
    }

    /**
     * set the department's code
     *
     * @param department 's code
     */
    public void setDepartment(String department) {
        this.department = new SimpleStringProperty(department);
    }

    /**
     * get the list of books required for the class
     *
     * @return books is a string list of books that is required for the course
     */
    public ArrayList<String> getBooks() {
        return books;
    }

    /**
     * set the list of books required for the class
     *
     * @param books is a string list of books that is required for the course
     */
    public void setBooks(ArrayList<String> books) {
        this.books = books;
    }

    /**
     * check if two class have the same CRN number if they do they are the same
     */
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        if (!super.equals(object)) return false;
        Course aCourse = (Course) object;
        return CRN == aCourse.CRN;
    }

    /**
     * return the hashcode for the book object
     *
     * @return hashcode based on the books crn
     */
    public int hashCode() {

        return java.util.Objects.hash(super.hashCode(), CRN);
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "data.Course{" +
                "CRN=" + CRN.getValue() +
                ", professor='" + professor.getValue() + '\'' +
                ", name='" + name.getValue() + '\'' +
                ", department='" + department.getValue() + '\'' +
                ", books=" + books.toString() +
                '}';
    }
    public static void main(String[] args){
        String test = new String("bob");
        ArrayList<String> books =  new ArrayList<>();
        int numb =  10;
        books.add("major");
        Course bob = new Course(numb,test,test,test,books);
        System.out.print(bob);
    }
}
