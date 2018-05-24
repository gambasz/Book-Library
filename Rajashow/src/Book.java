public class Book {
    final int ISBN;
    final String publisher, author, title;

    public Book(int ISBN) {

        this.ISBN = ISBN;
        // api query to get other
        this.publisher = this.author = this.title = null;
    }

    public Book(String title) {
        this.title = title;
        this.publisher = this.author = null;
        this.ISBN = -1;

    }

    public Book(int ISBN, String title, String author, String publisher) {
        this.ISBN = ISBN;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
    }

    public String getAuthor() {
        return this.author;
    }


    public String getTitle(String title) {
        return this.title;
    }


    public int getIsbn() {
        return this.ISBN;
    }


    public String getPublisher() {
        return this.publisher;
    }


}