package Models.frontend;

public enum Semester {
    FALL("FALL"), SUMMER_1("SUMMER 1"), SUMMER_2("SUMMER 2"), SPRING("SPRING"), WINTER("WINTER");

    Semester(String semester) {
        this.semester = semester.toUpperCase();
    }

    private String semester;

    public String semester() {
        return semester;
    }

    @Override
    public String toString() {
        return semester.replace(' ', '_');
    }
}
