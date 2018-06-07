package data;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.ArrayList;
import java.util.Objects;

/**
 * This is main data stut. for the program. Every class has a CRN which the unique/primary key, a NAME, department,
 * professor, and the book list.
 *
 * @author Rajahsow Parajuli
 */
public class Course {


    final private SimpleIntegerProperty CRN, YEAR;
    final private SimpleStringProperty SEMESTER;
    private SimpleStringProperty title, department;
    private Person professor;
    private ArrayList<Resource> resource;


    public Course(int CRN, int YEAR, String SEMESTER) {
        this.CRN = new SimpleIntegerProperty(CRN);
        this.YEAR = new SimpleIntegerProperty(YEAR);
        this.SEMESTER = new SimpleStringProperty(SEMESTER);
    }

    public Course(int CRN, int YEAR, String SEMESTER, String title, String department, Person professor, ArrayList<Resource> resource) {
        this.CRN = new SimpleIntegerProperty(CRN);
        this.YEAR = new SimpleIntegerProperty(YEAR);
        this.SEMESTER = new SimpleStringProperty(SEMESTER);
        this.title = new SimpleStringProperty(title);
        this.department = new SimpleStringProperty(department);
        this.professor = professor;
        this.resource = resource;
    }

    public int getCRN() {
        return CRN.get();
    }

    public SimpleIntegerProperty CRNProperty() {
        return CRN;
    }

    public void setCRN(int CRN) {
        this.CRN.set(CRN);
    }

    public int getYEAR() {
        return YEAR.get();
    }

    public SimpleIntegerProperty YEARProperty() {
        return YEAR;
    }

    public void setYEAR(int YEAR) {
        this.YEAR.set(YEAR);
    }

    public String getSEMESTER() {
        return SEMESTER.get();
    }

    public SimpleStringProperty SEMESTERProperty() {
        return SEMESTER;
    }

    public void setSEMESTER(String SEMESTER) {
        this.SEMESTER.set(SEMESTER);
    }

    public String getTitle() {
        return title.get();
    }

    public SimpleStringProperty titleProperty() {
        return title;
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public String getDepartment() {
        return department.get();
    }

    public SimpleStringProperty departmentProperty() {
        return department;
    }

    public void setDepartment(String department) {
        this.department.set(department);
    }

    public Person getProfessor() {
        return professor;
    }

    public void setProfessor(Person professor) {
        this.professor = professor;
    }

    public ArrayList<Resource> getResource() {
        return resource;
    }

    public void addResource(Resource newResource) {
        this.resource.add(newResource);
    }

    public void setResource(ArrayList<Resource> resource) {
        this.resource = resource;
    }

    @Override
    public String toString() {
        return "Course{" +
                "CRN=" + CRN +
                ", YEAR=" + YEAR +
                ", SEMESTER=" + SEMESTER +
                ", title=" + title +
                ", department=" + department +
                ", professor=" + professor +
                ", resource=" + resource +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return Objects.equals(CRN, course.CRN) &&
                Objects.equals(YEAR, course.YEAR) &&
                Objects.equals(SEMESTER, course.SEMESTER);
    }

    @Override
    public int hashCode() {

        return Objects.hash(CRN, YEAR, SEMESTER);
    }
}
