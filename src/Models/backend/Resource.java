package Models.backend;


public class Resource {

    private String type, title, author, isbn10, description, isbn13, edition;
    private int total_amount, current_amount, id, commonID = 0;
    private Publisher publisherInstance;

    public Resource(){

        id = 0;
        type = "";
        title = "";
        author = "";
        isbn10 = "";
        total_amount = 0;
        current_amount = 0;
        description = "";
        publisherInstance = new Publisher("Title", "Contact Information", "Description");
        isbn13 = "";


    }





    public Resource(int id, String type, String title, String author, String isbn, int total, int current,
                    String desc){


        this.id = id;
        this.type = type;
        this.title = title;
        this.author = author;
        this.isbn10 = isbn;
        this.total_amount = total;
        this.current_amount = current;
        this.description = desc;
        //this.publisherInstance = publisherInst;

    }

    public Resource(int id, String type, String title, String author, String isbn, int total, int current,
                    String desc, Publisher publisherInst){

        this.id = id;
        this.type = type;
        this.title = title;
        this.author = author;
        this.isbn10 = isbn;
        this.total_amount = total;
        this.current_amount = current;
        this.description = desc;
        this.publisherInstance = publisherInst;

    }

    public Models.frontend.Resource initResourceGUI() {


        Models.frontend.Resource tmp = new Models.frontend.Resource(this.isbn10, this.type, this.title, this.author,
                this.description, true, this.total_amount, this.id, this.current_amount);

        if (publisherInstance !=null){
            tmp.setPublisher(this.publisherInstance.initPublisherGUI());
        }
        if(isbn13!= null && isbn13!="" && !isbn13.isEmpty()){
            System.out.println("ISBN13: "+ isbn13 +" Resource Title: " + this.title);
            tmp.setISBN13(isbn13);}
            tmp.setEdition(this.edition);
        return tmp;
    }



    public void setCommonID(int commonID) {
        this.commonID = commonID;
    }

    public void setID(int id){

        this.id = id;
    }

    public void setType(String ty){

        this.type = ty;

    }

    public void setTitle(String ti){

        this.title = ti;

    }

    public void setAuthor(String au){

        this.author = au;

    }

    public void setISBN(String is){

        this.isbn10 = is;

    }

    public void setTotalAmount(int total){

        this.total_amount = total;
    }

    public void setCurrentAmount(int current){

        this.current_amount = current;
    }

    public void setDescription(String desc){

        this.description = desc;
    }

    public void setPublisherInstance(Publisher inst){

        this.publisherInstance = inst;
    }
    public void setEdition(String e){
        this.edition = e;
    }

    public int getID(){

        return this.id;
    }
    public String getEdition(){
        return this.edition;
    }

    public String getType(){

        return this.type;

    }

    public String getTitle(){

        return this.title;

    }

    public String getAuthor(){

        return this.author;
    }

    public String getISBN(){

        return this.isbn10;
    }

    public int getTotalAmount(){

        return this.total_amount;
    }

    public int getCurrentAmount(){

        return this.current_amount;
    }

    public String getDescription(){

        return this.description;
    }

    public Publisher getPublisherInstance(){

        return this.publisherInstance;
    }

    public int getCommonID() {

        return commonID;
    }

    public String getIsbn13() {
        return isbn13;
    }

    public void setIsbn13(String isbn13) {
        this.isbn13 = isbn13;
    }

    @Override
    public String toString(){

        return "ID: " + this.id + " "
        +"Type: " + this.type + " "
        + "Title: " + this.title + " "
        + "Author: " + this.author + " "
        + "ISBN: " + this.isbn10 + " "
        + "Total Amount: " + String.valueOf(this.total_amount) + " "
        + "Current Amount: " + String.valueOf(this.current_amount) + " "
        + "Description: " + this.description;
    }


}
