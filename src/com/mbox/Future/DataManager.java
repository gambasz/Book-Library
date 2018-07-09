package com.mbox.Future;

import frontend.data.Course;
import frontend.data.Person;
import frontend.data.Publisher;
import frontend.data.Resource;

import java.util.HashSet;

public class DataManager {
    private HashSet<Course> courseList;
    private HashSet<Resource> resourcesList;
    private HashSet<Person> personList;
    private HashSet<Publisher> publishersList;
    private DataBaseManager Database;

    // default constructor
    public DataManager() {
        this.courseList = new HashSet<>();
        this.resourcesList = new HashSet<>();
        this.personList = new HashSet<>();
        this.publishersList = new HashSet<>();
        this.Database = new DataBaseManager();
    }

    //direct insert
    public HashSet<Course> addCourse(Course newCourse) {
        courseList.add(newCourse);
        return courseList;
    }

    public HashSet<Resource> addResource(Resource newResource) {
        resourcesList.add(newResource);
        return resourcesList;
    }

    public HashSet<Person> addPerson(Person newPerson) {
        personList.add(newPerson);
        return personList;
    }

    public HashSet<Publisher> addPublisher(Publisher newPublisher) {
        publishersList.add(newPublisher);
        return publishersList;
    }

    //direct removal
    public HashSet<Course> removeCourse(Course toBeRemovedCourse) {
        courseList.remove(toBeRemovedCourse);
        return courseList;
    }

    public HashSet<Resource> removeResource(Resource toBeRemovedResource) {
        resourcesList.remove(toBeRemovedResource);
        return resourcesList;
    }

    public HashSet<Person> removePerson(Person toBeRemovedPerson) {
        personList.remove(toBeRemovedPerson);
        return personList;
    }

    public HashSet<Publisher> removePublisher(Publisher toBeRemovedPublisher) {
        publishersList.remove(toBeRemovedPublisher);
        return publishersList;
    }

    //getters and setters
    public HashSet<Course> getCourseList() {
        return courseList;
    }

    public void setCourseList(HashSet<Course> courseList) {
        this.courseList = courseList;
    }

    public HashSet<Resource> getResourcesList() {
        return resourcesList;
    }

    public void setResourcesList(HashSet<Resource> resourcesList) {
        this.resourcesList = resourcesList;
    }

    public HashSet<Person> getPersonList() {
        return personList;
    }

    public void setPersonList(HashSet<Person> personList) {
        this.personList = personList;
    }

    public HashSet<Publisher> getPublishersList() {
        return publishersList;
    }

    public void setPublishersList(HashSet<Publisher> publishersList) {
        this.publishersList = publishersList;
    }

}
