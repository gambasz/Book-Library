package Models.backend;

public class Semester {
    private String season;
    private int year, id;

    public Semester(){
        this.season = "FALL";
        this.year = 2018;

    }
    public Semester(String season, int  year){
        this.season = season.toUpperCase();
        this.year = year;

    }

    public Semester(String season, int  year, int id){
        this.season = season;
        this.year = year;
        this.id= id;
    }

    public int  getYear() {
        return year;
    }

    public void setYear(int  year) {
        this.year = year;
    }

    public String getSeason() {

        return season;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSeason(String season) {
        this.season = season;
    }

}
