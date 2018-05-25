import java.util.ArrayList;

/**
 * This is main data stut. for the program. Every class has a CRN which the unique/primary key, a name, department,
 * professor, and the book list.
 *
 * @author Rajahsow Parajuli
 */
public class Course {


    final private int CRN;
    private String professor, name, department;
    private ArrayList<String> books;

    /**
     * The no arg constructor set the crn to -1, and all the other information of the class to null.
     */
    public Course() {
        CRN = -1;
        professor = name = department = null;
    }

    /**
     * The constructor takes a CRN, the name of the prof., the name of the class, the department code, and the books to
     * return a class object represnting all the infomation about the class
     *
     * @param CRN        is the Course regestation number. it is unquie for all classes
     * @param professor  name of the professor teaching the class
     * @param name       of the class
     * @param department is the deparatment code
     * @param books      is a string list of books that is required for the course
     */
    public Course(int CRN, String professor, String name, String department, ArrayList<String> books) {
        this.CRN = CRN;
        this.professor = professor;
        this.name = name;
        this.department = department;
        this.books = books;
    }

    /**
     * Return the unique CRN
     *
     * @return CRN of the class
     */
    public int getCRN() {
        return CRN;
    }

    /**
     * Return the name of the professor teaching the class
     *
     * @return professor 's name teaching the class
     */
    public String getProfessor() {
        return professor;
    }

    /**
     * set professor 's name teaching the class
     *
     * @param professor name of the professor teaching the class
     */
    public void setProfessor(String professor) {
        this.professor = professor;
    }

    /**
     * Return the name of the class
     *
     * @return name of the class
     */
    public String getName() {
        return name;
    }

    /**
     * set name of the class
     *
     * @param name of the professor teaching the class
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Return the deparatment's code
     *
     * @return deparatment 's code
     */
    public String getDepartment() {
        return department;
    }

    /**
     * set the department's code
     *
     * @param department 's code
     */
    public void setDepartment(String department) {
        this.department = department;
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
        return "Course{" +
                "CRN=" + CRN +
                ", professor='" + professor + '\'' +
                ", name='" + name + '\'' +
                ", department='" + department + '\'' +
                ", books=" + books +
                '}';
    }
}
