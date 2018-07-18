package com.mbox;

public class Semester {
    String season;
    int year, id;

    Semester(){
        this.season = "FALL";
        this.year = 2018;

    }
    Semester(String season, int  year){
        this.season = season.toUpperCase();
        this.year = year;

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
