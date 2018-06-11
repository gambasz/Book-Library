import javafx.beans.property.SimpleStringProperty;

public class Course {


    private SimpleStringProperty Semester,title,resource,intstutor;
    public Course() {
    Semester=new SimpleStringProperty("Needs to be assigned");
    title =  new SimpleStringProperty("Needs to be assigned");
    resource = new SimpleStringProperty("Needs to be assigned");
    intstutor = new SimpleStringProperty("Needs to be assigned");
    }
    public Course(String semester, String title, String resource, String intstutor) {
        Semester =new SimpleStringProperty( semester);
        this.title =new SimpleStringProperty( title);
        this.resource =new SimpleStringProperty( resource);
        this.intstutor =new SimpleStringProperty( intstutor);
    }

    public String getSemester() {
        return Semester.get();
    }

    public SimpleStringProperty semesterProperty() {
        return Semester;
    }

    public void setSemester(String semester) {
        this.Semester.set(semester);
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

    public String getResource() {
        return resource.get();
    }

    public SimpleStringProperty resourceProperty() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource.set(resource);
    }

    public String getIntstutor() {
        return intstutor.get();
    }

    public SimpleStringProperty intstutorProperty() {
        return intstutor;
    }

    public void setIntstutor(String intstutor) {
        this.intstutor.set(intstutor);
    }
}
