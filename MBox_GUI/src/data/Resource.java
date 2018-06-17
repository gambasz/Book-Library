package data;

import java.util.Objects;

public class Resource {
    private String TYPE, title, author, description;
    private boolean mcUnique;
    private int totalAmount, ID, currentAmount;
    private Publisher publisher;
    public Resource(String TYPE, int ID) {
        this.TYPE = TYPE;
        this.ID = ID;
    }

    public Resource(String TYPE, int ID, String title, Publisher publisher, String author, String description, boolean mcUnique) {
        this.TYPE = TYPE;
        this.ID = ID;
        this.title = title;
        this.publisher = publisher;
        this.author = author;
        this.description = description;
        this.mcUnique = mcUnique;
    }

    public Resource(String TYPE, int ID, String title, String author, String description, boolean mcUnique, int ammount, Publisher publisher) {
        this.TYPE = TYPE;
        this.ID = ID;
        this.title = title;
        this.author = author;
        this.description = description;
        this.mcUnique = mcUnique;
        this.totalAmount = ammount;
        this.publisher = publisher;
    }

    public int getCurrentAmount() {
        return currentAmount;
    }

    public void setCurrentAmount(int currentAmount) {
        this.currentAmount = currentAmount;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getTYPE() {
        return TYPE;
    }

    public int getID() {
        return ID;
    }

    public void setTYPE(String TYPE) {
        this.TYPE = TYPE;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
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

    @Override
    public String toString() {
        return TYPE.concat(" : ").concat(title).concat(" by ").concat(author);

    }
}

