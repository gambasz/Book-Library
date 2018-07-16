package com.mbox.BookAPI;

import com.mbox.Publisher;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Book {

    private String title, description;
    private ArrayList<String> authors = new ArrayList<String>();
    private int id;
    private ImageView icon_de_la_book;

    private Map<String, String> isbn = new HashMap<String, String>();
    private Publisher publisherInstance;

    public Book(String title, ArrayList<String> authors, Map<String, String> isbn, String description, int id) {
        this.title = title;
        this.isbn = isbn;
        this.authors = authors;
        this.description = description;
        this.id = id;

    }
    public  Book(){


    }    public void setIcon(ImageView icon) {
        icon.setFitHeight(75);
        icon.setFitWidth(75);
        this.icon_de_la_book = icon;
    }

    public ImageView getIcon() {
        return icon_de_la_book;

    }

    public Map<String, String> getIsbn() {
        return isbn;
    }

    public void setIsbn(Map<String, String> isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<String> getAuthors() {
        return authors;
    }

    public void setAuthors(ArrayList<String> authors) {
        this.authors = authors;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Publisher getPublisherInstance() {
        return publisherInstance;
    }

    public void setPublisherInstance(Publisher publisherInstance) {
        this.publisherInstance = publisherInstance;
    }

    @Override
    public  String toString(){
        return String.format("Title: %s with ID: %d", this.title, this.id);
    }


}
