package Models.frontend;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.ArrayList;

/**
 * This is main frontend.data stut. for the program. Every class has a CRN which the unique/primary key, a NAME, department,
 * professor, and the book list.
 *
 * @author Rajahsow Parajuli
 */
@SuppressWarnings("unused")
public class Course {

    private int ID;
    private SimpleIntegerProperty CRN, YEAR;
    private Enum SEMESTER;
    private SimpleStringProperty title, department, description;
    private Person professor;
    private ArrayList<Resource> resource;
    private int commonID;
    private SimpleStringProperty notes;


    public Course() {
        commonID = 0;
    }

    public Course(int CRN, int YEAR, String SEMESTER) {
        this.CRN = new SimpleIntegerProperty(CRN);
        this.YEAR = new SimpleIntegerProperty(YEAR);
        this.SEMESTER = Semester.valueOf(SEMESTER);
        this.notes = new SimpleStringProperty();
    }

    public Course(int ID, String title, String department, String description) {
        this.ID = ID;
        this.title = new SimpleStringProperty(title);
        this.department = new SimpleStringProperty(department);
        this.description = new SimpleStringProperty(description);
        this.notes = new SimpleStringProperty();

    }

    public Course(int ID, int CRN, int YEAR, String semester, String title, String department, Person professor, String description, ArrayList<Resource> resource) {
        this.ID = ID;
        this.CRN = new SimpleIntegerProperty(CRN);
        this.YEAR = new SimpleIntegerProperty(YEAR);
        this.SEMESTER = Semester.valueOf(semester.toUpperCase());
        this.title = new SimpleStringProperty(title);
        this.department = new SimpleStringProperty(department);
        this.description = new SimpleStringProperty(description);
        this.notes = new SimpleStringProperty();
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


        this.description = new SimpleStringProperty(description);

    }

    public int getID() {
        return ID;
    }

    public int getCommonID() {
        return commonID;
    }

    public void setCommonID(int commonID) {
        this.commonID = commonID;
    }

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
        this.CRN = new SimpleIntegerProperty(CRN);
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


        this.title = new SimpleStringProperty(title);

    }

    public String getDepartment() {
        return department.get();
    }

    public SimpleStringProperty departmentProperty() {
        return department;
    }

    public void setDepartment(String department) {

        this.department = new SimpleStringProperty(department);
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

    public String getNotes() {
        return notes.get();
    }

    public void setNotes(String notes)
    {
        this.notes = new SimpleStringProperty(notes);
    }

    public SimpleStringProperty NotesProperty() {
        return notes;
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
