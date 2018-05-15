// by ga

/*

Right now Books are used as Strings, it should probably be a list/array in order
to efficiently allow for more than one book.

*/

public class Professor {
    
    private int crn;
    private String name, classname, department, book, latest;
    
public Professor(){
    
    crn = -1;
    name = null;
    classname = null;
    department = null;
    book = null;
    latest = null;
    
}

 public Professor(int crn, String name, String classname, String department, String book, String latest) {
        this.crn = crn;
        this.name = name;
        this.classname = classname;
        this.department = department;
        this.book = book;
        this.latest = latest;
        
    }
 
 // Getter methods
 public int getCRN(){
     
     return this.crn;
 }
 
 public String getName(){
     
     return this.name;
 }
 
 public String getClassName(){
     
     return this.classname;
 }
 
 public String getDepartment(){
     
     return this.classname;
 }
 
 public String getBooks(){
     
     return this.book;
 }

 public String getLatest(){
     
     return this.latest;
 }
 
 // Setter methods
 
 public void setName(String name){
     
     this.name = name;
 }
 
 public void setClassName(String classname){
     
     this.classname = classname;
 }
 
 public void setDepartment(String department){
     
     this.department = department;
 }
 
 public void setBooks(String book){
     
     this.book = book;    
     
 }
 
 public void setLatest(String latest){
     
     this.latest = latest;
 }
 
 // toString method for class (todo)
  

}
