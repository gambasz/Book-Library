package frontend.data;

public enum Semester {
    FALL("FALL"), SUMMER("SUMMER"), SPRING("SPRING"), WINTER("WINTER");

    Semester(String semester) {
        this.semester = semester.toUpperCase();
    }

    private String semester;

    public String semester() {
        return semester;
    }

    @Override
    public String toString() {
        return semester;
    }
}
