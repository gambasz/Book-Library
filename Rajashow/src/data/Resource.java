package data;

import java.util.Objects;

public class Resource {
    private final String TYPE, ID;
    private String name, publisher, author, description;
    private boolean mcUnique;


    public Resource(String TYPE, String ID) {
        this.TYPE = TYPE;
        this.ID = ID;
    }

    public Resource(String TYPE, String ID, String name, String publisher, String author, String description, boolean mcUnique) {
        this.TYPE = TYPE;
        this.ID = ID;
        this.name = name;
        this.publisher = publisher;
        this.author = author;
        this.description = description;
        this.mcUnique = mcUnique;
    }

    public String getTYPE() {
        return TYPE;
    }

    public String getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isMcUnique() {
        return mcUnique;
    }

    public void setMcUnique(boolean mcUnique) {
        this.mcUnique = mcUnique;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resource resource = (Resource) o;
        return Objects.equals(TYPE, resource.TYPE) &&
                Objects.equals(ID, resource.ID);
    }

    @Override
    public int hashCode() {

        return Objects.hash(TYPE, ID);
    }


}
