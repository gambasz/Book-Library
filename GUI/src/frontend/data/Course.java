package frontend.data;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.ArrayList;
import java.util.Objects;

/**
 * This is main frontend.data stut. for the program. Every class has a CRN which the unique/primary key, a NAME, department,
 * professor, and the book list.
 *
 * @author Rajahsow Parajuli
 */
public class Course {

    private int ID;
    private SimpleIntegerProperty CRN, YEAR;
    private Enum SEMESTER;
    private SimpleStringProperty title, department, description;
    private Person professor;
    private ArrayList<Resource> resource;
    private int commonID;




    public Course() {
        commonID =0;
    }

    public Course(int CRN, int YEAR, String SEMESTER) {
        this.CRN = new SimpleIntegerProperty(CRN);
        this.YEAR = new SimpleIntegerProperty(YEAR);
        this.SEMESTER = Semester.valueOf(SEMESTER);
    }

    public Course(int ID, String title, String department, String description) {
        this.ID = ID;
        this.title = new SimpleStringProperty(title);
        this.department = new SimpleStringProperty(department);
        this.description = new SimpleStringProperty(description);
    }

    public Course(int ID, int CRN, int YEAR, String SEMESTER, String title, String department, Person professor, String description, ArrayList<Resource> resource) {
        this.ID = ID;
        this.CRN = new SimpleIntegerProperty(CRN);
        this.YEAR = new SimpleIntegerProperty(YEAR);
        this.SEMESTER = Semester.valueOf(SEMESTER.toUpperCase());
        this.title = new SimpleStringProperty(title);
        this.department = new SimpleStringProperty(department);
        this.description = new SimpleStringProperty(description);
        this.professor = professor;
        this.resource = resource;
    }

    public String getDescription() {
        return description.get();
    }

    public SimpleStringProperty descriptionProperty() {
        return description;
    }

    public void setDescription(String description) {
        SimpleStringProperty tmp = new SimpleStringProperty();
        tmp.set(description);

        this.description = tmp;
    }

    public int getID() {
        return ID;
    }

    public int getCommonID() { return commonID; }
    public void setCommonID(int commonID) { this.commonID = commonID; }

    public void setID(int ID) {
        this.ID = ID;
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
        this.YEAR = new SimpleIntegerProperty(YEAR);
    }

    public String getSEMESTER() {
        return SEMESTER.toString();
    }


    public void setSEMESTER(String SEMESTER) {
        this.SEMESTER = Semester.valueOf(SEMESTER.toUpperCase());
    }

    public String getTitle() {
        return title.get();
    }

    public SimpleStringProperty titleProperty() {
        return title;
    }

    public void setTitle(String title) {

        SimpleStringProperty tmp = new SimpleStringProperty();
        tmp.set(title);

        this.title = tmp;
    }

    public String getDepartment() {
        return department.get();
    }

    public SimpleStringProperty departmentProperty() {
        return department;
    }

    public void setDepartment(String department) {
        SimpleStringProperty tmp = new SimpleStringProperty();
        tmp.set(department);

        this.department = tmp;
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
                "ID=" + ID +
                ", CRN=" + CRN +
                ", YEAR=" + YEAR +
                ", SEMESTER=" + SEMESTER +
                ", title=" + title +
                ", department=" + department +
                ", professor=" + professor +
                ", description='" + description + '\'' +
                ", resource=" + resource +
                '}';
    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        Course course = (Course) o;
//        return ID == course.ID;
//    }
//
//    @Override
//    public int hashCode() {
//
//        return Objects.hash(commonID);
//    }
}
