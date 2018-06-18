package data;

public enum PersonType {
    ProgramCoordinator("Program Coordinator"), CourseCoordinator("Course Coordinator"), CourseInstructor("Course Instructor");

    PersonType(String s) {
        this.type = s;

    }

    private String type;

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return this.name();
    }
}