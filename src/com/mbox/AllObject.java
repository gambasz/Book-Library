package com.mbox;

public class AllObject {
    private  Person person;
    private  Course course;
    private Resource[] resource;
    private int count=0;



    public AllObject(Person p, Course c,Resource r){
        person = p;
        course = c;
        resource[count] = r;
        count++;
    }

    //setter
    public void setPObject(Person p){
        person = p;
    }
    public void setCObject(Course c){
        course = c;
    }
    public void setRObject(Resource r){
        resource[count] = r;
        count++;
    }


    //getter
    public Person getPObject(){
        return person;
    }
    public Course getCObject(){
        return course;
    }
    public Resource getRObject(int count){
        return resource[count];
    }

}
